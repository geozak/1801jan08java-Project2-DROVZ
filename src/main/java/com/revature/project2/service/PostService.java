package com.revature.project2.service;

import java.util.List;

import com.revature.project2.model.Post;

public interface PostService {
	
	public boolean savePost(Post p);
	public List<Post> getPosts();
	public List<Post> getPostsByID(int id);

}
