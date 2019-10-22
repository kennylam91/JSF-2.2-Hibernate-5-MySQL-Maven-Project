/**
 * 
 */
package com.repository;

import java.util.List;

import com.Bean.Pagination;
import com.Bean.Student;
import com.Bean.StudentDto;

/**
 * @author BVCN 2 - LT88
 *
 */
public interface StudentRepository {
	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<StudentDto> findAllStudents();
	
	List<StudentDto> findStudentsByPagination(Pagination pagination);

}
