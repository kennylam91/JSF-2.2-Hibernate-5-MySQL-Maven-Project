/**
 * 
 */
package com.repository;

import java.io.Serializable;
import java.util.List;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;

public interface StudentRepository extends Serializable {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<StudentDto> findAllStudents();

	List<StudentDto> findStudentsByPagination(Pagination pagination);

	void deleteStudentList(List<Long> StudentIdList);

}
