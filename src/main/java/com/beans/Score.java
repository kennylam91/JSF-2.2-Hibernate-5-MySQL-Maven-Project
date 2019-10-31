package com.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Score implements Serializable {

	private static final long serialVersionUID = 3871552378550369769L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "student_id", nullable = false)
	private Long studentId;

	@Column(name = "course_id", nullable = false)
	private Long courseId;

	@Column(name = "score", nullable = false)
	private float score;

}
