package com.repository.impl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.User;
import com.repository.UserRepository;
import com.util.HibernateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "userRepository")
@SessionScoped
public class UserRepositoryImpl implements UserRepository {

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

	@Override
	public User validateUser(User user) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			User userFound = (User) session.createQuery("Select u from User u where u.email = :email")
					.setParameter("email", user.getEmail()).uniqueResult();
			transaction.commit();
			if (userFound == null)
				return null;
			else {
				if (userFound.getPassword().equals(user.getPassword())) {
					
					return User.builder().email(userFound.getEmail())
							.username(userFound.getUsername())
							.authority(userFound.getAuthority())
							.build();
				}
				return null;
			}

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
