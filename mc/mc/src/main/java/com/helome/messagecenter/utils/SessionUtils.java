package com.helome.messagecenter.utils;

public class SessionUtils {
	private static int id = 0;
	public synchronized static int getId(){
		return ++id;
	}
}
