package com.xinfang.taskdistribute.service;

import com.alibaba.fastjson.JSON;
import com.github.wenhao.jpa.Sorts;
import com.xinfang.Exception.BizException;
import com.xinfang.VO.LogInInfo;
import com.xinfang.dao.UserRepository;
import com.xinfang.taskdistribute.VO.*;
import com.xinfang.taskdistribute.dao.*;
import com.xinfang.taskdistribute.enums.DayWeekMonthEnum;
import com.xinfang.taskdistribute.enums.TaskIsFlag;
import com.xinfang.taskdistribute.enums.TaskLevelId;
import com.xinfang.taskdistribute.enums.TaskTypeId;
import com.xinfang.taskdistribute.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.github.wenhao.jpa.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;

import java.util.*;


/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 15:59
 **/

@Service
@Transactional
public class TaskDistributeService {

    @Autowired
    RwTaskRepository rwTaskRepository;

    @Autowired
    RwTaskPersonRepository rwTaskPersonRepository;

    @Autowired
    RwTaskZfPersonRepository rwTaskZfPersonRepository;

    @Autowired
    RwTaskListVORepository rwTaskListVORepository;

    @Autowired
    RwTaskGroupRepository rwTaskGroupRepository;

    @Autowired
    RwTaskPersonGroupRepository rwTaskPersonGroupRepository;

    @Autowired
    UserRepository userRepository;


    public Object addTaskDistribute(Integer requestType, RwTaskInputVO rwTaskInputVO, Set<Integer> taskReceiverSet) {
        if (requestType == 3) { // 删除任务
            Integer taskId = rwTaskInputVO.getTaskId();
            Integer createPersonId = rwTaskRepository.findCreatePersonIdByTaskId(taskId); // 任务的创建人id
            if (createPersonId != rwTaskInputVO.getCreatePersonId()) {
                throw new BizException("删除失败，任务id=" + taskId + "的创建者id为" + createPersonId + "，而当前录入的创建人id为" + rwTaskInputVO.getCreatePersonId());
            }
            Integer a = rwTaskRepository.deleteByTaskId(taskId);
            Integer b = rwTaskPersonRepository.deleteByTaskId(taskId);
            if (a != 0 && b != 0) {
                Map map = new HashMap();
                map.put("taskId", rwTaskInputVO.getTaskId());
                return map;
            } else {
                throw new BizException("删除任务失败");
            }
        }

        if (requestType == 4){  // 根据taskId获取录入的任务
            if (rwTaskInputVO.getTaskId() == null){
                throw new BizException("任务id--taskId为null,要获取任务请录入相应任务id");
            }
            return rwTaskRepository.findOne(rwTaskInputVO.getTaskId());  // 可以添加发起人条件
        }

        if (requestType == 1) {
            rwTaskInputVO.setTaskId(null);
        } else if (requestType != 1 && requestType != 2 && requestType != 3 && requestType != 4) {
            throw new BizException("requestType=" + requestType + "，requestType必须为1、2、3、4中的一个");
        }
        RwTask rwTask = new RwTask();
        BeanUtils.copyProperties(rwTaskInputVO, rwTask);
        if ((int) rwTaskInputVO.getIsWarn() != 1) {
            rwTask.setSmsContent(null);
        }
        rwTask.setCreateDate(new Date());
        rwTask.setIsFlag(0);

        if (taskReceiverSet == null || taskReceiverSet.size() == 0) {
            throw new BizException("任务接收人为空，需设置任务接收人");
        }

        rwTask = rwTaskRepository.save(rwTask);

        List<RwTaskPerson> rwTaskPersonList = new ArrayList<>();
        if (requestType == 1) { // 添加
        	rwTaskPersonList = addRwTaskPersonList(taskReceiverSet, rwTask.getTaskId());
		}else if (requestType == 2) { //编辑
            Set<RwTaskPerson>  oldRwTaskPersonList = rwTaskPersonRepository.findByTaskId(rwTask.getTaskId());

//            Integer deleteTag = rwTaskPersonRepository.deleteByTaskId(rwTask.getTaskId()); // 删除原有的数据
//            if (deleteTag == null || deleteTag == 0){
//                throw new BizException("修改失败，原因：删除原有数据失败。");
//            }

            if (oldRwTaskPersonList == null || oldRwTaskPersonList.size() == 0){
                rwTaskPersonList = addRwTaskPersonList(taskReceiverSet, rwTask.getTaskId());
            }else {
                Set<Integer> newTaskReceiverSet = new HashSet<>();
                
                for (Integer taskReceiverId: taskReceiverSet){
                	Boolean flag = false;
                    for (RwTaskPerson rwTaskPerson: oldRwTaskPersonList){
                        if (rwTaskPerson.getRyId() == taskReceiverId){
                            rwTaskPersonList.add(rwTaskPerson);
                            System.err.println("已有："+taskReceiverId);
                            flag = true;
                            break;
                        }
                    }
                    
                    if (!flag){
                        System.err.println("未有："+taskReceiverId);
                        newTaskReceiverSet.add(taskReceiverId); // 新增人员
                    }
                }
                for (RwTaskPerson rwTaskPerson: oldRwTaskPersonList){
                	if (!rwTaskPersonList.contains(rwTaskPerson)) {   // 不包含删除
                		rwTaskPersonRepository.delete(rwTaskPerson); // 删除
					}
                }
//                rwTaskPersonList = rwTaskPersonRepository.save(rwTaskPersonList); // 保存已有数据
                List<RwTaskPerson> newRwTaskPersonList = addRwTaskPersonList(newTaskReceiverSet, rwTask.getTaskId());
                newRwTaskPersonList = rwTaskPersonRepository.save(newRwTaskPersonList); // 保存新增
                if (newRwTaskPersonList != null && newRwTaskPersonList.size() > 0) {
                	rwTaskPersonList.addAll(newRwTaskPersonList); // 已有+新增
				}
            }
            return rwTaskPersonList;
		}
        rwTaskPersonRepository.save(rwTaskPersonList);

        return rwTask;
    }

