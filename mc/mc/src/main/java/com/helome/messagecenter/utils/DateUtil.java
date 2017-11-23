package com.helome.messagecenter.utils;

import java.util.*;
import java.text.*;
/**
 * 
 * @title DateUtil.java
 * @package com.helome.messagecenter.utils
 * @description 时间格式化
 * @author beyond.zhang   
 * @update 2014-4-2 下午3:27:59
 * @version V1.0
 */
public class DateUtil {
	public DateUtil() {
	}

	public static String formatDate(Date date, String dateformat) {
		try {
			SimpleDateFormat sDateformat = new SimpleDateFormat(dateformat); // yyyy.MM.dd
																				// hh:mm:ss
			return sDateformat.format(date).toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String formatDate(Date date) {
		String dateformat = "yyyy-MM-dd HH:mm:ss.SSS";
		return formatDate(date, dateformat);
	}
	public static String formatSimpleDate(Date date) {
		String dateformat = "yyyy-MM-dd";
		return formatDate(date, dateformat);
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	public static String getDate() {
		Date d = new Date(System.currentTimeMillis());
		return DateUtil.formatDate(d);
	}

	public static Date getDate(String szDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date ret = sdf.parse(szDate, new ParsePosition(0));
		return ret;
	}
	public static Date getSimpleDate(String szDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ret = sdf.parse(szDate, new ParsePosition(0));
		return ret;
	}
	
	public static String getSimDate() {
		Date d = new Date(System.currentTimeMillis());
		return DateUtil.formatSimpleDate(d);
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.getSimDate());
	}
}