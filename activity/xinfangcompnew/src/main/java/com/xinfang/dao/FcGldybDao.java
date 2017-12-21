package com.xinfang.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.xinfang.model.FcGldybEntity;

/**
 * 法律发挥和码表关联dao
 * @author sunbjx
 * Date: 2017年6月8日下午6:39:04
 */
public interface FcGldybDao extends CrudRepository<FcGldybEntity, Integer>, JpaSpecificationExecutor<FcGldybEntity> {

}
