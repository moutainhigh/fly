package com.helome.messagecenter.core;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class WebRTCEndpoint extends WebSocketEndpoint {
	private String sdp;
	private List<String> candidates;

	public WebRTCEndpoint(ChannelHandlerContext context) {
		super(context);
	}

	public String getSdp() {
		return sdp;
	}

	public void setSdp(String sdp) {
		this.sdp = sdp;
	}

	public List<String> getCandidates() {
		return candidates;
	}

	public void addCandidate(String candidate) {
		this.candidates.add(candidate);
	}

}
