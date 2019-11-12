package com.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseScoreDto implements Comparable<CourseScoreDto>, Serializable{
	
	
	private static final long serialVersionUID = -5021623617949588252L;
	private Long courseId;
	private Long subjectId;
	private float coefficient;
	private float score;
	public CourseScoreDto(Long courseId, Long subjectId) {
		this.courseId = courseId;
		this.subjectId = subjectId;
	}
	public CourseScoreDto(Long courseId, Long subjectId, float coefficient) {
		super();
		this.courseId = courseId;
		this.subjectId = subjectId;
		this.coefficient = coefficient;
	}
	@Override
	public int compareTo(CourseScoreDto o) {
		return (int) (this.subjectId - o.subjectId);
	}
	
	
	
}
