package com.xinfang.meetingmodule.service;

import com.alibaba.fastjson.JSON;
import com.github.wenhao.jpa.Sorts;
import com.github.wenhao.jpa.Specifications;
import com.xinfang.Exception.BizException;
import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FcRybAllFieldRepository;
import com.xinfang.dao.TsTransferPersonRepository;
import com.xinfang.meetingmodule.VO.*;
import com.xinfang.meetingmodule.dao.*;
import com.xinfang.meetingmodule.enums.MeetingJionPerson;
import com.xinfang.meetingmodule.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 16:54
 **/

@Service
@Transactional
public class MeetingService {

    @Autowired
    FhMeetingInitiateRepository fhMeetingInitiateRepository;

    @Autowired
    FhMeetingDepartmentRepository fhMeetingDepartmentRepository;

    @Autowired
    FcRybAllFieldRepository fcRybAllFieldRepository;

    @Autowired
    FhMeetingListVORepository fhMeetingListVORepository;

    @Autowired
    FhMeetingPersonRepository fhMeetingPersonRepository;

    @Autowired
    TsTransferPersonRepository tsTransferPersonRepository;  // 收文岗

    @Autowired
    FhMeetingSubmitPersonRepository fhMeetingSubmitPersonRepository;

    @Autowired
    FhMeetingDetailVORepository fhMeetingDetailVORepository;

    @Autowired
    FhMeetingContinueRepository fhMeetingContinueRepository;

    @Autowired
    FhContinueRemindRepository fhContinueRemindRepository;

    @Autowired
    FhMeetingContinueConfirmRepository fhMeetingContinueConfirmRepository;

    @Autowired
    FcDwbRepository fcDwbRepository;

    @Autowired
    FhMeetingUrgeRepository fhMeetingUrgeRepository;

    @Autowired
    EntityManager em;

    public PeopleRole basePersonRole(Integer peopleId, Integer meetingId) {
        PeopleRole peopleRole = new PeopleRole();
        peopleRole.setPeopleId(peopleId);
        peopleRole.setMeetingId(meetingId);
        if (fhMeetingInitiateRepository.findMeetingIdByMeetingIdAndMeetingOperateId(meetingId, peopleId) == null) {
            peopleRole.setInitiatePerson(false);
        } else {
            peopleRole.setInitiatePerson(true);
        }

        if (fhMeetingSubmitPersonRepository.findMeetingPersonIdByMeetingIdAndMeetingPersonId(meetingId, peopleId) == null) {
            peopleRole.setSubmitPerson(false);
        } else {
            peopleRole.setSubmitPerson(true);
        }

        if (fhMeetingPersonRepository.findByMeetingIdAndInitiatePersonId(meetingId, peopleId) == null ||
                fhMeetingPersonRepository.findByMeetingIdAndInitiatePersonId(meetingId, peopleId).size() == 0) {
            peopleRole.setJoinPerson(false);
        } else {
            peopleRole.setJoinPerson(true);
        }

        return peopleRole;
    }

