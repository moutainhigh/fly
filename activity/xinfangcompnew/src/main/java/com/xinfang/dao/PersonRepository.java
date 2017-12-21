package com.xinfang.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xinfang.model.Person;

/**
 * Created by jery on 2016/11/23.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	public Person findByPersonName(String personName);
	
}

