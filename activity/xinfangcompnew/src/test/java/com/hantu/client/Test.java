package com.hantu.client;

public class Test {

	public static void main(String[] args) {
		String aa = "9|aaa";
		String s1= aa.substring(0, aa.indexOf("|"));
		String s2= aa.substring(aa.indexOf("|")+1);
		System.out.println(s1);
		System.out.println(s2);

	}

}