    @Transactional
    public FhMeetingInitiateVO addMeetingSchedule(FhMeetingInitiateVO fhMeetingInitiateVO) {

        FhMeetingInitiate fhMeetingInitiate = new FhMeetingInitiate();
        BeanUtils.copyProperties(fhMeetingInitiateVO, fhMeetingInitiate);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        fhMeetingInitiate.setMeetingOperateDate(dateString); // 发起会议时间
        fhMeetingInitiate.setIsAccomplish((byte) 0);
        fhMeetingInitiate.setIsCase((byte) 0);
        fhMeetingInitiate.setIsContinue((byte) 0);
        fhMeetingInitiate.setIsDispatch((byte) 0);
        fhMeetingInitiate.setIsLead((byte) 0);
        fhMeetingInitiate.setMeetingNatureId(fhMeetingInitiateVO.getMeetingNatureId());
        if (fhMeetingInitiateVO.getMeetingId() != null) {
            fhMeetingInitiate.setMeetingId(fhMeetingInitiateVO.getMeetingId());
            fhMeetingDepartmentRepository.deleteByMeetingId(fhMeetingInitiateVO.getMeetingId());
            fhMeetingSubmitPersonRepository.deleteByMeetingId(fhMeetingInitiateVO.getMeetingId());
        }
        fhMeetingInitiate = fhMeetingInitiateRepository.save(fhMeetingInitiate);

        List<FhMeetingDepartment> fhMeetingDepartmentList = new ArrayList<>();
        List<Integer> departmentIds = JSON.parseArray(fhMeetingInitiateVO.getDepartmentIdList(), Integer.class);
        List<String> departmentNames = JSON.parseArray(fhMeetingInitiateVO.getDepartmentNameList(), String.class);
        for (int i = 0; i < departmentIds.size(); i++) {
            FhMeetingDepartment fhMeetingDepartment = new FhMeetingDepartment();
            fhMeetingDepartment.setMeetingId(fhMeetingInitiate.getMeetingId());
            fhMeetingDepartment.setDepartmentId(departmentIds.get(i));
            fhMeetingDepartment.setDepartmentName(departmentNames.get(i));
            fhMeetingDepartment.setIsAttend((byte) MeetingJionPerson.unconfirmed.getIndex()); // 未确认
            fhMeetingDepartmentList.add(fhMeetingDepartment);
        }
        fhMeetingDepartmentRepository.save(fhMeetingDepartmentList);


        //=======================添加接收会议的人员--每个单位的单位收文岗人员=================
        for (Integer dwId : departmentIds) {
            List<Map> swgInfo = tsTransferPersonRepository.findDwRyInfoByDwId(dwId);
            if (swgInfo == null || swgInfo.size() == 0) {
                String dwMc = fcDwbRepository.findDwMcAndDwIdByDwId(dwId);
                throw new BizException("单位id为：" + dwId + ",单位名称为：" + dwMc + " 的单位收文岗人员为空，请先为单位设置收文岗人员");
            }
            FhMeetingSubmitPerson fhMeetingSubmitPerson = new FhMeetingSubmitPerson();
            fhMeetingSubmitPerson.setMeetingId(fhMeetingInitiate.getMeetingId());
            fhMeetingSubmitPerson.setMeetingPersonId((Integer) swgInfo.get(0).get("ryId"));
            fhMeetingSubmitPerson.setMeetingPersonName((String) swgInfo.get(0).get("ryName"));
            fhMeetingSubmitPerson.setMeetingDepartmentId(fhMeetingInitiate.getDepartmentId());  // 召开部门id
            fhMeetingSubmitPerson.setMeetingDepartmentName(fhMeetingInitiate.getDepartmentName());  //  召开部门名称
            fhMeetingSubmitPersonRepository.save(fhMeetingSubmitPerson);
        }


        FhMeetingInitiateVO result = new FhMeetingInitiateVO();
        BeanUtils.copyProperties(fhMeetingInitiate, result);
        result.setDepartmentIdList(fhMeetingInitiateVO.getDepartmentIdList());
        result.setDepartmentNameList(fhMeetingInitiateVO.getDepartmentNameList());

        return result;
    }

    public FhMeetingInitiateVO getMeetingSchedule(Integer meetingId) {
        FhMeetingInitiateVO result = new FhMeetingInitiateVO();
        FhMeetingInitiate fhMeetingInitiate = fhMeetingInitiateRepository.findByMeetingId(meetingId);
        BeanUtils.copyProperties(fhMeetingInitiate, result);
        List<FhMeetingDepartment> fhMeetingDepartmentList = fhMeetingDepartmentRepository.findByMeetingId(meetingId);
        String departmentIdListStr = "[";
        String departmentNameListStr = "[";
        for (int i = 0; i < fhMeetingDepartmentList.size(); i++) {
            departmentIdListStr += fhMeetingDepartmentList.get(i).getDepartmentId();
            departmentNameListStr += fhMeetingDepartmentList.get(i).getDepartmentName();
            if (i < fhMeetingDepartmentList.size() - 1) {
                departmentIdListStr += ",";
                departmentNameListStr += ",";
            }
        }
        departmentIdListStr += "]";
        departmentNameListStr += "]";
        result.setDepartmentIdList(departmentIdListStr);
        result.setDepartmentNameList(departmentNameListStr);
        return result;
    }

