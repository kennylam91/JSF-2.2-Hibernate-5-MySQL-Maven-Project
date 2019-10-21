package com.service;

import java.util.List;

import com.Bean.Student;

public interface StudentService {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<Student> findAllStudents();
}
