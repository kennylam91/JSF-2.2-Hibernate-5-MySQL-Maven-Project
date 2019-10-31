package com.service.impl;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.Course;
import com.repository.CourseRepository;
import com.service.CourseService;
import com.service.SubjectService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "courseService")
@SessionScoped
public class CourseServiceImpl implements CourseService {

	private static final long serialVersionUID = -1271596208609992371L;
	@ManagedProperty(value = "#{courseRepository}")
	private CourseRepository courseRepository;

	@ManagedProperty(value = "#{subjectService}")
	private SubjectService subjectService;

	@Override
	public Long saveCourse(Course course) {
		return courseRepository.saveCourse(course);
	}

	@Override
	public void updateCourse(Course course) throws Exception {
		Course courseFound = courseRepository.findCourseById(course.getId());
		if (courseFound == null) {
			throw new Exception("CourseId not found");
		} else {
			courseFound.setBeginTime(course.getBeginTime());
			courseFound.setCapacity(course.getCapacity());
			courseFound.setCode(course.getCode());
			courseFound.setDescription(course.getDescription());
			courseFound.setFinishTime(course.getFinishTime());
			courseFound.setName(course.getName());
			courseFound.setStatus(course.getStatus());
			courseFound.setStudents(course.getStudents());
			courseFound.setSubject(course.getSubject());
			courseFound.setTeacher(course.getTeacher());
			courseRepository.updateCourse(courseFound);
		}
	}

	@Override
	public void deleteCourse(Long courseId) throws Exception {
		Course course = courseRepository.findCourseById(courseId);
		if (course != null) {
			courseRepository.deleteCourse(course);
		} else {
			throw new Exception("CourseId not found");
		}

	}

	@Override
	public Course findCourseById(Long courseId) {
		return courseRepository.findCourseById(courseId);
	}

	@Override
	public List<Course> findAllCourses() {
		return courseRepository.findAllCourses();
	}

}