    public Page<FhMeetingListVO> meetingLists(SearchRequest searchRequest) {  // 发起人和部门参与人id

//        FcRybTreeVO fcRybTreeVO = fcRybAllFieldRepository.findRybById(searchRequest.getRyId());
        Set<Integer> meetingPersonMeetingIdSet = fhMeetingPersonRepository.findMeetingIdByMeetingPersonId(searchRequest.getRyId()); // 参会人
        Set<Integer> submitPersonPersonMeetingIdSet = fhMeetingSubmitPersonRepository.findMeetingIdByMeetingPersonId(searchRequest.getRyId()); // 部门提交人，即收文岗人员
        Specification<FhMeetingListVO> specification = Specifications.<FhMeetingListVO>and()
                .predicate((root, query, cb) -> {
                    Predicate p1 = cb.equal(root.get("meetingOperateId"), searchRequest.getRyId()); // 发起人
                    Predicate p2 = null; // 部门接收人
                    Predicate p3 = null; // 部门确认人，即收文岗人员
                    if (root.get("meetingPersons") != null) {
//                        Path<FhMeetingListVO> meetingPersons = root.join("meetingPersons", JoinType.LEFT);
//                        p2 = cb.equal(meetingPersons.get("meetingPersonId"), searchRequest.getRyId());

                        if (meetingPersonMeetingIdSet != null && meetingPersonMeetingIdSet.size()>0)
                            p2 = root.get("meetingId").in(meetingPersonMeetingIdSet.toArray());

//                        Expression<String> exp = root.get("meetingId");
//                        Predicate predicate = exp.in(meetingPersonMeetingIdSet.toArray());
//                        p2 = query.where(predicate).getRestriction();
                    }
                    if (root.get("meetingSubmitPersons") != null) {
//                        Path<FhMeetingListVO> meetingPersons = root.join("meetingSubmitPersons", JoinType.LEFT);
//                        p3 = cb.equal(meetingPersons.get("meetingPersonId"), searchRequest.getRyId());
                        if (submitPersonPersonMeetingIdSet != null && submitPersonPersonMeetingIdSet.size()>0)
                            p3 = root.get("meetingId").in(submitPersonPersonMeetingIdSet.toArray());
                    }
                    if (searchRequest.getRyIdType() == 0) { // 所有人员
                        if (p2 == null && p3 != null){
                            query.where(cb.or(p1, p3));
                        } else if (p2 != null && p3 == null){
                            query.where(cb.or(p1, p2));
                        } else if (p2 == null && p3 == null){
                            query.where(cb.or(p1));
                        }else {
                            query.where(cb.or(p1, p2, p3));
                        }
                    } else if (searchRequest.getRyIdType() == 1) {  // 接收部门人员
                        if (p2 == null){
                            throw new BizException(404, "现在该会议暂时未有部门接受人员！！！请等部门确认人添加后再查看");
                        }else {
                            query.where(cb.and(p2));
                        }
                    } else if (searchRequest.getRyIdType() == 2) {  // 发起会议人员
                        query.where(cb.and(p1));
                    } else if (searchRequest.getRyIdType() == 3) {  // 部门确认人
                        if (p3 == null){
                            throw new BizException(404, "该人员不是部门确认人，即不是收文岗人员，无法返回结果");
                        }else {
                            query.where(cb.and(p3));
                        }
                    } else if (searchRequest.getRyIdType() == 4) {  // 表示接收部门人员或发起会议人员
                        if (p2 == null){
                            query.where(cb.or(p1));
                        }else {
                            query.where(cb.or(p1, p2));
                        }
                    }
//                    query.groupBy(root.get("meetingId")); // jap 不支持 groupBy
                    return query.getRestriction();
//                    return query.getGroupRestriction();
                })
                .predicate(StringUtils.isNotBlank(searchRequest.getKeyword()), (root, query, cb) -> {
                    Predicate a1 = cb.like(root.get("meetingDate").as(String.class), "%" + searchRequest.getKeyword() + "%");
                    Predicate a2 = cb.like(root.get("meetingEmcee"), "%" + searchRequest.getKeyword() + "%");
                    Predicate a3 = cb.like(root.get("meetingTypeName"), "%" + searchRequest.getKeyword() + "%");
                    Predicate a4 = cb.like(root.get("initiateDepartmentName"), "%" + searchRequest.getKeyword() + "%");
                    Predicate a5 = cb.like(root.get("meetingTitle"), "%" + searchRequest.getKeyword() + "%");
                    Predicate a6 = cb.like(root.get("meetingContent"), "%" + searchRequest.getKeyword() + "%");
                    Predicate a7 = cb.like(root.get("meetingOperateName"), "%" + searchRequest.getKeyword() + "%");
                    Predicate a8 = cb.like(root.get("meetingAddress"), "%" + searchRequest.getKeyword() + "%");
                    query.where(cb.or(a1, a2, a3, a4, a5, a6, a7, a8));
                    return query.getRestriction();
                })
                .eq(Objects.nonNull(searchRequest.getMeetingTypeId()), "meetingTypeId", searchRequest.getMeetingTypeId())
                .eq(Objects.nonNull(searchRequest.getIsCase()), "isCase", searchRequest.getIsCase())
                .eq(Objects.nonNull(searchRequest.getIsDispatch()), "isDispatch", searchRequest.getIsDispatch())
                .eq(Objects.nonNull(searchRequest.getNowOrHistory()), "isAccomplish", searchRequest.getNowOrHistory())
                .predicate(Objects.nonNull(searchRequest.getStartTime()), (root, query, cb) -> {
                    return cb.greaterThanOrEqualTo(root.get("meetingDate").as(Date.class), searchRequest.getStartTime());
                })
                .predicate(Objects.nonNull(searchRequest.getEndTime()), (root, query, cb) -> {
                    return cb.lessThanOrEqualTo(root.get("meetingDate").as(Date.class), searchRequest.getEndTime());
                })
                .build();

        Sort sort = Sorts.builder()
                .desc("meetingId")
                .build();


        Page<FhMeetingListVO> fhMeetingDepartmentPage = fhMeetingListVORepository.findAll(specification,
                new PageRequest(searchRequest.getStartPage(), searchRequest.getPageSize(), sort));

        return fhMeetingDepartmentPage;
    }

