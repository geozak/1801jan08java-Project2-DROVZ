package com.revature.project2.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sf = 
			new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	private static Session session;
	
	public static Session createSession() {
		return sf.openSession();
	}
	
	public static Session getSession() {
		if (session == null)
			session = createSession();
		
		return session;
	}
	
	public static void closeCurrentSession() {
		if (session != null)
			session.close();
	}
}
