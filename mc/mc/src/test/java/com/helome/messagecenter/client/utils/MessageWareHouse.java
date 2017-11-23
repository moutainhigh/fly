package com.helome.messagecenter.client.utils;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.AbNormalOfflineMessage;
import com.helome.messagecenter.message.base.BinaryMessage;
import com.helome.messagecenter.message.base.CacheBinaryMessage;
import com.helome.messagecenter.message.base.DownLoadMessage;
import com.helome.messagecenter.message.base.HeartbeatMessage;
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
import com.helome.messagecenter.message.business.DontHaveCameraMessage;
import com.helome.messagecenter.message.business.DontHaveMicMessage;
import com.helome.messagecenter.message.business.HangUpMessage;
import com.helome.messagecenter.message.business.InviteAudioMessage;
import com.helome.messagecenter.message.business.InviteVideoMessage;
import com.helome.messagecenter.message.business.LackBalanceMessage;
import com.helome.messagecenter.message.business.LackTimeMessage;
import com.helome.messagecenter.message.business.LeaveChatMessage;
import com.helome.messagecenter.message.business.NetworkSpeedMessage;
import com.helome.messagecenter.message.business.NotAvailable;
import com.helome.messagecenter.message.business.OnlineMessage;
import com.helome.messagecenter.message.business.OpenCameraMessage;
import com.helome.messagecenter.message.business.OpenMicMessage;
import com.helome.messagecenter.message.business.RejectAudioMessage;
import com.helome.messagecenter.message.business.RejectChatMessage;
import com.helome.messagecenter.message.business.RejectConsultEndMessage;
import com.helome.messagecenter.message.business.RejectVideoMessage;
import com.helome.messagecenter.message.business.ReplyMessage;
import com.helome.messagecenter.message.business.TransferMessage;
import com.helome.messagecenter.message.business.UpdateNumMessage;
import com.helome.messagecenter.message.business.VideoTimeoutMessage;
import com.helome.messagecenter.message.webrtc.AnswerMessage;
import com.helome.messagecenter.message.webrtc.OfferMessage;
import com.helome.messagecenter.message.webrtc.SendCandidateMessage;

/**
 * @description 消息仓库
 * @version V1.0
 */
public class MessageWareHouse {
 
	public static ConcurrentHashMap<Long,Message> wareHouse = new ConcurrentHashMap<Long,Message>();
	
	public static void add(Long id,Message msg){
		wareHouse.put(id, msg);
	}

	public static Message getById(Long id){
		return wareHouse.get(id);
	}
	
