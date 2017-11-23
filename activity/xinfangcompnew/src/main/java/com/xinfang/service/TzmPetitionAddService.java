package com.xinfang.service;

import com.xinfang.dao.FcRybAllFieldRepository;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.enums.HandleState;
import com.xinfang.model.FcRybAllField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-18 18:09
 **/

@Service
public class TzmPetitionAddService {

    @Autowired
    FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;

    @Autowired
    FcRybAllFieldRepository fcRybAllFieldRepository;

    @Autowired
    TzmPetitionService tzmPetitionService;

    public Map getStatisticsCaseStatus(Integer userId){
//        ASSIGNED(1, "交办"), TRUN(2, "转办"),ALLOW(3,"受理"),REPLY(4,"直接回复"),REFUSE(5,"不予受理 "),REFUSEL(6,"不在受理 ")
//                ,SAVE(7,"直接存档"),RETURN(8,"退回"),FINISH(9,"申请办结"),DELAY(10,"申请延期")
        Map resultMap = new HashMap();
        Integer assigned = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.ASSIGNED.getValue());
        Integer turn = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.TRUN.getValue());
        Integer allow = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.ALLOW.getValue());
        Integer reply = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.REPLY.getValue());
        Integer refuse = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.REFUSE.getValue());
        Integer refusel = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.REFUSEL.getValue());
        Integer save = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.SAVE.getValue());
        Integer returnCase = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.RETURN.getValue());
        Integer finish = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.FINISH.getValue());
        Integer delay = fdCaseFeedbackAllRepository.getCaseCount(userId, HandleState.DELAY.getValue());

        resultMap.put("assigned", assigned);
        resultMap.put("turn", turn);
        resultMap.put("allow", allow);
        resultMap.put("reply", reply);
        resultMap.put("refuse", refuse);
        resultMap.put("refusel", refusel);
        resultMap.put("save", save);
        resultMap.put("return", returnCase);
        resultMap.put("finish", finish);
        resultMap.put("delay", delay);
        return resultMap;
    }

    public Set<Integer> testLogin(){
        Set<Integer> result = new HashSet<>();
        List<FcRybAllField>  fcRybAllFieldList = fcRybAllFieldRepository.findAll();
        for (FcRybAllField fcRybAllField: fcRybAllFieldList){
            try {
                tzmPetitionService.selectLogInInfo(fcRybAllField.getLogInName(), fcRybAllField.getPassword());
            }catch (Exception e){
                result.add(fcRybAllField.getRyId());
            }
        }
        return result;
    }
}
