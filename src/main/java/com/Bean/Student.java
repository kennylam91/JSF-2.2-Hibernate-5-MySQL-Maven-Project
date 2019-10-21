package com.Bean;

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
	private int age;
	private String country;
	private String phone;
	private String email;
	private String note;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Subject> subjects = new HashSet<Subject>();

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Course> courses = new HashSet<Course>();
}
