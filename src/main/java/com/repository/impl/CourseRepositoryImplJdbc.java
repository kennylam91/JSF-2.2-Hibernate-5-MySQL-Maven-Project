package com.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.beans.Course;
import com.beans.pagination.Pagination;
import com.constant.COURSE_STATUSES;
import com.repository.CourseRepository;
import com.service.CourseService;
import com.service.SubjectService;
import com.util.JdbcConnection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "courseRepositoryJdbc")
@SessionScoped
public class CourseRepositoryImplJdbc implements CourseRepository{

	private static final long serialVersionUID = -8821446885745520303L;
	Connection con = null;
	PreparedStatement ps = null;

	@Override
	public Long saveCourse(Course course) {
		int affectedRow = 0;
		Long id = null;
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "INSERT INTO courses (course_code, course_name, begin_time, status, teacher, "
					+ "						capacity, description, subject_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, course.getCode());
			ps.setString(2, course.getName());
			ps.setTimestamp(3, new Timestamp(course.getBeginTime().getTime()));
			ps.setString(4, course.getStatus().toString());
			ps.setString(5, course.getTeacher());
			ps.setInt(6, course.getCapacity());
			ps.setString(7, course.getDescription());
			ps.setInt(8, (int)course.getSubject().getId().longValue());
			affectedRow = ps.executeUpdate();
			con.commit();
			if(affectedRow > 0) {
				try {
					ResultSet rs = ps.getGeneratedKeys();
					if(rs.next()) {
						id = (long) rs.getInt("course_id");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return id;
		
	}

	@Override
	public void updateCourse(Course course) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "UPDATE	courses "
					+ "SET"
					+ "		course_name = ? , "
					+ "		begin_time = ? , "
					+ "		status = ? , "
					+ "		teacher = ? , "
					+ "		capacity = ? , "
					+ "		description = ? , "
					+ "		subject_id = ? ";
			StringBuilder sqlBuilder = new StringBuilder(sql);
			if(course.getFinishTime() != null) {
				sqlBuilder.append(", finish_time = ? ");
			}
			sqlBuilder.append(""
					+ "WHERE course_id = ?");
			ps = con.prepareStatement(sqlBuilder.toString());
			int i = 1;
			ps.setString(i++, course.getName());
			ps.setTimestamp(i++, new Timestamp(course.getBeginTime().getTime()));
			ps.setString(i++, course.getStatus().toString());
			ps.setString(i++, course.getTeacher());
			ps.setInt(i++, course.getCapacity());
			ps.setString(i++, course.getDescription());
			ps.setLong(i++, course.getSubject().getId());
			if(course.getFinishTime() != null)
				ps.setTimestamp(i++, new Timestamp(course.getFinishTime().getTime()));
			ps.setInt(i++, (int)course.getId().longValue());
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	@Override
	public void deleteCourse(Course course) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "DELETE FROM courses "
					+ "WHERE course_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)course.getId().longValue());
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public Course findCourseById(Long courseId) {
		Course course = null;
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM 	courses "
					+ "WHERE 	course_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)courseId.longValue());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				course = Course.builder()
						.id(courseId)
						.code(rs.getString("course_code"))
						.name(rs.getString("course_name"))
						.beginTime(rs.getDate("begin_time"))
						.finishTime(rs.getDate("finish_time"))
						.status(COURSE_STATUSES.valueOf(rs.getString("status")))
						.teacher(rs.getString("teacher"))
						.capacity(rs.getInt("capacity"))
						.description(rs.getString("description"))
						.studentsNo(rs.getInt("student_number"))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return course;
	}

	@Override
	public List<Course> findAllCourses() {
		List<Course> courses = new LinkedList<>();
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM 	courses ";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Course course = Course.builder()
						.id(rs.getLong("course_id"))
						.code(rs.getString("course_code"))
						.name(rs.getString("course_name"))
						.beginTime(rs.getDate("begin_time"))
						.finishTime(rs.getDate("finish_time"))
						.status(COURSE_STATUSES.valueOf(rs.getString("status")))
						.teacher(rs.getString("teacher"))
						.capacity(rs.getInt("capacity"))
						.description(rs.getString("description"))
						.studentsNo(rs.getInt("student_number"))
						.build();
				courses.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return courses;
	}

	@Override
	public List<Course> findAllCoursesByPagination(Pagination pagination) {
		List<Course> courses = new LinkedList<>();
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM 	courses "
					+ "WHERE ";
			StringBuilder sqlBuilder = new StringBuilder(sql);
			if(pagination.getSearchField().contentEquals("all")) {
				sqlBuilder.append("CONCAT_WS(' ', course_id, course_code, course_name, begin_time, finish_time, "
						+ "status, teacher, capacity, description, student_number) ~ ? ");
			}
			else {
				sqlBuilder.append(getCourseField(pagination.getSearchField()))
				.append(" ~ ?");
			}
			sqlBuilder.append("ORDER BY ")
			.append(getCourseField(pagination.getOrderBy()))
			.append(" OFFSET ?")
			.append(" LIMIT ?");
			ps = con.prepareStatement(sqlBuilder.toString());
			int offset = (pagination.getPage() -1)*pagination.getRowsPerPage();
			int limit = pagination.getRowsPerPage();
			ps.setString(1, pagination.getSearchKeyword());
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Course course = Course.builder()
						.id(rs.getLong("course_id"))
						.code(rs.getString("course_code"))
						.name(rs.getString("course_name"))
						.beginTime(rs.getDate("begin_time"))
						.finishTime(rs.getDate("finish_time"))
						.status(COURSE_STATUSES.valueOf(rs.getString("status")))
						.teacher(rs.getString("teacher"))
						.capacity(rs.getInt("capacity"))
						.description(rs.getString("description"))
						.studentsNo(rs.getInt("student_number"))
						.build();
				courses.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return courses;
	}
	private String getCourseField(String input) {
		switch (input) {
		case "name":
			return "course_name";
		case "status":
			return "status";
		case "teacher":
			return "teacher";
		case "capacity":
			return "capacity";
		case "description":
			return "description";
		case "studentsNo":
			return "student_number";
		case "finishTime":
			return "finish_time";
		case "beginTime":
			return "begin_time";
		default:
			return "course_code";
		}
	}
	
}
