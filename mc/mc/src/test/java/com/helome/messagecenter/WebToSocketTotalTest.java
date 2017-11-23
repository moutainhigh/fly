package com.helome.messagecenter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.helome.messagecenter.webtosocket.TestAcceptAudioMessage;
import com.helome.messagecenter.webtosocket.TestAcceptChatMessage;
import com.helome.messagecenter.webtosocket.TestAcceptConsultEndMessage;
import com.helome.messagecenter.webtosocket.TestAcceptVideoMessage;
import com.helome.messagecenter.webtosocket.TestAnswerMessage;
import com.helome.messagecenter.webtosocket.TestAudioTimeoutMessage;
import com.helome.messagecenter.webtosocket.TestChatMessage;
import com.helome.messagecenter.webtosocket.TestCloseCameraMessage;
import com.helome.messagecenter.webtosocket.TestCloseMicMessage;
import com.helome.messagecenter.webtosocket.TestCommentMessage;
import com.helome.messagecenter.webtosocket.TestConsultAcceptMessage;
import com.helome.messagecenter.webtosocket.TestConsultEndMessage;
import com.helome.messagecenter.webtosocket.TestConsultRejectMessage;
import com.helome.messagecenter.webtosocket.TestConsultTimeMessage;
import com.helome.messagecenter.webtosocket.TestDontHaveCameraMessage;
import com.helome.messagecenter.webtosocket.TestDontHaveMicMessage;
import com.helome.messagecenter.webtosocket.TestDownLoadMessage;
import com.helome.messagecenter.webtosocket.TestHangUpMessage;
import com.helome.messagecenter.webtosocket.TestInviteAudioMessage;
import com.helome.messagecenter.webtosocket.TestInviteVideoMessage;
import com.helome.messagecenter.webtosocket.TestInviteVideoMessageSuccess;
import com.helome.messagecenter.webtosocket.TestLackBalanceMessage;
import com.helome.messagecenter.webtosocket.TestLackTimeMessage;
import com.helome.messagecenter.webtosocket.TestLeaveChatMessage;
import com.helome.messagecenter.webtosocket.TestNetworkSpeedMessage;
import com.helome.messagecenter.webtosocket.TestNotAvailable;
import com.helome.messagecenter.webtosocket.TestOfferMessage;
import com.helome.messagecenter.webtosocket.TestOnlineMessage;
import com.helome.messagecenter.webtosocket.TestOpenCameraMessage;
import com.helome.messagecenter.webtosocket.TestOpenMicMessage;
import com.helome.messagecenter.webtosocket.TestRejectAudioMessage;
import com.helome.messagecenter.webtosocket.TestRejectChatMessage;
import com.helome.messagecenter.webtosocket.TestRejectConsultEndMessage;
import com.helome.messagecenter.webtosocket.TestRejectVideoMessage;
import com.helome.messagecenter.webtosocket.TestReplyMessage;
import com.helome.messagecenter.webtosocket.TestReportMessage;
import com.helome.messagecenter.webtosocket.TestSendCandidateMessage;
import com.helome.messagecenter.webtosocket.TestTransferMessage;
import com.helome.messagecenter.webtosocket.TestTxtMessage;
import com.helome.messagecenter.webtosocket.TestUpdateNumMessage;
import com.helome.messagecenter.webtosocket.TestVideoTimeoutMessage;




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
	TestSendCandidateMessage.class,
	TestTransferMessage.class,
	TestTxtMessage.class,
	TestUpdateNumMessage.class,
	TestVideoTimeoutMessage.class
})
public class WebToSocketTotalTest {


}
