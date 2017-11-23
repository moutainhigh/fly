package com.helome.messagecenter.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.UnsupportedMessageTypeException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.AbNormalOfflineMessage;
import com.helome.messagecenter.message.base.BinaryMessage;
import com.helome.messagecenter.message.base.CacheBinaryMessage;
import com.helome.messagecenter.message.base.DownLoadMessage;
import com.helome.messagecenter.message.base.FileResponseMessage;
import com.helome.messagecenter.message.base.HeartbeatMessage;
import com.helome.messagecenter.message.base.NormalOfflineMessage;
import com.helome.messagecenter.message.base.ReportMessage;
import com.helome.messagecenter.message.base.ResponseMessage;
import com.helome.messagecenter.message.base.TxtMessage;
import com.helome.messagecenter.message.business.AcceptAudioMessage;
import com.helome.messagecenter.message.business.AcceptChatMessage;
import com.helome.messagecenter.message.business.AcceptConsultEndMessage;
import com.helome.messagecenter.message.business.AcceptVideoMessage;
import com.helome.messagecenter.message.business.AudioTimeoutMessage;
import com.helome.messagecenter.message.business.ChatMessage;
import com.helome.messagecenter.message.business.CloseCameraMessage;
import com.helome.messagecenter.message.business.CloseMicMessage;
import com.helome.messagecenter.message.business.CommentMessage;
import com.helome.messagecenter.message.business.ConsultAcceptMessage;
import com.helome.messagecenter.message.business.ConsultEndMessage;
import com.helome.messagecenter.message.business.ConsultRejectMessage;
import com.helome.messagecenter.message.business.ConsultTimeMessage;
import com.helome.messagecenter.message.business.CreateSessionFailMessage;
import com.helome.messagecenter.message.business.CreateSessionMessage;
import com.helome.messagecenter.message.business.CreateSessionSuccessMessage;
import com.helome.messagecenter.message.business.DontHaveCameraMessage;
import com.helome.messagecenter.message.business.DontHaveMicMessage;
import com.helome.messagecenter.message.business.HangUpMessage;
import com.helome.messagecenter.message.business.InviteAudioMessage;
import com.helome.messagecenter.message.business.InviteVideoMessage;
import com.helome.messagecenter.message.business.InviteVideoMessageSuccess;
import com.helome.messagecenter.message.business.JoinAudioVideoMessage;
import com.helome.messagecenter.message.business.LackBalanceMessage;
import com.helome.messagecenter.message.business.LackTimeMessage;
import com.helome.messagecenter.message.business.LeaveChatMessage;
import com.helome.messagecenter.message.business.MultimediaDownLoadMessage;
import com.helome.messagecenter.message.business.MultimediaMessage;
import com.helome.messagecenter.message.business.NotAvailable;
import com.helome.messagecenter.message.business.OnlineMessage;
import com.helome.messagecenter.message.business.OpenCameraMessage;
import com.helome.messagecenter.message.business.OpenMicMessage;
import com.helome.messagecenter.message.business.PictureDownLoadMessage;
import com.helome.messagecenter.message.business.PictureMessage;
import com.helome.messagecenter.message.business.RejectAudioMessage;
import com.helome.messagecenter.message.business.RejectChatMessage;
import com.helome.messagecenter.message.business.RejectConsultEndMessage;
import com.helome.messagecenter.message.business.RejectVideoMessage;
import com.helome.messagecenter.message.business.ReplyMessage;
import com.helome.messagecenter.message.business.TransferMessage;
import com.helome.messagecenter.message.business.UpdateNumMessage;
import com.helome.messagecenter.message.business.VideoTimeoutMessage;
import com.helome.messagecenter.message.group.AddGroupMember;
import com.helome.messagecenter.message.group.DeleteGroupMessage;
import com.helome.messagecenter.message.group.FutureMessage;
import com.helome.messagecenter.message.group.GroupNewMessage;
import com.helome.messagecenter.message.group.GroupTxtMessage;
import com.helome.messagecenter.message.group.RemoveGroupMember;
import com.helome.messagecenter.message.webrtc.AnswerMessage;
import com.helome.messagecenter.message.webrtc.OfferMessage;
import com.helome.messagecenter.message.webrtc.SendCandidateMessage;
import com.helome.messagecenter.utils.JsonUtils;

