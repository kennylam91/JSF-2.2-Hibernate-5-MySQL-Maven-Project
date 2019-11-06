package com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.beans.Score;
import com.beans.ScoreDto;

public interface ScoreService extends Serializable{

	public Set<Score> findScoresByCourseId(Long courseId);
	
	public Set<ScoreDto> findScoreDtosByCourseId(Long courseId);

	public void saveAll(Set<Score> scores);
	
	public void saveAllScoreDtos(Set<ScoreDto> scoreDtos);
	
	public void updateAll(Set<Score> scores);
	
	public void delete(Long courseId, Long studentId);
}
