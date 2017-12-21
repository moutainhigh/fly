package com.helome.messagecenter.utils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.utils.PropertiesUtils;

public class TestPropertiesUtils {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String value = PropertiesUtils.readValue("mc.socket.port");
		Assert.assertNotNull(value);
	}

}
