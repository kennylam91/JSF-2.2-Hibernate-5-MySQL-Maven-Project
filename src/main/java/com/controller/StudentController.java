package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
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

	public List<StudentDto> getStudentDtos() {
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
		studentService.saveStudent(newStudent);
		newStudent = new Student();
		closeCreateStudentDialog();
		logger.info("create Student successfully");

	}

	public void deleteStudent() throws Exception {
		studentService.deleteStudent(selectedStudentDto.getId());
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
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
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
		selectedStudentDtos = new ArrayList<>();

	}

	public void addStudentToCourse() {
		List<Student> students = studentService.findStudentsByStudentDtos(selectedStudentDtos);
		for (Student student : students) {
			courseController.getSelectedCourse().addStudent(student);
		}
		selectedStudentDtos = new ArrayList<>();
	}

	public void openCreateStudentDialog(ActionEvent ae) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("width","470px");
		options.put("height","560px");
		PrimeFaces.current().dialog().openDynamic("dialog_create_student", options, null);
	}

	public void closeCreateStudentDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

}
