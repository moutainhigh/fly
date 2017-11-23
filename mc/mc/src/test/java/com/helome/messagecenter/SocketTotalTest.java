package com.helome.messagecenter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.helome.messagecenter.client.socket.message.TestAcceptAudioMessage;
import com.helome.messagecenter.client.socket.message.TestAcceptChatMessage;
import com.helome.messagecenter.client.socket.message.TestAcceptConsultEndMessage;
import com.helome.messagecenter.client.socket.message.TestAcceptVideoMessage;
import com.helome.messagecenter.client.socket.message.TestAnswerMessage;
import com.helome.messagecenter.client.socket.message.TestAudioTimeoutMessage;
import com.helome.messagecenter.client.socket.message.TestChatMessage;
import com.helome.messagecenter.client.socket.message.TestCloseCameraMessage;
import com.helome.messagecenter.client.socket.message.TestCloseMicMessage;
import com.helome.messagecenter.client.socket.message.TestCommentMessage;
import com.helome.messagecenter.client.socket.message.TestConsultAcceptMessage;
import com.helome.messagecenter.client.socket.message.TestConsultEndMessage;
import com.helome.messagecenter.client.socket.message.TestConsultRejectMessage;
import com.helome.messagecenter.client.socket.message.TestConsultTimeMessage;
import com.helome.messagecenter.client.socket.message.TestDontHaveCameraMessage;
import com.helome.messagecenter.client.socket.message.TestDontHaveMicMessage;
import com.helome.messagecenter.client.socket.message.TestDownLoadMessage;
import com.helome.messagecenter.client.socket.message.TestHangUpMessage;
import com.helome.messagecenter.client.socket.message.TestInviteAudioMessage;
import com.helome.messagecenter.client.socket.message.TestInviteVideoMessage;
import com.helome.messagecenter.client.socket.message.TestInviteVideoMessageSuccess;
import com.helome.messagecenter.client.socket.message.TestLackBalanceMessage;
import com.helome.messagecenter.client.socket.message.TestLackTimeMessage;
import com.helome.messagecenter.client.socket.message.TestLeaveChatMessage;
import com.helome.messagecenter.client.socket.message.TestNetworkSpeedMessage;
import com.helome.messagecenter.client.socket.message.TestNotAvailable;
import com.helome.messagecenter.client.socket.message.TestOfferMessage;
import com.helome.messagecenter.client.socket.message.TestOnlineMessage;
import com.helome.messagecenter.client.socket.message.TestOpenCameraMessage;
import com.helome.messagecenter.client.socket.message.TestOpenMicMessage;
import com.helome.messagecenter.client.socket.message.TestRejectAudioMessage;
import com.helome.messagecenter.client.socket.message.TestRejectChatMessage;
import com.helome.messagecenter.client.socket.message.TestRejectConsultEndMessage;
import com.helome.messagecenter.client.socket.message.TestRejectVideoMessage;
import com.helome.messagecenter.client.socket.message.TestReplyMessage;
import com.helome.messagecenter.client.socket.message.TestReportMessage;
import com.helome.messagecenter.client.socket.message.TestReserveMessage;
import com.helome.messagecenter.client.socket.message.TestSendCandidateMessage;
import com.helome.messagecenter.client.socket.message.TestTestAbNormalOfflineMessage;
import com.helome.messagecenter.client.socket.message.TestTransferMessage;
import com.helome.messagecenter.client.socket.message.TestTxtMessage;
import com.helome.messagecenter.client.socket.message.TestUpdateNumMessage;
import com.helome.messagecenter.client.socket.message.TestVideoTimeoutMessage;

@RunWith(Suite.class)
@SuiteClasses({
	TestAcceptAudioMessage.class,
	TestAcceptChatMessage.class,
	TestAcceptConsultEndMessage.class,
	TestAcceptVideoMessage.class,
	TestAnswerMessage.class,
	TestAudioTimeoutMessage.class,
	TestChatMessage.class,
	TestCloseCameraMessage.class,
	TestCloseMicMessage.class,
	TestCommentMessage.class,
	TestConsultAcceptMessage.class,
	TestConsultEndMessage.class,
	TestConsultRejectMessage.class,
	TestConsultTimeMessage.class,
	TestDontHaveCameraMessage.class,
	TestDontHaveMicMessage.class,
	TestDownLoadMessage.class,
	TestHangUpMessage.class,
	TestInviteAudioMessage.class,
	TestInviteVideoMessage.class,
	TestInviteVideoMessageSuccess.class,
	TestLackBalanceMessage.class,
	TestLackTimeMessage.class,
	TestLeaveChatMessage.class,
	TestNetworkSpeedMessage.class,
	TestNotAvailable.class,
	TestOfferMessage.class,
	TestOnlineMessage.class,
	TestOpenCameraMessage.class,
	TestOpenMicMessage.class,
	TestRejectAudioMessage.class,
	TestRejectChatMessage.class,
	TestRejectConsultEndMessage.class,
	TestRejectVideoMessage.class,
	TestReplyMessage.class,
	TestReportMessage.class,
	TestReserveMessage.class,
	TestSendCandidateMessage.class,
	TestTestAbNormalOfflineMessage.class,
	TestTransferMessage.class,
	TestTxtMessage.class,
	TestUpdateNumMessage.class,
	TestVideoTimeoutMessage.class
})
/**
 * 
 * socket 测试套件
 *
 */
public class SocketTotalTest {

	

}
