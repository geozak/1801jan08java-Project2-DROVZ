package com.revature.project2.repository;

import org.springframework.data.repository.CrudRepository;

import com.revature.project2.model.PasswordReset;
//import com.revature.project2.model.Trainer;
import com.revature.project2.model.Trainer;

public interface PasswordResetRepository extends CrudRepository<PasswordReset, Integer> {
	
	public PasswordReset findByTrainerEmail(String email);
	public PasswordReset findByTrainerId(int id);
	public PasswordReset findById(int id);
	public PasswordReset findByToken(String token);
}
