package com.repository.impl;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.repository.StudentRepository;
import com.util.HibernateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "studentRepository")
@SessionScoped
public class StudentRepositoryImpl implements StudentRepository {

	private static final long serialVersionUID = -6478480089267477363L;

	private static final Logger logger = Logger.getLogger(StudentRepositoryImpl.class);

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<StudentDto> findAllStudents() {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select new "
				+ "com.beans.StudentDto(s.id,s.code,s.firstName,s.lastName,s.gender,s.field,s.dob,s.phone,s.email,s.note,s.avgScore) "
				+ "from Student s " + "order by s.code ");
		query.setMaxResults(20);
		List<StudentDto> studentDtoList = query.list();
		session.close();
		return studentDtoList;
	}

	@SuppressWarnings("unchecked")
	public List<StudentDto> findStudentsByPagination(Pagination pagination) {
		String orderedBy = getStudentField(pagination.getOrderBy());
		String ascOrDesc = getAscOrDescParameter(pagination.getAscOrDesc());
		String fieldSearch = getStudentField(pagination.getSearchField());
		Session session = this.sessionFactory.openSession();
		Query query;
		if (pagination.getSearchField().equals("all")) {
			query = session.createQuery("select new "
					+ "com.beans.StudentDto(s.id,s.code,s.firstName,s.lastName,s.gender,s.field,s.dob,s.phone,s.email,s.note,s.avgScore) "
					+ "from Student s " + "where s.code like :searchKeyword or" + " "
					+ "s.firstName like :searchKeyword or" + " " + "s.lastName like :searchKeyword or" + " "
					+ "s.gender like :searchKeyword or" + " " + "s.field like :searchKeyword or" + " "
					+ "s.dob like :searchKeyword or" + " " + "s.phone like :searchKeyword or" + " "
					+ "s.email like :searchKeyword or" + " " + "s.avgScore like :searchKeyword or" + " "
					+ "s.note like :searchKeyword " + " " + "order by " + orderedBy + " " + ascOrDesc + ",s.code asc");

			query.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
		} else {
			query = session.createQuery("select new "
					+ "com.beans.StudentDto(s.id,s.code,s.firstName,s.lastName,s.gender,s.field,s.dob,s.phone,s.email,s.note,s.avgScore) "
					+ "from Student s " + "where " + fieldSearch + " " + "like :searchKeyword" + " " + "order by "
					+ orderedBy + " " + ascOrDesc + ",s.code asc");

			query.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
		}

		query.setFirstResult((pagination.getPage() - 1) * pagination.getRowsPerPage());
		query.setMaxResults(pagination.getRowsPerPage());
		List<StudentDto> studentDtoList = query.list();
		session.close();
		return studentDtoList;
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
			logger.error("exception: ", e);
		} finally {
			if (session != null)
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

//			Student student = session.get(Student.class, studentId);
			Query query = session
					.createQuery("select s from Student s " + "left join fetch s.courses " + "where s.id = :studentId")
					.setParameter("studentId", studentId);
			Student student = (Student) query.uniqueResult();
			transaction.commit();
			return student;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}

	private String getStudentField(String orderedBy) {
		switch (orderedBy) {
		case "firstName":
			return "s.firstName";
		case "lastName":
			return "s.lastName";
		case "field":
			return "s.field";
		case "dob":
			return "s.dob";
		case "gender":
			return "s.gender";
		case "phone":
			return "email";
		case "avgScore":
			return "s.avgScore";
		case "score":
			return "s.score";
		default:
			return "s.code";
		}
	}

	private String getAscOrDescParameter(String ascOrDesc) {
		if (ascOrDesc.equalsIgnoreCase("desc"))
			return "desc";
		return "asc";
	}

}
