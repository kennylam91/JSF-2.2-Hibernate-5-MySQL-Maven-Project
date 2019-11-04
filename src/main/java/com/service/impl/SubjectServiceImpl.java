package com.service.impl;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.Subject;
import com.repository.SubjectRepository;
import com.service.SubjectService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "subjectService")
@SessionScoped
public class SubjectServiceImpl implements SubjectService {

	private static final long serialVersionUID = -4568732441570624725L;

	@ManagedProperty(value = "#{subjectRepository}")
	private SubjectRepository subjectRepository;

	@Override
	public Long saveSubject(Subject subject) {
		return subjectRepository.saveSubject(subject);
	}

	@Override
	public void updateSubject(Subject subject) throws Exception {
		Subject subjectFound = subjectRepository.findSubjectById(subject.getId());
		if (subjectFound != null) {
			subjectRepository.updateSubject(subject);
		} else {
			throw new Exception("SubjectId not found");
		}
	}

	@Override
	public void deleteSubject(Long subjectId) throws Exception {
		Subject subjectFound = subjectRepository.findSubjectById(subjectId);
		if (subjectFound != null) {
			subjectRepository.deleteSubject(subjectFound);
		} else {
			throw new Exception("SubjectId not found");
		}

	}

	@Override
	public Subject findSubjectById(Long subjectId) {
		return subjectRepository.findSubjectById(subjectId);

	}

	@Override
	public List<Subject> findAllSubjects() {
		return subjectRepository.findAllSubjects();
	}

}
