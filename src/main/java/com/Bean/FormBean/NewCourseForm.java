package com.Bean.FormBean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.Bean.Subject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "newCourseForm")
@SessionScoped
public class NewCourseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8355552067761168001L;
	private String code;
	private String name;
	private Date beginTime;
	private Date finishTime;
	private String status;
	private String teacher;
	private int capacity;
	private Subject subject;

}
