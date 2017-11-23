package com.xinfang.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xinfang.model.Comp;

public interface CompRepository extends JpaRepository<Comp, Long> {
	
	public Comp findByCompName(String personName);
	
}
