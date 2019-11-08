package com.repository.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.CourseScoreDto;
import com.beans.Score;
import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.constant.FIELDS;
import com.constant.GENDERS;
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

	private final String SELECT_NEW_STUDENTDTO_SQL = "select new "
			+ "com.beans.StudentDto(s.id,s.code,s.firstName,s.lastName,s.gender,s.field,s.dob,s.phone,s.email,s.note,s.avgScore) "
			+ "from Student s ";

	@SuppressWarnings("unchecked")
	public List<StudentDto> findStudentsByPagination(Pagination pagination) {
		String orderedBy = getStudentField(pagination.getOrderBy());
		String ascOrDesc = getAscOrDescParameter(pagination.getAscOrDesc());
		String fieldSearch = getStudentField(pagination.getSearchField());
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<StudentDto> query;
			String queryString = SELECT_NEW_STUDENTDTO_SQL;
			if (pagination.getSearchField().equals("all")) {
				queryString += "where s.code like :searchKeyword or "
						+ "s.firstName like :searchKeyword or" + " " + "s.lastName like :searchKeyword or "
						+ "s.gender like :searchKeyword or" + " " + "s.field like :searchKeyword or "
						+ "s.phone like :searchKeyword or" + " " + "s.email like :searchKeyword or "
						+ "s.note like :searchKeyword " + " " + "order by " + orderedBy + " " + ascOrDesc
						+ ",s.code asc";
				query = session.createQuery(queryString);
				query.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
			} else {
				queryString += "where " + fieldSearch + " "
						+ "like :searchKeyword " + "order by " + orderedBy + " " + ascOrDesc + ",s.code asc";
				query = session.createQuery(queryString);

				query.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
			}

			query.setFirstResult((pagination.getPage() - 1) * pagination.getRowsPerPage());
			query.setMaxResults(pagination.getRowsPerPage());
			List<StudentDto> studentDtoList = query.getResultList();
			transaction.commit();
			return studentDtoList;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error(e);
			return Collections.emptyList();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	public Long saveStudent(Student student) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Long studentId = (Long) session.save(student);
			transaction.commit();
			return studentId;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			logger.error(e);
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void updateStudent(Student student) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(student);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void deleteStudent(Student student) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(student);
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
	public void deleteStudentList(List<Long> Ids) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("DELETE FROM Student s WHERE s.id IN (:Ids)").setParameter("Ids", Ids).executeUpdate();
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

	@SuppressWarnings("unchecked")
	public Student findStudentById(Long studentId) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			org.hibernate.query.Query<Student> query = session
					.createQuery("select s from Student s where s.id = :studentId")
					.setParameter("studentId", studentId);
			Student student = query.uniqueResult();
			transaction.commit();
			return student;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> findStudentsByIds(List<Long> Ids) {

		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			List<Student> students = session.createQuery("SELECT s FROM Student s WHERE s.id IN (:Ids)")
					.setParameter("Ids", Ids).list();
			transaction.commit();
			return students;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e);
			return Collections.emptyList();
		} finally {
			if (session != null)
				session.close();
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public void updateStudentAvgScore(Long studentId) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			List<CourseScoreDto> courseScoreDtoList = session
					.createQuery("Select new com.beans.CourseScoreDto(c.id, c.subject.id, c.subject.coefficient) "
							+ "from Course c join c.students s "
							+ "where s.id = :studentId and c.status like 'COMPLETED'")
					.setParameter("studentId", studentId).getResultList();
			for (CourseScoreDto courseScoreDto : courseScoreDtoList) {
				Score score = (Score) session
						.createQuery(
								"Select s from Score s " + "where s.courseId = :courseId and s.studentId = :studentId")
						.setParameter("courseId", courseScoreDto.getCourseId()).setParameter("studentId", studentId)
						.getSingleResult();
				courseScoreDto.setScore(score.getScore());

			}
			Collections.sort(courseScoreDtoList);
			filterCourseScoreListBySubject(courseScoreDtoList);
			float avgScore = getAvgScore(courseScoreDtoList);
			session.createQuery("update Student s " + "set s.avgScore = :avgScore " + "where s.id = :studentId")
					.setParameter("avgScore", avgScore).setParameter("studentId", studentId).executeUpdate();

			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	 

	public void filterCourseScoreListBySubject(List<CourseScoreDto> courseScoreDtoList) {
		for (int i = 0; i < courseScoreDtoList.size() - 1; i++) {
			if (courseScoreDtoList.get(i).getSubjectId().equals(courseScoreDtoList.get(i + 1).getSubjectId())) {
				if (courseScoreDtoList.get(i).getScore() <= courseScoreDtoList.get(i + 1).getScore()) {
					courseScoreDtoList.remove(i);
					i--;
				} else {
					courseScoreDtoList.remove(i + 1);
					i--;
				}
			}
		}
	}

	public float getAvgScore(List<CourseScoreDto> courseScoreDtoList) {
		float coeffSum = 0;
		float total = 0;
		for (CourseScoreDto item : courseScoreDtoList) {
			coeffSum += item.getCoefficient();
			total += item.getCoefficient() * item.getScore();
		}
		float avgScore = 0;
		if (coeffSum > 0) {
			avgScore = total / coeffSum;
		}
		return avgScore;
	}
	
	

}
