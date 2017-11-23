package com.helome.messagecenter.core;

import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.*;
import com.helome.messagecenter.message.business.*;
import com.helome.messagecenter.message.webrtc.AnswerMessage;
import com.helome.messagecenter.message.webrtc.OfferMessage;
import com.helome.messagecenter.message.webrtc.SendCandidateMessage;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * User: Rocs Zhang
 */
public class MessageProxy implements MethodInterceptor {

    private static Logger Log = LoggerFactory.getLogger(MessageProxy.class);

    private Message msgTarget;
    private byte[] data;
    private Endpoint endpoint;
    private MessageProxy(Message msg){
        this.msgTarget = msg;
    }

    public static Message proxyTarget(Message message){
        Enhancer eh = new Enhancer();
        eh.setSuperclass(Message.class);
        eh.setCallback(new MessageProxy(message));
        return (Message)eh.create();

    }
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;
        if("fromBinary".equals(method.getName())){
            ByteBuf buffer = (ByteBuf) args[0];
            this.data = new byte[buffer.readableBytes()];
            buffer.markReaderIndex();
            buffer.readBytes(this.data);
            buffer.resetReaderIndex();
            switch (msgTarget.getCode()) {
                case ReportMessage.CODE: {
                    ((ReportMessage) msgTarget).setId(buffer.readLong());
                    short len = buffer.readUnsignedByte();
                    byte[] bytes = new byte[len];
                    buffer.readBytes(bytes);
                    ((ReportMessage) msgTarget).setToken(new String(bytes, CharsetUtil.UTF_8));
                }
                break;
                case TxtMessage.CODE:
                case CacheBinaryMessage.CODE:
                 {
                     try {
                         Class clazz = MessageFactory.SUPPORED_CODES.get(msgTarget.getCode());
                         Field fSenderId = clazz.getField("senderId");
                         fSenderId.setAccessible(true);
                         fSenderId.set(msgTarget, buffer.readLong());
                         short senderNameLen = buffer.readUnsignedByte();
                         buffer.skipBytes(senderNameLen);
                         Field fReceiverId = clazz.getField("receiverId");
                         fReceiverId.setAccessible(true);
                         fReceiverId.set(msgTarget, buffer.readLong());
                         short consumeNameLen = buffer.readUnsignedByte();
                         buffer.skipBytes(consumeNameLen);
                         int len = buffer.readUnsignedShort();
                         byte[] bytes = new byte[len];
                         buffer.readBytes(bytes);
                         Field fData= clazz.getField("data");
                         fData.setAccessible(true);
                         fData.set(msgTarget, new String(bytes,CharsetUtil.UTF_8));
                     } catch (Exception e) {
                         Log.error("", e);
                         throw new RuntimeException(e);
                     }
                }
                break;
                case BinaryMessage.CODE:
                case CommentMessage.CODE:
                case ConsultAcceptMessage.CODE:
                case LeaveChatMessage.CODE:
                case ChatMessage.CODE:
                case ConsultRejectMessage.CODE:
                case ReplyMessage.CODE:
                case TransferMessage.CODE:
                case RejectConsultEndMessage.CODE:
                case AcceptConsultEndMessage.CODE:
                case ConsultEndMessage.CODE:
                case ConsultTimeMessage.CODE:
                case AnswerMessage.CODE:
                case OfferMessage.CODE:
                case SendCandidateMessage.CODE:
                case DownLoadMessage.CODE:
                case HangUpMessage.CODE:
                case DontHaveCameraMessage.CODE:
                case DontHaveMicMessage.CODE:
                case MultimediaMessage.CODE:
                case MultimediaDownLoadMessage.CODE:
                case PictureMessage.CODE:
                case PictureDownLoadMessage.CODE:
                case CloseCameraMessage.CODE:
                case CloseMicMessage.CODE:
                case LackBalanceMessage.CODE:
                case LackTimeMessage.CODE:
                case OnlineMessage.CODE:
                case OpenCameraMessage.CODE:
                case OpenMicMessage.CODE: {
                    try {
                        Class clazz = MessageFactory.SUPPORED_CODES.get(msgTarget.getCode());
                        Field fSenderId = clazz.getField("senderId");
                        fSenderId.setAccessible(true);
                        fSenderId.set(msgTarget, buffer.readLong());
                        buffer.readBytes(new byte[buffer.readUnsignedByte()]);
                        Field fReceiverId = clazz.getField("receiverId");
                        fReceiverId.setAccessible(true);
                        fReceiverId.set(msgTarget, buffer.readLong());
                    } catch (Exception e) {
                        Log.error("", e);
                        throw new RuntimeException(e);
                    }
                }
                break;
                case RejectChatMessage.CODE:
                case AcceptChatMessage.CODE:
                case CreateSessionMessage.CODE:
                case NormalOfflineMessage.CODE: {
                    try {
                        Class clazz = MessageFactory.SUPPORED_CODES.get(msgTarget.getCode());
                        Field fUserId = clazz.getField("userId");
                        fUserId.setAccessible(true);
                        fUserId.set(msgTarget, buffer.readLong());
                    } catch (Exception e) {
                        Log.error("", e);
                        throw new RuntimeException(e);
                    }
                }
                break;
                case AcceptAudioMessage.CODE:
                case AcceptVideoMessage.CODE:
                case RejectVideoMessage.CODE:
                case RejectAudioMessage.CODE: {
                    try {
                        Class clazz = MessageFactory.SUPPORED_CODES.get(msgTarget.getCode());
                        Field fUserId = clazz.getDeclaredField("userId");
                        fUserId.setAccessible(true);
                        fUserId.set(msgTarget, buffer.readLong());
                        buffer.readBytes(new byte[buffer.readUnsignedByte()]);
                        Field fInviterId = clazz.getDeclaredField("inviterId");
                        fInviterId.setAccessible(true);
                        fInviterId.set(msgTarget, buffer.readLong());
                    } catch (Exception e) {
                        Log.error("", e);
                        throw new RuntimeException(e);
                    }
                }
                break;
                case AudioTimeoutMessage.CODE:
                case InviteAudioMessage.CODE:
                case InviteVideoMessage.CODE:
                case VideoTimeoutMessage.CODE: {
                    try {
                        Class clazz = MessageFactory.SUPPORED_CODES.get(msgTarget.getCode());
                        Field fUserId = clazz.getField("userId");
                        fUserId.setAccessible(true);
                        fUserId.set(msgTarget, buffer.readLong());
                        buffer.readBytes(new byte[buffer.readUnsignedByte()]);
                        Field fInviteeId = clazz.getField("inviteeId");
                        fInviteeId.setAccessible(true);
                        fInviteeId.set(msgTarget, buffer.readLong());
                    } catch (Exception e) {
                        Log.error("", e);
                        throw new RuntimeException(e);
                    }
                }
                break;
                default:
                break;
            }
        }else if("toBinary".equals(method.getName())){
            ByteBuf buffer = MessageFactory.createByteBuf(this.msgTarget, endpoint.getContext());
            buffer.writeBytes(this.data);
            return buffer;
        }else if("setEndpoint".equals(method.getName())){
            this.endpoint = (Endpoint)args[0];
            result = method.invoke(msgTarget, args);
        }else{
            result = method.invoke(msgTarget, args);
        }
        return result;
    }
}
