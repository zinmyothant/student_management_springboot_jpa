package com.example.demo.model;

import javax.validation.constraints.NotEmpty;


public class ClassBean {
	@NotEmpty
	private String id;
	@NotEmpty
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
