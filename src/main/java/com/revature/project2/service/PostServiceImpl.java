package com.revature.project2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.JSON.PostJSON;
import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;
import com.revature.project2.repository.PostRepository;

@Service("postService")
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepo;
	
	@Override
	public List<Post> getAllPosts() {
		// TODO Auto-generated method stub
		return (List<Post>) postRepo.findAll();
	}
	
	@Override
	public void savePost(Post post) {
		postRepo.save(post);

	}
	
	@Override
	public List<PostJSON> getAllById(int id) {
		List<PostJSON> output = new ArrayList<>();
		postRepo.findByCreatorId(id).forEach(post -> output.add(new PostJSON(post)));
		return output;

	}
	

}
