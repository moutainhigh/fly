package com.helome.messagecenter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.Utils;

public class SocketServerHandler extends SimpleChannelInboundHandler<Message> {
	private final static Logger logger = LoggerFactory.getLogger(SocketServer.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message)
			throws Exception {
		message.onReceived();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("{} inactive,from {}",ctx,ctx.channel());
		Context.deregisterChannelHandlerContext(ctx);
		ctx.fireChannelInactive();
		Utils.close(ctx);
	}
	
}
