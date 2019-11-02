package com.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import com.beans.Navigation;
import com.beans.Subject;
import com.beans.formbeans.NewCourseForm;
import com.beans.formbeans.NewSubjectForm;
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

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	@ManagedProperty(value = "#{courseController}")
	private CourseController courseController;

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

	public void createSubject() {
		Subject subject = ObjectMapper.convertToSubjectFromNewSubjectForm(newSubjectForm);
		Long subjectId = subjectService.saveSubject(subject);
		clearNewSubjectForm();
		
	}

	private void clearNewSubjectForm() {
		newSubjectForm.setCode(null);
		newSubjectForm.setCoefficient(0.0f);
		newSubjectForm.setCourses(null);
		newSubjectForm.setDescription(null);
		newSubjectForm.setField(null);
		newSubjectForm.setName(null);
		
	}

	public void onSubjectRowSelect(SelectEvent event) {

		if (!courseController.isEditMode()) {
			PrimeFaces.current().executeScript("PF('dlg_list_subject').hide(); PF('dlg_create_course').show()");
			newCourseForm.setSubject((Subject) event.getObject());
		} else {
			courseController.getSelectedCourse().setSubject((Subject) event.getObject());
		}
	}
}
