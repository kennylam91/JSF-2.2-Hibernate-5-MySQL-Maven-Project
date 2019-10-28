package com.repository;

import java.io.Serializable;
import java.util.List;

import com.Bean.Subject;

public interface SubjectRepository extends Serializable {

	Long saveSubject(Subject subject);

	void updateSubject(Subject subject);

	void deleteSubject(Subject subject);

	Subject findSubjectById(Long subjectId);

	List<Subject> findAllSubjects();
}