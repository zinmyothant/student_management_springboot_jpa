package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;

@Service
public class UserService {
	@Autowired
	private UserRepository urepo;

	public int save(UserDto user) {
		int result = 0;
		urepo.save(user);
		result = 1;
		return result;
	}

	public List<UserDto> getAllUser() {
		List<UserDto> list = (List<UserDto>) urepo.findAll();
		return list;
	}

	public Optional<UserDto> getUserById(String id) {
		return urepo.findById(id);
	}

	public int delete(String id) {
		int result = 0;
		urepo.deleteById(id);
		result = 1;
		return result;
	}

	public int update(UserDto user, String id) {
		int result = 0;
		urepo.save(user);
		result = 1;
		return result;
	}
}
