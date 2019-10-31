package com.repository;

import java.io.Serializable;
import java.util.List;

import com.beans.Course;

public interface CourseRepository extends Serializable{
	
	Long saveCourse(Course course);

	void updateCourse(Course course);

	void deleteCourse(Course course);

	Course findCourseById(Long courseId);

	List<Course> findAllCourses();

}
