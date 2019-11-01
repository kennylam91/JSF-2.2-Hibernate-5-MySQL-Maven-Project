package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
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

	private Student selectedStudent;

	@ManagedProperty(value = "#{studentService}")
	private StudentServiceImpl studentService;

	@ManagedProperty(value = "#{paginationStudentList}")
	private Pagination paginationStudentList;

	private Student newStudent = new Student();

	@ManagedProperty(value = "#{courseController}")
	private CourseController courseController;

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	private List<StudentDto> studentDtos;

	private StudentDto selectedStudentDto;

	private List<StudentDto> selectedStudentDtos;

	private String actionForMulti = "create";

	public List<StudentDto> getStudentDtos(Pagination paginationStudentList) {
		return studentService.findStudentsByPagination(paginationStudentList);
	}

	public List<StudentDto> getSelectedStudentDtos() {
		if (selectedStudentDtos == null) {
			selectedStudentDtos = new ArrayList<>();
		}
		return selectedStudentDtos;
	}

	public void getStudentListForm() {
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
		navigation.navigateToStudentList();
	}

	public Student getSelectedStudentFromStudentDto() {
		return studentService.findStudentById(selectedStudentDto.getId());
	}

	public void createStudent() {
		System.out.println(newStudent);
		studentService.saveStudent(newStudent);

	}

	public void deleteStudent(Long studentId) throws Exception {
		studentService.deleteStudent(studentId);
		studentDtos = studentService.findAllStudents();
	}

	public void getStudentDetail() throws Exception {
		selectedStudent = studentService.findStudentById(selectedStudentDto.getId());
		navigation.navigateToStudentDetail();
	}

	public void update() throws Exception {
		System.out.println("update: " + selectedStudent);
		studentService.updateStudent(selectedStudent);
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

	public void onActionForMultiChange() {
		studentService.deleteStudents(selectedStudentDtos);
		System.out.println("delete");
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
		selectedStudentDtos = new ArrayList<StudentDto>();

	}

	public void addStudentToCourse() {
		List<Student> students = studentService.findStudentsByStudentDtos(selectedStudentDtos);
		courseController.course.getStudents().addAll(students);
		selectedStudentDtos = new ArrayList<StudentDto>();
	}

}
