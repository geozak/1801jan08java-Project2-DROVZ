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
	public String updatePassword(Trainer trainer, String oldPassword, String newPassword, String confirmPassword);
	public RegisterReturn edit(Trainer trainer, String firstName, String lastName, String email, String url);
	
}
