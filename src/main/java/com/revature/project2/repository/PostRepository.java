package com.revature.project2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;

public interface PostRepository extends CrudRepository<Post, Integer> {
	
//	public List<Post> findAll();
	public List<Post> findByCreator(Trainer trainer);
	public List<Post> findByCreatorId(Integer id);
	public List<Post> findByCreatorUrl(String url);
	
}
