/**
 * 
 */
package com.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.beans.Course;
import com.beans.CourseScoreDto;
import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;

public interface StudentRepository extends Serializable {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Student student);

	Student findStudentById(Long studentId);

	List<StudentDto> findAllStudents();

	List<StudentDto> findStudentsByPagination(Pagination pagination);

	void deleteStudentList(List<Long> StudentIdList);
	
	List<Student> findStudentsByIds(List<Long> Ids);
	
	void updateStudentAvgScore(Long studentId);

}
