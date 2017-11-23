package com.xinfang.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FdGuest;

/**
 * Created by sunbjx on 2017/3/22.
 */
public interface FdGuestRepository extends JpaRepository<FdGuest, Integer>, JpaSpecificationExecutor<FdGuest> {

    /**
     * 通过随访人ID进行批量删除
     * @param state
     * @param followIds
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query("update FdGuest f set f.state =:state where f.id in(:followIds)")
    int removeFollowById(@Param("state") Integer state, @Param("followIds") Integer followIds);


    /**
     * 根据随访人ID查询随访人列表
     * @param followIds
     * @return
     */
    @Query("select f.id, f.username, f.idcard, f.censusRegister, f.phone from FdGuest f where f.id in (:followIds)")
    List<FdGuest> listFollowById(@Param("followIds") Integer[] followIds);


    /**
     * 去重
     * @param username
     * @param phone
     * @return
     */
    @Query("select id from FdGuest f where f.username =:username and f.phone =:phone")
    List<Integer> getRepeat(@Param("username") String username, @Param("phone") String phone);


    /**
     *
     * @return
     */
    @Query("select id from FdGuest f where f.idcard =:idcard")
    List<Integer> getRepeatByIdcard(@Param("idcard") String idcard);


    /**
     * 通过用户名关联案件信息
     * @param username
     * @return
     */
    @Query("from FdGuest T left join fetch T.fdCaseList where T.username =:username")
    List<FdGuest> listHistoryCaseByUsername(@Param("username") String username);

    /**
     * 通过证件号码关联案件信息
     * @param idcard
     * @return
     */
//    @Query("from FdGuest T left join fetch T.fdCaseList where T.idcard =:idcard")
//    //@Query("from FdGuest T where T.idcard =:idcard")
//    List<FdGuest> listHistoryCaseByIdcard(@Param("idcard") String idcard);
    
    @Query("from FdGuest T left join T.fdCaseList where T.idcard =:idcard")
    List<FdGuest> listHistoryCaseByIdcard(@Param("idcard") String idcard);

    /**
     * 通过证件号码获取基础信息
     * @param idcard
     * @return
     */
    @Query("from FdGuest f where f.idcard =:idcard")
    FdGuest getGuestByIdcard(@Param("idcard") String idcard);
    
    @Query("from FdGuest f where f.phone =:phone")
    FdGuest getGuestByPhone(@Param("phone") String phone);
    
    @Query("from FdGuest f where f.id =:id")
    FdGuest getGuestById(@Param("id") Integer id );
    
    @Query("select count(*) from FdGuest f where f.phone =:telephone")
    int countbymobile(@Param("telephone") String telephone);
    
    
    @Query("from FdGuest f where f.name =:name and f.passWd=:passwd")
    FdGuest getGuestByUsernameAndPasswd(@Param("name") String name,@Param("passwd") String passwd  );
    
}
