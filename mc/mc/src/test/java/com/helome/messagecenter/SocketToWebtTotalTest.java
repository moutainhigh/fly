package com.helome.messagecenter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.helome.messagecenter.sockettoweb.TestAcceptAudioMessage;
import com.helome.messagecenter.sockettoweb.TestAcceptChatMessage;
import com.helome.messagecenter.sockettoweb.TestAcceptConsultEndMessage;
import com.helome.messagecenter.sockettoweb.TestAcceptVideoMessage;
import com.helome.messagecenter.sockettoweb.TestAnswerMessage;
import com.helome.messagecenter.sockettoweb.TestAudioTimeoutMessage;
import com.helome.messagecenter.sockettoweb.TestChatMessage;
import com.helome.messagecenter.sockettoweb.TestCloseCameraMessage;
import com.helome.messagecenter.sockettoweb.TestCloseMicMessage;
import com.helome.messagecenter.sockettoweb.TestCommentMessage;
import com.helome.messagecenter.sockettoweb.TestConsultAcceptMessage;
import com.helome.messagecenter.sockettoweb.TestConsultEndMessage;
import com.helome.messagecenter.sockettoweb.TestConsultRejectMessage;
import com.helome.messagecenter.sockettoweb.TestConsultTimeMessage;
import com.helome.messagecenter.sockettoweb.TestDontHaveCameraMessage;
import com.helome.messagecenter.sockettoweb.TestDontHaveMicMessage;
import com.helome.messagecenter.sockettoweb.TestDownLoadMessage;
import com.helome.messagecenter.sockettoweb.TestHangUpMessage;
import com.helome.messagecenter.sockettoweb.TestInviteAudioMessage;
import com.helome.messagecenter.sockettoweb.TestInviteVideoMessage;
import com.helome.messagecenter.sockettoweb.TestInviteVideoMessageSuccess;
import com.helome.messagecenter.sockettoweb.TestLackBalanceMessage;
import com.helome.messagecenter.sockettoweb.TestLackTimeMessage;
import com.helome.messagecenter.sockettoweb.TestLeaveChatMessage;
import com.helome.messagecenter.sockettoweb.TestNetworkSpeedMessage;
import com.helome.messagecenter.sockettoweb.TestNotAvailable;
import com.helome.messagecenter.sockettoweb.TestOfferMessage;
import com.helome.messagecenter.sockettoweb.TestOnlineMessage;
import com.helome.messagecenter.sockettoweb.TestOpenCameraMessage;
import com.helome.messagecenter.sockettoweb.TestOpenMicMessage;
import com.helome.messagecenter.sockettoweb.TestRejectAudioMessage;
import com.helome.messagecenter.sockettoweb.TestRejectChatMessage;
import com.helome.messagecenter.sockettoweb.TestRejectConsultEndMessage;
import com.helome.messagecenter.sockettoweb.TestRejectVideoMessage;
import com.helome.messagecenter.sockettoweb.TestReplyMessage;
import com.helome.messagecenter.sockettoweb.TestReportMessage;
import com.helome.messagecenter.sockettoweb.TestSendCandidateMessage;
import com.helome.messagecenter.sockettoweb.TestTransferMessage;
import com.helome.messagecenter.sockettoweb.TestTxtMessage;
import com.helome.messagecenter.sockettoweb.TestUpdateNumMessage;
import com.helome.messagecenter.sockettoweb.TestVideoTimeoutMessage;



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
public class SocketToWebtTotalTest {


}
