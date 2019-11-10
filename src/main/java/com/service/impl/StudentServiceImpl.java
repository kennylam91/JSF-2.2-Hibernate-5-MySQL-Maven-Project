package com.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.constant.FIELDS;
import com.constant.GENDERS;
import com.repository.StudentRepository;
import com.service.StudentService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "studentService")
@SessionScoped
public class StudentServiceImpl implements StudentService, Serializable {

	private static final long serialVersionUID = -2495755716198044889L;

	@ManagedProperty(value = "#{studentRepository}")
	StudentRepository studentRepository;

	@Override
	public Long saveStudent(Student student) {
		return studentRepository.saveStudent(student);
	}

	@Override
	public void updateStudent(Student student) {
		studentRepository.updateStudent(student);

	}

	@Override
	public void deleteStudent(Long studentId) {
		Student student = studentRepository.findStudentById(studentId);
		studentRepository.deleteStudent(student);
	}

	@Override
	public Student findStudentById(Long studentId) {
		return studentRepository.findStudentById(studentId);

	}

	@Override
	public List<StudentDto> findStudentsByPagination(Pagination pagination) {
		return studentRepository.findStudentsByPagination(pagination);
	}

	@Override
	public void deleteStudents(List<StudentDto> studentDtos) {
		List<Long> studentIds = getStudentIdsFromStudentDtos(studentDtos);
		studentRepository.deleteStudentList(studentIds);

	}

	@Override
	public List<Student> findStudentsByStudentDtos(List<StudentDto> studentDtos) {
		List<Long> studentIds = getStudentIdsFromStudentDtos(studentDtos);
		return studentRepository.findStudentsByIds(studentIds);

	}

	private List<Long> getStudentIdsFromStudentDtos(List<StudentDto> studentDtos) {
		List<Long> studentIds = new ArrayList<>();
		for (StudentDto student : studentDtos) {
			studentIds.add(student.getId());
		}
		return studentIds;
	}

	@Override
	public void updateStudentAvgScore(Long studentId) {
		studentRepository.updateStudentAvgScore(studentId);

	}

	public boolean checkDuplicatedEmail(String email) {
		return studentRepository.checkDuplicatedEmail(email);
	}

	public int getTotalRecords(Pagination paginationStudentList) {
		return studentRepository.getTotalRecords(paginationStudentList);
	}

}
