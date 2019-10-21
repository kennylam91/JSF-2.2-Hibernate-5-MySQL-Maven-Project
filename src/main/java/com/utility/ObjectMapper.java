package com.utility;

import com.Bean.Student;
import com.Bean.StudentForm;

public class ObjectMapper {

	public static void convertToStudent(StudentForm studentForm, Student student) {
		student.setFirstName(studentForm.getFirstName());
		student.setLastName(studentForm.getLastName());
		student.setAge(studentForm.getAge());
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
		studentForm.setAge(student.getAge());
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
