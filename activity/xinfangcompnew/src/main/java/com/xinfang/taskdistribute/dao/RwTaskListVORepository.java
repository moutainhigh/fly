package com.xinfang.taskdistribute.dao;

import com.xinfang.taskdistribute.VO.RwTaskListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-02 10:45
 **/
public interface RwTaskListVORepository extends JpaRepository<RwTaskListVO, Integer>,
        JpaSpecificationExecutor<RwTaskListVO> {

    List<RwTaskListVO> findByTaskIdAndCreatePersonId(Integer taskId, Integer createPersonId);
}
