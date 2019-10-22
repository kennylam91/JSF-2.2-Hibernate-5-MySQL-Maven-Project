package com.Bean;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "courses")
@ManagedBean(name = "course")
@SessionScoped
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "begin_time", nullable = false)
	private Calendar beginTime;

	@Column(name = "finish_time", nullable = true)
	private Calendar finishTime;

	@OneToMany
	private Set<Score> scores = new HashSet<Score>();

	/*
	 * @ManyToMany(fetch = FetchType.EAGER) Set<Student> students = new
	 * HashSet<Student>();
	 */

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject_id", nullable = false)
	private Subject subject;

}
