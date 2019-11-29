package com.test.repository;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.beans.Score;
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
}
