package com.Bean;

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
@Entity
@Table(name = "subjects")
@ManagedBean(name = "subject")
@SessionScoped
public class Subject implements Serializable {

	private static final long serialVersionUID = 3073718612735697210L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Course> courses = new HashSet<>();

	@Override
	public String toString() {
		return name;
	}

}
