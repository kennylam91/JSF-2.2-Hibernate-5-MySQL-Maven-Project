package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.SortEvent;

import com.beans.Course;
import com.beans.Navigation;
import com.beans.Score;
import com.beans.ScoreDto;
import com.beans.Subject;
import com.beans.formbeans.NewCourseForm;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationCourseList;
import com.constant.Constant;
import com.service.CourseService;
import com.service.ScoreService;
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
	private List<Course> courses;
	private boolean editMode = false;
	private Subject selectedSubject;
	private Course selectedCourse;
	private List<ScoreDto> selectedScores = new ArrayList<>();
	private ScoreDto selectedScore;

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

	private Pagination paginationCourseList = new PaginationCourseList();

	public List<ScoreDto> getSelectedScores() {
		System.out.println("selected scores " + selectedScores);
		return selectedScores;
	}

	public void SetSelectedScores(List<ScoreDto> scores) {
		this.selectedScores.addAll(scores);
	}

	public Set<Score> scores = new HashSet<>();;

	public List<Course> getCourses() {
		return courseService.findAllCoursesByPagination(paginationCourseList);
	}

	public void createCourse() {
		Course course = ObjectMapper.convertToCourseFromNewCourseForm(newCourseForm);
		Long newCourseId = courseService.saveCourse(course);
		closeCreateCourseDialog();
		newCourseForm = new NewCourseForm();
	}

	public void deleteCourse() {
		try {
			courseService.deleteCourse(selectedCourse.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCourse() {
		try {
			courseService.updateCourse(selectedCourse);
			scoreService.saveAll(scores);
		} catch (Exception e) {
			e.printStackTrace();
		}
		editMode = false;

	}

	public void getCourseDetail() {
		selectedCourse = courseService.findCourseById(selectedCourse.getId());
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

	public void activeEditMode() {
		editMode = true;
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
		options.put("resizable", false);
		options.put("width", "470px");
		options.put("height", "470px");
		options.put("model", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_CREATE_COURSE_URL, options, null);
	}

	public void closeCreateCourseDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void openSubjectListDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("model", true);
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

	public void openStudentListDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("model", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_STUDENT_LIST_URL, options, null);
	}

	public void openAddScoresDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("model", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_ADD_SCORES_URL, options, null);
	}


	public void closeCourseScoreDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void updateScores() {

		Set<Score> scoreSet = new HashSet<>();
		for (Score score : selectedScores) {
			scoreSet.add(score);
		}

		scoreService.updateAll(scoreSet);
		closeCourseScoreDialog();
	}

	public void onRowSelectScoreTable(SelectEvent event) {
		PrimeFaces.current().executeScript("PF('input_score_dlg').show();");
	}

	public void onScoreChange() {
		PrimeFaces.current().executeScript("PF('input_score_dlg').hide();");
		for (int i = 0; i < selectedScores.size(); i++) {
			if (selectedScores.get(i).getId() == selectedScore.getId()) {
				selectedScores.get(i).setScore(selectedScore.getScore());
			}
		}

	}

}
