package com.Bean;

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
public class Pagination {
	private static final String SEARCH_KEYWORD_DEFAULT = "";

	private static final String ORDERBY_ASC_OR_DESC_DEFAULT = "asc";

	private static final String ORDERBY_FIELD_DEFAULT = "code";

	private static final int CURRENT_PAGE_DEFAULT = 1;

	private static final int ROWS_PER_PAGE_DEFAULT = 10;

	private int rowsperpage = ROWS_PER_PAGE_DEFAULT;

	private int page = CURRENT_PAGE_DEFAULT;

	private String orderBy = ORDERBY_FIELD_DEFAULT;
	
	private String ascOrDesc = ORDERBY_ASC_OR_DESC_DEFAULT;
	
	private String searchField = ORDERBY_FIELD_DEFAULT;
	
	private String searchKeyword = SEARCH_KEYWORD_DEFAULT;

}
