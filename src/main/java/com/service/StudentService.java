package com.service;

import java.io.Serializable;
import java.util.List;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;

public interface StudentService extends Serializable {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId) throws Exception;

	Student findStudentById(Long studentId);

	List<StudentDto> findStudentsByPagination(Pagination pagination);

	void deleteStudents(List<StudentDto> StudentDtos);

	List<Student> findStudentsByStudentDtos(List<StudentDto> studentDtos);

	void updateStudentAvgScore(Long studentId);

	boolean checkDuplicatedEmail(String string);

	int getTotalRecords(Pagination paginationStudentList);

	Student findStudentByEmail(String userEmail);

}
