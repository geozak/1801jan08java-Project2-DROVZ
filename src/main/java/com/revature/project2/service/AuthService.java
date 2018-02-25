package com.revature.project2.service;

import com.revature.project2.model.Trainer;

public interface AuthService {

	public Trainer login(String email, String password);
	public static class RegisterReturn {
		public static enum Status { SUCCESS, INPUTSINVALID, EMAILEXISTS, URLEXISTS, OTHERFAILURE }
		public Status status;
		public Trainer trainer;
		public RegisterReturn(Status status, Trainer trainer) {
			this.status = status;
			this.trainer = trainer;
		}
	}
	public RegisterReturn register(Trainer trainer);
	public boolean requestPasswordReset(String email);
	public boolean resetPassword(String email, String token, String Password);
	
}
