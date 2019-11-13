package com.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.beans.Navigation;
import com.beans.User;
import com.constant.AUTHORITIES;
import com.constant.Constant;
import com.service.UserService;
import com.util.SessionUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "userController")
@SessionScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 4908733315796400288L;

	private User user;
	
	private String locale;

	public UserController() {
		user = new User();
	}

	@ManagedProperty(value = "#{userService}")
	UserService userService;

	@ManagedProperty(value = "#{navigation}")
	Navigation navigation;

	@ManagedProperty(value = "#{studentController}")
	StudentController studentController;

	public void login() {
		User userFound = userService.validateUser(user);
		if (userFound != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("authority", userFound.getAuthority());
			user.setAuthority(userFound.getAuthority());
			user.setUsername(userFound.getUsername());
			// Admin Role view
			if (userFound.getAuthority().equals(AUTHORITIES.ADMIN_ROLE)) {
				navigation.setMainContentHead(Constant.DASHBOARD_CONTENT_HEAD_URL);
				navigation.setMainContentBody(Constant.DASHBOARD_CONTENT_BODY_URL);
				navigation.setLeftSidebar(Constant.LEFT_SIDEBAR_URL);
			}

			// Student Role View
			else {
				studentController.setUserEmail(userFound.getEmail());
				navigation.setMainContentHead(Constant.STUDENT_ROLE_STUDENT_DETAIL_CONTENT_HEAD_URL);
				navigation.setMainContentBody(Constant.STUDENT_ROLE_STUDENT_DETAIL_CONTENT_BODY_URL);
				navigation.setLeftSidebar(Constant.STUDENT_ROLE_LEFT_SIDEBAR);
				studentController.getStudentDetail();
			}
			FacesContext context = FacesContext.getCurrentInstance();
			try {
				String rootUrl = context.getExternalContext().getRequestContextPath();
				context.getExternalContext().redirect(rootUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid user"));

		}
	}

	public void logout() {
		HttpSession session = SessionUtils.getSession();
		FacesContext context = FacesContext.getCurrentInstance();
		String rootUrl = context.getExternalContext().getRequestContextPath();
		try {
			// destroy session to cannot get view after click log out
			context.getExternalContext().invalidateSession();
			context.getExternalContext().redirect(rootUrl + "/login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onLocaleChange() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		if(locale.contentEquals("vi")) {
			viewRoot.setLocale(new Locale("vi"));
		}
		if(locale.contentEquals("en")) {
			viewRoot.setLocale(new Locale("en"));
		}
	}
	

}
