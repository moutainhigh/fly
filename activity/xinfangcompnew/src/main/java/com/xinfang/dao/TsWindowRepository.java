package com.xinfang.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




import com.xinfang.model.TsWindow;

import org.springframework.transaction.annotation.Transactional;

public interface TsWindowRepository extends JpaRepository<TsWindow, Integer>,JpaSpecificationExecutor<TsWindow>{
	 @Query("select T.id from TsWindow T where T.windowName =:windowName AND T.orgId=:orgId")
	    Integer getWindowIdByWindowName(@Param("windowName") String windowName,@Param("orgId") Double orgId);
	 @Query("select T.windowName from TsWindow T where T.id =:id")
	 String getWindowNameByWindowId(@Param("id") Integer id);
	 @Modifying
	 @Transactional
	 @Query("update TsWindow t set t.status = :status where t.id = :id")
	 Integer updateWindowStatus(@Param("id") Integer id, @Param("status") Integer status);
	 @Modifying
	 @Transactional
	 @Query(value="delete from Ts_Window  where id = :id",nativeQuery=true)
	 Integer deleteWindow(@Param("id") Integer id);
	 @Query("FROM TsWindow T where T.orgId=:unitId")
	 List<TsWindow> getwindowListByUnitId(@Param("unitId") Double unitId);
}
