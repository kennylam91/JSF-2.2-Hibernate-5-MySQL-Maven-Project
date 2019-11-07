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
import com.beans.ScoreDto;
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
			org.hibernate.query.Query<Score> query = session
					.createQuery("SELECT s from Score s where s.courseId = :courseId");
			query.setParameter("courseId", courseId);
			List<Score> scores = query.getResultList();
			transaction.commit();
			return scores;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
			return Collections.emptyList();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreDto> findScoreDtosByCourseId(Long courseId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<ScoreDto> query = session.createQuery(
					"SELECT new com.beans.ScoreDto(s.id,s.studentId,s.courseId,s.score,st.code,st.firstName,st.lastName,st.field) "
							+ "from Score s left join Student st on s.studentId = st.id where s.courseId = :courseId");
			query.setParameter("courseId", courseId);
			List<ScoreDto> scores = query.getResultList();
			transaction.commit();
			return scores;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
			return Collections.emptyList();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void saveAll(Set<Score> scores) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			for (Score score : scores) {
				@SuppressWarnings("unchecked")
				List<Score> foundScores = session
						.createQuery("Select s.courseId, s.studentId from Score s "
								+ "where s.courseId = :courseId and s.studentId = :studentId")
						.setParameter("courseId", score.getCourseId()).setParameter("studentId", score.getStudentId())
						.getResultList();
				if (foundScores.isEmpty()) {
					session.save(score);
				} else {
					session.createQuery("update Score s " + "set s.score = :score "
							+ "where s.courseId = :courseId and s.studentId = :studentId")
							.setParameter("score", score.getScore()).setParameter("courseId", score.getCourseId())
							.setParameter("studentId", score.getStudentId()).executeUpdate();
				}
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public void delete(Long courseId, Long studentId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("Delete from Score s where s.courseId = :courseId and s.studentId = :studentId")
					.setParameter("courseId", courseId).setParameter("studentId", studentId).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public float findScoreByCourseIdAndStudentId(Long courseId, Long studentId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Score score = (Score) session
					.createQuery("Select s from Score s " + "where s.courseId = :courseId and s.studentId = :studentId")
					.setParameter("courseId", courseId).setParameter("studentId", studentId).getSingleResult();

			if (score != null) {
				return score.getScore();
			} else {
				return -1.0f;
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
			return -2.0f;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

}
