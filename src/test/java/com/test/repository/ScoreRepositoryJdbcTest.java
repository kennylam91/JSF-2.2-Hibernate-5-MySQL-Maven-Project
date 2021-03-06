package com.test.repository;

import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.beans.Score;
import com.beans.ScoreDto;
import com.repository.ScoreRepository;
import com.repository.impl.ScoreRepositoryImplJdbc;

public class ScoreRepositoryJdbcTest {
	
	ScoreRepository scoreRepo = null;
	Set<Score> scores = new HashSet<>();
	

	@Before
	public void init() {
		Score score1 = Score.builder().courseId(1L).studentId(1L).score(5.5f).build();
		Score score2 = Score.builder().courseId(2L).studentId(2L).score(6.5f).build();
		Score score3 = Score.builder().courseId(3L).studentId(3L).score(8.5f).build();
		scores.add(score1);
		scores.add(score2);
		scores.add(score3);
		scoreRepo = new ScoreRepositoryImplJdbc();
	}
	
	@Test
	public void testSaveAll() {
		Score score4 = Score.builder().courseId(4L).studentId(4L).score(8.0f).build();
		scores.add(score4);
		scoreRepo.saveAll(scores);
	}
	
	@Test
	public void testFindScoresByCourseId() {
		Score score5 = Score.builder().courseId(4L).studentId(3L).score(5.0f).build();
		scores.add(score5);
		scoreRepo.saveAll(scores);
		List<Score> scoresFound = scoreRepo.findScoresByCourseId(4L);
		for (Score score : scoresFound) {
			System.out.println(score);
		}
	}
	@Test
	public void testFindScoreDtosByCourseId() {
		List<ScoreDto> scoreDtosFound = scoreRepo.findScoreDtosByCourseId(4L);
		for (ScoreDto scoreDto : scoreDtosFound) {
			System.out.println(scoreDto);
		}
	}
	@Test
	public void testFindScoreByCourseIdAndStudentId() {
		Score scoreFound = scoreRepo.findScoreByCourseIdAndStudentId(4L, 410L);
		System.out.println(scoreFound);
	}
	
	@Test
	public void deleteScoreByCourseIdAndStudentId() {
		scoreRepo.delete(4L, 410L);
		Score scoreFound = scoreRepo.findScoreByCourseIdAndStudentId(4L, 410L);
		assertNull(scoreFound);
	}
}
