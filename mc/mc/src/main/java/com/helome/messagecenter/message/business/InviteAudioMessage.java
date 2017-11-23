package com.helome.messagecenter.message.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.*;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 邀请对方音频会话消息
 * @author beyond.zhang
 */
public class InviteAudioMessage implements Message, Timeout {

	public static final short CODE = 220;
	private final static Logger logger = LoggerFactory.getLogger(InviteAudioMessage.class);

	private Long userId;

	private String userName;

	private Long inviteeId;

	private String inviteeName;

	private long sessionId;

	private Long date;

	private Endpoint endpoint;

	private int length;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getinviteeId() {
		return inviteeId;
	}

	public void setinviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}

	public String getinviteeName() {
		return inviteeName;
	}

	public void setinviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}


	public Long getInviteeId() {
		return inviteeId;
	}

	public void setInviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}

	public String getInviteeName() {
		return inviteeName;
	}

	public void setInviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public short getCode() {
		return CODE;
	}

	@Override
	public void onReceived() {
        if(Context.existInVA(userId, inviteeId)){
            logger.debug("被邀请者{}已经在视/音频中", inviteeId);
            List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
            List<Endpoint> toEndpoints = Context.getEndpoints(inviteeId);
            if (fromEndpoints == null ) {
                Utils.close(endpoint.getContext());
            } else {
                try {
                    logger.info("移除用户{}在TIMEOUT_MANAGER的记录", inviteeId);
                    Context.TIMEOUT_MANAGER.deregister(Context.ID_TO_SLOT.remove(inviteeId));
                } catch (Exception e) {
                    logger.error("收到接受音频聊天消息异常：",e);
                }
                /**
                 * 对方同时正在跟自己音/视频会话中,将直接进入音频通话
                 */
                AcceptAudioMessage aaMsg = new AcceptAudioMessage();
                aaMsg.setUserId(getUserId());
                aaMsg.setUserName(getUserName());
                aaMsg.setInviterId(getInviteeId());
                aaMsg.setInviterName(getInviteeName());
                aaMsg.setDate(getDate());
                aaMsg.setSessionId(getSessionId());
                synchronized(fromEndpoints){
                    for(Endpoint toEndpoint : fromEndpoints){
                        if (toEndpoint instanceof SocketEndpoint) {
                            toEndpoint.getContext().channel().writeAndFlush(aaMsg.toBinary());
                        } else {
                            toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(aaMsg.toJson()));
                        }
                    }
                }
                synchronized(toEndpoints){
                    for(Endpoint toEndpoint : toEndpoints){
                        if (toEndpoint instanceof SocketEndpoint) {
                            toEndpoint.getContext().channel().writeAndFlush(aaMsg.toBinary());
                        } else {
                            toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(aaMsg.toJson()));
                        }
                    }
                }
            }
        }else if (Context.existInVA(userId)) {
			logger.info("邀请者{}自己在视频或者音频中",userId);
			List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
			if (fromEndpoints != null) {
				synchronized(fromEndpoints){
					for (Endpoint e : fromEndpoints) {
						Message m = MessageFactory.createMeNotAvailable(userId,userName,inviteeId,
								inviteeName);
						m.setEndpoint(e);
						if (e instanceof SocketEndpoint) {
							e.getContext().channel().writeAndFlush(m.toBinary());
						} else {
							e.getContext()
									.channel()
									.writeAndFlush(
											new TextWebSocketFrame(m.toJson()));
						}
					}
				}
			}
		}else if(Context.existInVA(inviteeId)){
			logger.info("被邀请者{}在视频或者音频中",inviteeId);
			List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
			if (fromEndpoints != null) {
				synchronized(fromEndpoints){
					for (Endpoint e : fromEndpoints) {
						Message m = MessageFactory.createYouNotAvailable(userId,userName,inviteeId,
								inviteeName);
						m.setEndpoint(e);
						if (e instanceof SocketEndpoint) {
							e.getContext().channel().writeAndFlush(m.toBinary());
						} else {
							e.getContext()
									.channel()
									.writeAndFlush(
											new TextWebSocketFrame(m.toJson()));
						}
					}
				}
			}
		}else{
			List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
			List<Endpoint> toEndpoints = Context.getEndpoints(inviteeId);
			if (fromEndpoints == null ) {
				Utils.close(endpoint.getContext());
			} else {
				if(Context.registerVA(userId,inviteeId)){
					logger.info("邀请者{}与被邀请者{}注册在音频中",userId,inviteeId);
					Context.ID_TO_SLOT.put(userId,Context.TIMEOUT_MANAGER.register(this));
					if (toEndpoints != null) {
						synchronized(toEndpoints){
							for (Endpoint toEndpoint : toEndpoints) {
								if (toEndpoint instanceof SocketEndpoint) {
									toEndpoint.getContext().channel()
											.writeAndFlush(toBinary());
								} else {
									toEndpoint
											.getContext()
											.channel()
											.writeAndFlush(new TextWebSocketFrame(toJson()));
								}
							}
						}
					}
				}else{
					logger.info("邀请者{}与被邀请者{}注册在音频中失败",userId,inviteeId);
					List<Endpoint> froms = Context.getEndpoints(userId);
					if (froms != null) {
						synchronized(froms){
							for (Endpoint e : froms) {
								Message m = MessageFactory.createYouNotAvailable(userId,userName,inviteeId,
										inviteeName);
								m.setEndpoint(e);
								if (e instanceof SocketEndpoint) {
									e.getContext().channel().writeAndFlush(m.toBinary());
								} else {
									e.getContext()
											.channel()
											.writeAndFlush(
													new TextWebSocketFrame(m.toJson()));
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		byte[] dateByte = Utils.unsigned48ToBytes(date);
		byte[] userNameBytes = userName.getBytes(CharsetUtil.UTF_8);
		byte[] inviteeNameBytes = inviteeName.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(userId);
		buffer.writeByte(userNameBytes.length);
		buffer.writeBytes(userNameBytes);
		buffer.writeLong(inviteeId);
		buffer.writeByte(inviteeNameBytes.length);
		buffer.writeBytes(inviteeNameBytes);
		buffer.writeBytes(dateByte);
		buffer.writeInt(new Long(sessionId).intValue());
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		userId = buffer.readLong();
		short userNameLen = buffer.readUnsignedByte();
		byte[] userNameBytes = new byte[userNameLen];
		buffer.readBytes(userNameBytes);
		userName = new String(userNameBytes, CharsetUtil.UTF_8);
		inviteeId = buffer.readLong();
		short inviteeNameLen = buffer.readUnsignedByte();
		byte[] inviteeNameBytes = new byte[inviteeNameLen];
		buffer.readBytes(inviteeNameBytes);
		inviteeName = new String(inviteeNameBytes, CharsetUtil.UTF_8);
		byte[] bytes = new byte[6];
		buffer.readBytes(bytes);
	//	date = Utils.from48Unsigned(bytes);
		date = new Date().getTime();
		sessionId = buffer.readUnsignedInt();

	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("userId", userId);
		map.put("inviteeId", inviteeId);
		map.put("date", date);
		map.put("userName", userName);
		map.put("inviteeName", inviteeName);
		map.put("sessionId", sessionId);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("邀请对方音频会话消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		userId = map.get("userId").asLong();
		inviteeId = map.get("inviteeId").asLong();
		date = map.get("date").asLong();
		userName = map.get("userName").asText();
		inviteeName = map.get("inviteeName").asText();
		sessionId = map.get("sessionId").intValue();
		this.length = 2 + 8 + 8 + 6 + 4 + 2 * 1
				+ Utils.getUTF8StringLength(userName)
				+ Utils.getUTF8StringLength(inviteeName);
	}

	@Override
	public void onTimeout() {
		Context.deregisterVA(userId,inviteeId);
		List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
		if (fromEndpoints != null) {
			for (Endpoint e : fromEndpoints) {
				Message m = MessageFactory.createAudioTimeoutMessage(userId, userName, inviteeId, inviteeName);
				m.setEndpoint(e);
				if (e instanceof SocketEndpoint) {
					e.getContext().channel().writeAndFlush(m.toBinary());
				} else {
					e.getContext()
							.channel()
							.writeAndFlush(
									new TextWebSocketFrame(m.toJson()));
				}
			}
		}
	}
	
}
