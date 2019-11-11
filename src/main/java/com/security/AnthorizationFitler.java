package com.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.constant.AUTHORITIES;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*" })
public class AnthorizationFitler implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession session = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			if (reqURI.contains("/login.xhtml") || (session != null && session.getAttribute("authority") != null)
					|| (reqURI.contains("/public/")) || reqURI.contains("javax.faces.resource"))
				chain.doFilter(request, response);
			else {
				resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void destroy() {

	}

}
