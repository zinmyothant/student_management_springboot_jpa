package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDto;

@Repository
public interface UserRepository extends CrudRepository<UserDto, String>{

}
