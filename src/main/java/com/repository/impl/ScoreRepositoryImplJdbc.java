package com.repository.impl;

import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.beans.Score;
import com.beans.ScoreDto;
import com.repository.ScoreRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "scoreRepositoryJdbc")
@SessionScoped
public class ScoreRepositoryImplJdbc implements ScoreRepository{
	 
	private static final long serialVersionUID = -5458687588917737626L;

	@Override
	public List<Score> findScoresByCourseId(Long courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScoreDto> findScoreDtosByCourseId(Long courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAll(Set<Score> scores) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long courseId, Long studentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Score findScoreByCourseIdAndStudentId(Long courseId, Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
