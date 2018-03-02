package com.revature.project2.service;

public interface ForgotPasswordService {
	public Boolean findEmail(String email);
	public boolean requestPasswordReset(String email);
	public boolean resetPassword(String email, String token, String Password);
}
