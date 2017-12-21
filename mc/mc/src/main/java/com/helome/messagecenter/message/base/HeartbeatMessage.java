package com.helome.messagecenter.message.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 
 * @description 心跳包
 * @author beyond.zhang   
 */
public class HeartbeatMessage implements Message{
	public static final short CODE = 2;
	private final static Logger logger = LoggerFactory.getLogger(HeartbeatMessage.class);
    public static BlockingQueue<String> testQueue = new LinkedBlockingQueue<String>();

	private int length;

	private Endpoint endpoint;
	
	public HeartbeatMessage() {
	    this.length = 2;
	}

    @Override
	public short getCode() {
		return 2;
	}

	@Override
	public int getLength() {
		return this.length;
	}

	@Override
	public void setLength(int length) {
		
	}


	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public void onReceived() {
	//	logger.debug("收到来之{}的心跳包",endpoint.getContext().channel().remoteAddress());
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this, endpoint.getContext());
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("心跳消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {

	}

	

}
