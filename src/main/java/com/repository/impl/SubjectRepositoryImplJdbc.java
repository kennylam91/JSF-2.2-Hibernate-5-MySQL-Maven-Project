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
import com.beans.Subject;
import com.constant.COURSE_STATUSES;
import com.constant.FIELDS;
import com.repository.SubjectRepository;
import com.util.JdbcConnection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "subjectRepositoryJdbc")
@SessionScoped
public class SubjectRepositoryImplJdbc implements SubjectRepository {

	private static final long serialVersionUID = -8207428553591375877L;

	Connection con = null;
	PreparedStatement ps = null;
	
	@Override
	public Long saveSubject(Subject subject) {
		int affectedRow = 0;
		Long id = null;
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "INSERT INTO subjects (subject_code, subject_name, field, description, coefficient ) "
					+ "VALUES (?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, subject.getCode());
			ps.setString(2, subject.getName());
			ps.setString(3, subject.getField().toString());
			ps.setString(4, subject.getDescription());
			ps.setFloat(5, subject.getCoefficient());
			affectedRow = ps.executeUpdate();
			con.commit();
			if(affectedRow > 0) {
				try {
					ResultSet rs = ps.getGeneratedKeys();
					if(rs.next()) {
						id = (long) rs.getInt("subject_id");
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
	public void updateSubject(Subject subject) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "UPDATE	subjects "
					+ "SET"
					+ "		subject_name = ? , "
					+ "		field = ? , "
					+ "		description = ? , "
					+ "		coefficient = ? "
					+ "WHERE 	subject_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, subject.getName());
			ps.setString(2, subject.getField().toString());
			ps.setString(3, subject.getDescription());
			ps.setFloat(4, subject.getCoefficient());
			ps.setInt(5, (int)subject.getId().longValue());
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
	public void deleteSubject(Subject subject) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "DELETE FROM subjects "
					+ "WHERE subject_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)subject.getId().longValue());
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
	public Subject findSubjectById(Long subjectId) {
		Subject subject = null;
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM 	subjects "
					+ "WHERE 	subject_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)subjectId.longValue());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				subject = Subject.builder()
						.id(subjectId)
						.code(rs.getString("subject_code"))
						.name(rs.getString("subject_name"))
						.field(FIELDS.valueOf(rs.getString("field")))
						.description(rs.getString("description"))
						.coefficient(rs.getFloat("coefficient"))
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
		return subject;
	}

	@Override
	public List<Subject> findAllSubjects() {
		List<Subject> subjects = new LinkedList<>();
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM 	subjects ";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Subject subject = Subject.builder()
						.id(rs.getLong("subject_id"))
						.code(rs.getString("subject_code"))
						.name(rs.getString("subject_name"))
						.field(FIELDS.valueOf(rs.getString("field")))
						.description(rs.getString("description"))
						.coefficient(rs.getFloat("coefficient"))
						.build();
				subjects.add(subject);
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
		return subjects;
	}

	@Override
	public Subject findSubjectByName(String name) {
		Subject subject = null;
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM 	subjects "
					+ "WHERE 	subject_name ~ ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				subject = Subject.builder()
						.id(rs.getLong("subject_id"))
						.code(rs.getString("subject_code"))
						.name(rs.getString("subject_name"))
						.field(FIELDS.valueOf(rs.getString("field")))
						.description(rs.getString("description"))
						.coefficient(rs.getFloat("coefficient"))
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
		return subject;
	}

	
}
