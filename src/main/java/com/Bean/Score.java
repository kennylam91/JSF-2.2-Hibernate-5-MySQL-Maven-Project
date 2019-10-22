package com.Bean;

import javax.annotation.Generated;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scores")
public class Score {

	@EmbeddedId
	private StudentCourseId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("studentId")
	private Student student;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("courseId")
	private Course course;

	private float score;

}
