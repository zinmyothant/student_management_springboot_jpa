package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.StudentDto;

@Repository
public interface StudentRepository extends CrudRepository<StudentDto, String>{

}
