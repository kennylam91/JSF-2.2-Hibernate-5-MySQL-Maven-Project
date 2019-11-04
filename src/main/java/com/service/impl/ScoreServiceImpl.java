package com.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.Score;
import com.repository.ScoreRepository;
import com.service.ScoreService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "scoreService")
@SessionScoped
public class ScoreServiceImpl implements ScoreService {

	private static final long serialVersionUID = -4393343651463239429L;

	@ManagedProperty(value = "#{scoreRepository}")
	ScoreRepository scoreRepository;

	@Override
	public Set<Score> findScoresByCourseId(Long courseId) {
		List<Score> scores = scoreRepository.findScoresByCourseId(courseId);
		return new HashSet<>(scores);
	}

}
