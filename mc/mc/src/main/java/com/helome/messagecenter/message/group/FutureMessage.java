package com.helome.messagecenter.message.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**  (翻译)群管理相关的返回结果消息
 *
 * User: Rocs Zhang
 */
public class FutureMessage implements Message {

    private static final Logger log = LoggerFactory.getLogger(FutureMessage.class);
    public static final short CODE = 300;

    private int state;
    private Endpoint endpoint;
    private int length;

    public static final short STATE_SUCCESS = 0;       /** 成功 **/
    public static final short STATE_FAIL_PARAMS = 1;   /** 参数错误 **/
    public static final short STATE_FAIL_OTHER = 2;    /** 其它错误**/

    public FutureMessage() {
        super();
    }
    public FutureMessage(short state) {
        this.state = state;
        this.length = 4;
    }

    public int getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ByteBuf toBinary() {
        ByteBuf buf = MessageFactory.createByteBuf(this, this.endpoint.getContext());
        buf.writeShort(state);
        return buf;
    }

    @Override
    public void fromBinary(ByteBuf buffer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toJson() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", CODE);
        map.put("state", state);
        String str = null;
        try {
            str = JsonUtils.stringify(map);
        } catch (JsonProcessingException e) {
            log.error("返回不正常的{}消息内容", CODE, e);
        }
        return str;
    }

    @Override
    public void fromMap(Map<String, JsonNode> map) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
