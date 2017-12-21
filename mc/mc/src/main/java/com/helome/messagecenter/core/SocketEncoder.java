package com.helome.messagecenter.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.helome.messagecenter.message.Message;

public class SocketEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message message,
			ByteBuf buf) throws Exception {
		buf.writeBytes(message.toBinary());
	}

}
