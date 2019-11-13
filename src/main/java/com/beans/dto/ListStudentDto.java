package com.beans.dto;

import java.util.LinkedList;
import java.util.List;

import com.beans.StudentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListStudentDto {

	private List<StudentDto> list;
	
	private int totalFoundRecords;

	public ListStudentDto() {
		list = new LinkedList<StudentDto>();
		totalFoundRecords = 0;
	}
	
	
}
