package com.util;

import org.modelmapper.ModelMapper;

import com.beans.Course;
import com.beans.Student;
import com.beans.StudentForm;
import com.beans.Subject;
import com.beans.formbeans.NewCourseForm;
import com.beans.formbeans.NewStudentForm;
import com.beans.formbeans.NewSubjectForm;

public class ObjectMapper {

	public static ModelMapper modelMapper = new ModelMapper();

	public static void convertToStudent(StudentForm studentForm, Student student) {
		student.setFirstName(studentForm.getFirstName());
		student.setLastName(studentForm.getLastName());
		student.setDob(studentForm.getDob());
		student.setEmail(studentForm.getEmail());
		student.setPhone(studentForm.getPhone());
		student.setNote(studentForm.getNote());
		student.setId(studentForm.getId());
		student.setCode(studentForm.getCode());
		student.setField(studentForm.getField());
		student.setAvgScore(studentForm.getAvgScore());
		student.setAddress(studentForm.getAddress());
		student.setCourses(studentForm.getCourses());
		student.setGender(studentForm.getGender());

	}

	public static void convertToStudentForm(Student student, StudentForm studentForm) {
		studentForm.setEmail(student.getEmail());
		studentForm.setFirstName(student.getFirstName());
		studentForm.setLastName(student.getLastName());
		studentForm.setNote(student.getNote());
		studentForm.setPhone(student.getPhone());
		studentForm.setField(student.getField());
		studentForm.setId(student.getId());
		studentForm.setDob(student.getDob());
		studentForm.setCode(student.getCode());
		studentForm.setAvgScore(student.getAvgScore());
		studentForm.setAddress(student.getAddress());
		studentForm.setCourses(student.getCourses());
		studentForm.setGender(student.getGender());
	}

	public static Student convertToStudentFromNewStudentForm(NewStudentForm newStudentForm) {
		return modelMapper.map(newStudentForm, Student.class);
	}

	public static Subject convertToSubjectFromNewSubjectForm(NewSubjectForm newSubjectForm) {
		return modelMapper.map(newSubjectForm, Subject.class);
	}

	public static Course convertToCourseFromNewCourseForm(NewCourseForm newCourseForm) {
		return modelMapper.map(newCourseForm, Course.class);
	}

}
