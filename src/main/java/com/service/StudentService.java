package com.service;

import java.util.List;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;

public interface StudentService {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<StudentDto> findStudentsByPagination(Pagination pagination);

	void deleteStudents(List<StudentDto> StudentDtos);

	List<Student> findStudentsByStudentDtos(List<StudentDto> studentDtos);
	
	void updateStudentAvgScore(Long studentId);
}
