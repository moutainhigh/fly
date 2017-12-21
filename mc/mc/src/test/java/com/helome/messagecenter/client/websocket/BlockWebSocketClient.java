package com.helome.messagecenter.client.websocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.helome.messagecenter.client.utils.CommonsUtils;

public class BlockWebSocketClient {

    private final URI uri;
    private final List<String> values;
     private  EventLoopGroup group;
     private  Channel ch=null;

    public BlockWebSocketClient(URI uri,List<String> values) {
        this.uri = uri;
        this.values = values;
        group = new NioEventLoopGroup();
    }

    public void run() throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            String protocol = uri.getScheme();
            if (!"ws".equals(protocol)) {
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
            }

            HttpHeaders customHeaders = new DefaultHttpHeaders();
            customHeaders.add("MyHeader", "MyValue");

            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, false, customHeaders));

            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline pipeline = ch.pipeline();
                     pipeline.addLast("http-codec", new HttpClientCodec());
                     pipeline.addLast("aggregator", new HttpObjectAggregator(8192));
                     pipeline.addLast("ws-handler", handler);
                 }
             });

            System.out.println("WebSocket Client connecting");
             ch = b.connect(uri.getHost(), uri.getPort()).sync().channel();
            handler.handshakeFuture().sync();

            // Send 10 messages and wait for responses
            System.out.println("WebSocket Client sending message");
            for(String value:values){
                ch.writeAndFlush(new TextWebSocketFrame(value));
                Thread.sleep(2000);
            }
            //"{\"code\":1,\"id\":2188,\"token\":\"good\",\"type\":1}"
            // Ping
            System.out.println("WebSocket Client sending ping");
            ch.writeAndFlush(new PingWebSocketFrame(Unpooled.copiedBuffer(new byte[]{1, 2, 3, 4, 5, 6})));

            // ch.closeFuture().sync();//  此处不注释就一直连接起
        } finally {
            // group.shutdownGracefully();
        }
    }
 
    public void shutdown() throws InterruptedException{
    	group.shutdownGracefully();
    }
    public static void main(String[] args) throws Exception {
        URI uri = new URI(CommonsUtils.getAddress());
        List<String> values = new ArrayList<String>();
        String reportMessage = "{\"code\":-100,\"id\":2188,\"token\":\"test\",\"type\":1}";
        values.add(reportMessage);
        new BlockWebSocketClient(uri,values).run();
    }
}
