package com.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.constant.COURSE_STATUSES;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course implements Serializable{

	private static final long serialVersionUID = -4729857325404297004L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "begin_time", nullable = true)
	private Date beginTime;

	@Column(name = "finish_time", nullable = true)
	private Date finishTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private COURSE_STATUSES status;

	@Column(name = "teacher", nullable = true)
	private String teacher;

	@Column(name = "capacity", nullable = true)
	private int capacity;

	@Column(name = "description", nullable = true)
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "student_course", 
	joinColumns = @JoinColumn(referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
	private Set<Student> students = new HashSet<>();
	
	private int studentsNo;

	public void addStudent(Student student) {
		students.add(student);
		student.getCourses().add(this);
		studentsNo = students.size();
	}

	public void removeStudent(Student student) {
		students.remove(student);
		student.getCourses().remove(this);
		studentsNo = students.size();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject_id")
	private Subject subject;

	@Override
	public String toString() {
		return name;
	}

	public int getStudentsNo() {
		return students.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
