package com.revature.project2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.project2.models.Posts;
import com.revature.project2.util.HibernateUtil;

public class PostsDaoImpl implements PostsDao {
	public PostsDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	// insert a new post entry
	@Override
	public void insertNewPost(Posts post) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(post);
		
		tx.commit();
	}

	// grab all the posts made by all the trainers
	@Override
	public List<Posts> selectAllPosts() {
		return HibernateUtil.getSession().createCriteria(Posts.class).list();
	}

	// grab post by id
	@Override
	public Posts selectSpecificPostById(int id) {
		return (Posts)HibernateUtil.getSession().get(Posts.class, id);
	}

	// update a post (if we include the functionality of editting a post)
	@Override
	public void updatePost(Posts post) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.update(post);
		
		tx.commit();
	}

}
