package com.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.beans.Score;

public interface ScoreRepository extends Serializable{

	public List<Score> findScoresByCourseId(Long courseId);
}
