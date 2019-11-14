/**
 * 
 */
package com.repository;

import java.io.Serializable;
import java.util.List;

import com.beans.Student;
import com.beans.dto.ListStudentDto;
import com.beans.pagination.Pagination;

public interface StudentRepository extends Serializable {

	Long saveStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(Student student) throws Exception;

	Student findStudentById(Long studentId);

	ListStudentDto findStudentsByPagination(Pagination pagination);

	void deleteStudentList(List<Long> StudentIdList);
	
	List<Student> findStudentsByIds(List<Long> Ids);
	
	void updateStudentAvgScore(Long studentId);
	
	boolean checkDuplicatedEmail(String email);
	
	int getTotalRecords(Pagination paginationStudentList);

	Student findStudentByEmail(String userEmail);


}
