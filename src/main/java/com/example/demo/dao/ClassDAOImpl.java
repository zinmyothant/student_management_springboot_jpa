package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ClassDto;
import com.example.demo.service.JPAUtil;
@Service
public class ClassDAOImpl implements ClassDAO {

	@Override
	public int insertData(ClassDto dto) {
		int result = 0;
		EntityManager em = null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			em.persist(dto);
			em.getTransaction().commit();
			result=1;
			
		} finally {
			em.close();
		}
		return result;
	}

	@Override
	public int updateData(ClassDto dto) {
		int result=0;
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			em.merge(dto);
			result=1;
		}finally {
			em.close();
		}
		return result;
	}

	@Override
	public int deleteData(ClassDto dto) {
		ClassDto cdto=new ClassDto();
		EntityManager em=null;
		int result=0;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			cdto=em.find(ClassDto.class,dto.getId());
			em.remove(cdto);
			em.getTransaction().commit();
		result=1;
		}finally {
			em.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassDto> selectAll() {
		List<ClassDto> list=new ArrayList<ClassDto>();
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			list=em.createQuery("select c from ClassDto c").getResultList();
			
		}finally {
			em.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassDto> select(ClassDto dto) {
		List<ClassDto> list=new ArrayList<ClassDto>();
		EntityManager em=null;
		try {
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			list=em.createQuery("select c from ClassDto c where c.id=:id").setParameter("id",dto.getId()).getResultList();
		}finally {
			em.close();
		}
		return list;
	}
	

}
