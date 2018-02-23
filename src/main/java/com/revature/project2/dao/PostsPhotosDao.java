package com.revature.project2.dao;

import java.util.List;

import com.revature.project2.models.PostsPhotos;

public interface PostsPhotosDao {
	// CRUD methods only

	// insert
	public void insert(PostsPhotos photo);

	// select
	public List<PostsPhotos> selectAllPostsPhotos();
	public PostsPhotos selectPostsPhotoById(int id);
	public List<PostsPhotos> selectPostsPhotosByPostId(int post_id);

}
