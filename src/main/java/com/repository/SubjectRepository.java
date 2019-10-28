package com.repository;

import java.util.List;

import com.Bean.Subject;

public interface SubjectRepository {

	Long saveSubject(Subject subject);

	void updateSubject(Subject subject);

	void deleteSubject(Subject subject);

	Subject findSubjectById(Long subjectId);

	List<Subject> findAllSubjects();
}
