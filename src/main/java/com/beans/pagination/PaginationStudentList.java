package com.beans.pagination;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.beans.StudentFilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "paginationStudentList")
@SessionScoped
public class PaginationStudentList extends Pagination {

	private static final long serialVersionUID = 7178035816978584438L;
	
	private StudentFilter studentFilter;

}
