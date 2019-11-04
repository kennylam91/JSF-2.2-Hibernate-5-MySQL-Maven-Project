package com.service;

import java.io.Serializable;
import java.util.Set;

import com.beans.Score;

public interface ScoreService extends Serializable{

	public Set<Score> findScoresByCourseId(Long courseId);
	
}
