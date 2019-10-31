package com.service;

import java.util.List;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.PaginationStudentList;

public interface StudentService {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<StudentDto> findAllStudents();
	
	List<StudentDto> findStudentsByPagination(PaginationStudentList pagination);
}
