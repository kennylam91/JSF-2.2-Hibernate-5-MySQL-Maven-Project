/**
 * 
 */
package com.repository;

import java.util.List;

import com.Bean.Student;

/**
 * @author BVCN 2 - LT88
 *
 */
public interface StudentRepository {
	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Long studentId);

	Student findStudentById(Long studentId);

	List<Student> findAllStudents();

}
