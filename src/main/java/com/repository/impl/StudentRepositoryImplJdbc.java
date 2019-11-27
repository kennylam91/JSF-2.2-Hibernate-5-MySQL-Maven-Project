package com.repository.impl;

import java.sql.*;
import java.util.List;

import com.beans.Student;
import com.beans.dto.ListStudentDto;
import com.beans.pagination.Pagination;
import com.repository.StudentRepository;
import com.util.JdbcConnection;

public class StudentRepositoryImplJdbc implements StudentRepository {

	private static final long serialVersionUID = 4964168177811809187L;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	@Override
	public Long saveStudent(Student student) {
		Long status = null;
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = "" + "insert into students"
					+ "(student_code, first_name, last_name, date_of_birth, gender, field, address, phone_number, email, note) "
					+ "values (?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, student.getCode());
			ps.setString(2, student.getFirstName());
			ps.setString(3, student.getLastName());
			ps.setString(4, student.getDob().toString());
			ps.setString(5, student.getGender().toString());
			ps.setString(6, student.getField().toString());
			ps.setString(7, student.getAddress());
			ps.setString(8, student.getPhone());
			ps.setString(9, student.getEmail());
			ps.setString(10, student.getNote());
			status = (long) ps.executeUpdate(sql);
			con.commit();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
		return status;
	}

	@Override
	public void updateStudent(Student student) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteStudent(Student student) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Student findStudentById(Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListStudentDto findStudentsByPagination(Pagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteStudentList(List<Long> StudentIdList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Student> findStudentsByIds(List<Long> Ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStudentAvgScore(Long studentId) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkDuplicatedEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTotalRecords(Pagination paginationStudentList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Student findStudentByEmail(String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

}
