package com.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.constant.FIELDS;
import com.constant.GENDERS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@ManagedBean(name = "studentFilter")
@SessionScoped
public class StudentFilter implements Serializable{

	
	private static final long serialVersionUID = 8707899865819089652L;
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
