package com.xinfang.personnelmanagement.service;

import com.xinfang.Exception.BizException;
import com.xinfang.VO.PermissionPersonDetail;
import com.xinfang.dao.FcKszwbRepository;
import com.xinfang.dao.FcRybAllFieldRepository;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.model.FcRybAllField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-27 14:45
 **/

@Service
public class PersonnelService {

    @Autowired
    FcRybAllFieldRepository fcRybAllFieldRepository;

    @Autowired
    FcKszwbRepository fcKszwbRepository;

    @Autowired
    PermissionService permissionService;

    @Autowired
    FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;

    /**
     *
     * @param type  type	Integer	是	条件类型，如：1为区县市、2为单位类型、3为单位、4为科室、即点击某个区县显示区县下所有机构列表，单位同理
     * @param id  id	Integer	是	某个类型下id，如type=1，id就是区县市id，type为空时id无效
     * @param ryId ryId	Integer	是	人员id，输入该字段获得返回人员详情
     * @param search  search	String	是	搜索条件，根据输入的值返回含有该值的人员列表
     * @return
     */
    public Page<PermissionPersonDetail> personList(Integer type, Integer id, Integer qxsId, Integer ryId,
                                                   String search, Integer startpage, Integer pagesize){
        PageRequest page = new PageRequest(startpage, pagesize);

        Page<PermissionPersonDetail> result = null;
        if (type != null && (type ==1 || type == 2 || type == 3 || type == 4|| type == 5) && (id != null)){  // 有类型和它的id
            if (search == null){    // 没有模糊搜索
                // 人员id为空，搜索所有
                if (ryId == null) switch (type) {
                    case 1:
                        result = fcRybAllFieldRepository.personListByQxsId(id, page);
                        break;
                    case 2:
                        result = fcRybAllFieldRepository.personListByDwTypeId(id, qxsId, page);
                        break;
                    case 3:
                        result = fcRybAllFieldRepository.personListByDwId(id, page);
                        break;
                    case 4:
                        result = fcRybAllFieldRepository.personListByKsId(id, page);
                        break;
                    case 5:
                        result = fcRybAllFieldRepository.personListByZwId(id, page);
                        break;
                    default:
                        ;
                }
                else {  // 人员id不为空，搜索某个人员。
                    result = fcRybAllFieldRepository.personListByRyId(ryId, page);
                }
            }else {    // 模糊搜索
                switch (type){
                    case 1:
                        result = fcRybAllFieldRepository.personListFilterByQxsId(id, search, page);
                        break;
                    case 2:
                        result = fcRybAllFieldRepository.personListFilterByDwTypeId(id, search, page);
                        break;
                    case 3:
                        result = fcRybAllFieldRepository.personListFilterByDwId(id, search, page);
                        break;
                    case 4:
                        result = fcRybAllFieldRepository.personListFilterByKsId(id, search, page);
                        break;
                    case 5:
                        result = fcRybAllFieldRepository.personListByZwId(id, page);
                        break;
                    default:
                        ;
                }
            }
        }else if (type == 0 && ryId != 0){
            result = fcRybAllFieldRepository.personListByRyId(ryId, page);
        }
        for (PermissionPersonDetail personDetail: result.getContent()){
            if (personDetail.getCheckPersonId() != null){
                Integer parentId = personDetail.getCheckPersonId();
                personDetail.setCheckPerson(fcRybAllFieldRepository.findRyMcByRyId(parentId));
            }
        }
        return result;
    }
    public Page<PermissionPersonDetail> personRybAll(Integer type, Integer id, Integer qxsId, Integer ryId,
                                                     String search, Integer startpage, Integer pagesize,Integer fuzzyState) {
        PageRequest page = new PageRequest(startpage, pagesize);
        Page<PermissionPersonDetail> result = null;
        if(!fuzzyState.equals(0)){//只有search字段的模糊查询
            result = fcRybAllFieldRepository.personRybFuzzyQueryByRyMc(search,page);
        }else{//其他组合查询
            result=personList(type,id,qxsId,ryId,search,startpage,pagesize);
        }

        return result;

    }
    @Transactional
    public Boolean addPerson(Integer request, Integer ryId,
                             String name, String img, String sex, String placeOfBirth,Integer qxsId, Integer workUnitId,
                             Integer officeId, Integer dutyId, Integer orderBy, Integer enabled,
                             Integer politicalId, Integer national, Integer schooling, String idcard,
                             String telephone, String birthDate, String email, String startWorkTime,
                             Integer windowId, String thePartyTime, Integer checkPersonId, String personBusiness,
                             String logInName, String password, Integer sfId, Integer sqId) throws ParseException {
        Boolean result = false;

        FcRybAllField ryb = new FcRybAllField();

        List<String> rySfzList = fcRybAllFieldRepository.findSfzByRySfz(idcard);
        List<String>  logInNameList = fcRybAllFieldRepository.findLogInNameRyLogInName(logInName);

        if (ryId != null && request == 2){
//            Map ryMap = fcRybAllFieldRepository.findRyNameAndIdById(ryId);
            if (ryb == null){
                throw new  BizException("未查找到要修改的人员");
            }
            List<String>  ryLogInNameList = fcRybAllFieldRepository.findLogInNameRyLogInNameAndNotInRyId(logInName, ryId);
            if (logInName != null && (ryLogInNameList != null && ryLogInNameList.size()>0)){
                throw new  BizException("修改登录名失败，已存在登录名"+logInName+",请重新录入一个登录名");
            }
            List<String>  ryIdRySfzList = fcRybAllFieldRepository.findSfzByRySfzAndNotInRyId(logInName, ryId);
            if (idcard != null && (ryIdRySfzList != null && ryIdRySfzList.size()>0)){
                throw new  BizException("身份证id"+idcard+"已经被注册！");
            }
//            List<FcRybAllField> fcRybAllFieldList = fcRybAllFieldRepository.findByRyId(ryId);
//            ryb = fcRybAllFieldList.get(0);
            ryb = fcRybAllFieldRepository.findOne(ryId);
        }else if (request == 1){
            if (idcard != null && (rySfzList != null && rySfzList.size()>0)){
                throw new  BizException("身份证id"+idcard+"已经被注册！");
            }
            if (logInName != null && (logInNameList != null && logInNameList.size()>0)){
                throw new  BizException("录入登录名失败，已存在登录名"+logInName+",请重新录入一个登录名");
            }
        }else {
            throw new  BizException("输入参数错误，request必须为1或2，或ryId在request=2时不能为空");
        }
        if (name != null)
            ryb.setRyMc(name);
        if (img != null)
            ryb.setRyImg(img);
        if (idcard != null)
            ryb.setRySfz(idcard);
        if (password != null)
            ryb.setPassword(password);
        if (dutyId != null)
            ryb.setZwId(dutyId);
        if (officeId != null)
            ryb.setKsId(officeId);
        if (workUnitId != null)
            ryb.setDwId(workUnitId);
        if (qxsId != null)
            ryb.setQxsId(qxsId);
        if (email != null)
            ryb.setRyEmail(email);
        if (sex != null)
            ryb.setRyXb(sex);
        if (telephone != null)
            ryb.setRySjh(telephone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //  HH:mm:ss

        if (birthDate != null){
            Date csrq = sdf.parse(birthDate);
            ryb.setRyCsrq(csrq);
        }

        if (placeOfBirth != null)
            ryb.setRyJg(placeOfBirth);
        if (startWorkTime != null && !"".equals(startWorkTime)){
            Date xfgzsj = sdf.parse(startWorkTime);
            ryb.setRyXfgzsj(xfgzsj);
        }

        if (thePartyTime != null && !"".equals(thePartyTime)){
            Date rdsj = sdf.parse(thePartyTime); // 入党时间
            ryb.setThePartyTime(rdsj);
        }
        if (orderBy != null)
            ryb.setXh(orderBy);
        Date now = new Date();
        ryb.setCjsj(now);
        ryb.setXgsj(now);
        if (enabled != null)
            ryb.setQyzt(enabled);
        if (national != null)
            ryb.setMzId(national);
        if (schooling != null)
            ryb.setXlId(schooling);
        if (politicalId != null)
            ryb.setZzmmId(politicalId);
        if (personBusiness != null)
            ryb.setPersonBusiness(personBusiness);
        if (logInName != null)
            ryb.setLogInName(logInName);

        if (sfId == null) ryb.setSfId(520100); else ryb.setSfId(sfId);
        if (sqId == null) ryb.setSqId(520000); else ryb.setSqId(sqId);

        // =======================默认审核人====================
        if (checkPersonId != null){
            ryb.setCheckPersonId(checkPersonId);
        }else {
            Integer parent_id = fcKszwbRepository.getParentIdByZwId(dutyId); //获取上级职位id
            if (parent_id != null){
               List<Integer> defaultCheckPersonIdList = fcRybAllFieldRepository.findRyIdByZwId(parent_id);  // 默认审核人id
               if (defaultCheckPersonIdList != null && defaultCheckPersonIdList.size() >0)
                ryb.setCheckPersonId(defaultCheckPersonIdList.get(0)); // 默认选择第一个
            }
        }

        FcRybAllField isSave = fcRybAllFieldRepository.save(ryb);

        if (isSave != null){
            result = true;
        }

        return result;
    }

    public FcRybAllField modifyPersonDetail(Integer ryId){
        FcRybAllField fcRybAllField = fcRybAllFieldRepository.findOne(ryId);
//        fcRybAllField.setFcQxsb(null);
//        fcRybAllField.setFcDwb(null);
//        fcRybAllField.setFcKsb(null);
//        fcRybAllField.setKszwb(null);
        return fcRybAllField;
    }

    public void deletePerson(Integer ryId){

        if (fcRybAllFieldRepository.findRybById(ryId) == null){
            throw new BizException("人员"+ryId+"不存在或已经被删除");
        }

        Integer zwId = fcRybAllFieldRepository.findZwIdByRyId(ryId);
        List<Integer> caseIdList = fdCaseFeedbackAllRepository.findCaseFeedbackAllByCreaterId(ryId);
        if (caseIdList != null && caseIdList.size() > 0){
            throw new BizException("该用户存在信访件办理记录，无法删除该人员，参与了案件" + caseIdList + "的办理");
        }
        if (zwId == null) {
//            throw new BizException("未给人员id为" + ryId + "的人员添加职位。请先为该人员添加职位!!!");
        }else {
            List<Integer> zwIdList =  permissionService.getZwIdList(zwId);
            if (zwIdList != null && zwIdList.size()>0){  // 下级职位id不为空
                List<Integer> subordinateRyIdList = fcRybAllFieldRepository.findZwRyIdsByZwIds(zwIdList); // 下属人员id列表
                if (subordinateRyIdList != null && subordinateRyIdList.size() > 0){
                    throw new BizException("该用户有下属，无法删除该人员。下属的人员id为：" + subordinateRyIdList);
                }
            }
        }
        fcRybAllFieldRepository.delete(ryId);

    }

    public Map updateLogInPerson(Integer ryId, String password, String newUsername, String newPassword){

        Map result = new HashMap();
        Integer isUpdate = 0;
        if (!password.equals(fcRybAllFieldRepository.findPasswordByRyId(ryId))){
            throw new BizException("密码错误");
        }
        if (ryId != null && password != null){
            if (newUsername == null && newPassword == null){
                throw new BizException("新用户名和新密码至少一个不能为空");
            }else if(newUsername != null && newPassword == null){  // 更新用户名
                String logInName = fcRybAllFieldRepository.findLogInNameByRyId(ryId);
                if (logInName == null || logInName.equals("")){
                    throw new BizException("人员id对应用户名为空，或者没有该人员id");
                }else if (fcRybAllFieldRepository.findLogInNameRyLogInName(newUsername).size()>0){  // 用户名已被使用
                    throw new BizException("用户名已被使用");
                }else {
                    isUpdate = fcRybAllFieldRepository.updateUsername(ryId, password, newUsername);
                }
            }else if (newUsername == null && newPassword != null){  // 更新密码
                if (newPassword.equals(fcRybAllFieldRepository.findPasswordByRyId(ryId))){
                    throw new BizException("新密码不能和老密码相同");
                };
                isUpdate = fcRybAllFieldRepository.updatePassword(ryId, password, newPassword);
            }else if (newUsername != null && newPassword != null){  // 更新用户名和密码
                throw new BizException("新用户名和新密码必须有一个字段为空");
            }
            if (isUpdate == 0){
                throw new BizException("更新失败！！！");
            }else if (isUpdate == 1){
                result.put("isUpdate", true);
                result.put("message", "更新成功");
                return result;
            }
        }else {
            throw new BizException("人员id和密码不能为空");
        }
        result.put("isUpdate", false);
        return result;
    }
    /**
     *
     * @param type  type	Integer	是	条件类型，如：1为区县市、2为单位类型、3为单位、4为科室、即点击某个区县显示区县下所有机构列表，单位同理
     * @param id  id	Integer	是	某个类型下id，如type=1，id就是区县市id，type为空时id无效
     * @param ryId ryId	Integer	是	人员id，输入该字段获得返回人员详情
     * @param search  search	String	是	搜索条件，根据输入的值返回含有该值的人员列表
     * @return
     */
    /*private Page<PermissionPersonDetail> personList2(Integer type, Integer id, Integer qxsId, Integer ryId,
                                                   String search, Integer startpage, Integer pagesize){
        PageRequest page = new PageRequest(startpage, pagesize);

        Page<PermissionPersonDetail> result = null;
        if (type != null && (type ==1 || type == 2 || type == 3 || type == 4|| type == 5) && (id != null)){  // 有类型和它的id
            if (search == null){    // 没有模糊搜索
                // 人员id为空，搜索所有
                if (ryId == null) switch (type) {
                    case 1:
                        result = fcRybAllFieldRepository.personListByQxsId(id, page);
                        break;
                    case 2:
                        result = fcRybAllFieldRepository.personListByDwTypeId(id, qxsId, page);
                        break;
                    case 3:
                        result = fcRybAllFieldRepository.personListByDwId(id, page);
                        break;
                    case 4:
                        result = fcRybAllFieldRepository.personListByKsId(id, page);
                        break;
                    case 5:
                        result = fcRybAllFieldRepository.personListByZwId(id, page);
                        break;
                    default:
                        ;
                }
                else {  // 人员id不为空，搜索某个人员。
                    result = fcRybAllFieldRepository.personListByRyId(ryId, page);
                }
            }else {    // 模糊搜索
                switch (type){
                    case 1:
                        result = fcRybAllFieldRepository.personListFilterByQxsId(id, search, page);
                        break;
                    case 2:
                        result = fcRybAllFieldRepository.personListFilterByDwTypeId(id, search, page);
                        break;
                    case 3:
                        result = fcRybAllFieldRepository.personListFilterByDwId(id, search, page);
                        break;
                    case 4:
                        result = fcRybAllFieldRepository.personListFilterByKsId(id, search, page);
                        break;
                    case 5:
                        result = fcRybAllFieldRepository.personListByZwId(id, page);
                        break;
                    default:
                        ;
                }
            }
        }else if (type == null && ryId != null){
        	System.err.println("跳入此方法");
            result = fcRybAllFieldRepository.personListByRyId(ryId, page);
        }
        System.err.println(result.getContent().toString());
        for (PermissionPersonDetail personDetail: result.getContent()){
            if (personDetail.getCheckPersonId() != null){
                Integer parentId = personDetail.getCheckPersonId();
                personDetail.setCheckPerson(fcRybAllFieldRepository.findRyMcByRyId(parentId));
            }
        }
        return result;
    } */
}
