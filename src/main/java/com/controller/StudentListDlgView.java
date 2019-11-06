package com.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.data.SortEvent;

import com.beans.Student;
import com.beans.StudentDto;
import com.beans.pagination.Pagination;
import com.beans.pagination.PaginationStudentList;
import com.constant.Constant;
import com.service.StudentService;
import com.util.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean(name = "studentListDlgView")
@SessionScoped
public class StudentListDlgView implements Serializable {

	private static final long serialVersionUID = 6318963726091789725L;
	private static final Logger logger = Logger.getLogger(StudentController.class);
	private List<StudentDto> studentDtos;
	private Pagination pagination;
	private List<StudentDto> selectedStudentDtos;

	@ManagedProperty(value = "#{studentService}")
	private StudentService studentService;

	@ManagedProperty(value = "#{courseController}")
	private CourseController courseController;

	@PostConstruct
	public void init() {
		pagination = new PaginationStudentList();
		studentDtos = studentService.findStudentsByPagination(pagination);
	}

	public void onPaginationChange() {
		studentDtos = studentService.findStudentsByPagination(pagination);
	}

	public void getPreviousPage() {
		if (pagination.getPage() > 1) {
			pagination.setPage(pagination.getPage() - 1);
			onPaginationChange();
		}
	}

	public void getNextPage() {
		pagination.setPage(pagination.getPage() + 1);
		onPaginationChange();
	}

	public void onSort(SortEvent event) {
		if (event.isAscending()) {
			pagination.setAscOrDesc("asc");
		} else {
			pagination.setAscOrDesc("desc");
		}
		pagination.setOrderBy(event.getSortColumn().getField());
		onPaginationChange();
	}

	public void openStudentListDialog() {
		selectedStudentDtos = new LinkedList<>();
		for (Student student : courseController.getSelectedCourse().getStudents()) {
			StudentDto studentDto = ObjectMapper.modelMapper.map(student, StudentDto.class);
			selectedStudentDtos.add(studentDto);
		}
		pagination = new PaginationStudentList();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("model", true);
		PrimeFaces.current().dialog().openDynamic(Constant.DIALOG_STUDENT_LIST_URL, options, null);
	}

	public void closeStudentListDialog() {
		PrimeFaces.current().dialog().closeDynamic(selectedStudentDtos);
	}

}
