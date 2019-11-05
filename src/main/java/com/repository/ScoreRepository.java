package com.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.beans.Score;
import com.beans.ScoreDto;

public interface ScoreRepository extends Serializable{

	public List<Score> findScoresByCourseId(Long courseId);
	
	public List<ScoreDto> findScoreDtosByCourseId (Long courseId);

	public void saveAll(Set<Score> scores);
	
	public void updateAll(Set<Score> scores);
}
