package com.repository.impl;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Bean.Subject;
import com.controller.StudentController;
import com.repository.SubjectRepository;
import com.util.HibernateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "subjectRepository")
@SessionScoped
public class SubjectRepositoryImpl implements SubjectRepository, Serializable {

	private static final long serialVersionUID = 6441286228975302099L;

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private static final Logger logger = Logger.getLogger(SubjectRepositoryImpl.class);

	@Override
	public Long saveSubject(Subject subject) {

		Session session = null;
		Transaction transaction = null;
		try {
			session = this.sessionFactory.openSession();
			transaction = session.beginTransaction();
			Long subjectId = (Long) session.save(subject);
			transaction.commit();
			return subjectId;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error("exception: ", e);
			return null;
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public void updateSubject(Subject subject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSubject(Long subjectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Subject findSubjectById(Long subjectId) {
		// TODO Auto-generated method stub
		return null;
	}

}
