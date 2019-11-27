package com.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.beans.Student;
import com.constant.FIELDS;
import com.constant.GENDERS;
import com.repository.StudentRepository;
import com.repository.impl.StudentRepositoryImplJdbc;

public class StudentRepositoryImplJdbcTest {

	public StudentRepository studentRepository;
	public Student firstStudent;
	public Long firstStudentId;
	
	@Before
	public void init() {
		studentRepository = new StudentRepositoryImplJdbc();
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.set(1991, 06, 18);
		firstStudent = Student.builder().code("A1")
				.firstName("Lam")
				.lastName("Pham")
				.dob(firstCalendar.getTime())
				.gender(GENDERS.MALE)
				.field(FIELDS.JAVA)
				.address("Ha noi")
				.phone("0123456789")
				.email("phamlam@gmail.com")
				.build();
	}
	
	@Test
	public void testSaveStudent() {
		firstStudentId = studentRepository.saveStudent(firstStudent);
		System.out.println(firstStudentId);
		assertNotNull(firstStudentId);
	}
	
	@Test
	public void testFindStudentById() {
		firstStudent.setCode("A109298");
		firstStudent.setEmail("phamdkfj@gmail.com");
		Long id = studentRepository.saveStudent(firstStudent);
		Student studentFound = studentRepository.findStudentById(id);
		firstStudent.setId(id);
		assertEquals(firstStudent, studentFound);
	}
}