    public Page<FhMeetingPerson> confirmConditionList(Integer submitPersonId, Integer meetingId,
                                                      Integer startPage, Integer pageSize) {
//        FcRybTreeVO fcRybTreeVO = fcRybAllFieldRepository.findRybById(ryId);
        Specification<FhMeetingPerson> specification = Specifications.<FhMeetingPerson>and()
                .eq(Objects.nonNull(meetingId), "meetingId", meetingId)
                .eq(Objects.nonNull(submitPersonId), "submitId", submitPersonId)
                .build();
        Page<FhMeetingPerson> fhMeetingPeople = fhMeetingPersonRepository.findAll(specification,
                new PageRequest(startPage, pageSize));

        return fhMeetingPeople;
    }

    public Integer confirmPeopleCondition(Integer submitPersonId, Integer meetingId,
                                          Integer meetingPersonPk, Integer personState, String absentReason) {
        Byte personStateByte = Byte.parseByte(String.valueOf(personState));
        if (personState != MeetingJionPerson.absent.getIndex()) {  // 如果不是‘无法参加’，表示都为空
            absentReason = null;
        }
        Integer result = fhMeetingPersonRepository.updatePersonState(submitPersonId, meetingId, meetingPersonPk, personStateByte,
                MeetingJionPerson.getName(personState), absentReason);
        return result;
    }

    public Page<FhMeetingPerson> confirmConditionInitiatePerson(Integer initiatePersonId, Integer meetingId,
                                                                Integer startPage, Integer pageSize) {
        Specification<FhMeetingPerson> specification = Specifications.<FhMeetingPerson>and()
                .eq(Objects.nonNull(meetingId), "meetingId", meetingId)
                .predicate(Objects.nonNull(initiatePersonId), ((root, query, cb) -> {
                    Path<FhMeetingInitiate> fhMeetingInitiatePath = root.join("fhMeetingInitiate", JoinType.INNER);
                    return cb.equal(fhMeetingInitiatePath.get("meetingOperateId"), initiatePersonId);
                }))
                .build();
        Page<FhMeetingPerson> fhMeetingPeople = fhMeetingPersonRepository.findAll(specification,
                new PageRequest(startPage, pageSize));

        return fhMeetingPeople;
    }

