package com.revature.project2.util;

import java.security.SecureRandom;
import java.util.Random;

public final class StringGenerator {

	private static final Random RANDOM = new SecureRandom();

	private StringGenerator() {
	}

	public static String generate(String charPool, int length) {
		StringBuilder string = new StringBuilder();

		for (int i = 0; i < length; i++) {
			string.append(charPool.charAt(RANDOM.nextInt(charPool.length())));
		}

		return string.toString();
	}
}