    /**
     * 根据人员id和任务id获取任务接收人员信息集合
     *
     * @param taskReceiverSet
     * @param taskId
     * @return
     */
    private List<RwTaskPerson> addRwTaskPersonList(Set<Integer> taskReceiverSet, Integer taskId) {
        List<RwTaskPerson> rwTaskPersonList = new ArrayList<>();
        for (Integer ryId : taskReceiverSet) {
            LogInInfo logInInfo = userRepository.findLogInInfoByRyId(ryId);
            if (logInInfo == null) {
                throw new BizException("发起任务失败！id为" + ryId + "的任务接收人不是系统中的人员。");
            }
            RwTaskPerson rwTaskPerson = new RwTaskPerson();
            rwTaskPerson.setTaskId(taskId);
            rwTaskPerson.setRyId(logInInfo.getRyId());
            rwTaskPerson.setRyMc(logInInfo.getUserName());
            rwTaskPerson.setDwId(logInInfo.getDwId());
            rwTaskPerson.setDwMc(logInInfo.getDwMc());
            rwTaskPerson.setQxsId(logInInfo.getQxsId());
            rwTaskPerson.setQxsMc(logInInfo.getQxsMc());
            rwTaskPerson.setKsId(logInInfo.getKsId());
            rwTaskPerson.setKsMc(logInInfo.getKsMc());
            rwTaskPerson.setKszwId(logInInfo.getZwId());
            rwTaskPerson.setKszwMc(logInInfo.getZwMc());
            rwTaskPerson.setTaskFlag(0); // 未操作
            rwTaskPerson.setRyImg(logInInfo.getRyImg());
            rwTaskPerson.setTelephone(logInInfo.getTelephone());
            rwTaskPersonList.add(rwTaskPerson);
        }
        return rwTaskPersonList;
    }

    public List<RwTaskGroup> getAllTaskGroup() {
        return rwTaskGroupRepository.findAll();
    }

    public RwTaskGroup editTaskGroup(Integer requestType, Integer groupId, String groupName, String groupDescribe,
                                     Integer ryRequestType, Set<Integer> rySet) {

        List<RwTaskPersonGroup> rwTaskPersonGroupList = new ArrayList<>(); // 任务组下人员集合
        switch (requestType) {
            case 1: // 1新增任务组
                RwTaskGroup rwTaskGroup = new RwTaskGroup();
                rwTaskGroup.setGroupName(groupName);
                rwTaskGroup.setGroupDescribe(groupDescribe);
                rwTaskGroup.setIsFlag((byte) 1); // 启用
                rwTaskGroup = rwTaskGroupRepository.save(rwTaskGroup);

                if (rySet != null && rySet.size() > 0) {
                    rwTaskPersonGroupList = addRwTaskPersonGroups(rySet, rwTaskGroup.getGroupId(), rwTaskGroup.getGroupName());
                    rwTaskPersonGroupRepository.save(rwTaskPersonGroupList);
                }
                return rwTaskGroup;
            case 2: // 2修改任务组
                if (ryRequestType == null || (ryRequestType != 1 && ryRequestType != 2)) {
                    throw new BizException("ryRequestType=" + ryRequestType + ",应该为1或2中的一个");
                }

                RwTaskGroup rwTaskGroupModify = rwTaskGroupRepository.findOne(groupId);
                if (rwTaskGroupModify == null) {
                    throw new BizException("未找到groupId=" + groupId + "的任务组，修改失败");
                }
                if (groupName != null) {  //  && !"".equals(groupName.trim())
                    rwTaskGroupModify.setGroupName(groupName);
                }
                if (groupDescribe != null) {  //  && !"".equals(groupDescribe.trim())
                    rwTaskGroupModify.setGroupDescribe(groupDescribe);
                }

                if (rySet != null && rySet.size() > 0) {
                    if (ryRequestType == 1) {  // 1表示添加人员
                        Set<Integer> alreadyRyIdSet = rwTaskPersonGroupRepository.findByGroupIdAndRyIds(groupId, rySet);
                        if (alreadyRyIdSet != null && alreadyRyIdSet.size() > 0) {
                            throw new BizException("添加人员失败，人员id" + alreadyRyIdSet + "已经存在");
                        }
                        RwTaskGroup rwTaskGroup2 = rwTaskGroupRepository.findOne(groupId);
                        rwTaskPersonGroupList = addRwTaskPersonGroups(rySet, groupId, rwTaskGroup2.getGroupName());
                        rwTaskPersonGroupRepository.save(rwTaskPersonGroupList);
                    } else if (ryRequestType == 2) { // 2表示删除人员
                        Integer deleteMessage = rwTaskPersonGroupRepository.deleteByGroupIdAndRyIds(groupId, rySet);
                        if (deleteMessage == 0) {
                            throw new BizException("删除失败，人员id" + rySet + "被删除或者根本不存在");
                        }
                    }
                }
                return rwTaskGroupModify;
            case 3: // 3删除任务组
                rwTaskGroupRepository.delete(groupId);
                rwTaskPersonGroupRepository.deleteByGroupId(groupId);
                break;
            default:
                throw new BizException("requestType=" + requestType + ",应该为1、2、3中任意一个");
        }
        return null;
    }