    public FhMeetingUrge confirmConditionInitiatePersonRemind(FhMeetingUrgeVO fhMeetingUrgeVO) {
        FhMeetingUrge fhMeetingUrge = new FhMeetingUrge();
        BeanUtils.copyProperties(fhMeetingUrgeVO, fhMeetingUrge);
        fhMeetingUrge.setUrgeDate(new Date());
        fhMeetingUrge = fhMeetingUrgeRepository.save(fhMeetingUrge);
        return fhMeetingUrge;
    }

    public FhMeetingPerson addMeetingPerson(FhMeetingPersonVO fhMeetingPersonVO) {
        FhMeetingPerson fhMeetingPerson = new FhMeetingPerson();
        BeanUtils.copyProperties(fhMeetingPersonVO, fhMeetingPerson);
        fhMeetingPerson.setSubmitDate(new Date());
        fhMeetingPerson.setPersonTypeName(MeetingJionPerson.getName(fhMeetingPersonVO.getPersonType())); // 添加类型名称
        fhMeetingPerson.setIsRegister((byte) MeetingJionPerson.unsignin.getIndex()); // 是否签到 0为否1为是
        fhMeetingPerson.setPersonState((byte) MeetingJionPerson.unconfirmed.getIndex());
        fhMeetingPerson.setPersonStateName(MeetingJionPerson.unconfirmed.getName());
//        fhMeetingPerson = fhMeetingPersonRepository.save(fhMeetingPerson);
        return fhMeetingPerson;
    }


    public void confirmReply(String fhMeetingPersonVOListStr) {
        System.err.print(fhMeetingPersonVOListStr);
        if (fhMeetingPersonVOListStr.startsWith("fhMeetingPersonVOListStr")){
            fhMeetingPersonVOListStr = fhMeetingPersonVOListStr.substring(25);  // 不要字符串 "fhMeetingPersonVOListStr="
            System.err.print("----\n"+fhMeetingPersonVOListStr+"\n----\n");
        }
        List<FhMeetingPerson> fhMeetingPersonList = JSON.parseArray(fhMeetingPersonVOListStr, FhMeetingPerson.class);
        fhMeetingPersonRepository.save(fhMeetingPersonList);
//        Integer meetingId = fhMeetingPersonList.get(0).getMeetingId();
//        Integer departmentId = fhMeetingPersonList.get(0).getMeetingDepartmentId();
//        Byte isAtend = fhMeetingDepartmentRepository.findIsAttendByMeetingId(meetingId, departmentId);
//        if (isAtend != null && isAtend == MeetingJionPerson.unconfirmed.getIndex())
//            fhMeetingPersonRepository.save(fhMeetingPersonList);
//        else if (isAtend != null && isAtend == (byte)MeetingJionPerson.confirmed.getIndex())
//            throw new BizException("会议id为"+meetingId+"的会议已经确定参加！");
//        else if (isAtend != null && isAtend == (byte)MeetingJionPerson.absent.getIndex())
//            throw new BizException("会议id为"+meetingId+"的会议已经确定无法参加，请勿重复操作！");
//        else
//            throw new BizException("会议id为"+meetingId+"的会议的参会情况isAtend字段状态异常，请联系数据库管理员！");
    }

    public Integer departmentAbsent(Integer meetingId, Integer departmentId, String absentReason){
        Byte isAtend = fhMeetingDepartmentRepository.findIsAttendByMeetingId(meetingId, departmentId);
        if (isAtend == MeetingJionPerson.unconfirmed.getIndex())
            return fhMeetingDepartmentRepository.updateDepartmentAbsent(meetingId, departmentId, absentReason);
        else if (isAtend == (byte)MeetingJionPerson.confirmed.getIndex())
            throw new BizException("会议id为"+meetingId+"的会议已经确定参加。");
        else if (isAtend == (byte)MeetingJionPerson.absent.getIndex())
            throw new BizException("会议id为"+meetingId+"的会议已经确定无法参加，请勿重复操作。");
        else
            throw new BizException("会议id为"+meetingId+"的会议的参会情况isAtend字段状态异常，请联系数据库管理员。");
    }