public class MessageFactory {
	public static final Map<Short, Class<?>> SUPPORED_CODES = new HashMap<Short, Class<?>>();
	private final static Logger logger = LoggerFactory.getLogger(MessageFactory.class);

	public static final byte IDENTITY = 77;
	public static final int MIN_LENGTH = 7;

	static {
		SUPPORED_CODES.put(ReportMessage.CODE, ReportMessage.class);
		SUPPORED_CODES.put(TxtMessage.CODE, TxtMessage.class);
		SUPPORED_CODES.put(ResponseMessage.CODE, ResponseMessage.class);
		SUPPORED_CODES.put(BinaryMessage.CODE, BinaryMessage.class);

		SUPPORED_CODES.put(CacheBinaryMessage.CODE, CacheBinaryMessage.class);
		SUPPORED_CODES.put(CommentMessage.CODE, CommentMessage.class);
		SUPPORED_CODES.put(ConsultAcceptMessage.CODE,
				ConsultAcceptMessage.class);
		SUPPORED_CODES.put(LeaveChatMessage.CODE, LeaveChatMessage.class);
		SUPPORED_CODES.put(ChatMessage.CODE, ChatMessage.class);
		SUPPORED_CODES.put(RejectChatMessage.CODE, RejectChatMessage.class);
		SUPPORED_CODES.put(AcceptChatMessage.CODE, AcceptChatMessage.class);
		SUPPORED_CODES.put(ConsultRejectMessage.CODE,
				ConsultRejectMessage.class);

		SUPPORED_CODES.put(ReplyMessage.CODE, ReplyMessage.class);
		/*SUPPORED_CODES.put(ReserveMessage.CODE, ReserveMessage.class);*/
		SUPPORED_CODES.put(TransferMessage.CODE, TransferMessage.class);

		SUPPORED_CODES.put(RejectConsultEndMessage.CODE,
				RejectConsultEndMessage.class);
		SUPPORED_CODES.put(AcceptConsultEndMessage.CODE,
				AcceptConsultEndMessage.class);
		SUPPORED_CODES.put(ConsultEndMessage.CODE, ConsultEndMessage.class);

		SUPPORED_CODES.put(ConsultTimeMessage.CODE, ConsultTimeMessage.class);
		/*SUPPORED_CODES.put(ConsultTimeAcpMessage.CODE,
				ConsultTimeAcpMessage.class);*/ //code和ConsultAcceptMessage相同 以后清理
		/*SUPPORED_CODES.put(ConsultTimeRejMessage.CODE,
				ConsultTimeRejMessage.class);*/ //code和ConsultTimeRejMessage相同 以后清理

		
		SUPPORED_CODES.put(UpdateNumMessage.CODE, AcceptAudioMessage.class);
		SUPPORED_CODES.put(AcceptAudioMessage.CODE, AcceptAudioMessage.class);
		SUPPORED_CODES.put(AcceptVideoMessage.CODE, AcceptVideoMessage.class);
		SUPPORED_CODES.put(RejectVideoMessage.CODE, RejectVideoMessage.class);
		SUPPORED_CODES.put(RejectAudioMessage.CODE, RejectAudioMessage.class);
		SUPPORED_CODES.put(AudioTimeoutMessage.CODE, AudioTimeoutMessage.class);
		SUPPORED_CODES.put(InviteAudioMessage.CODE, InviteAudioMessage.class);
		SUPPORED_CODES.put(InviteVideoMessage.CODE, InviteVideoMessage.class);
		SUPPORED_CODES.put(VideoTimeoutMessage.CODE, VideoTimeoutMessage.class);

		SUPPORED_CODES.put(CreateSessionFailMessage.CODE,
				CreateSessionFailMessage.class);
		SUPPORED_CODES.put(CreateSessionMessage.CODE,
				CreateSessionMessage.class);
		SUPPORED_CODES.put(CreateSessionSuccessMessage.CODE,
				CreateSessionSuccessMessage.class);
		SUPPORED_CODES.put(AnswerMessage.CODE, AnswerMessage.class);
		SUPPORED_CODES.put(OfferMessage.CODE, OfferMessage.class);
		SUPPORED_CODES.put(SendCandidateMessage.CODE, SendCandidateMessage.class);
		
		SUPPORED_CODES.put(DownLoadMessage.CODE, DownLoadMessage.class);
		SUPPORED_CODES.put(NormalOfflineMessage.CODE, NormalOfflineMessage.class);
		SUPPORED_CODES.put(HangUpMessage.CODE, HangUpMessage.class);
		SUPPORED_CODES.put(DontHaveCameraMessage.CODE, DontHaveCameraMessage.class);
		SUPPORED_CODES.put(DontHaveMicMessage.CODE, DontHaveMicMessage.class);
		SUPPORED_CODES.put(HeartbeatMessage.CODE, HeartbeatMessage.class);
		

		SUPPORED_CODES.put(MultimediaMessage.CODE, MultimediaMessage.class);
		SUPPORED_CODES.put(MultimediaDownLoadMessage.CODE, MultimediaDownLoadMessage.class);
		SUPPORED_CODES.put(PictureMessage.CODE, PictureMessage.class);
		SUPPORED_CODES.put(PictureDownLoadMessage.CODE, PictureDownLoadMessage.class);

		// 以下是写单元测试 补充的
		SUPPORED_CODES.put(CloseCameraMessage.CODE, CloseCameraMessage.class);
		SUPPORED_CODES.put(CloseMicMessage.CODE, CloseMicMessage.class);
		SUPPORED_CODES.put(LackBalanceMessage.CODE, LackBalanceMessage.class);
		SUPPORED_CODES.put(LackTimeMessage.CODE, LackTimeMessage.class);
		//SUPPORED_CODES.put(NetworkSpeedMessage.CODE, NetworkSpeedMessage.class);
		//NetworkSpeedMessage code 和HangUpMessage code相同
		SUPPORED_CODES.put((short)271, NotAvailable.class);
		SUPPORED_CODES.put((short)270, NotAvailable.class);
		SUPPORED_CODES.put(OnlineMessage.CODE, OnlineMessage.class);
		SUPPORED_CODES.put(OpenCameraMessage.CODE, OpenCameraMessage.class);
		SUPPORED_CODES.put(OpenMicMessage.CODE, OpenMicMessage.class);
        /* SUPPORED_CODES.put(ExistInVAMessage.CODE, ExistInVAMessage.class); */
        SUPPORED_CODES.put(JoinAudioVideoMessage.CODE, JoinAudioVideoMessage.class);

        //群组与翻译聊天相关的
        SUPPORED_CODES.put(FutureMessage.CODE, FutureMessage.class);
        SUPPORED_CODES.put(GroupNewMessage.CODE, GroupNewMessage.class);
        SUPPORED_CODES.put(AddGroupMember.CODE, AddGroupMember.class);
        SUPPORED_CODES.put(DeleteGroupMessage.CODE, DeleteGroupMessage.class);
        SUPPORED_CODES.put(GroupTxtMessage.CODE, GroupTxtMessage.class);
        SUPPORED_CODES.put(RemoveGroupMember.CODE, RemoveGroupMember.class);
		
	}

