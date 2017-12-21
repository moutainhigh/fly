package com.helome.messagecenter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.helome.messagecenter.base.TestDownLoadMessage;
import com.helome.messagecenter.base.TestReportMessage;
import com.helome.messagecenter.base.TestTxtMessage;
import com.helome.messagecenter.business.TestAcceptAudioMessage;
import com.helome.messagecenter.business.TestAcceptChatMessage;
import com.helome.messagecenter.business.TestAcceptConsultEndMessage;
import com.helome.messagecenter.business.TestAcceptVideoMessage;
import com.helome.messagecenter.business.TestAudioTimeoutMessage;
import com.helome.messagecenter.business.TestChatMessage;
import com.helome.messagecenter.business.TestCloseCameraMessage;
import com.helome.messagecenter.business.TestCloseMicMessage;
import com.helome.messagecenter.business.TestCommentMessage;
import com.helome.messagecenter.business.TestConsultAcceptMessage;
import com.helome.messagecenter.business.TestConsultEndMessage;
import com.helome.messagecenter.business.TestConsultRejectMessage;
import com.helome.messagecenter.business.TestConsultTimeMessage;
import com.helome.messagecenter.business.TestDontHaveCameraMessage;
import com.helome.messagecenter.business.TestDontHaveMicMessage;
import com.helome.messagecenter.business.TestHangUpMessage;
import com.helome.messagecenter.business.TestInviteAudioMessage;
import com.helome.messagecenter.business.TestInviteVideoMessage;
import com.helome.messagecenter.business.TestInviteVideoMessageSuccess;
import com.helome.messagecenter.business.TestLackBalanceMessage;
import com.helome.messagecenter.business.TestLackTimeMessage;
import com.helome.messagecenter.business.TestLeaveChatMessage;
import com.helome.messagecenter.business.TestNetworkSpeedMessage;
import com.helome.messagecenter.business.TestNotAvailable;
import com.helome.messagecenter.business.TestOnlineMessage;
import com.helome.messagecenter.business.TestOpenCameraMessage;
import com.helome.messagecenter.business.TestOpenMicMessage;
import com.helome.messagecenter.business.TestRejectAudioMessage;
import com.helome.messagecenter.business.TestRejectChatMessage;
import com.helome.messagecenter.business.TestRejectConsultEndMessage;
import com.helome.messagecenter.business.TestRejectVideoMessage;
import com.helome.messagecenter.business.TestReplyMessage;
import com.helome.messagecenter.business.TestReserveMessage;
import com.helome.messagecenter.business.TestTransferMessage;
import com.helome.messagecenter.business.TestUpdateNumMessage;
import com.helome.messagecenter.business.TestVideoTimeoutMessage;
import com.helome.messagecenter.webrtc.TestAnswerMessage;
import com.helome.messagecenter.webrtc.TestOfferMessage;
import com.helome.messagecenter.webrtc.TestSendCandidateMessage;


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
	TestTransferMessage.class,
	TestTxtMessage.class,
	TestUpdateNumMessage.class,
	TestVideoTimeoutMessage.class
})
public class WebsocketTotalTest {


}
