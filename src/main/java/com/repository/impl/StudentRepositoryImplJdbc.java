package com.repository.impl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.StudentFilter;
import com.beans.dto.ListStudentDto;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
import com.constant.FIELDS;
import com.constant.GENDERS;
import com.repository.StudentRepository;
import com.util.JdbcConnection;

public class StudentRepositoryImplJdbc implements StudentRepository {

	private static final long serialVersionUID = 4964168177811809187L;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	@Override
	public Long saveStudent(Student student) {
		int affectedRow = 0;
		Long id = null;
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "INSERT INTO students"
					+ "(student_code, first_name, last_name, date_of_birth, gender, field, address, phone_number, email, note) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, student.getCode());
			ps.setString(2, student.getFirstName());
			ps.setString(3, student.getLastName());
			ps.setTimestamp(4, new Timestamp(student.getDob().getTime()));
			ps.setString(5, student.getGender().toString());
			ps.setString(6, student.getField().toString());
			ps.setString(7, student.getAddress());
			ps.setString(8, student.getPhone());
			ps.setString(9, student.getEmail());
			ps.setString(10, student.getNote());
			affectedRow = ps.executeUpdate();
			if (affectedRow > 0) {
				try {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						id = rs.getLong("student_id");
					}
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} finally {
			try {
				if (ps != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public void updateStudent(Student student) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = "" 
					+ "UPDATE students " 
					+ "SET 	first_name = ?, " 
					+ "		last_name = ?, "
					+ "		date_of_birth = ?, " 
					+ "		gender = ?, " 
					+ "		field = ?, "
					+ "		address = ?, " 
					+ "		phone_number = ?, " 
					+ "		email = ?, " 
					+ "		note = ? "
					+ "WHERE student_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getLastName());
			ps.setTimestamp(3, new Timestamp(student.getDob().getTime()));
			ps.setString(4, student.getGender().toString());
			ps.setString(5, student.getField().toString());
			ps.setString(6, student.getAddress());
			ps.setString(7, student.getPhone());
			ps.setString(8, student.getEmail());
			ps.setString(9, student.getNote());
			ps.setInt(10, (int) student.getId().longValue());
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public void deleteStudent(Student student) throws Exception {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "DELETE FROM students "
					+ "WHERE student_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)student.getId().longValue());
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public Student findStudentById(Long studentId) {
		Student student = null;
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	student_id, student_code, first_name, last_name, date_of_birth, "
					+ "			gender, field, address, phone_number, email, note, avg_score "
					+ "FROM		students "
					+ "WHERE	student_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)studentId.longValue());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				student = Student.builder()
						.id(studentId)
						.code(rs.getString("student_code"))
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.dob(rs.getDate("date_of_birth"))
						.gender(GENDERS.valueOf(rs.getString("gender")))
						.field(FIELDS.valueOf(rs.getString("field")))
						.address(rs.getString("address"))
						.phone(rs.getString("phone_number"))
						.email(rs.getString("email"))
						.note(rs.getString("note"))
						.avgScore(rs.getFloat("avg_score"))
						.build();
			return student;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return student;
		} finally {
			try {
				if(ps != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return student;
	}

	@Override
	public ListStudentDto findStudentsByPagination(Pagination pagination) {
		ListStudentDto listStudentDto = new ListStudentDto();
		List<StudentDto> studentDtoList = new LinkedList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	student_id, student_code, first_name, last_name, "
					+ "			gender, field, date_of_birth, phone_number, email, note, avg_score "
					+ "FROM		students "
					+ "WHERE ";
			StringBuilder sqlBuilder = new StringBuilder(sql);
			StudentFilter filter = ((PaginationStudentList) pagination).getStudentFilter();
			if(filter.getIsByGender().booleanValue()) {
				sqlBuilder.append("gender = ").append("'")
				.append(filter.getGenderFilterValue().toString()).append("'");
			}
			if(filter.getIsByField().booleanValue()) {
				sqlBuilder.append(" AND ")
				.append("field = ").append("'")
				.append(filter.getFieldFilterValue().toString())
				.append("'");
			}
			if(filter.getIsByDOB().booleanValue()) {
				String dobFrom = dateFormat.format(filter.getDOBFilterFrom());
				String dobTo = dateFormat.format(filter.getDOBFilterTo());
				sqlBuilder.append(" AND ")
				.append("date_of_birth BETWEEN ")
				.append("'").append(dobFrom).append("'")
				.append(" AND ")
				.append("'").append(dobTo).append("'");
			}
			if(filter.getIsByScore().booleanValue()) {
				sqlBuilder.append(" AND ")
				.append("avg_score BETWEEN ")
				.append(filter.getScoreFilterFrom()).append(" AND ")
				.append(filter.getScoreFilterTo()).append(" ");
			}
			ps = con.prepareStatement(sqlBuilder.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StudentDto studentDto = StudentDto.builder()
						.id(rs.getLong("student_id"))
						.code(rs.getString("student_code"))
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.dob(rs.getDate("date_of_birth"))
						.gender(GENDERS.valueOf(rs.getString("gender")))
						.field(FIELDS.valueOf(rs.getString("field")))
						.phone(rs.getString("phone_number"))
						.email(rs.getString("email"))
						.note(rs.getString("note"))
						.avgScore(rs.getFloat("avg_score"))
						.build();
				studentDtoList.add(studentDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		listStudentDto.setList(studentDtoList);
		return listStudentDto;
	}

	@Override
	public void deleteStudentList(List<Long> StudentIdList) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "DELETE FROM students "
					+ "WHERE student_id = ?";
			ps = con.prepareStatement(sql);
			for (Long id : StudentIdList) {
				ps.setInt(1, (int)id.longValue());
				ps.addBatch();
			}
			ps.executeBatch();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public List<Student> findStudentsByIds(List<Long> Ids) {
		Student student = null;
		List<Student> students = new LinkedList<>();
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM		students "
					+ "WHERE	student_id = ?";
			StringBuilder sqlBuilder = new StringBuilder(sql);
			for (int i=0;i<Ids.size()-1;i++) {
				sqlBuilder.append(" OR student_id =?");
			}
			ps = con.prepareStatement(sqlBuilder.toString());
			for(int i=0;i<Ids.size();i++) {
				ps.setInt(i+1, (int)Ids.get(i).longValue());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				student = Student.builder()
						.id(rs.getLong("student_id"))
						.code(rs.getString("student_code"))
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.dob(rs.getDate("date_of_birth"))
						.gender(GENDERS.valueOf(rs.getString("gender")))
						.field(FIELDS.valueOf(rs.getString("field")))
						.address(rs.getString("address"))
						.phone(rs.getString("phone_number"))
						.email(rs.getString("email"))
						.note(rs.getString("note"))
						.avgScore(rs.getFloat("avg_score"))
						.build();
				students.add(student);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return students;
	}

	@Override
	public void updateStudentAvgScore(Long studentId) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkDuplicatedEmail(String email) {
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	1 "
					+ "FROM		students "
					+ "WHERE	email = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public int getTotalRecords(Pagination paginationStudentList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Student findStudentByEmail(String userEmail) {
		Student student = null;
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	student_id, student_code, first_name, last_name, date_of_birth, "
					+ "			gender, field, address, phone_number, email, note, avg_score "
					+ "FROM		students "
					+ "WHERE	email = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userEmail);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				student = Student.builder()
						.id(rs.getLong("student_id"))
						.code(rs.getString("student_code"))
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.dob(rs.getDate("date_of_birth"))
						.gender(GENDERS.valueOf(rs.getString("gender")))
						.field(FIELDS.valueOf(rs.getString("field")))
						.address(rs.getString("address"))
						.phone(rs.getString("phone_number"))
						.email(rs.getString("email"))
						.note(rs.getString("note"))
						.avgScore(rs.getFloat("avg_score"))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return student;
	}

}
