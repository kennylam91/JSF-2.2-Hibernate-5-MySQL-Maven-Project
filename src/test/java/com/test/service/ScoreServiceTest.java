package com.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beans.Score;
import com.beans.ScoreDto;
import com.repository.ScoreRepository;
import com.repository.impl.ScoreRepositoryImpl;
import com.service.ScoreService;
import com.service.impl.ScoreServiceImpl;

public class ScoreServiceTest {

	protected ScoreService scoreService;
	protected ScoreRepository scoreRepo;
	protected Long courseId;
	protected Score firstScore;
	protected Score secondScore;

	@Before
	public void init() {
		scoreRepo = new ScoreRepositoryImpl();
		scoreService = new ScoreServiceImpl(scoreRepo);
		courseId = new Long(1L);
		firstScore = Score.builder().courseId(courseId).studentId(new Long(101L)).build();
		secondScore = Score.builder().courseId(courseId).studentId(new Long(201L)).build();
		Set<Score> scores = new HashSet<>();
		scores.add(firstScore);
		scores.add(secondScore);
		scoreService.saveAll(scores);
	}

	@After
	public void after() {
		scoreRepo = null;
		scoreService = null;
	}

	@Test
	public void testFindScoresByCourseId() {
		Set<Score> scores = scoreService.findScoresByCourseId(courseId);
		assertTrue(scores.size() > 0);
	}

	@Test
	public void testFindScoreDtosByCourseId() {
		Set<ScoreDto> scores = scoreService.findScoreDtosByCourseId(courseId);
		assertTrue(scores.size() > 0);
	}
}
