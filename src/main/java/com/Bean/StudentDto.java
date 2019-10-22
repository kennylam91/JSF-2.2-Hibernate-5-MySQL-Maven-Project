package com.Bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "studentDto")
@SessionScoped
public class StudentDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8116736993772045415L;
	private Long Id;
	private String code;
	private String firstName;
	private String lastName;
	private String field;
	private Date DOB;
	private String phone;
	private String email;
	private String note;
	private float avgScore;
	
}
