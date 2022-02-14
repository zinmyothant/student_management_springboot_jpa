package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.ClassDto;

@Repository
public interface ClassRepository extends JpaRepository<ClassDto, String>{

}