    public FhMeetingDetailVO meetingDetail(Integer meetingId) {
//        return fhMeetingInitiateRepository.findByMeetingId(meetingId);
        return fhMeetingDetailVORepository.findByMeetingId(meetingId);
    }

    public Map signInList(Integer meetingId, Integer ryId) {
        Map result = new HashMap();

        List<FhMeetingPerson> personList = fhMeetingPersonRepository.findByMeetingIdAndInitiatePersonId(meetingId, ryId);
        // 会议发起人列表是否为空，人员是否为发起人
        if (personList == null && personList.size() == 0) {
            result.put("canModify", false);
            List<FhMeetingPerson> fhMeetingPersonList = new ArrayList<>();
            List<Integer> departmentId = fhMeetingPersonRepository.findDepartmentIdByPeopleId(ryId);
            fhMeetingPersonList = fhMeetingPersonRepository.findByMeetingIdAndOtherPersonId(meetingId, departmentId.get(0));
            result.put("fhMeetingPersonList", fhMeetingPersonList);
        } else {
            result.put("canModify", true);
            result.put("fhMeetingPersonList", personList);
        }
        return result;
    }

    public Integer signInMeeting(Integer meetingId, Integer meetingPersonPk, Byte isRegister, String registerDateStr) throws ParseException {
        Integer result = null;
        Date registerDate = new Date();
        if (registerDateStr != null && !"".equals(registerDateStr.trim())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            registerDate = sdf.parse(registerDateStr);
        }
        if ((int) isRegister != MeetingJionPerson.unsignin.getIndex() && (int) isRegister != MeetingJionPerson.singin.getIndex()) {
            throw new BizException("签到数字错误，字段isRegister应该为 30（未签到）或31（已签到）中的一个");
        }
        result = fhMeetingPersonRepository.signInPerson(meetingId, meetingPersonPk, isRegister, registerDate);
        return result;
    }

    public FhMeetingRecordVO meetingRecord(Integer typeId, FhMeetingRecordVO fhMeetingRecordVO) throws ParseException {
        FhMeetingInitiate fhMeetingInitiate = fhMeetingInitiateRepository.findOne(fhMeetingRecordVO.getMeetingId());
        if (typeId == 1) { // 获取
            BeanUtils.copyProperties(fhMeetingInitiate, fhMeetingRecordVO);
            return fhMeetingRecordVO;
        } else if (typeId == 2) {   // 设置
            FhMeetingRecordVO fhMeetingRecordVONew = null;
            BeanUtils.copyProperties(fhMeetingRecordVO, fhMeetingInitiate);
            fhMeetingInitiateRepository.save(fhMeetingInitiate);
            if (fhMeetingInitiate.getIsContinue() == 1) {
                List<Map> fhMeetingDepartmentList = JSON.parseArray(fhMeetingRecordVO.getDepartmentString(), Map.class);
                if (fhMeetingDepartmentList != null && fhMeetingDepartmentList.size() != 0) {
                    List<FhMeetingContinue> fhMeetingContinueList = new ArrayList<>();
                    for (Map map : fhMeetingDepartmentList) {
                        FhMeetingContinue fhMeetingContinue = new FhMeetingContinue();
                        fhMeetingContinue.setMeetingId(fhMeetingRecordVO.getMeetingId());
                        fhMeetingContinue.setMeetingDepartmentId(Integer.parseInt(map.get("departmentId").toString()));
                        fhMeetingContinue.setMeetingDepartmentName((String) map.get("departmentName"));
                        if (map.get("confirmDate") != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = sdf.parse(map.get("confirmDate").toString());
                            fhMeetingContinue.setConfirmDate(date);
                        }
                        fhMeetingContinueList.add(fhMeetingContinue);
                    }
                    fhMeetingContinueRepository.save(fhMeetingContinueList);
                }

            }
            return fhMeetingRecordVO;
        }
        return null;
    }

    public Set<Map> meetingRecordDepartment(Integer meetingId) {
        return fhMeetingDepartmentRepository.findIdAndNameByMeetingId(meetingId);
    }

