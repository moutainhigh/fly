package com.xinfang.personnelmanagement.service;

import com.xinfang.Exception.BizException;
import com.xinfang.VO.AuthGroupNewVO;
import com.xinfang.VO.PermissionHomePage;
import com.xinfang.VO.PermissionTransferPersonVO;
import com.xinfang.dao.*;
import com.xinfang.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-27 14:49
 **/
@Service
@Transactional
public class PermissionService {

    @Autowired
    FcRybAllFieldRepository fcRybAllFieldRepository;

    @Autowired
    AuthGroupNewRepository authGroupNewRepository;

    @Autowired
    AuthUserGroupNewRepository authUserGroupNewRepository;

    @Autowired
    TsForwardOrgRepository tsForwardOrgRepository;

    @Autowired
    FcDwbRepository fcDwbRepository;

    @Autowired
    TsPersonCaseTypeRepository tsPersonCaseTypeRepository;

    @Autowired
    TsInputPersonCaseTypeRepository tsInputPersonCaseTypeRepository;

    @Autowired
    TsTransferPersonRepository tsTransferPersonRepository;

    @Autowired
    FcKszwbRepository fcKszwbRepository;

    @Autowired
    BaseTreeService baseTreeService;


    /**
     * @param type   type	Integer	是	条件类型，如：1为区县市、2为单位类型、3为单位、4为科室、即点击某个区县显示区县下所有机构列表，单位同理
     * @param id     id	Integer	是	某个类型下id，如type=1，id就是区县市id，type为空时id无效
     * @param ryId   ryId	Integer	是	人员id，输入该字段获得返回人员详情
     * @param search search	String	是	搜索条件，根据输入的值返回含有该值的人员列表
     * @return
     */
    public List<PermissionHomePage> permissionList(Integer type, Integer id, Integer qxsId, Integer ryId,
                                                   String search, Integer startpage, Integer pagesize) {
        PageRequest page = new PageRequest(startpage, pagesize);
        List<PermissionHomePage> result = null;
        if ((type == 1 || type == 2 || type == 3 || type == 4) && (id != null)) {  // 有类型和它的id
            if (search == null) {    // 没有模糊搜索
                if (ryId == null) {  // 人员id为空，搜索所有
                    switch (type) {
                        case 1:
                            result = fcRybAllFieldRepository.permissionListByQxsId(id, page);
                            break;
                        case 2:
                            result = fcRybAllFieldRepository.permissionListByDwTypeId(id, qxsId, page);
                            break;
                        case 3:
                            result = fcRybAllFieldRepository.permissionListByDwId(id, page);
                            break;
                        case 4:
                            result = fcRybAllFieldRepository.permissionListByKsId(id, page);
                            break;
                        default:
                            ;
                    }
                } else {  // 人员id不为空，搜索某个人员。
                    result = fcRybAllFieldRepository.permissionListByRyId(ryId);
                }
            } else {    // 模糊搜索
                switch (type) {

                }
            }
        }
        return result;
    }


    @Transactional
    public List<AuthGroupNewVO> userRoleList(Integer ryId, Integer request, List<Integer> ids) {
        List<AuthGroupNewVO> result = new ArrayList<>();
        if (request == 1) {  // get 获取
            return userPermissionList(ryId);
        } else if (request == 2) {  // post 设置
            List<AuthUserGroupNew> authUserGroupNewList = new ArrayList<>();
            List<AuthGroupNew> authGroupNewList = authGroupNewRepository.findAllPermission(1); // 获取所有权限
            for (Integer permissionId : ids) {
                for (AuthGroupNew authGroupNew : authGroupNewList) {
                    if (authGroupNew.getId() == permissionId) {   // 原有权限里有的权限id才能设置人员具有的权限成功，不然获取会报错。
                        AuthUserGroupNew authUserGroupNew = new AuthUserGroupNew();
                        authUserGroupNew.setUserId(ryId);
                        authUserGroupNew.setGroupId(permissionId);
                        authUserGroupNewList.add(authUserGroupNew);

                        if (permissionId == 1) {  // 如果设置超级管理员，判断该单位有无收文岗，如果没有则设置该人员为单位收文岗
                            Integer dwId =  fcRybAllFieldRepository.findDwIdByRyId(ryId);
                            List<Integer> itemListA = tsTransferPersonRepository.findByOrgId(dwId);
                            if (itemListA == null || itemListA.size()==0){
                                TsTransferPerson tsTransferPerson = baseTreeService.setTransferPerson(ryId, dwId);
                                tsTransferPersonRepository.save(tsTransferPerson);
                            }

                        }
                    }
                }
            }
            List<AuthUserGroupNew> oldPermissionList = authUserGroupNewRepository.findByUserId(ryId);
            authUserGroupNewRepository.deleteInBatch(oldPermissionList);   // 删除原有权限
            authUserGroupNewRepository.save(authUserGroupNewList); // 设置新权限。
            return userPermissionList(ryId);
        }
        return result;
    }

