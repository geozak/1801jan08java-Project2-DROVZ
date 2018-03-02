package com.revature.project2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.revature.project2.model.Photo;
import com.revature.project2.model.Trainer;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
	
	public List<Photo> findByCreator(Trainer trainer);
	public Photo findByUrl(String url);
	
}
