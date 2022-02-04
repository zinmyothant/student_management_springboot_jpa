package com.example.demo.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
static EntityManagerFactory factory=null;
public static EntityManagerFactory getEntityManagerFactory() {
	return factory=Persistence.createEntityManagerFactory("student_management_springboot_jpa");
	
}
}
