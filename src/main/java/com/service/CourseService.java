package com.service;

import java.io.Serializable;
import java.util.List;

import com.beans.Course;
import com.beans.pagination.Pagination;

public interface CourseService extends Serializable {

	Long saveCourse(Course course);

	void updateCourse(Course course) throws Exception;

	void deleteCourse(Long courseId) throws Exception;

	Course findCourseById(Long courseId);

	List<Course> findAllCourses();

	List<Course> findAllCoursesByPagination(Pagination pagination);
}
