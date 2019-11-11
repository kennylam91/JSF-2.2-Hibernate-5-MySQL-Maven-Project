package com.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Long courseId = (Long) session.save(course);
			transaction.commit();
			return courseId;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateCourse(Course course) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(course);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void deleteCourse(Course course) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(course);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Course findCourseById(Long courseId) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<Course> query = session
					.createQuery("Select c from Course c where c.id = :courseId");
			query.setParameter("courseId", courseId);
			List<Course> courses = query.list();
			if (!courses.isEmpty())
				return courses.get(0);
			else
				return null;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> findAllCourses() {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<Course> query = session.createQuery(
					"Select c.id, c.name, c.subject.name, c.beginTime, c.finishTime, c.status, c.teacher, c.capacity "
							+ "from Course c");
			List<Course> courses = query.list();
			transaction.commit();
			if (!courses.isEmpty())
				return courses;
			else
				return Collections.emptyList();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return Collections.emptyList();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> findAllCoursesByPagination(Pagination pagination) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String orderedBy = getCourseField(pagination.getOrderBy());
			String ascOrDesc = getAscOrDescParameter(pagination.getAscOrDesc());
			String fieldSearch = getCourseField(pagination.getSearchField());
			org.hibernate.query.Query<Course> query;
			if (pagination.getSearchField().equals("all")) {
				query = session.createQuery("Select c from Course c where " + "c.code like :searchKeyword or "
						+ "c.name like :searchKeyword or " + "c.status like :searchKeyword or "
						+ "c.teacher like :searchKeyword or " + "c.description like :searchKeyword or "
						+ "c.subject like :searchKeyword " + "order by " + orderedBy + " " + ascOrDesc
						+ ",c.code asc");
			} else {
				query = session.createQuery("Select c from Course c where " + fieldSearch + " " + "like :searchKeyword "
						+ "order by " + orderedBy + " " + ascOrDesc + ",c.code asc");
			}
			query.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
			query.setFirstResult((pagination.getPage() - 1) * pagination.getRowsPerPage());
			query.setMaxResults(pagination.getRowsPerPage());
			List<Course> courses = query.getResultList();
			transaction.commit();
			if (!courses.isEmpty())
				return courses;
			else
				return Collections.emptyList();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return Collections.emptyList();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private String getAscOrDescParameter(String ascOrDesc) {
		if (ascOrDesc.equalsIgnoreCase("asc")) {
			return "asc";
		} else {
			return "desc";
		}
	}

	private String getCourseField(String orderBy) {
		switch (orderBy) {
		case "name":
			return "c.name";
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
		case "studentsNo":
			return "c.studentsNo";
		default:
			return "c.code";
		}
	}

}
