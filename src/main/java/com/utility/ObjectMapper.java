package com.utility;

import org.modelmapper.ModelMapper;

import com.Bean.Student;
import com.Bean.StudentForm;

public class ObjectMapper {

	public static ModelMapper modelMapper = new ModelMapper();

	public static void convertToStudent(StudentForm studentForm, Student student) {
		student.setFirstName(studentForm.getFirstName());
		student.setLastName(studentForm.getLastName());
		student.setDOB(studentForm.getDOB());
		student.setEmail(studentForm.getEmail());
		student.setPhone(studentForm.getPhone());
		student.setNote(studentForm.getNote());
		student.setId(studentForm.getId());
		student.setCode(studentForm.getCode());
		student.setField(studentForm.getField());
		student.setAvgScore(studentForm.getAvgScore());
		student.setAddress(studentForm.getAddress());
		student.setCourses(studentForm.getCourses());

	}

	public static void convertToStudentForm(Student student, StudentForm studentForm) {
		studentForm.setEmail(student.getEmail());
		studentForm.setFirstName(student.getFirstName());
		studentForm.setLastName(student.getLastName());
		studentForm.setNote(student.getNote());
		studentForm.setPhone(student.getPhone());
		studentForm.setField(student.getField());
		studentForm.setId(student.getId());
		studentForm.setDOB(student.getDOB());
		studentForm.setCode(student.getCode());
		studentForm.setAvgScore(student.getAvgScore());
		studentForm.setAddress(student.getAddress());
		studentForm.setCourses(student.getCourses());
	}
	
	

}
