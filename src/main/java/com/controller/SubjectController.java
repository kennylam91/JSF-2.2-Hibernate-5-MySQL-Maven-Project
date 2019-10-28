package com.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.Bean.Subject;
import com.service.SubjectService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "subjectController")
@SessionScoped
public class SubjectController implements Serializable {

	private static final long serialVersionUID = 5245246925977536252L;

	@ManagedProperty(value = "#{subjectService}")
	private SubjectService subjectService;

	private List<Subject> subjects;

	public List<Subject> getSubjects() {
		return subjectService.findAllSubjects();
	}

	public void deleteSubject(Long subjectId) throws Exception {
		subjectService.deleteSubject(subjectId);
	}

	public Subject getSubjectDetail(Long subjectId) {
		return subjectService.findSubjectById(subjectId);
	}

}
