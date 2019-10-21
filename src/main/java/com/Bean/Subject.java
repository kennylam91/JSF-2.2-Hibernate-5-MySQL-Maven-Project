package com.Bean;

import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.CascadeType;
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
@Table(name = "subjects")
@ManagedBean(name = "subject")
@SessionScoped
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String code;
	private String name;
	private float coefficient;
	private float score;

	@ManyToMany(fetch = FetchType.EAGER)
	Set<Student> students;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "students")
	private Set<Course> courses;

}
