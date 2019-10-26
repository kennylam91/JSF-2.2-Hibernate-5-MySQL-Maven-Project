package com.Bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ManagedBean(name = "pagination")
@SessionScoped
public class Pagination implements Serializable{

	private static final long serialVersionUID = -1964671145149488777L;

	private static final String SEARCH_KEYWORD_DEFAULT = "";

	private static final String ORDERBY_ASC_OR_DESC_DEFAULT = "asc";

	private static final String ORDERBY_FIELD_DEFAULT = "code";

	private static final int CURRENT_PAGE_DEFAULT = 1;

	private static final int ROWS_PER_PAGE_DEFAULT = 10;

	private static final String SEARCH_FIELD_DEFAULT = "field";

	private int rowsPerPage = ROWS_PER_PAGE_DEFAULT;

	private int page = CURRENT_PAGE_DEFAULT;

	private String orderBy = ORDERBY_FIELD_DEFAULT;

	private String ascOrDesc = ORDERBY_ASC_OR_DESC_DEFAULT;

	private String searchField = SEARCH_FIELD_DEFAULT;

	private String searchKeyword = SEARCH_KEYWORD_DEFAULT;
	
	public int getFirstRowIndexOnPage() {
		return 1 + rowsPerPage*(page-1);
	}
	
	public int getLastRowIndexOnPage() {
		return rowsPerPage*page;
	}
	
	public int getTotalRecords() {
		return 100;
	}

}
