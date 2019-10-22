package com.Bean;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	private String code;
	private String firstName;
	private String lastName;
	private Date DOB;
	private String field;
	private String country;
	private String phone;
	private String email;
	private String note;
	private float avgScore;
	

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Subject> subjects = new HashSet<Subject>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Course> courses = new HashSet<Course>();
}
