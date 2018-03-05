package com.revature.project2.service;

import java.util.List;

import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;

public interface PostService {
	
	public boolean savePost(Post p);
	public List<Post> getPosts();
	public List<Post> getPostsByID(int id);
	public Post getPostById(int id);
	public boolean likePost(Post post, Trainer trainer);
	public boolean unlikePost(Post post, Trainer trainer);
}
