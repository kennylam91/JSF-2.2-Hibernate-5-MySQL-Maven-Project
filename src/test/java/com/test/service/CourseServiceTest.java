package com.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.beans.Course;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationCourseList;
import com.repository.CourseRepository;
import com.repository.impl.CourseRepositoryImpl;
import com.service.CourseService;
import com.service.SubjectService;
import com.service.impl.CourseServiceImpl;

public class CourseServiceTest {

	protected CourseService courseService;
	protected CourseRepository courseRepo;
	protected SubjectService subjectService;
	protected Course firstCourse;
	protected Long firstCourseId;
	protected Pagination pagination;

	@Before
	public void init() {
		courseRepo = new CourseRepositoryImpl();
		subjectService = Mockito.mock(SubjectService.class);
		pagination = new PaginationCourseList();
		courseService = new CourseServiceImpl(courseRepo, subjectService);

		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.set(2019, 11, 03);
		firstCourse = Course.builder().code("A1111111").name("Java Core 01").beginTime(firstCalendar.getTime())
				.status("registering").teacher("Mr.Lam").capacity(20).build();
		firstCourseId = courseService.saveCourse(firstCourse);
		firstCourse.setId(firstCourseId);
	}

	@After
	public void after() {
		courseService = null;
	}

	@Test
	public void testSaveCourseAndFindCourseById() {
		Course courseFound = courseService.findCourseById(firstCourseId);
		assertEquals(firstCourse, courseFound);
	}

	@Test
	public void testUpdateCourse() {
		firstCourse.setCapacity(10);
		try {
			courseService.updateCourse(firstCourse);
			assertEquals(10, courseService.findCourseById(firstCourseId).getCapacity());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFindAllCourseByPagination() {
		pagination.setSearchKeyword("A1111111");
		List<Course> courses = courseService.findAllCoursesByPagination(pagination);
		assertTrue(courses.get(0).getCode().equals(firstCourse.getCode()));

	}

	@Test
	public void testDeleteCourse() {
		try {
			courseService.deleteCourse(firstCourseId);
			assertNull(courseService.findCourseById(firstCourseId));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
