package com.xinfang.listener.flow;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdDepCase;
/**
 * 
 * @title FinishRegListerer.java
 * @package com.xinfang.listener.flow
 * @description 市级审批
 * @author ZhangHuaRong   
 * @update 2017年4月20日 下午3:32:06
 */
@Component("cityAppryOverListerer")
public class CityAppryOverListerer implements TaskListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	FcDwbRepository fcDwbRepository;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	@Override
	public void notify(DelegateTask task) {
		
		/*if(){ 是否是回退案件判断
			
		}*/ //TODO 是否是回退案件判断
		try {
			Integer depid = (Integer) task.getVariable("depid");
			Integer handleday = (Integer) task.getVariable("handleday");
			Integer caseid = (Integer) task.getVariable("caseid");
			Integer approval = (Integer) task.getVariable("approval");
			Integer type = (Integer) task.getVariable("type");
			Object dep1 =  task.getVariable("dep1");
			 ApiLog.chargeLog1("==============流程控制===============cityAppryOverListerer==");
             System.out.println("==============流程控制===============cityAppryOverListerer==");
			if(approval<1){
				
			}else{
				
				List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
				Date nowtime = new Date();
				if(depcases!=null&&!depcases.isEmpty()){
					FdDepCase dep = depcases.get(0);
					dep.setState(0);
					dep.setUpdateTime(nowtime);
					fdDepCaseRepository.saveAndFlush(dep);
				}
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			ApiLog.log("交办领导审批后计算被交办单位限办时间出错："+e.getMessage());
		}
		
		

	}

}