package com.revature.project2.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.util.Random;

public final class PasswordHashing {

	private PasswordHashing() {
	}

	private static final String VALID_SLAT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+{}[]|:;<>?,./";
	private static final int DEFAULT_SALT_LENGTH = 12;
//	private static final Random RANDOM = new SecureRandom();

	public static String generateSalt() {
		return generateSalt(DEFAULT_SALT_LENGTH);
	}

	public static String generateSalt(int length) {
		return StringGenerator.generate(VALID_SLAT_CHARS, length);
//		StringBuilder salt = new StringBuilder();
//		
//		for (int i=0; i<length; i++) {
//			salt.append(VALID_SLAT_CHARS.charAt(
//					RANDOM.nextInt(VALID_SLAT_CHARS.length())));
//		}
//
//        return salt.toString();
	}

	public static String hashPassword(String salt, String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.update((salt + password).getBytes());
			return new String(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
