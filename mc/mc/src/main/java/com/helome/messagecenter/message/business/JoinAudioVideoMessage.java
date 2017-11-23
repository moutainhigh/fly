package com.helome.messagecenter.message.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.*;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 等待正式接入音/视频通讯消息
 *
 * User: Rocs Zhang
 */
public class JoinAudioVideoMessage implements Message,Timeout {

    public static final short CODE = 280;
    private final static Logger logger = LoggerFactory.getLogger(JoinAudioVideoMessage.class);

    private Endpoint endpoint;
    private int length;
    private long userId;
    private long inviteeId;
    private short state;

    public static final short STATE_RECEIVED = 0;       /** 成功收到 **/
    public static final short STATE_READY = 1;       /** 双方皆已收到 **/
    public static final short STATE_TIMEOUT = 2;       /** 等待对方超时 **/
    public static final short STATE_ERROR = 3;   /** 发生非常规错误 **/


    @Override
    public short getCode() {
        return JoinAudioVideoMessage.CODE;
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
        logger.debug("收到{}等待{}接入音/视频通讯消息{}", userId, inviteeId, this.getCode());
        if(Context.existInPAV(userId, inviteeId)){
            logger.info("接入者{}已接入.", inviteeId);
            try {
                Context.deregisterPAV(inviteeId, userId);
                Context.TIMEOUT_MANAGER_PAV.deregister(Context.ID_TO_SLOT_PAV.remove(userId * inviteeId));
            } catch (Exception e) {
                logger.error("在撤销双方{}->{}信息过程中有发生异常.", e);
            }
            this.state = JoinAudioVideoMessage.STATE_READY;
        }else{
            Context.deregisterPAV(userId, inviteeId);
            if(Context.registerPAV(userId, inviteeId)){
                logger.info("等待者{}开始等待...",userId);
                Context.ID_TO_SLOT_PAV.put(userId * inviteeId, Context.TIMEOUT_MANAGER_PAV.register(this));
                this.state = JoinAudioVideoMessage.STATE_RECEIVED;
            }else{
                logger.error("在注册双方{}->{}信息过程中失败", userId, inviteeId);
                this.state = JoinAudioVideoMessage.STATE_ERROR;
            }
        }
        List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
        if (fromEndpoints != null) {
            synchronized(fromEndpoints){
                for (Endpoint e : fromEndpoints) {
                    if (e instanceof SocketEndpoint) {
                        e.getContext().channel().writeAndFlush(toBinary());
                    } else {
                        e.getContext().channel().writeAndFlush(new TextWebSocketFrame(toJson()));
                    }
                }
            }
        }
    }

    @Override
    public ByteBuf toBinary() {
        ByteBuf buf = MessageFactory.createByteBuf(this, this.endpoint.getContext());
        buf.writeShort(state);
        return buf;
    }

    @Override
    public void fromBinary(ByteBuf buffer) {
        userId = buffer.readLong();
        inviteeId = buffer.readLong();
    }

    @Override
    public String toJson() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", JoinAudioVideoMessage.CODE);
        map.put("state", state);
        String str = null;
        try {
            str = JsonUtils.stringify(map);
        } catch (JsonProcessingException e) {
            logger.error("返回不正常的{}消息内容", CODE, e);
        }
        return str;
    }

    @Override
    public void fromMap(Map<String, JsonNode> map) {
        userId = map.get("userId").asLong();
        inviteeId = map.get("inviteeId").asLong();
        length = 2 + 8 + 8 + 2;
    }

    @Override
    public void onTimeout() {
        Context.deregisterPAV(userId, inviteeId);
        logger.info("{}等待{}接入音/视频通讯消息{}超时", userId, inviteeId, this.getCode());
        this.state = JoinAudioVideoMessage.STATE_TIMEOUT;
        List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
        if (fromEndpoints != null) {
            synchronized(fromEndpoints){
                for (Endpoint e : fromEndpoints) {
                    if (e instanceof SocketEndpoint) {
                        e.getContext().channel().writeAndFlush(toBinary());
                    } else {
                        e.getContext().channel().writeAndFlush(new TextWebSocketFrame(toJson()));
                    }
                }
            }
        }
    }
}
