package com.beans;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@ToString
@Entity
@Table(name = "students")
public class Student {

	@Column(name = "student_id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "dob", nullable = false)
	private Date dob;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(name = "field", nullable = false)
	private String field;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "note", nullable = true)
	private String note;

	@Column(name = "avg_score", nullable = true)
	private float avgScore;

	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "student_course", 
//		joinColumns = @JoinColumn(referencedColumnName = "student_id"), 
//		inverseJoinColumns = @JoinColumn(referencedColumnName = "course_id"))
	private Set<Course> courses = new HashSet<>();

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
		Student other = (Student) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
