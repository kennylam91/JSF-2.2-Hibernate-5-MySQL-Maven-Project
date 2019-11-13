package com.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import com.service.SubjectService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@ManagedBean(name = "subjectConverter")
@SessionScoped
public class SubjectConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7677257647326777983L;
	@ManagedProperty(value = "#{subjectService}")
	private SubjectService subjectService;

	@SuppressWarnings("unused")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || !value.matches("\\d+")) {
			return null;
		}

		Subject subject = subjectService.findSubjectById(Long.valueOf(value));

		return subject;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof Subject) || ((Subject) value).getId() == null) {
			return null;
		}
		return String.valueOf(((Subject) value).getId());

	}
}
