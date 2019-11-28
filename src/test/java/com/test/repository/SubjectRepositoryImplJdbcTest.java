package com.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.beans.Subject;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationSubjectList;
import com.constant.FIELDS;
import com.repository.SubjectRepository;
import com.repository.impl.SubjectRepositoryImpl;
import com.repository.impl.SubjectRepositoryImplJdbc;
import com.service.SubjectService;
import com.service.impl.SubjectServiceImpl;

public class SubjectRepositoryImplJdbcTest {

	protected SubjectRepository subjectRepo;
	protected Subject firstSubject;
	protected Long firstSubjectId;

	@Before
	public void init() {
		subjectRepo = new SubjectRepositoryImplJdbc();
		firstSubject = Subject.builder().code("A01").name("Test").field(FIELDS.JAVA).description("description")
				.coefficient(2.0f).build();
		
	}
	
	@Test
	public void testSaveSubjectAndFindSubjectById() {
		firstSubject.setCode("ADKskj09");
		Long id = subjectRepo.saveSubject(firstSubject);
		assertNotNull(id);
		Subject subject = subjectRepo.findSubjectById(id);
		assertEquals(subject.getCode(), firstSubject.getCode());
		
	}
	@Test
	public void testUpdateSubjectAndFindSubjectByName(){
		firstSubject.setName("PHP Core");
		firstSubject.setField(FIELDS.PHP);
		firstSubject.setDescription("new Description");
		firstSubject.setCoefficient(4.0f);
		firstSubject.setId(3L);
		subjectRepo.updateSubject(firstSubject);
		Subject subjectFound = subjectRepo.findSubjectByName("PHP Core");
		assertEquals(subjectFound.getCode(), firstSubject.getCode());
	}
	@Test
	public void testFindAllSubject() {
		List<Subject> subjects = subjectRepo.findAllSubjects();
		assertTrue(subjects.size() > 0);
	}
}
