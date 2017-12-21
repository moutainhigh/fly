package com.helome.messagecenter.client.utils;

import java.util.UUID;

/**
 * 
 * @title UUIDUtil.java
 * @package com.helome.test.client.utils
 * @description TODO
 * @author beyond.zhang
 * @update 2014-4-25 下午1:47:26
 * @version V1.0
 */
public class UUIDUtil {

	public static long longUUID() {
		return UUID.randomUUID().getMostSignificantBits();
	}

	public static long absLongUUID() {
		while (true) {
			long r = longUUID();
			if (r > 0) {
				return r;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(UUIDUtil.longUUID());
           
	}
}
