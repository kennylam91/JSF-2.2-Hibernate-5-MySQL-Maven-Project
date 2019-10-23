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
import com.utility.ObjectMapper;

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

	public List<StudentDto> getStudentDtos(Pagination pagination) {
		return studentService.findStudentsByPagination(pagination);
	}

	public String getStudentListForm() {
		studentDtos = studentService.findStudentsByPagination(pagination);
		return "student_list?faces-redirect=true";
	}

	public String getCreateForm() {
		initStudentForm();
		return "create_student_form?faces-redirect=true";
	}

	private void initStudentForm() {
		studentForm.setEmail(null);
		studentForm.setFirstName(null);
		studentForm.setLastName(null);
		studentForm.setPhone(null);
		studentForm.setCode(null);
		studentForm.setAddress(null);
		studentForm.setDOB(null);
		studentForm.setField(null);
		studentForm.setAvgScore(0);
		studentForm.setId(null);
		studentForm.setNote(null);
		studentForm.setCourses(null);
	}

	public void createStudent(StudentForm studentForm) {
		Student student = new Student();
		ObjectMapper.convertToStudent(studentForm, student);
		studentService.saveStudent(student);
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
		return "student_detail?faces-redirect=true";
	}

	public String update(StudentForm studentForm) throws Exception {
		// Student student = studentService.findStudentById(studentForm.getId());
		Student student = new Student();
		ObjectMapper.convertToStudent(studentForm, student);
		studentService.updateStudent(student);
		studentDtos = studentService.findAllStudents();
		return "student_list?faces-redirect=true";
	}

	public void onPaginationChange() {
		logger.info(pagination.toString());
		studentDtos = studentService.findStudentsByPagination(pagination);
	}

}
