package com.xinfang.supervise.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xinfang.supervise.model.SuperviseRecord;

public interface SuperviseRecordDao extends JpaRepository<SuperviseRecord, Integer>{

}
