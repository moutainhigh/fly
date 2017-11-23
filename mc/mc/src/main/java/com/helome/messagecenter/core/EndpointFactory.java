package com.helome.messagecenter.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

public class EndpointFactory {

	public static ConcurrentHashMap<Channel, Endpoint> ENDPOINTS = new ConcurrentHashMap<Channel, Endpoint>();

	public static Endpoint createSocketEndpoint(ChannelHandlerContext ctx) {
		if (!ENDPOINTS.containsKey(ctx.channel())) {
			ENDPOINTS.put(ctx.channel(), new SocketEndpoint(ctx));
		}
		return ENDPOINTS.get(ctx.channel());
	}

	public static Endpoint createWebSocketEndpoint(ChannelHandlerContext ctx) {
		if (!ENDPOINTS.containsKey(ctx.channel())) {
			ENDPOINTS.put(ctx.channel(), new WebSocketEndpoint(ctx));
		}
		return ENDPOINTS.get(ctx.channel());
	}
	
	public static Endpoint createWebRTCEndpoint(ChannelHandlerContext ctx) {
		if (!ENDPOINTS.containsKey(ctx.channel())) {
			ENDPOINTS.put(ctx.channel(), new WebRTCEndpoint(ctx));
		}
		return ENDPOINTS.get(ctx.channel());
	}

	public static Endpoint removeEndpoint(ChannelHandlerContext ctx) {
		return ENDPOINTS.remove(ctx.channel());
	}
}
