package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.StudentDto;

public interface StudentDAO {
	public int insertData(StudentDto dto);
	public int updateData(StudentDto dto);
	public int deleteData(StudentDto dto);
	public List<StudentDto> select(StudentDto dto);
	public List<StudentDto> selectAll();
	
}
