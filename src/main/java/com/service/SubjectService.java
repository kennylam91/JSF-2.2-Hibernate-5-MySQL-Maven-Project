package com.service;

import java.io.Serializable;
import java.util.List;

import com.beans.Subject;

public interface SubjectService extends Serializable {

	Long saveSubject(Subject subject);

	void updateSubject(Subject subject) throws Exception;

	void deleteSubject(Long subjectId) throws Exception;

	Subject findSubjectById(Long subjectId);

	List<Subject> findAllSubjects();
}
