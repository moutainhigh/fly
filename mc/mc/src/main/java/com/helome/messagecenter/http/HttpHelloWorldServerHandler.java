/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.helome.messagecenter.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.MemCachedUtil;

public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {
    private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
           
            String uri = req.getUri();
            System.out.println("Uri:" + uri);
            List<Long> result = new ArrayList<Long>();
            Response res = null;
            if(uri.startsWith("/getonline?id=")){//查询是否在线
            	//MemCachedUtil.setOnLine(id, "1");
            	String id=uri.substring(uri.indexOf("=")+1);
            	Object obj = MemCachedUtil.getInstance().get(id);
            	if(obj!=null){
            		 if(obj.toString().equals("1")){
            			 res=new Response(1, 1, " is online !");
            		 }else{
            			 res=new Response(0, 0, " is not online !");
            		 }
            		
            	}else{
            		res=new Response(0, 0, " is not online !");
            	}
            	
            	 
            	
            }else  if(uri.startsWith("/getonlines")){//获取所有在线
            	
            	for(Entry<Long, String> e: Context.ID_TO_TOKEN.entrySet() ){
                    System.out.println("键:"+e.getKey()+", 值:"+e.getValue());
                    result.add(e.getKey());
                }
            	  res=new Response(1, result, "online users!");
            }
          
           
            
            if (is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            boolean keepAlive = isKeepAlive(req);
           // FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
           FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(JsonUtils.stringify(res).getBytes()));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set("Access-Control-Allow-Origin", "*");

            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
