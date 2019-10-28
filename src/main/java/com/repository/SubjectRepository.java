package com.repository;

import java.util.List;

import com.Bean.Subject;

public interface SubjectRepository {

	void saveSubject(Subject subject);

	void updateSubject(Subject subject);

	void deleteSubject(Long subjectId);

	Subject findSubjectById(Long subjectId);

//	List<SubjectDto> findAllSubjects();
}
