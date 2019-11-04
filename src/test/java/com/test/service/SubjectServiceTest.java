package com.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beans.Subject;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationSubjectList;
import com.repository.SubjectRepository;
import com.repository.impl.SubjectRepositoryImpl;
import com.service.SubjectService;
import com.service.impl.SubjectServiceImpl;

public class SubjectServiceTest {

	protected SubjectService subjectService;
	protected SubjectRepository subjectRepo;
	protected Subject firstSubject;
	protected Long firstSubjectId;
	protected Pagination pagination;

	@Before
	public void init() {
		subjectRepo = new SubjectRepositoryImpl();
		pagination = new PaginationSubjectList();
		subjectService = new SubjectServiceImpl(subjectRepo);
		firstSubject = Subject.builder().code("A01").name("Test").field("JAVA").description("description")
				.coefficient(2.0f).build();
		firstSubjectId = subjectService.saveSubject(firstSubject);
	}

	@After
	public void after() {
		subjectService = null;
	}

	@Test
	public void testSaveSubjectAndFindCourseById() {
		Subject subjectFound = subjectService.findSubjectById(firstSubjectId);
		assertEquals(subjectFound, firstSubject);
	}

	@Test
	public void testUpdateSubject() {
		firstSubject.setField("PHP");
		try {
			subjectService.updateSubject(firstSubject);
			assertTrue(subjectService.findSubjectById(firstSubjectId).getField().equals("PHP"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteSubject() {
		try {
			subjectService.deleteSubject(firstSubjectId);
			assertNull(subjectService.findSubjectById(firstSubjectId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
