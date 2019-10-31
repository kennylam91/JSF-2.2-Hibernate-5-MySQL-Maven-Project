package com.beans.formbeans;

import java.io.Serializable;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.beans.Course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "newSubjectForm")
@SessionScoped
public class NewSubjectForm implements Serializable {

	private static final long serialVersionUID = 3382533192759350210L;

	private String code;

	private String name;
	
	private String field;

	private String description;

	private float coefficient;

	private Set<Course> courses;
}