    /**
     * 添加任务组下的人员
     *
     * @param rySet
     * @param groupId
     * @param groupName
     * @return
     */
    private List<RwTaskPersonGroup> addRwTaskPersonGroups(Set<Integer> rySet, Integer groupId, String groupName) {
        List<RwTaskPersonGroup> rwTaskPersonGroupList = new ArrayList<>();
        if (rySet != null && rySet.size() > 0) {
            for (Integer ryId : rySet) {
                RwTaskPersonGroup rwTaskPersonGroup = new RwTaskPersonGroup();
                LogInInfo logInInfo = userRepository.findLogInInfoByRyId(ryId);
                if (logInInfo == null) {
                    throw new BizException("系统中不存在id=" + ryId + "的人员");
                }
                rwTaskPersonGroup.setGroupId(groupId);
                rwTaskPersonGroup.setGroupName(groupName);
                rwTaskPersonGroup.setRyId(ryId);
                rwTaskPersonGroup.setRyName(logInInfo.getUserName());
                rwTaskPersonGroup.setRyImg(logInInfo.getRyImg());
                rwTaskPersonGroup.setQxsId(logInInfo.getQxsId());
                rwTaskPersonGroup.setQxsMc(logInInfo.getQxsMc());
                rwTaskPersonGroup.setDwId(logInInfo.getDwId());
                rwTaskPersonGroup.setDwMc(logInInfo.getDwMc());
                rwTaskPersonGroup.setKsId(logInInfo.getKsId());
                rwTaskPersonGroup.setKsMc(logInInfo.getKsMc());
                rwTaskPersonGroup.setKszwId(logInInfo.getZwId());
                rwTaskPersonGroup.setKszwMc(logInInfo.getZwMc());
                rwTaskPersonGroup.setTelephone(logInInfo.getTelephone()); // 新增电话
                rwTaskPersonGroupList.add(rwTaskPersonGroup);
            }
        }
        return rwTaskPersonGroupList;
    }

