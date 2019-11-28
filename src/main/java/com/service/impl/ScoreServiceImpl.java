package com.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.Score;
import com.beans.ScoreDto;
import com.exception.ScoreNotFoundException;
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

	@ManagedProperty(value = "#{scoreRepositoryJdbc}")
	ScoreRepository scoreRepository;

	@Override
	public Set<Score> findScoresByCourseId(Long courseId) {
		List<Score> scores = scoreRepository.findScoresByCourseId(courseId);
		return new HashSet<>(scores);
	}

	@Override
	public Set<ScoreDto> findScoreDtosByCourseId(Long courseId) {
		List<ScoreDto> scores = scoreRepository.findScoreDtosByCourseId(courseId);
		return new HashSet<>(scores);
	}

	@Override
	public void saveAll(Set<Score> scores) {
		scoreRepository.saveAll(scores);

	}

	@Override
	public void updateAll(Set<Score> scores) {
		scoreRepository.saveAll(scores);

	}

	@Override
	public void saveAllScoreDtos(Set<ScoreDto> scoreDtos) {
		Set<Score> scores = new HashSet<>();
		for (ScoreDto scoreDto : scoreDtos) {
			Score score = Score.builder().courseId(scoreDto.getCourseId())
								.studentId(scoreDto.getStudentId())
								.score(scoreDto.getScore()).id(scoreDto.getId())
								.build();
			scores.add(score);
		}
		scoreRepository.saveAll(scores);

	}

	@Override
	public void delete(Long courseId, Long studentId) {
		scoreRepository.delete(courseId, studentId);
		
	}

	@Override
	public float findScoreByCourseIdAndStudentId(Long courseId, Long studentId) throws ScoreNotFoundException {
		Score score = scoreRepository.findScoreByCourseIdAndStudentId(courseId, studentId);
		if(score != null) return score.getScore();
		else {
			throw new ScoreNotFoundException();
		}
	}
	
	
	
	

}
