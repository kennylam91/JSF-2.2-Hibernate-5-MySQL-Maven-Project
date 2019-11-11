package com.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.beans.Navigation;
import com.beans.User;
import com.service.UserService;
import com.util.SessionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "userController")
@SessionScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 4908733315796400288L;

	private User user;

	public UserController() {
		user = new User();
	}

	@ManagedProperty(value = "#{userService}")
	UserService userService;

	@ManagedProperty(value = "#{navigation}")
	private Navigation navigation;

	public void login() {
		boolean valid = userService.validateUser(user);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", user.getUsername());
			FacesContext context = FacesContext.getCurrentInstance();
			try {
				String rootUrl = context.getExternalContext().getRequestContextPath();
				context.getExternalContext().redirect(rootUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("user is invalid");
		}
	}
	
	public void logout() {
		HttpSession session = SessionUtils.getSession();
		session.removeAttribute("username");
		FacesContext context = FacesContext.getCurrentInstance();
		String rootUrl = context.getExternalContext().getRequestContextPath();
		try {
			context.getExternalContext().redirect(rootUrl +"/login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
