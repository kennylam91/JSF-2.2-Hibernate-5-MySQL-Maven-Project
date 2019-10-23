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
	private int rowsperpage = 5;

	private int page = 1;

	private String orderBy = "code";
	
	private String ascOrDesc = "asc";
	
	private String searchField = "code";
	
	private String searchKeyword = "";

}
