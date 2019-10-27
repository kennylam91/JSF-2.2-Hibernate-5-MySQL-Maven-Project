package com.Bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "newStudentForm")
@SessionScoped
public class NewStudentForm implements Serializable {

	private static final long serialVersionUID = -2615270572543229725L;

	private String code;
	private String firstName;
	private String lastName;
	private Date dob;
	private String gender;
	private String field;
	private String address;
	private String phone;
	private String email;

}
