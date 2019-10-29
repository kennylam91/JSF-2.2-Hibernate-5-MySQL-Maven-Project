package com.repository.impl;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Bean.Course;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCourse(Course course) {
		// TODO Auto-generated method stub

	}

	@Override
	public Course findCourseById(Long courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> findAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}

}
