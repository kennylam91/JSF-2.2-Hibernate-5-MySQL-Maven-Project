package com.service;

import java.util.List;

import com.Bean.Student;
import com.Bean.StudentDto;

public interface StudentService {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<StudentDto> findAllStudents();
}
