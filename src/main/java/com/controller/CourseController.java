package com.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.data.SortEvent;

import com.beans.Course;
import com.beans.Navigation;
import com.beans.Subject;
import com.beans.formbeans.NewCourseForm;
import com.beans.pagination.Pagination;
import com.service.CourseService;
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

	@ManagedProperty(value = "#{courseService}")
	private CourseService courseService;

	@ManagedProperty(value = "#{subjectService}")
	private SubjectService subjectService;

	@ManagedProperty(value = "#{newCourseForm}")
	private NewCourseForm newCourseForm;

	@ManagedProperty(value = "#{course}")
	private Course course;

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	@ManagedProperty(value = "#{paginationCourseList}")
	private Pagination paginationCourseList;

	@PostConstruct
	public void init() {
		courses = courseService.findAllCourses(paginationCourseList);
	}

	public void createCourse(NewCourseForm newCourseForm) {
		Course course = ObjectMapper.convertToCourseFromNewCourseForm(newCourseForm);
		Long newCourseId = courseService.saveCourse(course);
		clearNewCourseForm();
	}

	private void clearNewCourseForm() {
		newCourseForm.setBeginTime(null);
		newCourseForm.setCapacity(0);
		newCourseForm.setCode(null);
		newCourseForm.setFinishTime(null);
		newCourseForm.setName(null);
		newCourseForm.setStatus(null);
		newCourseForm.setSubject(null);
		newCourseForm.setTeacher(null);

	}

	public void deleteCourse(Long courseId) {
		try {
			courseService.deleteCourse(courseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCourse() {
		try {
			System.out.println(course);
			courseService.updateCourse(course);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editMode = false;

	}

	public void getCourseDetail(Long courseId) {
		course = courseService.findCourseById(courseId);
		navigation.navigateToCourseDetail();
	}

	public void setSubjectToCourse(Long subjectId) {
		Subject subject = subjectService.findSubjectById(subjectId);
		course.setSubject(subject);
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
		courses = courseService.findAllCourses(paginationCourseList);
		System.out.println(paginationCourseList);
		System.out.println(courses);
	}

}
