package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.SortEvent;

import com.beans.Course;
import com.beans.Navigation;
import com.beans.ScoreDto;
import com.beans.Student;
import com.beans.StudentDto;
import com.beans.StudentFilter;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
import com.constant.COURSE_STATUSES;
import com.constant.Constant;
import com.constant.FIELDS;
import com.constant.GENDERS;
import com.exception.ScoreNotFoundException;
import com.service.ScoreService;
import com.service.StudentService;
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

	private Student selectedStudent;

	private Student newStudent = new Student();

	private Pagination pagination = new PaginationStudentList();

	private List<StudentDto> studentDtos;

	private StudentDto selectedStudentDto;

	private List<StudentDto> selectedStudentDtos;

	private Map<Long, String> courseScoreMap;

	private String userEmail;

	@ManagedProperty(value = "#{studentService}")
	private StudentService studentService;

	@ManagedProperty(value = "#{courseController}")
	private CourseController courseController;

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	@ManagedProperty(value = "#{scoreService}")
	private ScoreService scoreService;

	@PostConstruct
	public void init() {
		StudentFilter studentFilter = new StudentFilter();
		((PaginationStudentList) pagination).setStudentFilter(studentFilter);
		onPaginationChange();
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
		onPaginationChange();
		navigation.navigateToStudentList();
	}

	public Student getSelectedStudentFromStudentDto() {
		return studentService.findStudentById(selectedStudentDto.getId());
	}

	public void createStudent() {
		studentService.saveStudent(newStudent);
		closeCreateStudentDialog();
	}

	public void informAfterCreateStudent() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "CREATING COMPLETED", "STUDENT: " + newStudent.getCode()));
		newStudent = new Student(); // Clear new Student Form
	}

	public void deleteStudent() {
		try {
			studentService.deleteStudent(selectedStudentDto.getId());
			studentDtos = studentService.findStudentsByPagination(pagination);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"DELETING COMPLETED", "STUDENT: " + selectedStudentDto.getCode()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"DELETING FAILED", "STUDENT: " + selectedStudentDto.getCode()));
		}
		
		
	}

	public void getStudentDetail() throws Exception {

		// get selectedStudent from student-list-page
		if (selectedStudentDto != null) {
			selectedStudent = studentService.findStudentById(selectedStudentDto.getId());
		}
		// get selectedStudent from login view
		else {
			selectedStudent = studentService.findStudentByEmail(userEmail);
		}
		if(selectedStudent == null) {
			throw new Exception("Cannot find student detail");
		}
		// get Score by StudentId and CourseId
		if (selectedStudent.getCourses() != null) {
			for (Course course : selectedStudent.getCourses()) {
				if (course.getStatus().equals(COURSE_STATUSES.COMPLETED)) {
					try {
						courseScoreMap.put(course.getId(),
								String.valueOf(getScoreByStudentIdAndCourseId(course.getId())));
					} catch (ScoreNotFoundException e) {
						e.printStackTrace(); // need logger
					}
				}
			}
		}

		if (selectedStudentDto != null) {
			navigation.navigateToStudentDetail();
		} else {
			navigation.navigateToDashboardStudentRole();
		}

	}

	public void update() {
		studentService.updateStudent(selectedStudent);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"UPDATING COMPLETED", "STUDENT: " + selectedStudent.getCode()));
	}

	public void onPaginationChange() {
		studentDtos = studentService.findStudentsByPagination(pagination);
		int totalRecords = studentService.getTotalRecords(pagination);
		pagination.setTotalRecords(totalRecords);

	}

	public void getPreviousPage() {
		if (pagination.getPage() > 1) {
			pagination.setPage(pagination.getPage() - 1);
			studentDtos = studentService.findStudentsByPagination(pagination);
		}
	}

	public void getNextPage() {
		if (pagination.getPage() * pagination.getRowsPerPage() < pagination.getTotalRecords()) {
			pagination.setPage(pagination.getPage() + 1);
			studentDtos = studentService.findStudentsByPagination(pagination);
		}
	}

	public void onSort(SortEvent event) {
		if (event.isAscending()) {
			pagination.setAscOrDesc("asc");
		} else {
			pagination.setAscOrDesc("desc");
		}
		pagination.setOrderBy(event.getSortColumn().getField());
		onPaginationChange();
	}

	public void onActionForMultiChange() {
		studentService.deleteStudents(selectedStudentDtos);
		StringBuilder deletedStudentCodes = new StringBuilder();
		for (StudentDto student : selectedStudentDtos) {
			deletedStudentCodes.append(student.getCode()).append(", ");
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "DELETING COMPLETED", "STUDENTS: " + deletedStudentCodes));
		studentDtos = studentService.findStudentsByPagination(pagination);
		selectedStudentDtos = new ArrayList<>();

	}

	@SuppressWarnings("unchecked")
	public void addStudentToCourse(SelectEvent event) {
		List<StudentDto> list = (List<StudentDto>) event.getObject();
		List<Student> students = studentService.findStudentsByStudentDtos(list);
		for (Student student : students) {
			courseController.getSelectedCourse().addStudent(student);

			courseController.getSelectedScores()
					.add(createScoreDtoObjectFromCourseAndStudent(courseController.getSelectedCourse(), student));
		}
		courseController.updateCourse();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"ADD STUDENTS COMPLETED", "COURSE: " + courseController.getSelectedCourse().getName()));
	}

	public void addCourseToStudentAndThenUpdateCourse() {
		if (courseController.getSelectedCourse() != null) {
			courseController.getSelectedCourse().addStudent(selectedStudent);
			courseController.getSelectedScores().add(
					createScoreDtoObjectFromCourseAndStudent(courseController.getSelectedCourse(), selectedStudent));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"ADD COURSE COMPLETED", "COURSE: " + courseController.getSelectedCourse().getName()));
			courseController.updateCourse();
			update();
			courseListOfSelectedStudent = new ArrayList<>(selectedStudent.getCourses());
		}
	}

	public void removeCourseOutOfStudent() {
		if (courseController.getSelectedCourse() != null) {
			courseController.getSelectedCourse().removeStudent(selectedStudent);
			for (ScoreDto scoreDto : courseController.getSelectedScores()) {
				if (scoreDto.getCourseId().equals(courseController.getSelectedCourse().getId())
						&& scoreDto.getStudentId().equals(selectedStudent.getId())) {
					courseController.getSelectedScores().remove(scoreDto);
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"REMOVE COURSE COMPLETED", "COURSE: " + courseController.getSelectedCourse().getName()));
			courseController.updateCourse();
			update();
			courseListOfSelectedStudent = new ArrayList<>(selectedStudent.getCourses());
		}
	}

	public String checkCourseStatusAndGetRegisterBySelectedStudentOrNot(Course course) {
		boolean isCourseOnRegistering = course.getStatus().equals(COURSE_STATUSES.REGISTERING);
		boolean isCourseGetRegisterBySelectedStudent = false;
		for (Course c : selectedStudent.getCourses()) {
			if (c.equals(course))
				isCourseGetRegisterBySelectedStudent = true;
		}
		if (!isCourseOnRegistering)
			return "cannot be registerd";
		else if (isCourseGetRegisterBySelectedStudent)
			return "can be canceled";
		else
			return "can be registerd";
	}

	public boolean isCourseCanBeRegistered(Course course) {
		return (checkCourseStatusAndGetRegisterBySelectedStudentOrNot(course).equals("can be registerd"));
	}

	public boolean isCourseCanBeCanceled(Course course) {
		return checkCourseStatusAndGetRegisterBySelectedStudentOrNot(course).contentEquals("can be canceled");
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
		pagination = new PaginationStudentList();
		selectedStudentDtos = new LinkedList<>();
	}

	public float getScoreByStudentIdAndCourseId(Long courseId) throws ScoreNotFoundException {
		// Admin_role view
		if (selectedStudentDto != null)
			return scoreService.findScoreByCourseIdAndStudentId(courseId, selectedStudentDto.getId());
		// Student_role view
		else {
			return scoreService.findScoreByCourseIdAndStudentId(courseId, selectedStudent.getId());
		}
	}

	// return null if not found
	public String getScore(Long courseId) {
		return courseScoreMap.get(courseId);
	}

	public void openFilterDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("width", "500px");
		options.put("height", "450px");
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("modal", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_STUDENT_LIST_FILTER_URL, options, null);
	}

	public void closeStudentFilterDialog() {
		if (isFilterBoxValid()) {
			PrimeFaces.current().dialog().closeDynamic(null);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "FILTER NOT VALID", "PLEASE CHECK!!!!"));
		}
	}

	private boolean isFilterBoxValid() {
		StudentFilter filter = ((PaginationStudentList) pagination).getStudentFilter();
		if (filter.getIsByDOB().booleanValue()) {
			if (filter.getDOBFilterFrom() == null || filter.getDOBFilterTo() == null
					|| (filter.getDOBFilterFrom().compareTo(filter.getDOBFilterTo()) > 0))
				return false;
		}
		if (filter.getIsByScore().booleanValue()) {
			if ((filter.getScoreFilterFrom().compareTo(filter.getScoreFilterTo()) > 0))
				return false;
		}
		return true;

	}

	public void resetFilter() {
		((PaginationStudentList) pagination).setStudentFilter(new StudentFilter());
	}

	public void validateEmail(FacesContext context, UIComponent component, Object value) {

		/*
		 * 1) A-Z characters allowed 2) a-z characters allowed 3) 0-9 numbers allowed 4)
		 * Additionally email may contain only dot(.), dash(-) and underscore(_) 5) Rest
		 * all characters are not allowed
		 */
		String email = value.toString();
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation:Error", "Invalid Email !"));
		}

		if (studentService.checkDuplicatedEmail(email)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation:Error", "Email is existed!"));
		}
	}

	private ScoreDto createScoreDtoObjectFromCourseAndStudent(Course course, Student student) {
		ScoreDto scoreDto = new ScoreDto();
		scoreDto.setCourseId(course.getId());
		scoreDto.setStudentId(student.getId());
		scoreDto.setStudentCode(student.getCode());
		scoreDto.setStudentFirstname(student.getFirstName());
		scoreDto.setStudentLastname(student.getLastName());
		scoreDto.setStudentField(student.getField());
		return scoreDto;
	}

	private List<Course> courseListOfSelectedStudent = new ArrayList<>();

	public List<Course> getCourseListOfSelectedStudent() {
		if (courseListOfSelectedStudent.isEmpty()) {
			courseListOfSelectedStudent = new ArrayList<>(selectedStudent.getCourses());
		}
		return courseListOfSelectedStudent;
	}

}
