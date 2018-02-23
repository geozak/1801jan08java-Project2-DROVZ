package com.revature.project2;

import com.revature.project2.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
		HibernateUtil.getSession();
		
		HibernateUtil.closeCurrentSession();
		System.out.println("done");
	}
}
