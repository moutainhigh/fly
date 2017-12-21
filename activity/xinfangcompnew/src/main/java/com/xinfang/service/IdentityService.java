package com.xinfang.service;

import com.xinfang.model.FdGuest;

import java.util.List;
import java.util.Map;

/**
 * 上访人信息CRUD接口
 * Created by sunbjx on 2017/3/22.
 */
public interface IdentityService {

    /**
     * 上访人信息添加
     * @param fdGuest
     * @param strPetitionTime
     * @return
     */
    Map<String, Integer> save(FdGuest fdGuest, String strPetitionTime, Integer petitionNumbers, Integer associatedNumbers, String strFollowIds, Integer form);

    /**
     * 更新上访人信息
     * @param fdGuest
     * @param guestId
     * @param strPetitionTime
     * @return
     */
    int update(FdGuest fdGuest, Integer guestId, String strPetitionTime, Integer caseId, Integer petitionNumbers, Integer associatedNumbers, String strFollowIds);

    /**
     * 随访人信息添加
     * @param strPetitionTime
     * @param username
     * @param idcard
     * @param censusRegister
     * @param phone
     * @return
     */
    Map<String, Object> saveFollowReturnId(String strPetitionTime, String username, String idcard,String censusRegister, String phone);

    /**
     * 根据随访人ID删除随访人 做逻辑删除
     * @param followIds
     * @return
     */
    int removeFollowById(String followIds);

    /**
     * 通过上访人ID 关联出随访人ID查询随访人信息
     * @param caseId
     * @return
     */
    List<FdGuest> listFollowById(Integer caseId);

    /**
     * 通过基础信息ID查看基础信息(显示id)
     */
    FdGuest getGuestById(Integer guestId);
    
    /**
     * 通过基础信息ID查看基础信息(显示id)
     */
    FdGuest getGuestByIdForUrl(Integer guestId);

    /**
     * 通过信访人ID和案件ID查询基本信息
     * @param guestId
     * @param caseId
     * @return
     */
    Map<String, Object> getBaseByGuestIdAndCaseId(Integer guestId, Integer caseId);


    /**
     * 通过证件号码获取基础信息
     * @param idcard
     * @return
     */
    FdGuest getGuestByIdCard(String idcard);
}