package com.test.repository;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.beans.Course;
import com.beans.Subject;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationCourseList;
import com.constant.COURSE_STATUSES;
import com.repository.CourseRepository;
import com.repository.impl.CourseRepositoryImplJdbc;
import com.service.CourseService;
import com.service.SubjectService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CourseRepositoryImplJdbcTest {

	protected CourseRepository courseRepo;
	protected Course firstCourse;
	protected Long firstCourseId;
	protected Pagination pagination;

	@Before
	public void init() {
		courseRepo = new CourseRepositoryImplJdbc();
		pagination = new PaginationCourseList();
		
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.set(2019, 11, 03);
		firstCourse = Course.builder().code("A1111111").name("Java Core 01").beginTime(firstCalendar.getTime())
				.status(COURSE_STATUSES.REGISTERING).teacher("Mr.Lam").capacity(20)
				.subject(Subject.builder().id(1L).build()).build();
		Calendar secondCalendar = Calendar.getInstance();
		secondCalendar.set(2019,11,31);
	}
	
	@Test
	public void testSaveCourse() {
		Long firstCourseId = courseRepo.saveCourse(firstCourse);
		System.out.println(firstCourseId);
		assertNotNull(firstCourseId);
	}
	
	@Test
	public void testUpdateCourse() {
		firstCourse.setCode("DKA092");
		firstCourse.setName("Java Core 02");
		Long firstCourseId = courseRepo.saveCourse(firstCourse);
		firstCourse.setId(firstCourseId);
		Calendar secondCalendar = Calendar.getInstance();
		secondCalendar.set(2019,11,31);
		firstCourse.setFinishTime(secondCalendar.getTime());
		courseRepo.updateCourse(firstCourse);
		
		
	}
}