    public Page<RwTaskListVO> taskDistributeList(SearchList searchList) {
//        Specification<RwTaskListVO> specification = new Specification<RwTaskListVO>() {
//            @Override
//            public Predicate toPredicate(Root<RwTaskListVO> root,
//                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Predicate p1 = null;
//                cb.and(p1);
//
//                return query.getRestriction();
//            }
//        };
        List<Integer> taskIdList = rwTaskPersonRepository.findTaskIdByRyId(searchList.getRyId());
        List<Integer> zfTaskIdList = rwTaskZfPersonRepository.findTaskIdByRyId(searchList.getRyId());

        Specification<RwTaskListVO> specification = Specifications.<RwTaskListVO>and()
                .predicate(Objects.nonNull(searchList.getRyId()), (root, query, cb) -> { // 登录人id
                    Predicate p1 = cb.equal(root.get("createPersonId"), searchList.getRyId()); // 任务发起人，即派发人

                    Predicate p2 = root.get("taskId").as(Integer.class).in(taskIdList);  // 任务接收人

                    Predicate p3 = null ; // 转发
                    if (zfTaskIdList != null && zfTaskIdList.size() > 0) {
                        p3 = root.get("taskId").as(Integer.class).in(zfTaskIdList);
                    }

                    if (searchList.getTaskCategory() != null){
                        // 任务分类，1我派发的任务、2我接受的任务、3我转发的任务、4未处理的任务、5进行中的任务、6历史汇总、7任务汇总
                        switch (searchList.getTaskCategory()){
                            case 1:
                                return p1;
                            case 2:
                                return p2;
                            case 3:
                                if (p3 != null)
                                    return p3;
                                else
                                    throw new BizException("人员id="+searchList.getRyId()+"的人员不存在‘我转发的任务’");
                            case 4:
                                Set<Integer> taskFlag = new HashSet<>();
                                taskFlag.add(TaskIsFlag.undo.getIndex()); // 未处理
                                List<Integer> taskIdUndoList = rwTaskPersonRepository.findTaskIdByRyIdAndTaskFlag(searchList.getRyId(), taskFlag);
                                if (taskIdUndoList == null || taskIdUndoList.size()==0){
                                    throw new BizException("人员id为"+searchList.getRyId()+"的人员没有‘未处理’的任务");
                                }
                                return root.get("taskId").as(Integer.class).in(taskIdUndoList);
                            case 5:
                                Set<Integer> taskFlagDoing = new HashSet<>();
                                taskFlagDoing.add(TaskIsFlag.reveive.getIndex());
                                taskFlagDoing.add(TaskIsFlag.transfer.getIndex());
                                List<Integer> taskIdDoingList = rwTaskPersonRepository.findTaskIdByRyIdAndTaskFlag(searchList.getRyId(), taskFlagDoing);
                                if (taskIdDoingList == null || taskIdDoingList.size()==0){
                                    throw new BizException("人员id为"+searchList.getRyId()+"的人员没有‘进行中’的任务");
                                }
                                return root.get("taskId").as(Integer.class).in(taskIdDoingList);
                            case 6:
                                Set<Integer> taskFlagDid = new HashSet<>();  // 已完成，历史汇总
                                taskFlagDid.add(TaskIsFlag.did.getIndex());
                                List<Integer> taskIdDidList = rwTaskPersonRepository.findTaskIdByRyIdAndTaskFlag(searchList.getRyId(), taskFlagDid);
                                if (taskIdDidList == null || taskIdDidList.size()==0){
                                    throw new BizException("人员id为"+searchList.getRyId()+"的人员没有‘历史汇总’的任务，即已完成的任务");
                                }
                                return root.get("taskId").as(Integer.class).in(taskIdDidList);
                            case 7:
                                return cb.equal(root.get("taskTypeId").as(Integer.class), TaskTypeId.task.getIndex());
                            default: throw new BizException("taskCategory的值应该为null或1-7中的任意数字，但当前taskCategory="+searchList.getTaskCategory());
                        }
                    }
                    if (p3 != null) {
                        query.where(cb.or(p1, p2, p3));
                    } else {
                        query.where(cb.or(p1, p2));
                    }

                    return query.getRestriction();
                })
                .eq(Objects.nonNull(searchList.getIsFlag()), "isFlag", searchList.getIsFlag()) // 任务状态
                .eq(Objects.nonNull(searchList.getTaskTypeId()), "taskTypeId", searchList.getTaskTypeId()) // 任务类型
                .eq(Objects.nonNull(searchList.getTaskLevelId()), "taskLevelId", searchList.getTaskLevelId()) // 任务级别
                .predicate(StringUtils.isNotEmpty(searchList.getKeyword()), (root, query, cb) -> { // 关键字搜索
                    Predicate a1 = cb.like(root.get("createDate").as(String.class), "%" + searchList.getKeyword() + "%");
                    Predicate a2 = cb.like(root.get("createPersonName").as(String.class), "%" + searchList.getKeyword() + "%");
                    Predicate a3 = cb.like(root.get("receiverPersonNameStr").as(String.class), "%" + searchList.getKeyword() + "%");
                    Predicate a4 = cb.like(root.get("transferPersonNameStr").as(String.class), "%" + searchList.getKeyword() + "%");
                    Predicate a5 = cb.like(root.get("taskTitle").as(String.class), "%" + searchList.getKeyword() + "%");
                    Predicate a6 = cb.like(root.get("taskContent").as(String.class), "%" + searchList.getKeyword() + "%");
                    Predicate a7 = cb.like(root.get("taskTypeName").as(String.class), "%" + searchList.getKeyword() + "%");
                    Predicate a8 = cb.like(root.get("taskLevelName").as(String.class), "%" + searchList.getKeyword() + "%");
                    Set<Integer> isFlagSet = new HashSet<>();
                    if (TaskIsFlag.undo.getName().contains(searchList.getKeyword())) {  // 未处理
                        isFlagSet.add(TaskIsFlag.undo.getIndex());
                    }
                    if (TaskIsFlag.reveive.getName().contains(searchList.getKeyword())) {  // 接收
                        isFlagSet.add(TaskIsFlag.reveive.getIndex());
                    }
                    if (TaskIsFlag.transfer.getName().contains(searchList.getKeyword())) {  // 转发
                        isFlagSet.add(TaskIsFlag.transfer.getIndex());
                    }
                    if (TaskIsFlag.sendback.getName().contains(searchList.getKeyword())) {  // 退回
                        isFlagSet.add(TaskIsFlag.sendback.getIndex());
                    }
                    if (TaskIsFlag.did.getName().contains(searchList.getKeyword())) {  // 完结
                        isFlagSet.add(TaskIsFlag.did.getIndex());
                    }
                    Predicate a9 = null;
                    if (isFlagSet != null && isFlagSet.size() > 0) {
                        a9 = root.get("isFlag").in(isFlagSet);
                    }
                    if (a9 != null) {
                        query.where(cb.or(a1, a2, a3, a4, a5, a6, a7, a8, a9));
                    } else {
                        query.where(cb.or(a1, a2, a3, a4, a5, a6, a7, a8));
                    }

                    return query.getRestriction();
                })
                .predicate(Objects.nonNull(searchList.getStartTime()), (root, query, cb) -> {
                    return cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class), searchList.getStartTime());
                })
                .predicate(Objects.nonNull(searchList.getEndTime()), (root, query, cb) -> {
                    return cb.lessThanOrEqualTo(root.get("createDate").as(Date.class), searchList.getEndTime());
                })
                .build();

        Sort sort = Sorts.builder()
                .desc("isTop")
                .build();


        return rwTaskListVORepository.findAll(specification,
                new PageRequest(searchList.getStartPage(), searchList.getPageSize(), sort));
    }

    public Object taskReceive(Integer taskId, Integer receiverId, Integer requestType, Integer taskOperate,
                              String content, String attachment) {
        RwTaskListVO rwTaskListVO = rwTaskListVORepository.findOne(taskId);

        if (rwTaskListVO == null) {
            throw new BizException("任务id=" + taskId + "的任务不存在");
        }
        List<Integer> receiverIdSet = JSON.parseArray("[" + rwTaskListVO.getReceiverPersonIdStr() + "]", Integer.class);
        if (!receiverIdSet.contains(receiverId)) {
            throw new BizException("任务id=" + taskId + "的任务的接收人为" + receiverIdSet + "，不包含人员id=" + receiverId + "的人员");
        }
        if (requestType == null || (requestType != 1 && requestType != 2)) {
            throw new BizException("requestType=" + requestType + "，必须为1或2中的一个");
        }
        List<RwTaskPerson> rwTaskPersonList = rwTaskPersonRepository.findByTaskIdAndRyId(taskId, receiverId);
        if (rwTaskPersonList == null || rwTaskPersonList.size() != 1) {
            throw new BizException("接受任务人员表中的taskId=" + taskId + "并且receiverId=" + receiverId + "的人员个数不为1。");
        }

        if (requestType == 1) { // 获取
            LogInInfo receiver = userRepository.findLogInInfoByRyId(receiverId);    // 任务接收人信息
            LogInInfo creater = userRepository.findLogInInfoByRyId(rwTaskListVO.getCreatePersonId()); // 派发人信息

            RwTaskReceiverVO rwTaskReceiverVO = new RwTaskReceiverVO();
            BeanUtils.copyProperties(receiver, rwTaskReceiverVO);
            rwTaskReceiverVO.setRyMc(receiver.getUserName()); // 人员名字，因为和ry_mc不同，所有属性不能copy
            BeanUtils.copyProperties(rwTaskListVO, rwTaskReceiverVO);
            rwTaskReceiverVO.setCreatePersonMessage(creater);
            rwTaskReceiverVO.setReceiverPersonMessage(receiver);
            return rwTaskReceiverVO;
//            return taskDetail(taskId);

        } else if (requestType == 2) { // 设置
            RwTaskPerson rwTaskPerson = rwTaskPersonList.get(0);
            // 任务类型判断, ‘任务’ 只能为0-4，‘非任务’只能为0或4。。。。。。

            rwTaskPerson.setTaskFlag(taskOperate); // 设置人员项目状态，1接收

            if (taskOperate == TaskIsFlag.reveive.getIndex()) {  // 1接收
                rwTaskPerson.setTaskReceiveDate(new Date()); // 任务接收时间
                rwTaskPerson.setTaskDate(new Date());
            }
            if (taskOperate == TaskIsFlag.transfer.getIndex()) {  // 2 转发
                List<Integer> ryIds = JSON.parseArray(content, Integer.class);
                Set<Integer> zfRyIdSet = new HashSet(ryIds); // 转发人员
                List<RwTaskPerson> rwTaskPersonList2 = addRwTaskPersonList(zfRyIdSet, taskId);
                rwTaskPersonList2 = rwTaskPersonRepository.save(rwTaskPersonList2); // 转发人员录入到任务接收人员表

                List<RwTaskZfPerson> rwTaskZfPersonList2 = new ArrayList<>();
                for (RwTaskPerson item : rwTaskPersonList2) {
                    RwTaskZfPerson rwTaskZfPerson = new RwTaskZfPerson();
                    BeanUtils.copyProperties(item, rwTaskZfPerson);
                    rwTaskZfPerson.setTaskZfPersonId(item.getTaskPersonId());
                    rwTaskZfPersonList2.add(rwTaskZfPerson);
                }
                return rwTaskZfPersonRepository.save(rwTaskZfPersonList2);  // 转发人员录入到任务转发接收人员表
            }
            if (taskOperate == TaskIsFlag.sendback.getIndex()) { // 3 退回
                rwTaskPerson.setTaskReturnContent(content);
                rwTaskPerson.setTaskReturnDate(new Date());
            }
            if (taskOperate == TaskIsFlag.did.getIndex()) {  // 4 完成
                rwTaskPerson.setTaskContent(content);
                rwTaskPerson.setTaskFile(attachment);
                rwTaskPerson.setTaskDate(new Date());
                if (rwTaskListVO.getTaskTypeId() != TaskTypeId.task.getIndex() ){
                    rwTaskPerson.setTaskReceiveDate(new Date());
                }
            }
            RwTaskPerson rwTaskPersonResult = rwTaskPersonRepository.save(rwTaskPerson);

            //  ======================  更新任务表中的任务状态 ===================
            RwTask rwTask = rwTaskRepository.findOne(taskId);
            if (taskOperate == TaskIsFlag.did.getIndex()){ // 完成
                List<Integer> taskFlags = rwTaskPersonRepository.findTaskFlagByTaskId(taskId);
                if (taskFlags.size()==1 && taskFlags.get(0)==TaskIsFlag.did.getIndex()){
                    rwTask.setIsFlag(taskOperate);
                }
            }else {
                rwTask.setIsFlag(taskOperate);
            }
            rwTaskRepository.save(rwTask);

            return rwTaskPersonResult;
        }
        return null;
    }

    private RwTaskBaseVO getRwTaskBaseVO(Integer taskId, Set<RwTaskPerson> receiverPersonSet, LogInInfo creater) {
        RwTask rwTask = rwTaskRepository.findOne(taskId);

        RwTaskBaseVO rwTaskBaseVO = new RwTaskBaseVO();  // 左侧任务基本情况
        BeanUtils.copyProperties(rwTask, rwTaskBaseVO);
        rwTaskBaseVO.setReceiverPersonSet(receiverPersonSet);
        rwTaskBaseVO.setCreatePerson(creater);
        Integer timeLimit = Math.toIntExact((rwTask.getFeedbackDate().getTime() - rwTask.getCreateDate().getTime()) / (24 * 60 * 60 * 1000));
        rwTaskBaseVO.setTimeLimit(timeLimit);    // 时限
        return rwTaskBaseVO;
    }

    public Object taskDetail(Integer taskId) {

        RwTaskListVO rwTaskListVO = rwTaskListVORepository.findOne(taskId);
        LogInInfo creater = userRepository.findLogInInfoByRyId(rwTaskListVO.getCreatePersonId()); // 派发人信息
        Set<RwTaskPerson> rwTaskPersonList = rwTaskPersonRepository.findByTaskId(taskId);
        RwTaskBaseVO rwTaskBaseVO = getRwTaskBaseVO(taskId, rwTaskPersonList, creater); // 基础信息

        return rwTaskBaseVO;
    }

    /**
     * 任务统计
     * @param createPersonIdSet 发起人id集合
     * @param startTime
     * @param endTime
     * @param taskCondition 状况，发送任务、收到任务、进行中的任务等
     * @param taskLevel 普通、重要、特别重要
     * @param taskDegree 任务完成度
     * @return
     */
    public Object taskStatistics(Set<Integer> createPersonIdSet,Date startTime, Date endTime,
                                 Integer taskSendOrReceive, Integer taskCondition) {
        if (startTime != null || endTime != null){
            return taskStatisticsByTime(createPersonIdSet, taskSendOrReceive, startTime, endTime);
        }else {
            if (taskCondition == null) {
                throw new BizException("taskCondition为" + taskCondition + ",必须为1、2、3中的一个，或者‘时间筛选’不为空");
            }

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.set(Calendar.HOUR_OF_DAY, 0);
            calendarStart.set(Calendar.MINUTE, 0);
            calendarStart.set(Calendar.SECOND, 0);

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
            calendarEnd.set(Calendar.MINUTE, 59);
            calendarEnd.set(Calendar.SECOND, 59);

            if (taskCondition == DayWeekMonthEnum.day.getIndex()) { // 当天
                startTime = calendarStart.getTime();
                endTime = calendarEnd.getTime();
            } else if (taskCondition == DayWeekMonthEnum.week.getIndex()) { // 当周
                calendarStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 本周一
                calendarEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
                calendarEnd.add(Calendar.WEEK_OF_YEAR, 1);  // 增加一个星期，才是我们中国人理解的本周日的日期
                startTime = calendarStart.getTime();
                endTime = calendarEnd.getTime();
            } else if (taskCondition == DayWeekMonthEnum.month.getIndex()) { // 当月
                calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DATE)); // 本月最小
                calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DATE));
                startTime = calendarStart.getTime();
                endTime = calendarEnd.getTime();
            }

