package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.service.JPAUtil;
@Service
public class UserDAOImpl implements UserDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDto> select(UserDto dto) {
		List<UserDto> list=new ArrayList<UserDto>();
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
		
			list=em.createQuery("select u from UserDto u where u.id=:id").setParameter("id",dto.getId()).getResultList();
					}finally {

		}
		return list;
	}

	@Override
	public int insertData(UserDto dto) {
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
	public int updateData(UserDto dto) {
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
	public int deleteData(UserDto dto) {
		int result=0;
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			UserDto udto=em.find(UserDto.class,dto.getId());
			em.remove(udto);
			em.getTransaction().commit();
			result=1;
		}finally {
			em.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDto> selectAll() {
		
		List<UserDto> list=new ArrayList<UserDto>();
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			list=em.createQuery("select u from UserDto u").getResultList();
		}finally {
			em.close();
		}
		return list;
	}

}
