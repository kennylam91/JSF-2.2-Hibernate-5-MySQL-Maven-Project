package com.test.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.Calendar;

import com.beans.Student;
import com.repository.StudentRepository;
import com.service.StudentService;
import com.service.impl.StudentServiceImpl;

public class StudentServiceTest {

	private StudentService studentService;
	private StudentRepository studentRepo;
	private Student firstStudent;
	private Student secondStudent;

	@Before
	public void setup() {
		studentRepo = mock(StudentRepository.class);
		studentService = new StudentServiceImpl(studentRepo);
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.set(1991, 06, 18);

		Calendar secondCalendar = Calendar.getInstance();
		secondCalendar.set(1993, 05, 58);

		firstStudent = Student.builder().code("A1").firstName("Lam").lastName("Pham").dob(firstCalendar.getTime())
				.gender("male").field("JAVA").address("Ha noi").phone("0123456789").email("phamlam@gmail.com").build();
		secondStudent = Student.builder().code("A2").firstName("Phuong").lastName("Thom").dob(secondCalendar.getTime())
				.gender("female").field("PHP").address("Nam Dinh").phone("01234567890").email("phuongthom@gmail.com")
				.build();

	}

	@After
	public void after() {
		studentService = null;
	}

	@Test
	public void testStudentServiceNotNull() {
		assertNotNull(studentService);
	}

	@Test
	public void testCreateStudent() {
		Long id = new Long(100L);
		when(studentRepo.saveStudent(firstStudent)).thenReturn(id);
		Long firstStudentId = studentService.saveStudent(firstStudent);
		assertEquals(firstStudentId, id);
	}

}
