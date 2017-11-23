package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.core.SocketEndpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;

public class LeaveChatMessage implements Message {

	public static final short CODE = 108;
	private final static Logger logger = LoggerFactory.getLogger(LeaveChatMessage.class);

    private Long senderId;

    private Long receiverId;

	private int length;
	
	private Long date;

	private Endpoint endpoint;

	public LeaveChatMessage() {
		super();
	}

	public LeaveChatMessage(Long senderId, Long receiverId) {
		this.senderId = senderId;
        this.receiverId = receiverId;
		this.date = new Date().getTime();
		this.length = 2+8+8+6;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	@Override
	public short getCode() {
		return LeaveChatMessage.CODE;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public void setEndpoint(Endpoint Endpoint) {
		this.endpoint = Endpoint;
	}

    public Long getReceiverId() {
        return receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    @Override
	public void onReceived() {
    	logger.debug("收到离开聊天界面消息senderId {}, receiverId{}",senderId,receiverId);
		List<Endpoint> fromEndpoints = Context.getEndpoints(senderId);
		if (fromEndpoints != null) {
			synchronized(fromEndpoints){
				for (Endpoint toEndpoint : fromEndpoints) {
					if(toEndpoint != endpoint){
						if (toEndpoint instanceof SocketEndpoint) {
							toEndpoint.getContext().channel().writeAndFlush(toBinary());
						} else {
							toEndpoint.getContext().channel()
									.writeAndFlush(new TextWebSocketFrame(toJson()));
						}
					}
				}
			}
		}
        List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
        if (toEndpoints != null) {
            synchronized(toEndpoints){
                for (Endpoint toEndpoint : toEndpoints) {
                    if(toEndpoint != endpoint){
                        if (toEndpoint instanceof SocketEndpoint) {
                            toEndpoint.getContext().channel().writeAndFlush(toBinary());
                        } else {
                            toEndpoint.getContext().channel()
                                    .writeAndFlush(new TextWebSocketFrame(toJson()));
                        }
                    }
                }
            }
        }
	}

	@Override
	public ByteBuf toBinary() {
		byte[] dateByte = Utils.unsigned48ToBytes(date);
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		buffer.writeLong(senderId);
		buffer.writeLong(receiverId);
		buffer.writeBytes(dateByte);
		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
        map.put("receiverId", receiverId);
		map.put("date", date);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("LeaveChatMessage 消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId=buffer.readLong();
		receiverId=buffer.readLong();
		byte[] bytes = new byte[6];
		buffer.readBytes(bytes);
		date = Utils.from48Unsigned(bytes);
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
        senderId = map.get("senderId").asLong();
        receiverId = map.get("receiverId").asLong();
        date = map.get("date").asLong();
        this.length = 24;
	}

}
