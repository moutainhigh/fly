package com.helome.messagecenter.core;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helome.messagecenter.utils.HttpUtils;

public class SendChatRecordes extends Thread{
	private final static Logger logger = LoggerFactory
			.getLogger(SendChatRecordes.class);
	@Override
	public void run() {
		String jsonStr = HttpUtils.takeMsgRecords();
		try {
			String response = HttpUtils.sendChatRequest(jsonStr);
			logger.debug("保存{},结果{}", jsonStr, response);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	

}
