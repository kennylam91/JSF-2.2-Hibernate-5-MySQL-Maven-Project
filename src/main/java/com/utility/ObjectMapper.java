package com.utility;

import org.modelmapper.ModelMapper;

import com.Bean.Student;
import com.Bean.StudentForm;

public class ObjectMapper {
	public static ModelMapper mapper = new ModelMapper();

	public static void convertToStudent(StudentForm studentForm, Student student) {
		student.setFirstName(studentForm.getFirstName());
		student.setLastName(studentForm.getLastName());
		student.setEmail(studentForm.getEmail());
		student.setPhone(studentForm.getPhone());
		student.setNote(studentForm.getNote());
		student.setId(studentForm.getId());
		student.setCode(studentForm.getCode());
		student.setCountry(studentForm.getCountry());
		student.setCourses(studentForm.getCourses());
		student.setSubjects(studentForm.getSubjects());
	}

	public static void convertToStudentForm(Student student, StudentForm studentForm) {
		studentForm.setEmail(student.getEmail());
		studentForm.setFirstName(student.getFirstName());
		studentForm.setLastName(student.getLastName());
		studentForm.setNote(student.getNote());
		studentForm.setPhone(student.getPhone());
		studentForm.setId(student.getId());
		;
		studentForm.setCode(student.getCode());
		studentForm.setCountry(student.getCountry());
		studentForm.setCourses(student.getCourses());
		studentForm.setSubjects(student.getSubjects());
	}

}
