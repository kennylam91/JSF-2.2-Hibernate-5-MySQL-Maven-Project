package com.Bean;

import java.io.Serializable;
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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "courses")
@ManagedBean(name = "course")
@SessionScoped
public class Course implements Serializable {

	private static final long serialVersionUID = 8223570377101294949L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "begin_time", nullable = true)
	private Date beginTime;

	@Column(name = "finish_time", nullable = true)
	private Date finishTime;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "teacher", nullable = true)
	private String teacher;

	@Column(name = "capacity", nullable = true)
	private int capacity;

	@Column(name = "description", nullable = true)
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	Set<Student> students = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject_id", nullable = true)
	private Subject subject;

}
