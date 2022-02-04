package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.UserDto;

public interface UserDAO {
	public List<UserDto> select(UserDto dto);
	public int insertData(UserDto dto);
	public int updateData(UserDto dto);
	public int deleteData(UserDto dto);
	public List<UserDto> selectAll() ;
}
