package com.beans;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.constant.FIELDS;
import com.constant.GENDERS;
import com.controller.StudentController;
import com.service.StudentService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class StudentFilter {

	private Boolean isByGender;
	private Boolean isByField;
	private Boolean isByDOB;
	private Boolean isByScore;

	private GENDERS genderFilterValue;
	private FIELDS fieldFilterValue;
	private Date DOBFilterFrom;
	private Date DOBFilterTo;
	private Float scoreFilterFrom;
	private Float scoreFilterTo;
	
	public StudentFilter() {
		isByGender = false;
		isByField = false;
		isByDOB = false;
		isByScore = false;
		genderFilterValue = null;
		fieldFilterValue = null;
		DOBFilterFrom = null;
		DOBFilterTo = null;
		scoreFilterFrom = new Float(0.0f);
		scoreFilterTo = new Float(0.0f);
	}

	

}
