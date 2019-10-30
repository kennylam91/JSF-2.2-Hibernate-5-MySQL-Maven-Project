package com.Bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

	private String mainContentHead = "/templates/dashboard/contentHead.xhtml";
	private String mainContentBody = "/templates/dashboard/dashboard_row_content.xhtml";

	public void navigateToStudentList() {
		mainContentHead = "/templates/student-list-page/contentHead.xhtml";
		mainContentBody = "/templates/student-list-page/contentBody.xhtml";
		System.out.println("student list:" + mainContentHead);
	}

	public void navigateToDashboard() {
		mainContentHead = "/templates/dashboard/contentHead.xhtml";
		mainContentBody = "/templates/dashboard/dashboard_row_content.xhtml";
		System.out.println("dashboard:" + mainContentHead);
	}

}
