package com.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.repository.StudentRepository;
import com.repository.impl.StudentRepositoryImpl;
import com.service.StudentService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "studentService")
@SessionScoped
public class StudentServiceImpl implements StudentService, Serializable {

	private static final long serialVersionUID = -2495755716198044889L;

	private List<Student> students;

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
		studentRepository.deleteStudent(studentId);
	}

	@Override
	public Student findStudentById(Long studentId) {
		return studentRepository.findStudentById(studentId);

	}

	@Override
	public List<StudentDto> findAllStudents() {
		return studentRepository.findAllStudents();

	}

	@Override
	public List<StudentDto> findStudentsByPagination(Pagination pagination) {
		return studentRepository.findStudentsByPagination(pagination);
	}

	@Override
	public void deleteStudents(List<StudentDto> StudentDtos) {
		List<Long> studentIds = new ArrayList<Long>();
		for (StudentDto student : StudentDtos) {
			studentIds.add(student.getId());
		}
		studentRepository.deleteStudentList(studentIds);

	}

}