    public List<AuthGroupNewVO> userPermissionList(Integer ryId) {
        List<AuthGroupNewVO> result = new ArrayList<>();
        List<AuthGroupNew> authGroupNewList = authGroupNewRepository.findAllPermission(1); // 获取所有权限
        List<AuthUserGroupNew> authUserGroupNewList = authUserGroupNewRepository.findByUserId(ryId); //该用户具有的权限

        for (AuthGroupNew authGroupNew : authGroupNewList) {
            AuthGroupNewVO authGroupNewVO = new AuthGroupNewVO();
            BeanUtils.copyProperties(authGroupNew, authGroupNewVO);
            authGroupNewVO.setTag(false);
            for (AuthUserGroupNew authUserGroupNew : authUserGroupNewList) {
                if (authUserGroupNew.getGroupId() == authGroupNew.getId()) {  // 用户具有的权限id等于当前权限组的id
                    authGroupNewVO.setTag(true);
                }
            }
            result.add(authGroupNewVO);
        }
        return result;
    }

    @Transactional
    public Map getForwardOrgList(Integer ryId, Integer request, Set<Integer> dwIds) {
        Map result = new HashMap();
        if (request == 1) {
            result = getForwardOrgListByRyId(ryId);
        } else if (request == 2) {
            Set<Integer> dwIdSet = fcDwbRepository.findDwIdsByDwIds(dwIds); // 判断数据库是否有这些单位
            List<TsForwardOrg> tsForwardOrgList = new ArrayList<>();
            if (dwIdSet.containsAll(dwIds)) {  // 数据库表里有所有单位，可以
                for (Integer dwId : dwIdSet) {
                    TsForwardOrg tsForwardOrg = new TsForwardOrg();
                    tsForwardOrg.setUserId(ryId);
                    tsForwardOrg.setOrgId(dwId);
                    Date date = new Date();
                    Timestamp now = new Timestamp(date.getTime());
                    tsForwardOrg.setCreatedAt(now);
                    tsForwardOrg.setUpdatedAt(now);
                    tsForwardOrgList.add(tsForwardOrg);
                }
            } else {
                throw new BizException("设置的单位id有的在数据库中不存在，数据库只包含以下单位：" + dwIdSet.toString());
            }

            List<TsForwardOrg> oldTsForwardOrgList = tsForwardOrgRepository.findByRyId(ryId);
            tsForwardOrgRepository.deleteInBatch(oldTsForwardOrgList);
            if (tsForwardOrgList.size() > 0 && tsForwardOrgList.size() == dwIds.size())
                tsForwardOrgRepository.save(tsForwardOrgList);
            result = getForwardOrgListByRyId(ryId);
        }
        return result;
    }

    public Map getForwardOrgListByRyId(Integer ryId) {
        Map result = new HashMap();
        List<Map> tsForwardOrgList = new ArrayList<>();
        tsForwardOrgList = tsForwardOrgRepository.findDwInfoByRyId(ryId);  // 该人员可以转交的单位
        result.put("ryId", ryId);
        result.put("dws", tsForwardOrgList);
        return result;
    }

    @Transactional
    public Map getPersonCaseTypeList(Integer ryId, Integer request, List<Integer> receiveCaseTypes) {
        Map result = new HashMap();
        if (request == 1) {
            result = getPersonCaseTypeListByRyId(ryId);
        } else if (request == 2) {
            List<TsPersonCaseTypeNew> tsPersonCaseTypeList = new ArrayList<>();
            if (receiveCaseTypes != null && receiveCaseTypes.size() > 0) {
                for (Integer typeId : receiveCaseTypes) {
                    TsPersonCaseTypeNew tsPersonCaseType = new TsPersonCaseTypeNew();
                    tsPersonCaseType.setUserId(ryId);
                    tsPersonCaseType.setTypeId(typeId);
                    Date date = new Date();
                    Timestamp now = new Timestamp(date.getTime());
                    tsPersonCaseType.setCreatedAt(now);
                    tsPersonCaseType.setUpdatedAt(now);
                    tsPersonCaseTypeList.add(tsPersonCaseType);
                }
            }
            List<TsPersonCaseTypeNew> oldTsPersonCaseTypeNewList = tsPersonCaseTypeRepository.findByRyId(ryId);
            tsPersonCaseTypeRepository.deleteInBatch(oldTsPersonCaseTypeNewList);
            if (tsPersonCaseTypeList.size() > 0)
                tsPersonCaseTypeRepository.save(tsPersonCaseTypeList);
            result = getPersonCaseTypeListByRyId(ryId);
        }
        return result;
    }

