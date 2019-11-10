package com.beans.pagination;


import com.beans.StudentFilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationStudentList extends Pagination {

	private static final long serialVersionUID = 7178035816978584438L;
	
	private StudentFilter studentFilter;

}
