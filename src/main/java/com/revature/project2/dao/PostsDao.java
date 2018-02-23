package com.revature.project2.dao;

import java.util.List;

import com.revature.project2.models.Posts;

public interface PostsDao {
	// CRUD methods go here
	// create methods:
	public void insertNewPost(Posts post);
	
	// select(read) methods:
	public List<Posts> selectAllPosts();
	public Posts selectSpecificPostById(int id);
	
	// update methods:
	public void updatePost(Posts post);
	
	// no delete methods
}
