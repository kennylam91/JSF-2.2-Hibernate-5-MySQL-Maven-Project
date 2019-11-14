package com.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.SortEvent;

import com.beans.Course;
import com.beans.Navigation;
import com.beans.Score;
import com.beans.ScoreDto;
import com.beans.Student;
import com.beans.Subject;
import com.beans.formbeans.NewCourseForm;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationCourseList;
import com.constant.COURSE_STATUSES;
import com.constant.Constant;
import com.service.CourseService;
import com.service.ScoreService;
import com.service.StudentService;
import com.service.SubjectService;
import com.util.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "courseController")
@SessionScoped
public class CourseController implements Serializable {

	private static final long serialVersionUID = 4251571962723500481L;
	
	private List<Course> courses = new LinkedList<>();
	private Subject selectedSubject = new Subject();
	private Course selectedCourse = new Course();
	private Set<ScoreDto> selectedScores = new HashSet<>();
	private ScoreDto selectedScore = new ScoreDto();

	@ManagedProperty(value = "#{courseService}")
	private CourseService courseService;

	@ManagedProperty(value = "#{subjectService}")
	private SubjectService subjectService;

	@ManagedProperty(value = "#{newCourseForm}")
	private NewCourseForm newCourseForm;

	@ManagedProperty(value = "#{scoreService}")
	private ScoreService scoreService;

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	@ManagedProperty(value = "#{studentService}")
	private StudentService studentService;

	private Pagination paginationCourseList = new PaginationCourseList();

	public COURSE_STATUSES[] getCourseStatuses() {
		return COURSE_STATUSES.values();
	}

	public Set<Score> scores = new HashSet<>();;

	public List<Course> getCourses() {
		return courseService.findAllCoursesByPagination(paginationCourseList);
	}

	public void createCourse() {
		Course course = ObjectMapper.convertToCourseFromNewCourseForm(newCourseForm);
		courseService.saveCourse(course);
		closeCreateCourseDialog();

	}

	public void informAfterCreateCourse() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"CREATING COMPLETED", "COURSE: " + newCourseForm.getCode()));
		newCourseForm = new NewCourseForm(); // Clear new Course Form
	}

	public void deleteCourse() {
		try {
			courseService.deleteCourse(selectedCourse.getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"DELETING COMPLETED", "COURSE: " + selectedCourse.getName()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCourse() {
		try {
			courseService.updateCourse(selectedCourse);
			scoreService.saveAllScoreDtos(selectedScores);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"UPDATING COMPLETED", "COURSE: " + selectedCourse.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getCourseDetail() {
		Long selectedCourseId = selectedCourse.getId();
		selectedCourse = courseService.findCourseById(selectedCourseId);
		selectedScores = scoreService.findScoreDtosByCourseId(selectedCourseId);
		navigation.navigateToCourseDetail();
	}

	public void setSubjectToCourse(Long subjectId) {
		Subject subject = subjectService.findSubjectById(subjectId);
		selectedCourse.setSubject(subject);
	}

	public void getCourseListView() {
		courses = courseService.findAllCourses();
		navigation.navigateToCourseList();
	}

	public void onSort(SortEvent event) {
		paginationCourseList.setOrderBy(event.getSortColumn().getField());
		if (event.isAscending()) {
			paginationCourseList.setAscOrDesc("asc");
		} else {
			paginationCourseList.setAscOrDesc("desc");
		}
		onPaginationChange();

	}

	public void onPaginationChange() {
		courses = courseService.findAllCoursesByPagination(paginationCourseList);
	}

	public void getPreviousPage() {
		int currentPage = paginationCourseList.getPage();
		if (currentPage > 1) {
			paginationCourseList.setPage(currentPage - 1);
		}
		onPaginationChange();

	}

	public void getNextPage() {
		int currentPage = paginationCourseList.getPage();
		paginationCourseList.setPage(currentPage + 1);
		onPaginationChange();
	}

	public void openCreateCourseDialog(ActionEvent ae) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", true);
		options.put("width", "670px");
		options.put("height", "470px");
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("modal", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_CREATE_COURSE_URL, options, null);
	}

	public void closeCreateCourseDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void openSubjectListDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", true);
		options.put("width", "650px");
		options.put("height", "600px");
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("modal", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_SUBJECT_LIST_URL, options, null);
	}

	public void onSubjectSelectedDialogClose(SelectEvent event) {
		newCourseForm.setSubject((Subject) event.getObject());
	}

	public void onSubjectSelectedDialogCloseOnCourseDetail(SelectEvent event) {
		selectedCourse.setSubject((Subject) event.getObject());
	}

	public void closeSubjectListDialog() {
		PrimeFaces.current().dialog().closeDynamic(selectedSubject);
	}

	public void openAddScoresDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", true);
		options.put("width", "500px");
		options.put("height", "500px");
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("modal", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_ADD_SCORES_URL, options, null);
	}

	public void closeCourseScoreDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void updateScores() {
		scoreService.saveAllScoreDtos(selectedScores);
		for (ScoreDto scoreDto : selectedScores) {
			studentService.updateStudentAvgScore(scoreDto.getStudentId());
		}
		closeCourseScoreDialog();
	}

	public void informAfterAddingScores() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"ADD SCORES COMPLETED", "COURSE: " + selectedCourse.getName()));
	}

	public void removeStudentOutOfCourse(Long studentId) {
		Student student = studentService.findStudentById(studentId);
		selectedCourse.removeStudent(student);
		try {
			courseService.updateCourse(selectedCourse);
			scoreService.delete(selectedCourse.getId(), studentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		selectedScores = scoreService.findScoreDtosByCourseId(selectedCourse.getId());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "REMOVING COMPLETED", "STUDENT: " + student.getCode()));
	}

	// Sort for Course Detail Table of Student detail page

	public boolean isSelectedCourseStatusCompleted() {
		return selectedCourse.getStatus().equals(COURSE_STATUSES.COMPLETED);
	}

	public int sortCourseByString(Object c1, Object c2) {
		return ((String) c1).compareTo((String) c2);
	}

}
