/**
 * 
 */
package com.repository;

import java.io.Serializable;
import java.util.List;

import com.Bean.Pagination;
import com.Bean.Student;
import com.Bean.StudentDto;

public interface StudentRepository extends Serializable {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<StudentDto> findAllStudents();

	List<StudentDto> findStudentsByPagination(Pagination pagination);

}
