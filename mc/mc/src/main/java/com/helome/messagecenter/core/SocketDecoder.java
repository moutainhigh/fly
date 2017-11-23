package com.helome.messagecenter.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.Utils;

public class SocketDecoder extends ByteToMessageDecoder {
	private final static Logger logger = LoggerFactory.getLogger(SocketDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> result) throws Exception {
		Message message = null;
		try {
			message = MessageFactory.create(buf);
		} catch (UnsupportedMessageTypeException e) {
			logger.error("二进制消息解码出错,关闭连接",e);
			Utils.close(ctx);
			return;
		}
		if (message != null) {
			message.setEndpoint(EndpointFactory.createSocketEndpoint(ctx));
			result.add(message);
		}
	}

}
