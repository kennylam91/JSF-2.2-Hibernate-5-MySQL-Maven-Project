/**
 * 
 */
package com.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.constant.FIELDS;
import com.constant.GENDERS;

public interface StudentRepository extends Serializable {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Student student);

	Student findStudentById(Long studentId);

	List<StudentDto> findStudentsByPagination(Pagination pagination);

	void deleteStudentList(List<Long> StudentIdList);
	
	List<Student> findStudentsByIds(List<Long> Ids);
	
	void updateStudentAvgScore(Long studentId);
	
	boolean checkDuplicatedEmail(String email);
	
	int getTotalRecords(Pagination paginationStudentList);


}
