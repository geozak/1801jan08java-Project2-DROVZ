package com.revature.project2.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.revature.project2.model.Trainer;

public interface TrainerRepository extends CrudRepository<Trainer, Integer> {

	public List<Trainer> findAll();
	public Trainer findByUrl(String url);
	public Trainer findByEmail(String email);
	public Trainer findById(int id);
	public boolean existsByEmail(String email);
	public boolean existsByUrl(String url);
	
}
