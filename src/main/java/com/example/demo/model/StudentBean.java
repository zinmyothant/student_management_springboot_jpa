package com.example.demo.model;

import javax.validation.constraints.NotEmpty;

public class StudentBean {
	@NotEmpty
	private String studentId;
	@NotEmpty
	private String studentName;
	@NotEmpty
	private String className;
	@NotEmpty
	private String register;
	@NotEmpty
	private String status;
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
