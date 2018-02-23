package com.revature.project2.dao;

import java.util.List;

import com.revature.project2.models.ProfilePhotos;

public interface ProfilePhotosDao {
	// CRUD methods only
	
	// insert
	public void insert(ProfilePhotos photo);
	
	//select
	public List<ProfilePhotos> selectAllProfilePhotos();
	public ProfilePhotos selectProfilePhotoById(int id);
}
