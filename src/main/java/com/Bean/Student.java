package com.Bean;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "student")
@SessionScoped
@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "dob", nullable = false)
	private Date DOB;

	@Column(name = "field", nullable = false)
	private String field;

	@Column(name = "country", nullable = false)
	private String country;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "note", nullable = true)
	private String note;

	@Column(name = "avg_score", nullable = true)
	private float avgScore;

	@OneToMany
	private Set<Score> scores = new HashSet<Score>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Subject> subjects = new HashSet<Subject>();

	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "scores") private Set<Course> courses = new
	 * HashSet<Course>();
	 */
}
