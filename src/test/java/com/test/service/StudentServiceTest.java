package com.test.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
import com.repository.StudentRepository;
import com.repository.impl.StudentRepositoryImpl;
import com.service.StudentService;
import com.service.impl.StudentServiceImpl;
import com.util.ObjectMapper;

public class StudentServiceTest {

	protected StudentService studentService;
	protected StudentRepository studentRepo;
	protected Pagination pagination;
	protected Student firstStudent;
	protected Long firstStudentId;
	protected Student secondStudent;
	protected Long secondStudentId;
	protected StudentDto firstStudentDto;
	protected StudentDto secondStudentDto;
	List<Long> studentIds = new ArrayList<>();
	List<Student> students = new ArrayList<>();
	List<StudentDto> studentDtos = new ArrayList<>();

	@Before
	public void setup() {
		studentRepo = new StudentRepositoryImpl();
		pagination = new PaginationStudentList();
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
		firstStudentId = studentRepo.saveStudent(firstStudent);
		firstStudent.setId(firstStudentId);
		secondStudentId = studentRepo.saveStudent(secondStudent);
		secondStudent.setId(secondStudentId);
		studentIds.add(firstStudentId);
		studentIds.add(secondStudentId);
		students.add(firstStudent);
		students.add(secondStudent);
		firstStudentDto = ObjectMapper.modelMapper.map(firstStudent, StudentDto.class);
		secondStudentDto = ObjectMapper.modelMapper.map(secondStudent, StudentDto.class);
		studentDtos.add(firstStudentDto);
		studentDtos.add(secondStudentDto);

	}

	@After
	public void after() {
		try {
			studentService.deleteStudents(studentDtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testStudentServiceNotNull() {
		assertNotNull(studentService);
	}

	@Test
	public void testCreateAndFindStudentsByPagination() {
		pagination.setSearchKeyword(secondStudent.getEmail());
		List<StudentDto> studentDtos = studentService.findStudentsByPagination(pagination);
		assertTrue(studentDtos.get(0).getEmail().equals(secondStudent.getEmail()));
	}
	
	@Test
	public void testUpdateStudentAndFindStudentById() {
		String newCode = "AAA";
		firstStudent.setCode(newCode);
		studentService.updateStudent(firstStudent);
		assertTrue(studentService.findStudentById(firstStudent.getId()).getCode().equals(newCode));
	}
	
	@Test
	public void testFindStudentByStudentDtos() {
		List<Student> students = studentService.findStudentsByStudentDtos(studentDtos);
		assertEquals(students.size(), studentDtos.size());
	}

}
