package com.Bean;

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
public class StudentDto {

	private Long Id;
	private String code;
	private String firstName;
	private String lastName;
	private int age;
	private String country;
	private String phone;
	private String email;
	private String note;
}
