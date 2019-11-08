package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.beans.Course;
import com.beans.Navigation;
import com.beans.Score;
import com.beans.ScoreDto;
import com.beans.Student;
import com.beans.StudentDto;
import com.beans.StudentFilter;
import com.beans.StudentForm;
import com.beans.formbeans.NewStudentForm;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
import com.constant.COURSE_STATUSES;
import com.constant.Constant;
import com.constant.FIELDS;
import com.constant.GENDERS;
import com.service.ScoreService;
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

	private Pagination paginationStudentList = new PaginationStudentList();

	private Student newStudent = new Student();

	@ManagedProperty(value = "#{studentService}")
	private StudentServiceImpl studentService;

	@ManagedProperty(value = "#{courseController}")
	private CourseController courseController;

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	@ManagedProperty(value = "#{scoreService}")
	private ScoreService scoreService;

	private List<StudentDto> studentDtos;

	private StudentDto selectedStudentDto;

	private List<StudentDto> selectedStudentDtos;

	private Map<Long, String> courseScoreMap;

	@PostConstruct
	public void init() {
		StudentFilter studentFilter = new StudentFilter();
		((PaginationStudentList)paginationStudentList).setStudentFilter(studentFilter);
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
		courseScoreMap = new HashMap<>();
	}

	public GENDERS[] getGenders() {
		return GENDERS.values();
	}

	public FIELDS[] getFields() {
		return FIELDS.values();
	}

	public List<StudentDto> getSelectedStudentDtos() {
		if (selectedStudentDtos == null) {
			selectedStudentDtos = new LinkedList<>();
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

	public void deleteStudent() {
		studentService.deleteStudent(selectedStudentDto.getId());
		studentDtos = studentService.findStudentsByPagination(paginationStudentList);
	}

	public void getStudentDetail() {
		selectedStudent = studentService.findStudentById(selectedStudentDto.getId());
		for (Course course : selectedStudent.getCourses()) {
			if (course.getStatus().equals(COURSE_STATUSES.COMPLETED)) {
				courseScoreMap.put(course.getId(), String.valueOf(getScoreByStudentIdAndCourseId(course.getId())));
			}
		}
		navigation.navigateToStudentDetail();
	}

	public void update() {
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

	public void openCreateStudentDialog(ActionEvent ae) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", true);
		options.put("width", "700px");
		options.put("height", "560px");
		options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
		options.put("modal", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_CREATE_STUDENT_URL, options, null);
	}

	public void closeCreateStudentDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void clearPaginationAndSelectedStudentDtos() {
		courseController.setSelectedCourse(null);
		paginationStudentList = new PaginationStudentList();
		selectedStudentDtos = new LinkedList<>();
	}

	public float getScoreByStudentIdAndCourseId(Long courseId) {
		return scoreService.findScoreByCourseIdAndStudentId(courseId, selectedStudentDto.getId());
	}

	// return null if not found
	public String getScore(Long courseId) {
		return courseScoreMap.get(courseId);
	}

	public void openFilterDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("width", "470px");
		options.put("height", "400px");
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_STUDENT_LIST_FILTER_URL, options, null);
	}
	
	public void closeStudentFilterDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void resetFilter() {
		((PaginationStudentList)paginationStudentList).setStudentFilter(new StudentFilter());
	}
}
