package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.ClassDto;

public interface ClassDAO {
	public int insertData(ClassDto dto);
	public int updateData(ClassDto dto);
	public int deleteData(ClassDto dto);
	public List<ClassDto> selectAll();
	public List<ClassDto> select(ClassDto dto);
}