	public static Message create(ByteBuf buffer) {
		if (buffer.readableBytes() < MIN_LENGTH) {
			return null;
		} else {
			buffer.markReaderIndex();
			if (buffer.readByte() == IDENTITY) {
				int len = buffer.readInt();
				if (buffer.readableBytes() < len) {
					buffer.resetReaderIndex();
					return null;
				} else {
					short code = buffer.readShort();
					if (SUPPORED_CODES.containsKey(code)) {
						Message message = null;
						try {
							message = (Message) SUPPORED_CODES.get(code)
									.newInstance();
						} catch (InstantiationException
								| IllegalAccessException e) {
							logger.error("二进制消息解码出错："+e);
							throw new IllegalArgumentException(
									"new instance error");
						}
                      //  message = MessageProxy.proxyTarget(message);
						message.setLength(len);
						message.fromBinary(buffer);
						return message;
					} else {
						logger.error("二进制消息解码出错"+code+"不存在");
						throw new UnsupportedMessageTypeException(code + 
								" Not supported by Me!");
					}
				}
			} else {
				logger.error("二进制消息解码出错IDENTITY数据不正确");
				throw new UnsupportedMessageTypeException(
						"Not supported by Me!");
			}
		}
	}

	public static Message create(String str) {
		Map<String, JsonNode> map = null;
		try {
			map = JsonUtils.parse(str);
		} catch (IOException e) {
			logger.error("json消息解码出错,{}",str,e);
			throw new UnsupportedMessageTypeException("Not supported by Me!");
		}
		if (!map.containsKey("code")) {
			logger.error("所解析的json消息code不存在！");
			throw new UnsupportedMessageTypeException("Not supported by Me!");
		} else {
			short code = 0;
			try {
				code = (short) map.get("code").asInt();
			} catch (Exception e) {
				logger.error("json消息解码code出错：",e);
				throw new UnsupportedMessageTypeException(
						"Not supported by Me!");
			}
			if (SUPPORED_CODES.containsKey(code)) {
				Message message = null;
				try {
					message = (Message) SUPPORED_CODES.get(code).newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					logger.error("json消息解码出错："+e);
					throw new IllegalArgumentException("new instance error");
				}
				message.fromMap(map);
				return message;
			} else {
				logger.error("所解析的json消息code {}不存在！", code);
				throw new UnsupportedMessageTypeException(
						"Not supported by Me!");
			}
		}
	}

