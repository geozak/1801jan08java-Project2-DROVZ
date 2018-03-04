package com.revature.project2.service;

import com.revature.project2.model.PasswordReset;
import com.revature.project2.model.Trainer;

public interface ForgotPasswordService {
	public Trainer findEmail(String email);
	public PasswordReset findToken(String token);
	public PasswordReset findTokenByTrainerId(int id);
	public void deletePreviousTokens(Trainer trainer);
	public boolean requestPasswordReset(String email, String token);
	public boolean resetPassword(Trainer trainer, String newPassword);
	public boolean deleteToken(String token);
}