    public Map getPersonCaseTypeListByRyId(Integer ryId) {
        Map result = new HashMap();
        List<Integer> tsPersonCaseTypeList = new ArrayList<>();
        tsPersonCaseTypeList = tsPersonCaseTypeRepository.findTypeIdByRyId(ryId);  // 该人员可以接收的案件类型
        result.put("ryId", ryId);
        result.put("receiveCaseTypes", tsPersonCaseTypeList);
        return result;
    }

    @Transactional
    public Map getInputPersonCaseTypeList(Integer ryId, Integer request, List<Integer> inputCaseTypes) {
        Map result = new HashMap();
        if (request == 1) {
            result = getInputPersonCaseTypeListByRyId(ryId);
        } else if (request == 2) {
            List<TsInputPersonCaseTypeNew> tsInputPersonCaseTypeList = new ArrayList<>();
            if (inputCaseTypes != null && inputCaseTypes.size() > 0) {
                for (Integer typeId : inputCaseTypes) {
                    TsInputPersonCaseTypeNew tsInputPersonCaseType = new TsInputPersonCaseTypeNew();
                    tsInputPersonCaseType.setUserId(ryId);
                    tsInputPersonCaseType.setType(typeId);
                    Date date = new Date();
                    Timestamp now = new Timestamp(date.getTime());
                    tsInputPersonCaseType.setCreatedAt(now);
                    tsInputPersonCaseType.setUpdatedAt(now);
                    tsInputPersonCaseTypeList.add(tsInputPersonCaseType);
                }
            }
            List<TsInputPersonCaseTypeNew> oldTsInputPersonCaseTypeNewList = tsInputPersonCaseTypeRepository.findByRyId(ryId);
            tsInputPersonCaseTypeRepository.deleteInBatch(oldTsInputPersonCaseTypeNewList);
            if (tsInputPersonCaseTypeList.size() > 0)
                tsInputPersonCaseTypeRepository.save(tsInputPersonCaseTypeList);
            result = getInputPersonCaseTypeListByRyId(ryId);
        }
        return result;
    }

    public Map getInputPersonCaseTypeListByRyId(Integer ryId) {
        Map result = new HashMap();
        List<Integer> tsInputPersonCaseTypeList = new ArrayList<>();
        tsInputPersonCaseTypeList = tsInputPersonCaseTypeRepository.findTypeByRyId(ryId);  // 该人员可以录入的案件类型
        result.put("ryId", ryId);
        result.put("inputCaseTypes", tsInputPersonCaseTypeList);
        return result;
    }

