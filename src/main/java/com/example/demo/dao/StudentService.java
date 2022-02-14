package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.StudentDto;

@Service
public class StudentService {
	@Autowired
	private StudentRepository srepo;

	public int save(StudentDto student) {
		int result = 0;
		srepo.save(student);
		result = 1;
		return result;
	}

	public List<StudentDto> getAllStudent() {
		List<StudentDto> list = (List<StudentDto>) srepo.findAll();
		return list;
	}

	public Optional<StudentDto> getStudentById(String id) {
		return srepo.findById(id);
	}

	public int delete(String id) {
		int result = 0;
		srepo.deleteById(id);
		result = 1;
		return result;
	}

	public int update(StudentDto student, String studentId) {
		int result = 0;
		srepo.save(student);
		result = 1;
		return result;
	}
}
