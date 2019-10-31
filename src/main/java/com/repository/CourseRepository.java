package com.repository;

import java.io.Serializable;
import java.util.List;

import com.beans.Course;
import com.beans.pagination.Pagination;

public interface CourseRepository extends Serializable {

	Long saveCourse(Course course);

	void updateCourse(Course course);

	void deleteCourse(Course course);

	Course findCourseById(Long courseId);

	List<Course> findAllCourses();

	List<Course> findAllCourses(Pagination pagination);

}
