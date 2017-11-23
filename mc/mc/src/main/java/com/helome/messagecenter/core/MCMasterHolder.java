package com.helome.messagecenter.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class MCMasterHolder extends Thread{
	
	private static final String host = "172.16.2.227";
	private   int port;
	private static   EventLoopGroup group;
	private static   Bootstrap b;
	private static   Channel ch;
	
	public MCMasterHolder(int port) {
		this.port = port;
	}
	public void run() {
				 try {
					group = new NioEventLoopGroup();
					 b = new Bootstrap();
					 b.group(group).channel(NioSocketChannel.class).handler(new SocketServerInitializer());
					 b.localAddress(new InetSocketAddress(9090));
					 ch = b.connect(host, port).sync().channel();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

}