	public static ByteBuf createByteBuf(Message msg, ChannelHandlerContext ctx) {
		ByteBuf buffer = ctx.alloc().buffer(msg.getLength() + 5);
		buffer.writeByte(IDENTITY);
		buffer.writeInt(msg.getLength());
		buffer.writeShort(msg.getCode());
		return buffer;
	}
	
	public static ResponseMessage createResponseMessage(short state) {
		return new ResponseMessage(state);
	}
	
	public static ResponseMessage createSuccessResponseMessage() {
		return new ResponseMessage(ResponseMessage.SUCCESS);
	}
	
	public static AbNormalOfflineMessage createAbNormalOfflineMessage() {
		return new AbNormalOfflineMessage();
	}
	
	public static InviteVideoMessageSuccess createInviteVideoMessageSuccess() {
		return new InviteVideoMessageSuccess();
	}
	
	public static NotAvailable createMeNotAvailable(Long userId, String userName,Long inviteeId,String inviteeName) {
		return new NotAvailable(NotAvailable.ME, userId, userName,inviteeId,inviteeName);
	}
	
	public static NotAvailable createYouNotAvailable(Long userId, String userName,Long inviteeId,String inviteeName) {
		return new NotAvailable(NotAvailable.YOU, userId, userName,inviteeId,inviteeName);
	}
	
	public static VideoTimeoutMessage createVideoTimeoutMessage(Long userId, String userName, Long inviteeId,
			String inviteeName){
		return new VideoTimeoutMessage(userId, userName, inviteeId, inviteeName);
	}
	
	public static AudioTimeoutMessage createAudioTimeoutMessage(Long userId, String userName, Long inviteeId,
			String inviteeName){
		return new AudioTimeoutMessage(userId, userName, inviteeId, inviteeName);
	}
	
	public static LeaveChatMessage createLeaveChatMessage(Long userId, Long inviteeId){
		return new LeaveChatMessage(userId, inviteeId);
	}
	public static FileResponseMessage createFileResponseMessage(int packageNo, Long dateTime,int state){
		return new FileResponseMessage(packageNo, dateTime,state);
	}
	public static UpdateNumMessage createUpdateNumMessage(){
		return new UpdateNumMessage();
	}

    public static FutureMessage createFutureMessage(short state) {
        return new FutureMessage(state);
    }
    public static FutureMessage createSuccessFutureMessage() {
        return new FutureMessage(FutureMessage.STATE_SUCCESS);
    }
}
