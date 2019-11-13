package com.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private boolean editMode = false;
	private Subject selectedSubject = new Subject();

	public List<Subject> getSubjects() {
		return subjectService.findAllSubjects();
	}

	public void deleteSubject() {
		try {
			subjectService.deleteSubject(selectedSubject.getId());
			System.out.println("delete " + selectedSubject.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subjects = subjectService.findAllSubjects();
	}

	public Subject getSubjectDetail(Long subjectId) {
		return subjectService.findSubjectById(subjectId);
	}

	public void createSubject() {
		Subject subject = ObjectMapper.convertToSubjectFromNewSubjectForm(newSubjectForm);
		Long subjectId = subjectService.saveSubject(subject);
		closeCreateSubjectDialog();
		newSubjectForm = new NewSubjectForm();

	}

	public void openCreateSubjectDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", true);
		options.put("width", "410px");
		options.put("height", "470px");
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("modal", true);
		PrimeFaces.current().dialog().openDynamic("/templates/subject-list-page/dialog_create_subject", options, null);
	}

	public void closeCreateSubjectDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void activeEditMode() {
		editMode = true;
	}

}
