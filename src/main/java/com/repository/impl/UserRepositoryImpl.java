package com.repository.impl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.User;
import com.repository.UserRepository;
import com.util.HibernateUtil;

@ManagedBean(name = "userRepository")
@SessionScoped
public class UserRepositoryImpl implements UserRepository{

	private static final long serialVersionUID = 2213169556142957993L;
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public Long save(User user) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Long userId = (Long) session.save(user);
			transaction.commit();
			return userId;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	
}
