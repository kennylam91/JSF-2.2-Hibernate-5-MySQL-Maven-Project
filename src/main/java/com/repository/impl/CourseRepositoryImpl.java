package com.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.Course;
import com.beans.pagination.Pagination;
import com.repository.CourseRepository;
import com.util.HibernateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "courseRepository")
@SessionScoped
public class CourseRepositoryImpl implements CourseRepository {

	private static final long serialVersionUID = -3195080766897367763L;

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public Long saveCourse(Course course) {
		Transaction transaction = null;
		Session session = null;
		boolean committed = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Long courseId = (Long) session.save(course);
			transaction.commit();
			committed = true;
			return courseId;
		} finally {
			if (session != null) {
				if (!committed && transaction != null)
					transaction.rollback();
				session.close();
			}
		}
	}

	@Override
	public void updateCourse(Course course) {
		Transaction transaction = null;
		Session session = null;
		boolean committed = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(course);
			System.out.println("update: " + course);
			transaction.commit();
			committed = true;
		} finally {
			if (session != null) {
				if (!committed && transaction != null)
					transaction.rollback();
				session.close();
			}
		}
	}

	@Override
	public void deleteCourse(Course course) {
		Transaction transaction = null;
		Session session = null;
		boolean committed = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(course);
			transaction.commit();
			committed = true;

		} finally {
			if (session != null) {
				if (!committed && transaction != null)
					transaction.rollback();
				session.close();
			}
		}

	}

	@Override
	public Course findCourseById(Long courseId) {
		Transaction transaction = null;
		Session session = null;
		boolean committed = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("Select c from Course c where c.id = :courseId");
			query.setParameter("courseId", courseId);
			@SuppressWarnings("unchecked")
			List<Course> courses = query.list();
			transaction.commit();
			committed = true;
			if (!courses.isEmpty())
				return courses.get(0);
			else
				return null;
		} finally {
			if (session != null) {
				if (!committed && transaction != null)
					transaction.rollback();
				session.close();
			}
		}
	}

	@Override
	public List<Course> findAllCourses() {
		Transaction transaction = null;
		Session session = null;
		boolean committed = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("Select c from Course c");
			@SuppressWarnings("unchecked")
			List<Course> courses = query.list();
			transaction.commit();
			committed = true;
			if (!courses.isEmpty())
				return courses;
			else
				return Collections.emptyList();
		} finally {
			if (session != null) {
				if (!committed && transaction != null)
					transaction.rollback();
				session.close();
			}
		}
	}

	@Override
	public List<Course> findAllCourses(Pagination pagination) {
		Transaction transaction = null;
		Session session = null;
		boolean committed = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String orderedBy = getCourseField(pagination.getOrderBy());
			String ascOrDesc = getAscOrDescParameter(pagination.getAscOrDesc());
			String fieldSearch = getCourseField(pagination.getSearchField());
			Query query;
			if (pagination.getOrderBy().equals("all")) {
				query = session.createQuery("Select c from Course c where " + "c.code like :searchKeyword or "
						+ "c.name like :searchKeyword or " + "c.beginTime like :searchKeyword or "
						+ "c.finishTime like :searchKeyword or " + "c.status like :searchKeyword or "
						+ "c.teacher like :searchKeyword or " + "c.capacity like :searchKeyword or "
						+ "c.description like :searchKeyword or " + "c.subject.name like :searchKeyword " + "order by "
						+ orderedBy + " " + ascOrDesc + ",c.code asc");
			} else {
				query = session.createQuery("Select c from Course c where " + fieldSearch + " " + "like :searchKeyword "
						+ "order by " + orderedBy + " " + ascOrDesc + ",c.code asc");
			}
			query.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
			@SuppressWarnings("unchecked")
			List<Course> courses = query.list();
			transaction.commit();
			committed = true;
			if (!courses.isEmpty())
				return courses;
			else
				return Collections.emptyList();
		} finally {
			if (session != null) {
				if (!committed && transaction != null)
					transaction.rollback();
				session.close();
			}
		}
	}

	private String getAscOrDescParameter(String ascOrDesc) {
		if (ascOrDesc.toLowerCase().equals("asc")) {
			return "asc";
		} else {
			return "desc";
		}
	}

	private String getCourseField(String orderBy) {
		switch (orderBy) {
		case "name":
			return "c.name";
		case "beginTime":
			return "c.beginTime";
		case "finishTime":
			return "c.finishTime";
		case "status":
			return "c.status";
		case "teacher":
			return "c.teacher";
		case "capacity":
			return "c.capacity";
		case "description":
			return "c.description";
		case "subject":
			return "c.subject";
		default:
			return "c.code";
		}
	}

}
