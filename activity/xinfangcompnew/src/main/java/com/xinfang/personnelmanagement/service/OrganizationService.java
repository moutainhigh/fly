package com.xinfang.personnelmanagement.service;

import com.xinfang.model.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhangbo
 *机构列表
 */
public interface OrganizationService {
 
 /**
  * 通过区县市ID获取单位列表
  */
  public Map<String,Object> getdeptByQXSId(int QxsId, String fuzzy,Integer startPage,Integer pagecount );
  

  
  /**
   * 添加修改区县市
   */
  public Map<String, Object> updateOrAddQxs(FcQxsb fcQxs,String cjsj,String xgsj);
  
 /**
  * 获取区县市列表
  */
  public Map<String,Object> getQxsList(Integer startPage,Integer pageCount,String fuzzy);
  
  /**
   * 添加修改职能部门
   * id字段辨识修改或添加
   */
  public Map<String,Object> updateOrAddDept(FcDwb fcDwb,String cjsj,String xgsj);
  /**
   * 通过单位ID获取窗口列表
   */
  public Map<String,Object> getTsWindowByDeptId(int deptId,Integer startPage,Integer pageCount,String fuzzy);
  /**
   * 添加修改科室
   * 
   */
  public Map<String,Object> updateOrAddKs(FcKsb fcKsb,String cjsj,String xgsj );
  /**
   * 通过单位ID科室列表
   */
  public Map<String,Object> getKsListByDeptId(int deptId,Integer startPage,Integer pageCount,String fuzzy);
  /**
   * 添加修改职务
   */
  public Map<String,Object> updateOrAddZw(FcKszwb fcKszwb,String cjsj,String xgsj );
  /**
   * 通过科室ID获取职务列表
   */
  public Map<String, Object> getZwListByKsId(int ksId,Integer startPage,Integer pageCount,String fuzzy);
  /**
   * 添加修改窗口
   */
  public Map<String, Object> updateOrAddWindow(TsWindow tsWindow,List<Integer> rIds,String cjsj,String xgsj);
  /**
   * 通过单位类型ID获取单位
   */
  public Map<String, Object> getDeptListByDeptType(int typeId,int startePage,int pageCount,String fuzzy);

 /**
  *机构人员总览
  * @param countyId 区县市ID
  * @param unitId 单位ID
  * @param departmentId 科室ID
  * @return
  */
  Map<String, Object> staffPandect(int countyId, int unitId, int departmentId);
  /**
   * @param typeId 种类（区县市，单位）
   * @param Id    id
   */
  Map<String,Object> getObjectByTypeIdAndId(int typeId,int id);
  /**
   * @param 单位ID
   * 通过单位ID获取科室列表（科室包含职务）
   */
  List<Map> getKsListByUnitId(int unitId); 
  /**
   * @type id种类
   * @Id id
   * 删除功能
   */
  Map<String, Object> deleteObject(Integer id,Integer type);
  /**
   * 查找单位父id为空的职务
   * @param unitId
   * @return
   */
  Map<String, Object> getParentByUnitId(Integer unitId);
  /**
   * 获取除该窗口ID下 该单位窗口关联所有人员
   * @param windowId
   * @param unitId
   * @return
   */
  Map<String, Object> getRyIdListByWindowId(Integer windowId,Integer unitId );
  /**
   * 根据登录人编号获取窗口编号
   * @return
   */
  Map<String, Object> getWindowIdBysignUserId(Integer signUserId);


}

