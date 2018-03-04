package com.revature.project2.service;

import java.util.List;

import com.revature.project2.JSON.PostJSON;
import com.revature.project2.model.Photo;
import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;

public interface PostService {
	
	public List<Post> getAllPosts();
	void savePost(Post post);
	List<PostJSON> getAllById(int id);

}
