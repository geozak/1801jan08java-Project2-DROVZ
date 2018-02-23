package com.revature.project2.dao;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.revature.project2.models.PostsPhotos;
import com.revature.project2.util.HibernateUtil;

public class PostsPhotosDaoImpl implements PostsPhotosDao {
	
	public PostsPhotosDaoImpl() {
	}

	// insert new post photo
	@Override
	public void insert(PostsPhotos photo) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(photo);
		tx.commit();
	}

	// grab all posts photos
	@Override
	public List<PostsPhotos> selectAllPostsPhotos() {
		return HibernateUtil.getSession().createCriteria(PostsPhotos.class).list();
	}

	// grab a post photos by photo id
	@Override
	public PostsPhotos selectPostsPhotoById(int id) {
		return (PostsPhotos)HibernateUtil.getSession().get(PostsPhotos.class, id);
	}
	
	// grab all post photos by post id
	@Override
	public List<PostsPhotos> selectPostsPhotosByPostId(int post_id) {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(PostsPhotos.class);
		
		List<PostsPhotos> photos = (List<PostsPhotos>)criteria.add(Restrictions.eq("post_id", post_id)).list();
		
		return photos;
	}

}
