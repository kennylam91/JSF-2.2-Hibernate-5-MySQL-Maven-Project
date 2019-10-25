package com.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.Bean.Pagination;
import com.Bean.Student;
import com.Bean.StudentDto;
import com.Bean.StudentForm;
import com.service.StudentServiceImpl;
import com.util.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ManagedBean(name = "studentController")
@SessionScoped
public class StudentController implements Serializable {

	private static final long serialVersionUID = 7828547113215254846L;

	private static final Logger logger = Logger.getLogger(StudentController.class);

	@ManagedProperty(value = "#{studentForm}")
	private StudentForm studentForm;

	@ManagedProperty(value = "#{studentService}")
	private StudentServiceImpl studentService;

	@ManagedProperty(value = "#{pagination}")
	private Pagination pagination;

	private List<StudentDto> studentDtos;

	private List<StudentDto> selectedStudentDtos;

	public List<StudentDto> getStudentDtos(Pagination pagination) {
		return studentService.findStudentsByPagination(pagination);
	}

	public String getStudentListForm() {
		clearStudentForm();
		studentDtos = studentService.findStudentsByPagination(pagination);
		return "student_list?faces-redirect=true";
	}

	private void clearStudentForm() {
		studentForm.setAddress(null);
		studentForm.setAvgScore(0);
		studentForm.setCode(null);
		studentForm.setCourses(null);
		studentForm.setDOB(null);
		studentForm.setEmail(null);
		studentForm.setField(null);
		studentForm.setFirstName(null);
		studentForm.setId(null);
		studentForm.setLastName(null);
		studentForm.setNote(null);
		studentForm.setPhone(null);
	}

	public void getCreateForm() {
		studentForm = new StudentForm();
	}

	public void createStudent(StudentForm studentForm) {
		Student student = new Student();
		ObjectMapper.convertToStudent(studentForm, student);
		studentService.saveStudent(student);

		// update studentDtos for table student_list
		studentDtos = studentService.findStudentsByPagination(pagination);
	}

	public String deleteStudent(Long studentId) throws Exception {
		studentService.deleteStudent(studentId);
		studentDtos = studentService.findAllStudents();
		return "student_list";
	}

	public String getStudentDetail(Long studentId) throws Exception {
		Student student = studentService.findStudentById(studentId);
		ObjectMapper.convertToStudentForm(student, studentForm);
		logger.info(studentForm);
		return "student_detail?faces-redirect=true";
	}

	public void update(StudentForm studentForm) throws Exception {
		Student student = new Student();
		ObjectMapper.convertToStudent(studentForm, student);
		studentService.updateStudent(student);
	}

	public void onPaginationChange() {
		logger.info(pagination.toString());
		studentDtos = studentService.findStudentsByPagination(pagination);
	}

	public void getPreviousPage() {
		if (pagination.getPage() > 1) {
			pagination.setPage(pagination.getPage() - 1);
			studentDtos = studentService.findStudentsByPagination(pagination);
		}
	}

	public void getNextPage() {
		pagination.setPage(pagination.getPage() + 1);
		studentDtos = studentService.findStudentsByPagination(pagination);
	}

}
