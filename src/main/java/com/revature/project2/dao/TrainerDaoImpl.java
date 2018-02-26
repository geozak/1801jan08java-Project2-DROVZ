package com.revature.project2.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.revature.project2.models.Trainer;
import com.revature.project2.util.HibernateUtil;

public class TrainerDaoImpl implements TrainerDao {
	public TrainerDaoImpl() {
		super();
	}

	// add a new trainer
	@Override
	public void insertNewTrainer(Trainer trainer) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(trainer);
		
		tx.commit();
	}

	// grab all trainers
	@Override
	public List<Trainer> selectAllTrainers() {
		return HibernateUtil.getSession().createCriteria(Trainer.class).list();
	}

	// grab trainer by id
	@Override
	public Trainer selectTrainerById(int id) {
		return (Trainer)HibernateUtil.getSession().get(Trainer.class, id);
	}

	// grab trainer by email
	@Override
	public Trainer selectTrainerByEmail(String email) {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(Trainer.class);
		
		Trainer trainer = (Trainer)criteria.add(Restrictions.eq("trainer_email", email)).uniqueResult();
		
		return trainer;
	}

	// grab trainer by first name
	@Override
	public List<Trainer> selectTrainerByFirstname(String firstname) {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(Trainer.class);
		
		List<Trainer> trainers = (List<Trainer>)criteria.add(Restrictions.eq("trainer_first_name", firstname)).list();
		
		return trainers;
	}

	// grab trainer by last name
	@Override
	public List<Trainer> selectTrainerByLastname(String lastname) {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(Trainer.class);
		
		List<Trainer> trainers = (List<Trainer>)criteria.add(Restrictions.eq("trainer_last_name", lastname)).list();
		
		return trainers;
	}

	// update trainer
	@Override
	public void updateTrainer(Trainer trainer) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.update(trainer);
		
		tx.commit();
	}
	
	
}
