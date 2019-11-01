package com.beans;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
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
@Table(name = "subjects")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "field", nullable = true)
	private String field;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "coefficient", nullable = false)
	private float coefficient;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "subject")
	private Set<Course> courses = new HashSet<>();

	@Override
	public boolean equals(Object other) {
		return (other instanceof Subject) && (id != null) ? id.equals(((Subject) other).id) : (other == this);
	}

	@Override
	public int hashCode() {
		return (id != null) ? (this.getClass().hashCode() + id.hashCode()) : super.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

}
