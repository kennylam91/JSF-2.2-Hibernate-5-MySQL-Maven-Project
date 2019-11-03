package com.test.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
import com.repository.StudentRepository;
import com.repository.impl.StudentRepositoryImpl;
import com.service.StudentService;
import com.service.impl.StudentServiceImpl;

import junit.framework.TestCase;

public class StudentRepositoryTest extends TestCase {
	
	protected StudentRepository studentRepo;
	protected Student firstStudent;
	protected Student secondStudent;
	protected Pagination pagination;
	Long firstStudentId;
	Long secondStudentId;
	List<Long> studentIds = new ArrayList<>();
	
	protected void setUp() {
		
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.set(1991, 06, 18);
		
		Calendar secondCalendar = Calendar.getInstance();
		secondCalendar.set(1993, 05, 58);
		
		firstStudent = Student.builder().code("A1")
						.firstName("Lam")
						.lastName("Pham")
						.dob(firstCalendar.getTime())
						.gender("male")
						.field("JAVA")
						.address("Ha noi")
						.phone("0123456789")
						.email("phamlam@gmail.com")
						.build();
		secondStudent = Student.builder().code("A2")
				.firstName("Phuong")
				.lastName("Thom")
				.dob(secondCalendar.getTime())
				.gender("female")
				.field("PHP")
				.address("Nam Dinh")
				.phone("01234567890")
				.email("phuongthom@gmail.com")
				.build();
		
		studentRepo = new StudentRepositoryImpl();

		pagination = new PaginationStudentList();
		firstStudentId = studentRepo.saveStudent(firstStudent);
		firstStudent.setId(firstStudentId);
		secondStudentId = studentRepo.saveStudent(secondStudent);
		secondStudent.setId(secondStudentId);
		studentIds.add(firstStudentId);
		studentIds.add(secondStudentId);

	}

	//Single method not related to each other
	//Cannot set studentId in @Test because it doestnot get setted
	@Test
	public void testCreateStudendAnd() {
		assertTrue(firstStudentId instanceof Long);
		assertTrue(secondStudentId instanceof Long);

	}

	@Test
	public void testFindStudentsByPagination() {
		pagination.setSearchKeyword(secondStudent.getEmail());
		List<StudentDto> studentDtos = studentRepo.findStudentsByPagination(pagination);
		assertTrue(studentDtos.get(0).getEmail().equals(secondStudent.getEmail()));
	}
	
	@Test
	public void testFindStudentById() {
		Student studentFound = studentRepo.findStudentById(firstStudent.getId());
		assertTrue(studentFound.getId().equals(firstStudent.getId()));
	}
	
	@Test
	public void testUpdateStudent() {
		String newCode = "AAA";
		firstStudent.setCode(newCode);
		studentRepo.updateStudent(firstStudent);
		assertTrue(studentRepo.findStudentById(firstStudent.getId()).getCode().equals(newCode));
	}
	
	@Test
	public void testFindStudentByIds() {
		List<Student> students = studentRepo.findStudentsByIds(studentIds);
		assertEquals(students.size(), studentIds.size());
	}
	
	@Test
	public void testDeleteStudent() {
		studentRepo.deleteStudent(secondStudent);
		assertTrue(studentRepo.findStudentById(secondStudent.getId()) == null);
	}

	@Test
	public void testDeleteStudentList() {
		studentRepo.deleteStudentList(studentIds);
		assertTrue(studentRepo.findStudentById(firstStudent.getId()) == null);
	}
}
