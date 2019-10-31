package com.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.data.SortEvent;

import com.beans.Navigation;
import com.beans.Student;
import com.beans.StudentDto;
import com.beans.StudentForm;
import com.beans.formbeans.NewStudentForm;
import com.beans.pagination.Pagination;
import com.service.impl.StudentServiceImpl;
import com.util.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ManagedBean(name = "studentController")
@SessionScoped
public class StudentController implements Serializable {

	private static final long serialVersionUID = 7828547113215254846L;

	private static final Logger logger = Logger.getLogger(StudentController.class);

	@ManagedProperty(value = "#{studentForm}")
	private StudentForm studentForm;

	@ManagedProperty(value = "#{studentService}")
	private StudentServiceImpl studentService;

	@ManagedProperty(value = "#{paginationStudentList}")
	private Pagination paginationStudentList;

	@ManagedProperty(value = "#{newStudentForm}")
	private NewStudentForm newStudentForm;

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	private List<StudentDto> studentDtos;

	private List<StudentDto> selectedStudentDtos;

	public List<StudentDto> getStudentDtos(Pagination paginationStudentList) {
		return studentService.findStudentsByPagination(paginationStudentList);
	}

	public void getStudentListForm() {
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
		navigation.navigateToStudentList();
	}

	public void createStudent(NewStudentForm newStudentForm) {
		Student student = ObjectMapper.convertToStudentFromNewStudentForm(newStudentForm);
		studentService.saveStudent(student);

		// update studentDtos for table student_list
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
		clearNewStudenForm(newStudentForm);

	}

	public void deleteStudent(Long studentId) throws Exception {
		studentService.deleteStudent(studentId);
		studentDtos = studentService.findAllStudents();
	}

	public void getStudentDetail(Long studentId) throws Exception {
		Student student = studentService.findStudentById(studentId);
		ObjectMapper.convertToStudentForm(student, studentForm);
		navigation.navigateToStudentDetail();
	}

	public void update(StudentForm studentForm) throws Exception {
		Student student = new Student();
		ObjectMapper.convertToStudent(studentForm, student);
		studentService.updateStudent(student);
	}

	public void onPaginationChange() {
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
	}

	public void getPreviousPage() {
		if (paginationStudentList.getPage() > 1) {
			paginationStudentList.setPage(paginationStudentList.getPage() - 1);
			studentDtos = studentService.findStudentsByPagination(paginationStudentList);
		}
	}

	public void getNextPage() {
		paginationStudentList.setPage(paginationStudentList.getPage() + 1);
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
	}

	private void clearNewStudenForm(NewStudentForm newStudentForm) {
		newStudentForm.setAddress(null);
		newStudentForm.setCode(null);
		newStudentForm.setDob(null);
		newStudentForm.setEmail(null);
		newStudentForm.setField(null);
		newStudentForm.setFirstName(null);
		newStudentForm.setLastName(null);
		newStudentForm.setPhone(null);
	}

	private void clearStudentForm() {
		studentForm.setAddress(null);
		studentForm.setAvgScore(0);
		studentForm.setCode(null);
		studentForm.setCourses(null);
		studentForm.setDob(null);
		studentForm.setEmail(null);
		studentForm.setField(null);
		studentForm.setFirstName(null);
		studentForm.setId(null);
		studentForm.setLastName(null);
		studentForm.setNote(null);
		studentForm.setPhone(null);
	}

	@PostConstruct
	public void init() {
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
	}

	public void onSort(SortEvent event) {
		if (event.isAscending()) {
			paginationStudentList.setAscOrDesc("asc");
		} else {
			paginationStudentList.setAscOrDesc("desc");
		}
		paginationStudentList.setOrderBy(event.getSortColumn().getField());
		onPaginationChange();
	}

}
