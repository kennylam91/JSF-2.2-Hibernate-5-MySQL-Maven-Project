package com.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.beans.CourseScoreDto;
import com.beans.Score;
import com.beans.Student;
import com.beans.StudentDto;
import com.beans.StudentFilter;
import com.beans.dto.ListStudentDto;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
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

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private final String SELECT_NEW_STUDENTDTO_SQL = "select new "
			+ "com.beans.StudentDto(s.id,s.code,s.firstName,s.lastName,s.gender,s.field,s.dob,s.phone,s.email,s.note,s.avgScore) "
			+ "from Student s where ";

	@SuppressWarnings("unchecked")
	public ListStudentDto findStudentsByPagination(Pagination pagination) {
		String orderedBy = getStudentField(pagination.getOrderBy());
		String ascOrDesc = getAscOrDescParameter(pagination.getAscOrDesc());
		String fieldSearch = getStudentField(pagination.getSearchField());
		Transaction transaction = null;
		Session session = null;
		StudentFilter filter = ((PaginationStudentList) pagination).getStudentFilter();
		ListStudentDto listStudentDto = new ListStudentDto();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.query.Query<StudentDto> query;
			org.hibernate.query.Query<Number> querySecond;

			// Query to get StudentDto List
			String queryString = SELECT_NEW_STUDENTDTO_SQL;

			// Query to get total Records
			String queryStringSecond = "select count(s.id) from Student s where ";

			// When using student filter function
			if (filter != null) {

				if (filter.getIsByGender().booleanValue()) {
					queryString += "s.gender = :genderFV" + " " + "and ";
					queryStringSecond += "s.gender = :genderFV" + " " + "and ";
				}
				if (filter.getIsByField().booleanValue()) {
					queryString += "s.field = :fieldFV" + " " + "and ";
					queryStringSecond += "s.field = :fieldFV" + " " + "and ";
				}
				if (filter.getIsByScore().booleanValue()) {
					queryString += "s.avgScore between :scoreFVFrom and :scoreFVTo" + " " + "and ";
					queryStringSecond +="s.avgScore between :scoreFVFrom and :scoreFVTo" + " " + "and ";
				}
				if (filter.getIsByDOB().booleanValue()) {
					queryString += "s.dob between :dobFVFrom and :dobFVTo" + " " + "and ";
					queryStringSecond+= "s.dob between :dobFVFrom and :dobFVTo" + " " + "and ";
				}
			}

			if (pagination.getSearchField().equals("all")) {
				queryString += "(s.code like :searchKeyword or " + "s.firstName like :searchKeyword or" + " "
						+ "s.lastName like :searchKeyword or " + "s.gender like :searchKeyword or" + " "
						+ "s.field like :searchKeyword or " + "s.phone like :searchKeyword or" + " "
						+ "s.email like :searchKeyword or " + "s.note like :searchKeyword" + " )";
				
				queryStringSecond += "(s.code like :searchKeyword or " + "s.firstName like :searchKeyword or" + " "
						+ "s.lastName like :searchKeyword or " + "s.gender like :searchKeyword or" + " "
						+ "s.field like :searchKeyword or " + "s.phone like :searchKeyword or" + " "
						+ "s.email like :searchKeyword or " + "s.note like :searchKeyword" + " )";
			} else {
				queryString += fieldSearch + " " + "like :searchKeyword" + " ";
				queryStringSecond +=fieldSearch + " " + "like :searchKeyword" + " ";

			}

			queryString += "order by " + orderedBy + " " + ascOrDesc + ",s.code asc";
			query = session.createQuery(queryString);
			querySecond = session.createQuery(queryStringSecond);
			query.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
			querySecond.setParameter("searchKeyword", "%" + pagination.getSearchKeyword() + "%");
			if (filter != null) {
				if (filter.getIsByGender().booleanValue()) {
					query.setParameter("genderFV", filter.getGenderFilterValue());
					querySecond.setParameter("genderFV", filter.getGenderFilterValue());
				}
				if (filter.getIsByField().booleanValue()) {
					query.setParameter("fieldFV", filter.getFieldFilterValue());
					querySecond.setParameter("fieldFV", filter.getFieldFilterValue());
				}
				if (filter.getIsByScore().booleanValue()) {
					query.setParameter("scoreFVFrom", filter.getScoreFilterFrom());
					query.setParameter("scoreFVTo", filter.getScoreFilterTo());
					querySecond.setParameter("scoreFVFrom", filter.getScoreFilterFrom());
					querySecond.setParameter("scoreFVTo", filter.getScoreFilterTo());
				}
				if (filter.getIsByDOB().booleanValue()) {
					query.setParameter("dobFVFrom", filter.getDOBFilterFrom());
					query.setParameter("dobFVTo", filter.getDOBFilterTo());
					querySecond.setParameter("dobFVFrom", filter.getDOBFilterFrom());
					querySecond.setParameter("dobFVTo", filter.getDOBFilterTo());
				}
			}
			query.setFirstResult((pagination.getPage() - 1) * pagination.getRowsPerPage());
			query.setMaxResults(pagination.getRowsPerPage());
			List<StudentDto> studentDtoList = query.getResultList();
			int totalRecords = querySecond.getSingleResult().intValue();
			transaction.commit();
			listStudentDto.setList(studentDtoList);
			listStudentDto.setTotalFoundRecords(totalRecords);
			return listStudentDto;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			
			return listStudentDto;
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkDuplicatedEmail(String email) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			org.hibernate.query.Query<Student> query = session
					.createQuery("select s from Student s where s.email = :email").setParameter("email", email);
			Student student = query.uniqueResult();
			transaction.commit();
			return (student != null);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotalRecords(Pagination paginationStudentList) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			/*
			 * The reason we are casting to Number is that some databases will return Long
			 * while others will return BigInteger, so for portability sake you are better
			 * off casting to a Number and getting an int or a long, depending on how many
			 * rows you are expecting to be counted
			 */
			org.hibernate.query.Query<Number> query = session.createQuery("select count(s.id) from Student s");
			int totalRecords = query.getSingleResult().intValue();
			transaction.commit();
			return totalRecords;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return 0;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Student findStudentByEmail(String userEmail) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			org.hibernate.query.Query<Student> query = session
					.createQuery("select s from Student s where s.email = :email")
					.setParameter("email", userEmail);
			Student student = query.uniqueResult();
			transaction.commit();
			return student;
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
	
	 

}
