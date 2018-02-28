package com.revature.project2.model.validator;

import java.util.regex.Pattern;

import com.revature.project2.model.Trainer;

public class TrainerValidator {
	
	
	private static final Pattern emailPattern = Pattern.compile(".+");
	private static final Pattern urlPattern = Pattern.compile(".+");
	private static final Pattern namePattern = Pattern.compile(".+");
	private static final Pattern passwordPattern = Pattern.compile(".+");

	
	private TrainerValidator() { }

	
	public static boolean isEmailValid (Trainer trainer) {
		return isEmailValid(trainer.getEmail());
	}
	public static boolean isEmailValid (String email) {
		return email != null && !email.isEmpty() && emailPattern.matcher(email).matches();
	}
	
	
	public static boolean isUrlValid (Trainer trainer) {
		return isUrlValid(trainer.getUrl());
	}
	public static boolean isUrlValid (String url) {
		return url != null && !url.isEmpty() && urlPattern.matcher(url).matches();
	}
	
	
	public static boolean isFirstNameValid (Trainer trainer) {
		return isFirstNameValid(trainer.getFirstName());
	}
	public static boolean isLastNameValid (Trainer trainer) {
		return isLastNameValid(trainer.getLastName());
	}
	public static boolean isFirstNameValid (String firstName) {
		return isNameValid(firstName);
	}
	public static boolean isLastNameValid (String lastName) {
		return isNameValid(lastName);
	}
	public static boolean isNameValid (String name) {
		return name != null && !name.isEmpty() && namePattern.matcher(name).matches();
	}
	
	
	public static boolean isPasswordValid (Trainer trainer) {
		return isPasswordValid(trainer.getPassword());
	}
	public static boolean isPasswordValid (String password) {
		return password != null && !password.isEmpty() && passwordPattern.matcher(password).matches();
	}
}