//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.err.println(formatter.format(startTime));
//            System.err.println(formatter.format(endTime));

            return taskStatisticsByTime(createPersonIdSet, taskSendOrReceive, startTime, endTime);
        }
    }

    private RwTaskStatisticsVO taskStatisticsByTime(Set<Integer> createPersonIdSet,Integer taskSendOrReceive,
                                                    Date startTime, Date endTime){
        List<Map<String, Integer>> mapList = new ArrayList<>();
        Map taskCountMap = new HashMap();  // 任务类型
        Map notificationCountMap = new HashMap();  // 通知类型
        Map announcementCountMap = new HashMap();  // 公告类型
        Map shareCountMap = new HashMap();  // 共享类型
        Map othersCountMap = new HashMap();  // 其它类型
        mapList.add(taskCountMap);
        mapList.add(notificationCountMap);
        mapList.add(announcementCountMap);
        mapList.add(shareCountMap);
        mapList.add(othersCountMap);

        for (Map<String, Integer> map: mapList){
            map.put(TaskLevelId.common.toString(), 0);
            map.put(TaskLevelId.important.toString(), 0);
            map.put(TaskLevelId.veryimportant.toString(), 0);
        }

        Specification specification = rwTaskListVOSpecification(createPersonIdSet, taskSendOrReceive, startTime, endTime);

        List<RwTaskListVO> rwTaskListVOList = rwTaskListVORepository.findAll(specification);
        RwTaskStatisticsVO rwTaskStatisticsVO = new RwTaskStatisticsVO(0,0,0,0, 0);
        if (taskSendOrReceive == 1) {  // 发出的任务
            rwTaskStatisticsVO.setTaskSendCount(rwTaskListVOList.size()); // 发送任务
            rwTaskStatisticsVO.setTaskReceiveCount(null);
        }
        else if (taskSendOrReceive == 2) {  // 接收的任务
            rwTaskStatisticsVO.setTaskReceiveCount(rwTaskListVOList.size());
            rwTaskStatisticsVO.setTaskSendCount(null);
        }
        for (RwTaskListVO rwTaskListVO: rwTaskListVOList){
            List<Integer> taskFlagList = JSON.parseArray("["+rwTaskListVO.getTaskFlagStr()+"]", Integer.class); // 该任务的人员处理状态
//            for (Integer taskFlag: taskFlagList){
//                if (taskFlag != null && taskFlag != TaskIsFlag.undo.getIndex()){
//                    rwTaskStatisticsVO.setTaskReplyCount(rwTaskStatisticsVO.getTaskReplyCount()+1);  // 收到的回复，只要不为0，都表示回复了
//                }
//            }

            List<Integer> receiverIdList = JSON.parseArray("["+rwTaskListVO.getReceiverPersonIdStr()+"]", Integer.class);
//            for (Integer createrId: createPersonIdSet){
//                for (int i=0; i<receiverIdList.size(); i++){
//                    if (receiverIdList.get(i)==createrId){
//                        rwTaskStatisticsVO.setTaskReceiveCount(rwTaskStatisticsVO.getTaskReceiveCount()+1); // 收到的任务
//                        if (taskFlagList.get(i)==TaskIsFlag.undo.getIndex())
//                            rwTaskStatisticsVO.setTaskUncheckedCount(rwTaskStatisticsVO.getTaskUncheckedCount()+1); // 未查看
//                        if (taskFlagList.get(i)==TaskIsFlag.did.getIndex())
//                            rwTaskStatisticsVO.setTaskCompleteCount(rwTaskStatisticsVO.getTaskCompleteCount()+1);
//
//                    }
//                }
//            }

            if (rwTaskListVO.getIsFlag() == TaskIsFlag.reveive.getIndex() || rwTaskListVO.getIsFlag() == TaskIsFlag.transfer.getIndex())
                rwTaskStatisticsVO.setTaskDoingCount(rwTaskStatisticsVO.getTaskDoingCount()+1);  // 进行中的任务,登录人发起或接收,仅限‘任务’，未处理任务不算进行中

            //=========================  类型和级别统计 （包括当前用户发送的和接收的）===================================
            if (rwTaskListVO.getTaskTypeId() == TaskTypeId.task.getIndex()){
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.common.getIndex()){
                    taskCountMap.put(TaskLevelId.common.toString(), (int)taskCountMap.get(TaskLevelId.common.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.important.getIndex()){
                    taskCountMap.put(TaskLevelId.important.toString(), (int)taskCountMap.get(TaskLevelId.important.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.veryimportant.getIndex()){
                    taskCountMap.put(TaskLevelId.veryimportant.toString(), (int)taskCountMap.get(TaskLevelId.veryimportant.toString())+1);
                }
            }
            if (rwTaskListVO.getTaskTypeId() == TaskTypeId.notification.getIndex()){
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.common.getIndex()){
                    notificationCountMap.put(TaskLevelId.common.toString(), (int)notificationCountMap.get(TaskLevelId.common.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.important.getIndex()){
                    notificationCountMap.put(TaskLevelId.important.toString(), (int)notificationCountMap.get(TaskLevelId.important.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.veryimportant.getIndex()){
                    notificationCountMap.put(TaskLevelId.veryimportant.toString(), (int)notificationCountMap.get(TaskLevelId.veryimportant.toString())+1);
                }
            }

            if (rwTaskListVO.getTaskTypeId() == TaskTypeId.announcement.getIndex()){
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.common.getIndex()){
                    announcementCountMap.put(TaskLevelId.common.toString(), (int)announcementCountMap.get(TaskLevelId.common.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.important.getIndex()){
                    announcementCountMap.put(TaskLevelId.important.toString(), (int)announcementCountMap.get(TaskLevelId.important.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.veryimportant.getIndex()){
                    announcementCountMap.put(TaskLevelId.veryimportant.toString(), (int)announcementCountMap.get(TaskLevelId.veryimportant.toString())+1);
                }
            }

            if (rwTaskListVO.getTaskTypeId() == TaskTypeId.share.getIndex()){
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.common.getIndex()){
                    shareCountMap.put(TaskLevelId.common.toString(), (int)shareCountMap.get(TaskLevelId.common.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.important.getIndex()){
                    shareCountMap.put(TaskLevelId.important.toString(), (int)shareCountMap.get(TaskLevelId.important.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.veryimportant.getIndex()){
                    shareCountMap.put(TaskLevelId.veryimportant.toString(), (int)shareCountMap.get(TaskLevelId.veryimportant.toString())+1);
                }
            }

            if (rwTaskListVO.getTaskTypeId() == TaskTypeId.others.getIndex()){
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.common.getIndex()){
                    othersCountMap.put(TaskLevelId.common.toString(), (int)othersCountMap.get(TaskLevelId.common.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.important.getIndex()){
                    othersCountMap.put(TaskLevelId.important.toString(), (int)othersCountMap.get(TaskLevelId.important.toString())+1);
                }
                if (rwTaskListVO.getTaskLevelId() == TaskLevelId.veryimportant.getIndex()){
                    othersCountMap.put(TaskLevelId.veryimportant.toString(), (int)othersCountMap.get(TaskLevelId.veryimportant.toString())+1);
                }
            }
        }
        rwTaskStatisticsVO.setTaskCountMap(taskCountMap);
        rwTaskStatisticsVO.setNotificationCountMap(notificationCountMap);
        rwTaskStatisticsVO.setAnnouncementCountMap(announcementCountMap);
        rwTaskStatisticsVO.setShareCountMap(shareCountMap);
        rwTaskStatisticsVO.setOthersCountMap(othersCountMap);

        //=========================  完成任务统计 发起人和接收人====================================
//        Integer receiveCount = rwTaskStatisticsVO.getTaskReceiveCount();
//        Integer completeCount = rwTaskStatisticsVO.getTaskCompleteCount();
//        if (receiveCount == 0){
//            rwTaskStatisticsVO.setTaskComplteDegree(1);
//            rwTaskStatisticsVO.setTaskRemainCount(0); // 剩余任务
//        }else {
//            rwTaskStatisticsVO.setTaskComplteDegree(receiveCount/receiveCount);
//            rwTaskStatisticsVO.setTaskRemainCount(receiveCount - completeCount); // 剩余任务
//        }

        Integer completeCount = rwTaskStatisticsVO.getTaskCompleteCount();
        Integer taskTotalCount = 0;
        if (taskSendOrReceive == 1){  // 发出的
            taskTotalCount = rwTaskStatisticsVO.getTaskSendCount();
        }else if (taskSendOrReceive == 2){  // 接收的
            taskTotalCount = rwTaskStatisticsVO.getTaskReceiveCount();
        }

        if (taskTotalCount == 0){
            rwTaskStatisticsVO.setTaskComplteDegree(1);
            rwTaskStatisticsVO.setTaskRemainCount(0); // 剩余任务
        }else {
            rwTaskStatisticsVO.setTaskComplteDegree(completeCount/taskTotalCount);
            rwTaskStatisticsVO.setTaskRemainCount(taskTotalCount - completeCount); // 剩余任务
        }

        return rwTaskStatisticsVO;
    }

    private Specification rwTaskListVOSpecification(Set<Integer> createPersonIdSet, Integer taskSendOrReceive,
                                                    Date startTime, Date endTime) {

        List<Integer> taskIdList = rwTaskPersonRepository.findTaskIdByRyIdSet(createPersonIdSet);
        Specification<RwTaskListVO> specification = Specifications.<RwTaskListVO>and()
                .predicate(Objects.nonNull(createPersonIdSet), (root, query, cb) -> {
                    if (taskSendOrReceive == 1){ // 发出的任务
                        return root.get("createPersonId").in(createPersonIdSet.toArray());
                    }else if (taskSendOrReceive == 2){ // 接收的任务
                        return root.get("taskId").as(Integer.class).in(taskIdList);
                    }
                    return query.getRestriction();
                })
//                .in("createPersonId", createPersonIdSet.toArray())
                .predicate(Objects.nonNull(startTime), (root, query, cb) -> {
                    return cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class), startTime);
                })
                .predicate(Objects.nonNull(endTime), (root, query, cb) -> {
                    return cb.lessThanOrEqualTo(root.get("createDate").as(Date.class), endTime);
                })
                .build();
        return specification;
    }

    public Integer taskComplete(Integer taskId, Integer createrId){
        return rwTaskRepository.updateTaskState(taskId, createrId, TaskIsFlag.did.getIndex());
    }
    
    public Integer setTaskTop(Integer taskId, Byte taskTop){
        return rwTaskRepository.updateTaskTop(taskId, taskTop);
    }
}
