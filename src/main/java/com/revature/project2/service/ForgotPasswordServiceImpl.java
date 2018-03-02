package com.revature.project2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.model.PasswordReset;
import com.revature.project2.repository.PasswordResetRepository;

@Service("forgotPasswordService")
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Override
	public Boolean findEmail(String email) {
		PasswordReset pr = passwordResetRepository.findByTrainerEmail(email);
		if (pr == null)
			return false;
		return true;
	}
	
	@Override
	public boolean requestPasswordReset(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetPassword(String email, String token, String Password) {
		// TODO Auto-generated method stub
		return false;
	}
}
