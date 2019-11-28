package com.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.StudentFilter;
import com.beans.dto.ListStudentDto;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
import com.constant.FIELDS;
import com.constant.GENDERS;
import com.repository.StudentRepository;
import com.repository.impl.StudentRepositoryImplJdbc;

public class StudentRepositoryImplJdbcTest {

	public StudentRepository studentRepository;
	public Student firstStudent;
	public Student secondStudent;
	public Long firstStudentId;
	
	@Before
	public void init() {
		studentRepository = new StudentRepositoryImplJdbc();
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.set(1991, 06, 18);
		Calendar secondCalendar = Calendar.getInstance();
		firstCalendar.set(1995, 01, 14);
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
		secondStudent = Student.builder().code("A09282")
				.firstName("Dung")
				.lastName("Duc")
				.dob(firstCalendar.getTime())
				.gender(GENDERS.MALE)
				.field(FIELDS.PHP)
				.address("Ha noi")
				.phone("01234562789")
				.email("duongkdjwk@gmail.com")
				.build();
	}
	
	@Test
	public void testSaveStudent() {
		firstStudentId = studentRepository.saveStudent(firstStudent);
		assertNotNull(firstStudentId);
	}
	
	@Test
	public void testFindStudentByIdAndUpdateStudent() {
		firstStudent.setCode("A109298");
		firstStudent.setEmail("phamdkfj@gmail.com");
		Long id = studentRepository.saveStudent(firstStudent);
		Student studentFound = studentRepository.findStudentById(id);
		firstStudent.setId(id);
		assertEquals(firstStudent, studentFound);
		
		Calendar dob = Calendar.getInstance();
		dob.set(1992, 04, 20);
		firstStudent.setFirstName("Van");
		firstStudent.setDob(dob.getTime());
		studentRepository.updateStudent(firstStudent);
		Student studentFound2 = studentRepository.findStudentById(id);
		assertEquals(firstStudent, studentFound2);
	}
	@Test
	public void testDeleteStudent() {
		firstStudent.setCode("Dkdiew");
		firstStudent.setEmail("kdfjow@gmail.com");
		Long id = studentRepository.saveStudent(firstStudent);
		firstStudent.setId(id);
		try {
			studentRepository.deleteStudent(firstStudent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Student studentFound = studentRepository.findStudentById(id);
		assertNull(studentFound);
		
	}
	
	@Test
	public void testDeleteStudentListAndFindStudentsByIds() {
		firstStudent.setCode("A092k2");
		firstStudent.setEmail("lkfjskfdf@gmail.com");
		Long firstId = studentRepository.saveStudent(firstStudent);
		Long secondId = studentRepository.saveStudent(secondStudent);
		List<Long> idList = new ArrayList<>();
		idList.add(firstId);
		idList.add(secondId);
		List<Student> students = studentRepository.findStudentsByIds(idList);
		assertEquals(2,students.size());
		assertNotNull(students.get(0).getId());
		studentRepository.deleteStudentList(idList);
		Student firstFound = studentRepository.findStudentById(firstId);
		Student secondFound = studentRepository.findStudentById(secondId);
		assertNull(firstFound);
		assertNull(secondFound);	
	}
	@Test
	public void testCheckDuplicateEmail() {
		firstStudent.setCode("Dks092");
		firstStudent.setEmail("duplicate@gmail.com");
		studentRepository.saveStudent(firstStudent);
		boolean actual = studentRepository.checkDuplicatedEmail("duplicate@gmail.com");
		assertTrue(actual);			
	}
	@Test
	public void testFindStudentByEmail() {
		String email = "check@gmail.com";
		firstStudent.setCode("DKske092");
		firstStudent.setEmail(email);
		studentRepository.saveStudent(firstStudent);
		Student studentFound = studentRepository.findStudentByEmail(email);
		assertEquals(studentFound.getCode(), firstStudent.getCode());
		assertEquals(studentFound.getEmail(), firstStudent.getEmail());
		assertEquals(studentFound.getFirstName(), firstStudent.getFirstName());
	}
	@Test
	public void testFindStudentsByPagination() {
		Calendar dobFilterFrom = Calendar.getInstance();
		dobFilterFrom.set(1990, 01, 01);
		Calendar dobFilterTo = Calendar.getInstance();
		dobFilterTo.set(1996, 01, 01);
		StudentFilter studentFilter = StudentFilter.builder()
				.isByGender(false)
				.isByField(false)
				.isByDOB(true)
				.isByScore(true)
				.genderFilterValue(GENDERS.MALE)
				.fieldFilterValue(FIELDS.JAVA)
				.DOBFilterFrom(dobFilterFrom.getTime())
				.DOBFilterTo(dobFilterTo.getTime())
				.scoreFilterFrom(0.0f)
				.scoreFilterTo(10.0f)
				.build();
		Pagination pagination = new PaginationStudentList();
		((PaginationStudentList)pagination).setStudentFilter(studentFilter);
		pagination.setSearchField("firstName");
		pagination.setSearchKeyword("lam");
		pagination.setAscOrDesc("desc");
		pagination.setPage(1);
		pagination.setRowsPerPage(3);
		ListStudentDto result = studentRepository.findStudentsByPagination(pagination);
		List<StudentDto> studentDtos = result.getList();
		for (StudentDto studentDto : studentDtos) {
			System.out.println(studentDto);
		}
		int totalFoundRecords = result.getTotalFoundRecords();
		System.out.println(totalFoundRecords);
		assertTrue(totalFoundRecords > 0);
		assertTrue(studentDtos.size() > 0);
		
	}
	
}
