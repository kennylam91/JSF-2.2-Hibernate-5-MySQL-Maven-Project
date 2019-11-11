package com.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.constant.AUTHORITIES;
import com.constant.Constant;
import com.controller.StudentController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@ManagedBean(name = "navigation")
@SessionScoped
public class Navigation implements Serializable {

	private static final long serialVersionUID = -4795264471692140604L;

	private String mainContentHead;
	private String mainContentBody;
	private String leftSidebar;

	private AUTHORITIES userAuthority;

	public void navigateToLogin() {
		mainContentBody = "/templates/login/contentBody.xhtml";
	}

	public void navigateToStudentList() {
		mainContentHead = "/templates/student-list-page/contentHead.xhtml";
		mainContentBody = "/templates/student-list-page/contentBody.xhtml";
	}

	public void navigateToDashboard() {
		mainContentHead = "/templates/dashboard/contentHead.xhtml";
		mainContentBody = "/templates/dashboard/contentBody.xhtml";
	}

	public void navigateToCourseList() {
		mainContentHead = "/templates/course-list-page/content-head.xhtml";
		mainContentBody = "/templates/course-list-page/content-body.xhtml";
	}

	public void navigateToSubjectList() {
		mainContentHead = "/templates/subject-list-page/content-head.xhtml";
		mainContentBody = "/templates/subject-list-page/content-body.xhtml";
	}

	public void navigateToStudentDetail() {
		mainContentBody = Constant.STUDENT_DETAIL_CONTENT_BODY_URL;
	}

	public void navigateToCourseDetail() {
		mainContentBody = "/templates/course-detail-page/course-detail-form.xhtml";
	}
	
	public void navigateToDashboardStudentRole() {
		mainContentHead = Constant.STUDENT_ROLE_STUDENT_DETAIL_CONTENT_HEAD_URL;
		mainContentBody = Constant.STUDENT_ROLE_STUDENT_DETAIL_CONTENT_BODY_URL;
	}
	
	public void navigateToCourseListStudentRole() {
		mainContentHead = Constant.STUDENT_ROLE_COURSE_LIST_CONTENT_HEAD_URL;
		mainContentBody = Constant.STUDENT_ROLE_COURSE_LIST_CONTENT_BODY_URL;
	}

}
