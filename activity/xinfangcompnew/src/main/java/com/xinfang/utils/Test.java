package com.xinfang.utils;

public class Test {

	public static void main(String[] args) {

		MathOperation subtraction = (a, b) -> a - b;
		System.out.println(subtraction.operation(6, 5));	
	}

	interface MathOperation {
		int operation(int a, int b);
	}
}
