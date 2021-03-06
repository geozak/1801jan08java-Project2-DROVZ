package com.revature.project2.service;

import java.util.List;

import com.revature.project2.model.Trainer;

public interface TrainerService {
	
	public List<Trainer> getAllTrainers();
	public Trainer getTrainerByUrl(String url);
	public Trainer getTrainerById(int id);

}
