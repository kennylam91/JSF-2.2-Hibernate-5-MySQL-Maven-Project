package com.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.Bean.Course;
import com.Bean.FormBean.NewCourseForm;
import com.service.CourseService;
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
	private List<Course> course;

	@ManagedProperty(value = "#{courseService}")
	private CourseService courseService;

	@ManagedProperty(value = "#{newCourseForm}")
	private NewCourseForm newCourseForm;

	public List<Course> getCourses() {
		return courseService.findAllCourses();
	}

	public void createCourse(NewCourseForm newCourseForm) {
		Course course = ObjectMapper.convertToCourseFromNewCourseForm(newCourseForm);
		Long newCourseId = courseService.saveCourse(course);
	}

	public void deleteCourse(Long courseId) {
		try {
			courseService.deleteCourse(courseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
