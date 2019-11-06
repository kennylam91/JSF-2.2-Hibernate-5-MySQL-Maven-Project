package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.SortEvent;

import com.beans.Navigation;
import com.beans.Score;
import com.beans.ScoreDto;
import com.beans.Student;
import com.beans.StudentDto;
import com.beans.StudentForm;
import com.beans.formbeans.NewStudentForm;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
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

	private Pagination paginationStudentList = new PaginationStudentList();

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
		//when students-list-dialog open
		if(courseController.getSelectedCourse() != null) {
			selectedStudentDtos = new LinkedList<>();
			for (Student student : courseController.getSelectedCourse().getStudents()) {
				StudentDto studentDto = ObjectMapper.modelMapper.map(student,StudentDto.class);
				selectedStudentDtos.add(studentDto);
			}
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

	@SuppressWarnings("unchecked")
	public void addStudentToCourse(SelectEvent event) {
		List<StudentDto> list = (List<StudentDto>) event.getObject();
		List<Student> students = studentService.findStudentsByStudentDtos(list);
		for (Student student : students) {
			courseController.getSelectedCourse().addStudent(student);
			ScoreDto scoreDto = new ScoreDto();
			scoreDto.setCourseId(courseController.getSelectedCourse().getId());
			scoreDto.setStudentId(student.getId());
			scoreDto.setStudentCode(student.getCode());
			scoreDto.setStudentFirstname(student.getFirstName());
			scoreDto.setStudentLastname(student.getLastName());
			scoreDto.setStudentField(student.getField());
			courseController.getSelectedScores().add(scoreDto);
		}
		

	}
	
	public void removeStudentOutOfCourse(Long studentId) {
		System.out.println("remove student " + studentId);
		/*
		 * for (StudentDto studentDto : selectedStudentDtos) {
		 * System.out.println(studentDto); }
		 */
//		courseController.getSelectedCourse().removeStudent(student);
	}

	public void openCreateStudentDialog(ActionEvent ae) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("width", "470px");
		options.put("height", "550px");
		options.put("model", true);
		PrimeFaces.current().dialog().openDynamic("/templates/student-list-page/dialog_create_student", options, null);
	}

	public void closeCreateStudentDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void closeStudentListDialog() {
		PrimeFaces.current().dialog().closeDynamic(selectedStudentDtos);
	}

}
