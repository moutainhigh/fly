package com.helome.messagecenter.core;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.HeartbeatMessage;

/**
 * 
 * @package com.helome.messagecenter.core
 * @description 心跳处理器
 */
public class WebHeartbeatHandler extends ChannelDuplexHandler {
	private final static Logger logger = LoggerFactory
			.getLogger(WebHeartbeatHandler.class);

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		Endpoint endpoint = new Endpoint(ctx);
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			Message message = new HeartbeatMessage();// 心跳包
			message.setEndpoint(endpoint);
			if (e.state() == IdleState.READER_IDLE) {
				logger.info("超时未收来之{}的心跳包自动，暂时不断开连接",ctx.channel().remoteAddress());
				//ctx.close();
			} else if (e.state() == IdleState.WRITER_IDLE) {
				logger.info("向{}发到心跳包自动",ctx.channel().remoteAddress());
				ctx.channel().writeAndFlush(
						new TextWebSocketFrame(message.toJson()));
			}
		}

	}

}
