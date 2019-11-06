package com.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@ManagedBean(name = "studentDto")
@SessionScoped
public class StudentDto implements Serializable {

	private static final long serialVersionUID = 8116736993772045415L;
	private Long id;
	private String code;
	private String firstName;
	private String lastName;
	private String gender;
	private String field;
	private Date dob;
	private String phone;
	private String email;
	private String note;
	private float avgScore;

}
