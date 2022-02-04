package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.example.demo.dto.StudentDto;

import com.example.demo.service.JPAUtil;
@Service
public class StudentDAOImpl implements StudentDAO {

	@Override
	public int insertData(StudentDto dto) {
		int result=0;
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			em.persist(dto);
			em.getTransaction().commit();
			result=1;
		}finally {
			em.close();
		}
		return result;
	}

	@Override
	public int updateData(StudentDto dto) {
		int result=0;
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			em.merge(dto);
			em.getTransaction().commit();
			result=1;
		}finally {
			em.close();
		}
		return result;
	}

	@Override
	public int deleteData(StudentDto dto) {
		int result=0;
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			em.find(StudentDto.class, dto.getStudentId());
			em.getTransaction().commit();
			result=1;
		}finally {
			em.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentDto> select(StudentDto dto) {
		//int result=0;
		List<StudentDto> list=new ArrayList<StudentDto>();
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			list=em.createQuery("select s from StudentDto s where s.studentId=:studentId").setParameter("studentId", dto.getStudentId()).getResultList();
			
		}finally {
			em.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentDto> selectAll() {
		List<StudentDto> list=new ArrayList<StudentDto>();
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			list=em.createQuery("select s from StudentDto s").getResultList();
		}finally {
			em.close();
		}
		return list;
	}

	
}
