package com.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.Score;
import com.repository.ScoreRepository;
import com.util.HibernateUtil;

@ManagedBean(name = "scoreRepository")
@SessionScoped
public class ScoreRepositoryImpl implements ScoreRepository {

	private static final long serialVersionUID = -1703957031646067590L;
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Logger logger = Logger.getLogger(ScoreRepositoryImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Score> findScoresByCourseId(Long courseId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<Score> query = session.createQuery("SELECT s from Score s where s.courseId = :courseId");
			query.setParameter("courseId", courseId);
			List<Score> scores =  query.getResultList();
			transaction.commit();
			return scores;
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
			return Collections.emptyList();
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

}
