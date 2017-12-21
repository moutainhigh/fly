package com.helome.messagecenter.message.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.core.SocketEndpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预判断用户是否在音/视频中消息
 *          (作废, 被方案 等待正式接入音/视频通讯消息(280) 所替换)
 *
 * User: Rocs Zhang
 */
@Deprecated
public class ExistInVAMessage implements Message {

    public static final short CODE = 279;
    private final static Logger logger = LoggerFactory.getLogger(ExistInVAMessage.class);

    private Long userId;

    private String userName;

    private Long inviteeId;

    private String inviteeName;

    private short tag;

    private Endpoint endpoint;

    private int length;

    public static final short TAG_IN_VIDEO = 0;
    public static final short TAG_IN_AUDIO = 1;

    @Override
    public short getCode() {
        return ExistInVAMessage.CODE;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void onReceived() {
        if (Context.existInVA(userId)) {
            logger.info("邀请者{}自己在视频或者音频中", userId);
            List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
            if (fromEndpoints != null) {
                synchronized (fromEndpoints) {
                    for (Endpoint e : fromEndpoints) {
                        Message m = MessageFactory.createMeNotAvailable(userId, userName, inviteeId, inviteeName);
                        m.setEndpoint(e);
                        if (e instanceof SocketEndpoint) {
                            e.getContext().channel().writeAndFlush(m.toBinary());
                        } else {
                            e.getContext().channel().writeAndFlush(new TextWebSocketFrame(m.toJson()));
                        }
                    }
                }
            }
        } else if (Context.existInVA(inviteeId)) {
            logger.info("被邀请者{}在视频或者音频中", inviteeId);
            List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
            if (fromEndpoints != null) {
                synchronized (fromEndpoints) {
                    for (Endpoint e : fromEndpoints) {
                        Message m = MessageFactory.createYouNotAvailable(userId, userName, inviteeId, inviteeName);
                        m.setEndpoint(e);
                        if (e instanceof SocketEndpoint) {
                            e.getContext().channel().writeAndFlush(m.toBinary());
                        } else {
                            e.getContext().channel().writeAndFlush(new TextWebSocketFrame(m.toJson()));
                        }
                    }
                }
            }
        } else {
            List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
            if (fromEndpoints == null) {
                Utils.close(endpoint.getContext());
            } else {
                logger.info("邀请者{}与被邀请者{}皆未注册在视频或者音频中", userId, inviteeId);
                synchronized (fromEndpoints) {
                    for (Endpoint toEndpoint : fromEndpoints) {
                        if (toEndpoint instanceof SocketEndpoint) {
                            toEndpoint.getContext().channel() .writeAndFlush(toBinary());
                        } else {
                            toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(toJson()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public ByteBuf toBinary() {
        ByteBuf buffer = MessageFactory.createByteBuf(this, endpoint.getContext());
        byte[] userNameBytes = userName.getBytes(CharsetUtil.UTF_8);
        byte[] inviteeNameBytes = inviteeName.getBytes(CharsetUtil.UTF_8);
        buffer.writeLong(userId);
        buffer.writeByte(userNameBytes.length);
        buffer.writeBytes(userNameBytes);
        buffer.writeLong(inviteeId);
        buffer.writeByte(inviteeNameBytes.length);
        buffer.writeBytes(inviteeNameBytes);
        buffer.writeByte(tag);
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
        short inviterNameLen = buffer.readUnsignedByte();
        byte[] inviterNameBytes = new byte[inviterNameLen];
        buffer.readBytes(inviterNameBytes);
        inviteeName = new String(inviterNameBytes, CharsetUtil.UTF_8);
        tag = buffer.readUnsignedByte();
    }

    @Override
    public String toJson() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ExistInVAMessage.CODE);
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("inviteeId", inviteeId);
        map.put("inviteeName", inviteeName);
        map.put("tag", tag);
        String str = null;
        try {
            str = JsonUtils.stringify(map);
        } catch (JsonProcessingException e) {
            logger.error("预判断用户是否在音/视频中消息{}异常:" ,ExistInVAMessage.CODE, e);
        }
        return str;
    }

    @Override
    public void fromMap(Map<String, JsonNode> map) {
        userId = map.get("userId").asLong();
        inviteeId = map.get("inviteeId").asLong();
        userName = map.get("userName").asText();
        inviteeName = map.get("inviteeName").asText();
        tag = (short)map.get("tag").asInt();
        this.length = 2 + 8 + 8 + 2*1 + 1 + Utils.getUTF8StringLength(userName) + Utils.getUTF8StringLength(inviteeName);
    }
}
