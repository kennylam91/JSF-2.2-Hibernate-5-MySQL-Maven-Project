package com.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.Subject;
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
public class SubjectRepositoryImpl implements SubjectRepository {

	private static final long serialVersionUID = -1428662570058705569L;

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
			logger.error(e);
			return null;
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public void updateSubject(Subject subject) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(subject);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public void deleteSubject(Subject subject) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(subject);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Subject findSubjectById(Long subjectId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<Subject> query = session
					.createQuery("Select s from Subject s where s.id = :subjectId");
			query.setParameter("subjectId", subjectId);
			Subject subject = query.uniqueResult();
			transaction.commit();
			return subject;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error(e);
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> findAllSubjects() {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<Subject> query = session.createQuery("Select s from Subject s");
			List<Subject> subjects = query.list();
			transaction.commit();
			return subjects;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error(e);
			return Collections.emptyList();
		} finally {
			if (session != null)
				session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Subject findSubjectByName(String name) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<Subject> query = session
					.createQuery("Select s from Subject s where s.name = :name");
			query.setParameter("name", name.trim());
			Subject subject = query.uniqueResult();
			transaction.commit();
			return subject;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error(e);
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