	public static void addMessageMapper(Message message, ChannelHandlerContext ctx){
		message.setEndpoint(new Endpoint(ctx));
		if(message.getCode()==ReportMessage.CODE){
			ReportMessage report = (ReportMessage)message;
			wareHouse.put(report.getId(), report);
		}else if(message.getCode()==ResponseMessage.CODE){
			ResponseMessage response = (ResponseMessage)message;
			wareHouse.put(0L, response);
		}else if(message.getCode()==TxtMessage.CODE){
			TxtMessage  txtMessage = (TxtMessage)message;
			wareHouse.put(txtMessage.getSenderId(), txtMessage);
		}else if(message.getCode()==AbNormalOfflineMessage.CODE){
			AbNormalOfflineMessage  abnormalOfflineMessage = (AbNormalOfflineMessage)message;
			wareHouse.put(21L, abnormalOfflineMessage);
		}else if(message.getCode()==BinaryMessage.CODE){
			BinaryMessage  binaryMessage = (BinaryMessage)message;
			wareHouse.put(binaryMessage.getSenderId(), binaryMessage);
		}else if(message.getCode()==CacheBinaryMessage.CODE){
			CacheBinaryMessage  cacheBinaryMessage = (CacheBinaryMessage)message;
			wareHouse.put(cacheBinaryMessage.getSenderId(), cacheBinaryMessage);
		}else if(message.getCode()==DownLoadMessage.CODE){
			DownLoadMessage  downLoadMessage = (DownLoadMessage)message;
			wareHouse.put(downLoadMessage.getSenderId(), downLoadMessage);
		}else if(message.getCode()==HeartbeatMessage.CODE){
			HeartbeatMessage  heartbeatMessage = (HeartbeatMessage)message;
			wareHouse.put(2L, heartbeatMessage);
		}else if(message.getCode()==ChatMessage.CODE){
			ChatMessage  chatMessage = (ChatMessage)message;
			wareHouse.put(chatMessage.getSenderId(), chatMessage);
		}else if(message.getCode()==CommentMessage.CODE){
			CommentMessage  commentMessage = (CommentMessage)message;
			wareHouse.put(commentMessage.getSenderId(), commentMessage);
		}else if(message.getCode()==ReplyMessage.CODE){
			ReplyMessage  replyMessage = (ReplyMessage)message;
			wareHouse.put(replyMessage.getReceiverId(), replyMessage);
		}else if(message.getCode()==UpdateNumMessage.CODE){
			UpdateNumMessage  updateNumMessage = (UpdateNumMessage)message;
			wareHouse.put(111L, updateNumMessage);
		}else if(message.getCode()==DownLoadMessage.CODE){
			DownLoadMessage  downLoadMessage = (DownLoadMessage)message;
			wareHouse.put(downLoadMessage.getSenderId(), downLoadMessage);
		}else if(message.getCode()==AcceptAudioMessage.CODE){
			AcceptAudioMessage  acceptAudioMessage = (AcceptAudioMessage)message;
			wareHouse.put(acceptAudioMessage.getUserId(), acceptAudioMessage);
		}else if(message.getCode()==AcceptChatMessage.CODE){
			AcceptChatMessage  acceptChatMessage = (AcceptChatMessage)message;
			wareHouse.put(acceptChatMessage.getUserId(), message);
		}else if(message.getCode()==AcceptConsultEndMessage.CODE){
			AcceptConsultEndMessage  acceptConsultEndMessage = (AcceptConsultEndMessage)message;
			wareHouse.put(acceptConsultEndMessage.getReceiverId(), acceptConsultEndMessage);
		}else if(message.getCode()==AcceptVideoMessage.CODE){
			AcceptVideoMessage  acceptVideoMessage = (AcceptVideoMessage)message;
			wareHouse.put(acceptVideoMessage.getUserId(), acceptVideoMessage);
		}else if(message.getCode()==AudioTimeoutMessage.CODE){
			AudioTimeoutMessage  audioTimeoutMessage = (AudioTimeoutMessage)message;
			wareHouse.put(audioTimeoutMessage.getUserId(), audioTimeoutMessage);
		}else if(message.getCode()==CloseCameraMessage.CODE){
			CloseCameraMessage  closeCameraMessage = (CloseCameraMessage)message;
			wareHouse.put(closeCameraMessage.getReceiverId(), closeCameraMessage);
		}else if(message.getCode()==CloseMicMessage.CODE){
			CloseMicMessage  closeMicMessage = (CloseMicMessage)message;
			wareHouse.put(closeMicMessage.getReceiverId(), closeMicMessage);
		}else if(message.getCode()==ConsultAcceptMessage.CODE){
			ConsultAcceptMessage  consultAcceptMessage = (ConsultAcceptMessage)message;
			wareHouse.put(consultAcceptMessage.getReceiverId(), consultAcceptMessage);
		}else if(message.getCode()==ConsultEndMessage.CODE){
			ConsultEndMessage  consultEndMessage = (ConsultEndMessage)message;
			wareHouse.put(consultEndMessage.getReceiverId(), consultEndMessage);
		}else if(message.getCode()==ConsultRejectMessage.CODE){
			ConsultRejectMessage  consultRejectMessage = (ConsultRejectMessage)message;
			wareHouse.put(consultRejectMessage.getReceiverId(), consultRejectMessage);
		}else if(message.getCode()==ConsultTimeMessage.CODE){
			ConsultTimeMessage  consultTimeMessage = (ConsultTimeMessage)message;
			wareHouse.put(consultTimeMessage.getReceiverId(), consultTimeMessage);
		}else if(message.getCode()==DontHaveCameraMessage.CODE){
			DontHaveCameraMessage  dontHaveCameraMessage = (DontHaveCameraMessage)message;
			wareHouse.put(dontHaveCameraMessage.getReceiverId(), dontHaveCameraMessage);
		}else if(message.getCode()==DontHaveMicMessage.CODE){
			DontHaveMicMessage  dontHaveMicMessage = (DontHaveMicMessage)message;
			wareHouse.put(dontHaveMicMessage.getReceiverId(), dontHaveMicMessage);
		}else if(message.getCode()==HangUpMessage.CODE){
			HangUpMessage  hangUpMessage = (HangUpMessage)message;
			wareHouse.put(hangUpMessage.getReceiverId(), hangUpMessage);
		}else if(message.getCode()==InviteAudioMessage.CODE){
			InviteAudioMessage  inviteAudioMessage = (InviteAudioMessage)message;
			wareHouse.put(inviteAudioMessage.getUserId(), inviteAudioMessage);
		}else if(message.getCode()==InviteVideoMessage.CODE){
			InviteVideoMessage  inviteVideoMessage = (InviteVideoMessage)message;
			wareHouse.put(inviteVideoMessage.getUserId(), inviteVideoMessage);
		}else if(message.getCode()==LackBalanceMessage.CODE){
			LackBalanceMessage  lackBalanceMessage = (LackBalanceMessage)message;
			wareHouse.put(lackBalanceMessage.getSenderId(), lackBalanceMessage);
		}else if(message.getCode()==LackTimeMessage.CODE){
			LackTimeMessage  lackTimeMessage = (LackTimeMessage)message;
			wareHouse.put(lackTimeMessage.getSenderId(), lackTimeMessage);
		}else if(message.getCode()==LeaveChatMessage.CODE){
			LeaveChatMessage  leaveChatMessage = (LeaveChatMessage)message;
			wareHouse.put(leaveChatMessage.getSenderId(), leaveChatMessage);
		}else if(message.getCode()==NetworkSpeedMessage.CODE){
			NetworkSpeedMessage  networkSpeedMessage = (NetworkSpeedMessage)message;
			wareHouse.put(networkSpeedMessage.getSenderId(), networkSpeedMessage);
		}else if(message.getCode()==270 || message.getCode()==271){
			NotAvailable  notAvailable = (NotAvailable)message;
			wareHouse.put(notAvailable.getUserId(), notAvailable);
		}else if(message.getCode()==OnlineMessage.CODE){
			OnlineMessage  onlineMessage = (OnlineMessage)message;
			wareHouse.put(onlineMessage.getSenderId(), onlineMessage);
		}else if(message.getCode()==OpenCameraMessage.CODE){
			OpenCameraMessage  openCameraMessage = (OpenCameraMessage)message;
			wareHouse.put(openCameraMessage.getSenderId(), openCameraMessage);
		}else if(message.getCode()==OpenMicMessage.CODE){
			OpenMicMessage  openCameraMessage = (OpenMicMessage)message;
			wareHouse.put(openCameraMessage.getSenderId(), openCameraMessage);
		}else if(message.getCode()==RejectAudioMessage.CODE){
			RejectAudioMessage  rejectAudioMessage = (RejectAudioMessage)message;
			wareHouse.put(rejectAudioMessage.getUserId(), rejectAudioMessage);
		}else if(message.getCode()==RejectChatMessage.CODE){
			RejectChatMessage  rejectChatMessage = (RejectChatMessage)message;
			wareHouse.put(rejectChatMessage.getUserId(), rejectChatMessage);
		}else if(message.getCode()==RejectConsultEndMessage.CODE){
			RejectConsultEndMessage  rejectConsultEndMessage = (RejectConsultEndMessage)message;
			wareHouse.put(rejectConsultEndMessage.getSenderId(), rejectConsultEndMessage);
		}else if(message.getCode()==RejectVideoMessage.CODE){
			RejectVideoMessage  rejectConsultEndMessage = (RejectVideoMessage)message;
			wareHouse.put(rejectConsultEndMessage.getUserId(), rejectConsultEndMessage);
		}else if(message.getCode()==TransferMessage.CODE){
			TransferMessage  transferMessage = (TransferMessage)message;
			wareHouse.put(transferMessage.getSenderId(), transferMessage);
		}else if(message.getCode()==VideoTimeoutMessage.CODE){
			VideoTimeoutMessage  videoTimeoutMessage = (VideoTimeoutMessage)message;
			wareHouse.put(videoTimeoutMessage.getUserId(), videoTimeoutMessage);
		}else if(message.getCode()==AnswerMessage.CODE){
			AnswerMessage answerMessage = (AnswerMessage)message;
			wareHouse.put(answerMessage.getSenderId(), answerMessage);
		}else if(message.getCode()==OfferMessage.CODE){
			OfferMessage offerMessage = (OfferMessage)message;
			wareHouse.put(offerMessage.getSenderId(), offerMessage);
		}else if(message.getCode()==SendCandidateMessage.CODE){
			SendCandidateMessage sendCandidateMessage = (SendCandidateMessage)message;
			wareHouse.put(sendCandidateMessage.getSenderId(), sendCandidateMessage);
		}
		
		
	}

}
