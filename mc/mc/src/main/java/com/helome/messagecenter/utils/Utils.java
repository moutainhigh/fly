package com.helome.messagecenter.utils;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private final static Logger logger = LoggerFactory
			.getLogger(Utils.class);
	
	public static byte[] signed64ToBytes(long value) {
		return toBytes(value, 8);
	}

	public static byte[] unsigned48ToBytes(long value) {
		return toBytes(value, 6);
	}

	public static byte[] unsigned32ToBytes(long value) {
		return toBytes(value, 4);
	}

	public static byte[] unsigned16ToBytes(int value) {
		return toBytes(value, 2);
	}

	public static byte[] unsigned8ToBytes(int value) {
		return toBytes(value, 1);
	}

	public static byte[] toBytes(long value, int len) {
		byte[] array = new byte[len];
		for (int i = 0; i < len; i++) {
			array[i] = (byte) (value >>> ((len - i - 1) * 8));
		}
		return array;
	}

	public static long fromBytes(byte[] bytes, int len) {
		long v = 0;
		for (int i = 0; i < len; i++) {
			v += (long) (bytes[i] & 255) << (len - i - 1) * 8;
		}
		return v;
	}

	public static short from8Unsigned(byte[] bytes) {
		return (short) fromBytes(bytes, 1);
	}

	public static int from16Unsigned(byte[] bytes) {
		return (int) fromBytes(bytes, 2);
	}

	public static long from32Unsigned(byte[] bytes) {
		return fromBytes(bytes, 4);
	}

	public static long from48Unsigned(byte[] bytes) {
		return fromBytes(bytes, 6);
	}

	public static long from64(byte[] bytes) {
		return fromBytes(bytes, 8);
	}

	public static int getUTF8StringLength(String str) {
		return str.getBytes(CharsetUtil.UTF_8).length;
	}
	

	public static void close(final ChannelHandlerContext ctx) {
		if (ctx.channel().isOpen() || ctx.channel().isActive()) {
			logger.debug("close channel {} ", ctx.channel().remoteAddress());
			ctx.close().addListener(new ChannelFutureListener() {
	
				@Override
				public void operationComplete(ChannelFuture future)
						throws Exception {
					close(ctx);
				}
			});
		}
	}
}
