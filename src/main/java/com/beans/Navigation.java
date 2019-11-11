package com.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.constant.AUTHORITIES;
import com.controller.StudentController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@ManagedBean(name = "navigation")
@SessionScoped
public class Navigation implements Serializable {

	private static final long serialVersionUID = -4795264471692140604L;

//	private String mainContentHead = "/templates/dashboard/contentHead.xhtml";
//	private String mainContentBody = "/templates/dashboard/dashboard_row_content.xhtml";

	private String mainContentHead;
	private String mainContentBody;

	private AUTHORITIES userAuthority;

	public Navigation() {

	}

	public String getMainContentHead() {
		if (userAuthority.equals(AUTHORITIES.ADMIN_ROLE)) {
			return "/templates/dashboard/contentHead.xhtml";
		} else {
			return "/templates/student-list-page/contentHead.xhtml";
		}
	}

	public String getMainContentBody() {
		if (userAuthority.equals(AUTHORITIES.ADMIN_ROLE)) {
			return "/templates/dashboard/dashboard_row_content.xhtml";
		} else {
			return "/templates/student-detail-page/student-detail-form.xhtml";
		}
	}

	public void navigateToLogin() {
		mainContentBody = "/templates/login/contentBody.xhtml";
	}

	public void navigateToStudentList() {
		mainContentHead = "/templates/student-list-page/contentHead.xhtml";
		mainContentBody = "/templates/student-list-page/contentBody.xhtml";
	}

	public void navigateToDashboard() {
		mainContentHead = "/templates/dashboard/contentHead.xhtml";
		mainContentBody = "/templates/dashboard/dashboard_row_content.xhtml";
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
		mainContentBody = "/templates/student-detail-page/student-detail-form.xhtml";
	}

	public void navigateToCourseDetail() {
		mainContentBody = "/templates/course-detail-page/course-detail-form.xhtml";
	}

}
