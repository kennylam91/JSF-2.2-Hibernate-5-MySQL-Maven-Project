package com.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.modelmapper.ModelMapper;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 7828547113215254846L;

	@ManagedProperty(value = "#{studentForm}")
	private StudentForm studentForm;

	@ManagedProperty(value = "#{studentService}")
	private StudentServiceImpl studentService;

	private List<StudentDto> studentDtos;

	public List<StudentDto> getStudentDtos() {
		return studentService.findAllStudents();
	}

	public String getStudentListForm() {
		studentDtos = studentService.getStudentList();
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
	}

	public String createStudent(StudentForm studentForm) {
		Student student = new Student();
		ObjectMapper.convertToStudent(studentForm, student);
		studentService.saveStudent(student);
		studentDtos = studentService.findAllStudents();
		return "student_list?faces-redirect=true";
	}

	public String deleteStudent(Long studentId) throws Exception {
		studentService.deleteStudent(studentId);
		studentDtos = studentService.findAllStudents();
		return "student_list";
	}

	public String getStudentDetail(Long studentId) throws Exception {
		initStudentForm();
		System.out.println(studentForm);
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

}