    public FhMeetingProcessVO meetingProcess(Integer typeId, FhMeetingProcessVO fhMeetingProcessVO) {
        FhMeetingInitiate fhMeetingInitiate = fhMeetingInitiateRepository.findOne(fhMeetingProcessVO.getMeetingId());
        if (typeId == 1) { // 获取
            BeanUtils.copyProperties(fhMeetingInitiate, fhMeetingProcessVO);
            return fhMeetingProcessVO;
        } else if (typeId == 2) {   // 设置
            FhMeetingRecordVO fhMeetingRecordVONew = null;
            BeanUtils.copyProperties(fhMeetingProcessVO, fhMeetingInitiate);
            fhMeetingInitiateRepository.save(fhMeetingInitiate);
            return fhMeetingProcessVO;
        }
        return null;
    }

    public List<FhMeetingContinueDetailVO> continueCondition(Integer meetingId) {
        List<FhMeetingContinue> fhMeetingContinueList = fhMeetingContinueRepository.findByMeetingId(meetingId);
        List<Map> fhMeetingContinueConfirmList = fhMeetingContinueConfirmRepository.findByMeetingId(meetingId);

        List<FhMeetingContinueDetailVO> result = new ArrayList<>();
        for (FhMeetingContinue fhMeetingContinue : fhMeetingContinueList) {
            FhMeetingContinueDetailVO item = new FhMeetingContinueDetailVO();
            BeanUtils.copyProperties(fhMeetingContinue, item);
            for (Map map : fhMeetingContinueConfirmList) {
                if ((int) map.get("continueId") == fhMeetingContinue.getContinueId()) {
                    item.setContinueDate((Date) map.get("confirmDate"));
                    item.setContinuePersonId((int) map.get("continuePersonId"));
                    item.setContinuePersonName(map.get("continuePersonName").toString());
                    item.setContinueContent(map.get("continueContent").toString());
                    item.setConfirmId((int) map.get("confirmId"));
                }
            }
            result.add(item);
        }
        return result;
    }

    public FhContinueRemind continueConditionWarn(FhContinueRemindVO fhContinueRemindVO) {
        FhContinueRemind fhContinueRemind = new FhContinueRemind();
        BeanUtils.copyProperties(fhContinueRemindVO, fhContinueRemind);
        fhContinueRemind.setRemindDate(new Date());
        return fhContinueRemindRepository.save(fhContinueRemind);
    }

    public FhMeetingContinueConfirmVO continueConditionFeedBack(FhMeetingContinueConfirmVO fhMeetingContinueConfirmVO) {
        FhMeetingContinueConfirm fhMeetingContinueConfirm = new FhMeetingContinueConfirm();
        Date now = new Date();
        fhMeetingContinueConfirmVO.setContinueDate(now);
        BeanUtils.copyProperties(fhMeetingContinueConfirmVO, fhMeetingContinueConfirm);
        fhMeetingContinueConfirmRepository.save(fhMeetingContinueConfirm);
        return fhMeetingContinueConfirmVO;
    }

    public List<FhMeetingContinueConfirmVO> continueConditionFeedBackList(Integer meetingId, Integer continueId) {
//        List<FhMeetingContinueConfirm> fhMeetingContinueConfirmList = fhMeetingContinueConfirmRepository.findByMeetingIdAndContinueId(meetingId, continueId);
//        List<FhMeetingContinueConfirmVO> fhMeetingContinueConfirmVOList = new ArrayList<>();
//        BeanUtils.copyProperties(fhMeetingContinueConfirmList,fhMeetingContinueConfirmVOList);
//        return fhMeetingContinueConfirmVOList;
        return fhMeetingContinueConfirmRepository.findByMeetingIdAndContinueId(meetingId, continueId);
    }

    public FhMeetingInitiate meetingComplete(Integer meetingId){
        FhMeetingInitiate fhMeetingInitiate = fhMeetingInitiateRepository.findByMeetingId(meetingId);
        if (fhMeetingInitiate.getIsAccomplish() == (byte)1){
            throw new BizException(90040001, "会议id为"+meetingId+"的会议已经完成", fhMeetingInitiate);
        }else {
            fhMeetingInitiate.setIsAccomplish((byte)1);
        }
        return fhMeetingInitiate;
    }
}
