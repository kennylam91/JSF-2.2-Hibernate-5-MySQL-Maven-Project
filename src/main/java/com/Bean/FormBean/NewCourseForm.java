package com.Bean.FormBean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "newCourseForm")
@SessionScoped
public class NewCourseForm {

	private String code;
	private String name;
	private Date beginTime;
	private String status;
	private String teacher;
	private int capacity;

}
