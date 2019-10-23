package com.Bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
@ManagedBean(name = "studentForm")
@SessionScoped
public class StudentForm implements Serializable {

	private static final long serialVersionUID = -5939218400151972369L;

	private Long Id;
	private String code;
	private String firstName;
	private String lastName;
	private Date DOB;
	private String field;
	private String address;
	private String phone;
	private String email;
	private String note;
	private float avgScore;
	private Set<Course> courses;

}
