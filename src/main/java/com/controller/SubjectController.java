package com.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import com.Bean.Subject;
import com.Bean.FormBean.NewCourseForm;
import com.Bean.FormBean.NewSubjectForm;
import com.service.SubjectService;
import com.util.ObjectMapper;

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

	@ManagedProperty(value = "#{newSubjectForm}")
	private NewSubjectForm newSubjectForm;

	@ManagedProperty(value = "#{newCourseForm}")
	private NewCourseForm newCourseForm;

	private List<Subject> subjects;

	private Subject selectedSubject;

	public List<Subject> getSubjects() {
		return subjectService.findAllSubjects();
	}

	public void deleteSubject(Long subjectId) throws Exception {
		subjectService.deleteSubject(subjectId);
	}

	public Subject getSubjectDetail(Long subjectId) {
		return subjectService.findSubjectById(subjectId);
	}

	public Long createSubject(NewSubjectForm newSubjectForm) {
		Subject subject = ObjectMapper.convertToSubjectFromNewSubjectForm(newSubjectForm);
		return subjectService.saveSubject(subject);
	}

	public void onSubjectRowSelect(SelectEvent event) {
		System.out.println((Subject) event.getObject());
		newCourseForm.setSubject((Subject) event.getObject());
		PrimeFaces.current().executeScript("PF('dlg_list_subject').hide(); PF('dlg_create_course').show()");
	}

}
