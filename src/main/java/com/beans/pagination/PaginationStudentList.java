package com.beans.pagination;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "paginationStudentList")
@SessionScoped
public class PaginationStudentList extends Pagination {

	private static final long serialVersionUID = 7178035816978584438L;

}
