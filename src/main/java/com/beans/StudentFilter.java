package com.beans;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.constant.FIELDS;
import com.constant.GENDERS;
import com.service.StudentService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ManagedBean(name = "studentFilter")
@SessionScoped
public class StudentFilter {

	private Boolean isByGender;
	private Boolean isByField;
	private Boolean isByDOB;
	private Boolean isByScore;
	
	private GENDERS genderFilterValue;
	private FIELDS fieldFilterValue;
	private Date DOBFilterFrom;
	private Date DOBFilterTo;
	private Float ScoreFilterFrom;
	private Float ScoreFilterTo;
	
	@PostConstruct
	public void init() {
		isByGender = false;
		isByField = false;
		isByDOB = false;
		isByScore = false;
		genderFilterValue = null;
		fieldFilterValue = null;
		DOBFilterFrom = null;
		DOBFilterTo = null;
		ScoreFilterFrom = new Float(0.0f);
		ScoreFilterTo = new Float(0.0f);
		
	}
	
	@ManagedProperty(value = "#{studentService}")
	StudentService studentService;
	
	public void filter() {
		System.out.println(isByGender);
		System.out.println(isByField);
		System.out.println(isByDOB);
		System.out.println(isByScore);
		System.out.println(genderFilterValue);
		System.out.println(fieldFilterValue);
		System.out.println(DOBFilterFrom);
		System.out.println(DOBFilterTo);
		System.out.println(ScoreFilterFrom);
		System.out.println(ScoreFilterTo);
	}
	
}
