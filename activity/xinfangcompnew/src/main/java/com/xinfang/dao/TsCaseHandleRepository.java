package com.xinfang.dao;

import com.xinfang.model.TsCaseHandle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-25 11:43
 **/
public interface TsCaseHandleRepository extends JpaRepository<TsCaseHandle, Integer> {

    List<TsCaseHandle> findByDwId(Integer dwId);

    List<TsCaseHandle> findByCaseId(Integer caseId);

    List<TsCaseHandle> findByHandlePersonId(Integer handlePersonId);
}

