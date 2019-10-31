package com.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.Bean.Course;
import com.Bean.Navigation;
import com.Bean.Subject;
import com.Bean.FormBean.NewCourseForm;
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

	public List<Course> getCourses() {
		return courseService.findAllCourses();
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

}
