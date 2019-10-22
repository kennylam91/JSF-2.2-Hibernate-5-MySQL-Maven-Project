package com.Bean;

import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StudentCourseId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713481191147509352L;

	@Column(name = "student_id")
	private Long studentId;
	
	@Column(name = "course_id")
	private Long courseId;
	
	
	
}
