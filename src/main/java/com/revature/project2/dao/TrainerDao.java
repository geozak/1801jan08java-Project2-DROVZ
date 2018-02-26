package com.revature.project2.dao;

import java.util.List;

import com.revature.project2.models.Trainer;

public interface TrainerDao {
	// CRUD methods go here
	// create methods:
	public void insertNewTrainer(Trainer trainer);
	
	// select(read) methods:
	public List<Trainer> selectAllTrainers();
	public Trainer selectTrainerById(int id);
	public Trainer selectTrainerByEmail(String email);
	public List<Trainer> selectTrainerByFirstname(String firstname);
	public List<Trainer> selectTrainerByLastname(String lastname);
	
	// update methods:
	public void updateTrainer(Trainer trainer);
	
	// no delete methods
}
