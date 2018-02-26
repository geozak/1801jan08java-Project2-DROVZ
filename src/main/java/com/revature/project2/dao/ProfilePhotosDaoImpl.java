package com.revature.project2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.project2.models.ProfilePhotos;
import com.revature.project2.util.HibernateUtil;

public class ProfilePhotosDaoImpl implements ProfilePhotosDao {

	public ProfilePhotosDaoImpl() {
	}
	
	// insert new photo
	@Override
	public void insert(ProfilePhotos photo) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(photo);
		tx.commit();
	}

	// grab all photos
	@Override
	public List<ProfilePhotos> selectAllProfilePhotos() {
		return HibernateUtil.getSession().createCriteria(ProfilePhotos.class).list();
	}

	// grab photo by the id
	@Override
	public ProfilePhotos selectProfilePhotoById(int id) {
		return (ProfilePhotos)HibernateUtil.getSession().get(ProfilePhotos.class, id);
	}

}
