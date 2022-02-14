package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClassDto;

@Service
public class ClassService {
	@Autowired
	private ClassRepository crepo;

	public int save(ClassDto dto) {
		int result = 0;
		crepo.save(dto);
		result = 1;
		return result;
	}

	public List<ClassDto> getAllClass() {
		List<ClassDto> list = (List<ClassDto>) crepo.findAll();
		return list;
	}

	public Optional<ClassDto> getFindById(String id) {
		return crepo.findById(id);
	}

	public void delete(String id) {
		crepo.deleteById(id);
	}

	public void update(ClassDto dto, String id) {
		crepo.save(dto);
	}
}
