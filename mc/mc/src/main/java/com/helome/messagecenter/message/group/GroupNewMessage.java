package com.helome.messagecenter.message.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.Map;

/**
 * 创建(翻译)群的消息
 *
 * User: Rocs Zhang
 */
public class GroupNewMessage implements Message {

    private final static Logger log = LoggerFactory.getLogger(GroupNewMessage.class);
    public static final short CODE = 301;

    private Long groupId;

    private Long masterId;

    private String groupName;

    private Endpoint endpoint;

    private int length;

    public Long getMasterId() {
        return masterId;
    }

    public String getGroupName() {
        return groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public short getCode() {
        return CODE;
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
        log.info("收到来自{}的创建(翻译)群{}消息",masterId, CODE);
        Message msg = null;
        if(GroupManager.createGroup(groupId, masterId, groupName) == 1){
                msg = MessageFactory.createSuccessFutureMessage();
        }else{
               msg = MessageFactory.createFutureMessage(FutureMessage.STATE_FAIL_OTHER);
        }
        msg.setEndpoint(endpoint);
        if (endpoint instanceof SocketEndpoint) {
            endpoint.getContext().channel().writeAndFlush(msg.toBinary());
        } else {
            endpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(msg.toJson()));
        }
    }

    @Override
    public ByteBuf toBinary() {
        ByteBuf buffer = MessageFactory.createByteBuf(this,endpoint.getContext());
        buffer.writeLong(masterId);
        buffer.writeLong(groupId);
        byte[] groupNameBytes = groupName.getBytes(CharsetUtil.UTF_8);
        buffer.writeByte(groupNameBytes.length);
        buffer.writeBytes(groupNameBytes);
        return buffer;
    }

    @Override
    public void fromBinary(ByteBuf buffer) {
        masterId = buffer.readLong();
        groupId = buffer.readLong();
        short groupNameLen = buffer.readUnsignedByte();
        byte[] groupNameBytes = new byte[groupNameLen];
        buffer.readBytes(groupNameBytes);
        groupName = new String(groupNameBytes, CharsetUtil.UTF_8);
    }

    @Override
    public String toJson() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", CODE);
        map.put("masterId", masterId);
        map.put("groupId", groupId);
        map.put("groupName", groupName);
        String str = null;
        try {
            str = JsonUtils.stringify(map);
        } catch (JsonProcessingException e) {
            log.error("创建(翻译)群消息{}异常：",CODE ,e);
        }
        return str;
    }

    @Override
    public void fromMap(Map<String, JsonNode> map) {
        masterId = map.get("masterId").asLong();
        groupId = map.get("groupId").asLong();
        groupName = map.get("groupName").asText();
        length = 19 + Utils.getUTF8StringLength(groupName);
    }
}