    @Transactional
    public Map getTransferPersonList(Integer dwId, Integer request, List<Map> receiveCaseTypes) {
        Map result = new HashMap();

        if (request == 1) {  // 获取收文岗
            result.put("dwId", dwId);
            List<PermissionTransferPersonVO> transferList = tsTransferPersonRepository.findTransferPersonVOByDwId(dwId);
            for (PermissionTransferPersonVO item : transferList) {
                if (item.getTypeId() == null || item.getTypeId().equals("")) {
                    item.setTypeId(-1);
                }
            }
            result.put("receiveCaseTypes", transferList);
        } else if (request == 2) {  // 设置收文岗

            List<Integer> itemListA = tsTransferPersonRepository.findByOrgId(dwId);

            for (Map map : receiveCaseTypes) {

                if (map.get("receiverId").toString() == null || map.get("receiverId").toString().equals("")) {  // 设置单位收文岗為空
                    Integer typeIdItem = Integer.parseInt(map.get("typeId").toString().trim());
                    if (typeIdItem == -1) {  // 刪除单位收文岗
//                        List<Integer> itemListA = tsTransferPersonRepository.findByOrgId(dwId);
                        if (itemListA != null && itemListA.size() > 0) {
                            for (Integer item : itemListA) {
                                tsTransferPersonRepository.delete(item);
                            }
                        }
                    } else {   // 刪除接收类型收文岗
                        List<Integer> itemListB = tsTransferPersonRepository.findByOrgIdAndCaseTypeId(dwId, typeIdItem);
                        if (itemListB != null && itemListB.size() > 0) {
                            for (Integer item : itemListB) {
                                tsTransferPersonRepository.delete(item);
                            }
                        }
                    }
                    continue;
                }

                TsTransferPerson tsTransferPerson = new TsTransferPerson();
                tsTransferPerson.setOrgId(dwId);
                tsTransferPerson.setUserId(Integer.parseInt(map.get("receiverId").toString().trim()));
                Date now = new Date();
                tsTransferPerson.setUpdatedAt(new Timestamp(now.getTime()));
                if (map.get("typeId") != null && Integer.parseInt(map.get("typeId").toString().trim()) == -1) {  // 设置单位收文岗
//                    List<Integer> dwList = tsTransferPersonRepository.findByOrgId(dwId);
                    if (itemListA != null && itemListA.size() > 1) {
                        throw new BizException("单位收文岗人数大于1，请联系数据库管理员删除多余人员！");
                    } else if (itemListA == null || itemListA.size() == 0) {  // 未有单位收文岗，设置
                        tsTransferPerson.setCreatedAt(new Timestamp(now.getTime()));
                        tsTransferPersonRepository.save(tsTransferPerson);
                    } else if (itemListA.size() == 1) {  // 已有单位收文岗，更新人员
                        tsTransferPerson.setId(itemListA.get(0));
                        Date date = new Date();
                        Timestamp nowUpdate = new Timestamp(date.getTime());
                        tsTransferPerson.setUpdatedAt(nowUpdate);
//                        TsTransferPerson oldTsTransferPerson = tsTransferPersonRepository.findOne(dwList.get(0));
//                        tsTransferPerson.setCreatedAt(oldTsTransferPerson.getCreatedAt());
                        tsTransferPersonRepository.save(tsTransferPerson);
                    }
                } else if (map.get("typeId") != null && Integer.parseInt(map.get("typeId").toString().trim()) != -1) {  // 设置单位类型收文岗
                    List<Integer> dwCaseTypeIdList = tsTransferPersonRepository.findByOrgIdAndCaseTypeId(dwId,
                            Integer.parseInt(map.get("typeId").toString().trim()));
                    if (dwCaseTypeIdList != null && dwCaseTypeIdList.size() > 1) {
                        throw new BizException("单位类型收文岗人数大于1，请联系数据库管理员删除多余人员！");
                    } else if (dwCaseTypeIdList == null || dwCaseTypeIdList.size() == 0) {  // 未有单位类型收文岗，设置
                        tsTransferPerson.setCreatedAt(new Timestamp(now.getTime()));
                        tsTransferPerson.setCaseTypeId(Integer.parseInt(map.get("typeId").toString().trim()));
                        tsTransferPersonRepository.save(tsTransferPerson);
                    } else if (dwCaseTypeIdList.size() == 1) {  // 已有单位类型收文岗，更新人员
                        tsTransferPerson.setId(dwCaseTypeIdList.get(0));
                        tsTransferPerson.setCaseTypeId(Integer.parseInt(map.get("typeId").toString().trim()));
                        tsTransferPersonRepository.save(tsTransferPerson);
                    }
                }
            }
            result.put("isUpdate", true);
            result.put("message", "设置收文岗人员成功");
        }

        return result;
    }

    public List<Map> getRyListByDwIdAndRoleId(Integer dwId, Integer roleId) {
        return authUserGroupNewRepository.findByDwIdAndRoleId(dwId, roleId);
    }

    public Boolean isByRyIdAndRoleId(Integer ryId, Integer roleId) {
        if (authUserGroupNewRepository.findByRyIdAndRoleId(ryId, roleId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Integer> getKsRyIdsByRyId(Integer ryId) {
        List<Integer> result = new ArrayList<>();
        if (true) {
//        if (isByRyIdAndRoleId(ryId, 11) == true){  // ‘科室信访件查看’权限
            result = fcRybAllFieldRepository.findKsRyIdsRybById(ryId);
        }
        return result;
    }

    public List<Integer> getZwRyIdsByRyId(Integer ryId) {
        List<Integer> result = new ArrayList<>();
//        if (isByRyIdAndRoleId(ryId, 6) == true){  // ‘信访件查询’权限
        if (true) {
            Integer zwId = fcRybAllFieldRepository.findZwIdByRyId(ryId);
            if (zwId == null) {
                throw new BizException("未给人员id为" + ryId + "的人员添加职位。请先为该人员添加职位!!!");
            }
            List<Integer> zwIdList = getZwIdList(zwId);
//            zwIdList.add(zwId);
            if (zwIdList != null && zwIdList.size()>0) {
            	List<Integer> ryIds = fcRybAllFieldRepository.findZwRyIdsByZwIds(zwIdList);
                if (ryIds != null && ryIds.size()>0){
                    result.addAll(ryIds);
                }
			}
            result.add(ryId);
        }
        return result;
    }

    public List<Integer> getZwIdList(Integer zwId) {
        List<Integer> result = new ArrayList<>();
        List<Integer> zwIdList = fcKszwbRepository.findZwIdByParentId(zwId);
        if (zwIdList.size() > 0) {
            result.addAll(zwIdList);
            for (Integer item : zwIdList) {
                result.addAll(getZwIdList(item));
            }
        } else {
            result = zwIdList;
        }
        return result;
    }

}
