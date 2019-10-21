package com.Bean;

import java.io.Serializable;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.ManyToMany;
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
@ManagedBean(name = "studentForm", eager = true)
@SessionScoped
public class StudentForm implements Serializable {

  private static final long serialVersionUID = -5939218400151972369L;

  private Long Id;

  private String code;
  private String firstName;
  private String lastName;
  private int age;
  private String country;
  private String phone;
  private String email;
  private String note;
  private Set<Subject> subjects;
  private Set<Course> courses;
}
