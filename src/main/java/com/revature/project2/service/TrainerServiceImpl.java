package com.revature.project2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.model.Trainer;
import com.revature.project2.repository.TrainerRepository;


@Service("trainerService")
public class TrainerServiceImpl implements TrainerService{
	
	@Autowired
	private TrainerRepository trainerRepository;

	@Override
	public List<Trainer> getAllTrainers() {
		// TODO Auto-generated method stub
		return trainerRepository.findAll();
	}


	@Override
	public Trainer getTrainerByUrl(String url) {
		// TODO Auto-generated method stub
		return trainerRepository.findByUrl(url);
	}


	@Override
	public Trainer getTrainerById(int id) {
		// TODO Auto-generated method stub
		return trainerRepository.findById(id);
	}

}
