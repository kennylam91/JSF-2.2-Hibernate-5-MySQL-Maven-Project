package com.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Bean.Student;
import com.util.HibernateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "studentRepository")
@SessionScoped
public class StudentRepositoryImpl implements Serializable, StudentRepository {

	private static final long serialVersionUID = -6478480089267477363L;

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<Student> findAllStudents() {
		Session session = this.sessionFactory.openSession();
		Criteria cr = session.createCriteria(Student.class);
		List<Student> customerList = cr.list();
		session.close();
		return customerList;
	}

	public Long saveStudent(Student student) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Long id = (Long) session.save(student);
		transaction.commit();
		session.close();
		return id;
	}

	public void updateStudent(Student student) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			session.saveOrUpdate(student);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteStudent(Long studentId) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Student student = session.get(Student.class, studentId);
		session.delete(student);
		transaction.commit();
		session.close();

	}

	public Student findStudentById(Long studentId) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			Student student = session.get(Student.class, studentId);

			transaction.commit();
			return student;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}finally {
			session.close();
		}

		return null;
	}

}
