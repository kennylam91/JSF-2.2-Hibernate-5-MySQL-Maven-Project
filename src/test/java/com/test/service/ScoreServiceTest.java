package com.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beans.Score;
import com.repository.ScoreRepository;
import com.repository.impl.ScoreRepositoryImpl;
import com.service.ScoreService;
import com.service.impl.ScoreServiceImpl;

public class ScoreServiceTest {

	protected ScoreService scoreService;
	protected ScoreRepository scoreRepo;
	protected Long courseId;

	@Before
	public void init() {
		scoreRepo = new ScoreRepositoryImpl();
		scoreService = new ScoreServiceImpl(scoreRepo);
		courseId = new Long(1L);
	}

	@After
	public void after() {
		scoreRepo = null;
		scoreService = null;
	}

	@Test
	public void testFindScoresByCourseId() {
		Set<Score> scores = scoreService.findScoresByCourseId(courseId);
		for (Score score : scores) {
			System.out.println(score);
		}
		assertTrue(scores.size() > 0);
	}
}
