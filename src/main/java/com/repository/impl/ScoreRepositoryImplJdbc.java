package com.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.beans.Score;
import com.beans.ScoreDto;
import com.constant.FIELDS;
import com.repository.ScoreRepository;
import com.util.JdbcConnection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "scoreRepositoryJdbc")
@SessionScoped
public class ScoreRepositoryImplJdbc implements ScoreRepository{
	 
	private static final long serialVersionUID = -5458687588917737626L;
	Connection con = null;
	PreparedStatement ps = null;

	@Override
	public List<Score> findScoresByCourseId(Long courseId) {
		List<Score> scores = new LinkedList<>();
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	* "
					+ "FROM		scores "
					+ "WHERE	course_id = ? "
					+ "ORDER BY score_id ";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)courseId.longValue());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Score score = Score.builder()
						.id((long)rs.getInt("score_id"))
						.studentId((long)rs.getInt("student_id"))
						.courseId(courseId)
						.score(rs.getFloat("score"))
						.build();
				scores.add(score);
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
		return scores;
	}

	@Override
	public List<ScoreDto> findScoreDtosByCourseId(Long courseId) {
		List<ScoreDto> scoreDtos = new LinkedList<>();
		
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	sc.*, st.student_code, st.first_name, st.last_name, st.field "
					+ "FROM		scores sc "
					+ "JOIN		students st ON sc.student_id = st.student_id "
					+ "WHERE	course_id = ? "
					+ "ORDER BY score_id "
					+ "";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)courseId.longValue());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ScoreDto scoreDto = new ScoreDto();
				scoreDto.setId((long)rs.getInt("score_id"));
				scoreDto.setStudentId((long)rs.getInt("student_id"));
				scoreDto.setCourseId(courseId);
				scoreDto.setScore(rs.getFloat("score"));
				scoreDto.setStudentCode(rs.getString("student_code"));
				scoreDto.setStudentFirstname(rs.getString("first_name"));
				scoreDto.setStudentLastname(rs.getString("last_name"));
				scoreDto.setStudentField(FIELDS.valueOf(rs.getString("field")));
				scoreDtos.add(scoreDto);
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
		return scoreDtos;
	}

	@Override
	public void saveAll(Set<Score> scores) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String checkExistScoreSql = ""
					+ "SELECT	1 "
					+ "FROM		scores "
					+ "WHERE	student_id = ? "
					+ "			AND course_id = ? ";
			String updateSql = ""
					+ "UPDATE	scores "
					+ "SET		score = ? "
					+ "WHERE	student_id = ? "
					+ "			AND course_id = ? ";
			String insertSql = ""
					+ "INSERT INTO scores (student_id, course_id, score ) "
					+ "VALUES ( ?, ?, ?) ";
			PreparedStatement checkExistScorePs = con.prepareStatement(checkExistScoreSql);
			PreparedStatement updatePs = con.prepareStatement(updateSql);
			PreparedStatement insertPs = con.prepareStatement(insertSql);
			for (Score score : scores) {
				int studentId = (int) score.getStudentId().longValue();
				int courseId = (int) score.getCourseId().longValue();
				float mark = score.getScore();
				checkExistScorePs.setInt(1, studentId);
				checkExistScorePs.setInt(2, courseId);
				ResultSet checkExistScoreRs = checkExistScorePs.executeQuery();
				//score was existed, do update
				if(checkExistScoreRs.next()) {
					updatePs.setFloat(1, mark);
					updatePs.setInt(2, studentId);
					updatePs.setInt(3, courseId);
					updatePs.addBatch();
				}
				else {
					insertPs.setInt(1, studentId);
					insertPs.setInt(2, courseId);
					insertPs.setFloat(3, mark);
					insertPs.addBatch();
				}
			}
			if(updatePs != null)
				updatePs.executeBatch();
			if(insertPs != null)
				insertPs.executeBatch();
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
	public void delete(Long courseId, Long studentId) {
		try {
			con = JdbcConnection.getConnection();
			con.setAutoCommit(false);
			String sql = ""
					+ "DELETE FROM scores "
					+ "WHERE	student_id = ? "
					+ "			AND course_id = ? ";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)studentId.longValue());
			ps.setInt(2, (int)courseId.longValue() );
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
	public Score findScoreByCourseIdAndStudentId(Long courseId, Long studentId) {
		Score score = null;
		try {
			con = JdbcConnection.getConnection();
			String sql = ""
					+ "SELECT	*"
					+ "FROM		scores"
					+ "WHERE	course_id = ? "
					+ "			AND student_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)courseId.longValue());
			ps.setInt(2, (int)studentId.longValue());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				score = Score.builder()
						.id((long)rs.getInt("score_id"))
						.studentId((long)rs.getInt("student_id"))
						.courseId(courseId)
						.score(rs.getFloat("score"))
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
		return score;
	}

	
}
