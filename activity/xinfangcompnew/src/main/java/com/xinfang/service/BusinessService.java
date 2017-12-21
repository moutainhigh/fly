package com.xinfang.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinfang.Exception.BizException;
import com.xinfang.VO.FdCaseFeedbackVO;
import com.xinfang.VO.FlowVO;
import com.xinfang.VO.TaskVO;
import com.xinfang.context.PageFinder;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.enums.HandleState;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.model.FdDepCase;
import com.xinfang.utils.DateUtils;

@Service
public class BusinessService {
	@Autowired
	TzmPetitionService tzmPetitionService;
	@Autowired
	TaskService taskService;
	@Autowired
	ActivitiService activitiService;
	@Autowired
	FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;
	@Autowired
	CaseService caseService;
	@Autowired
	FdCaseFeedbackAllService fdCaseFeedbackAllService;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;

	/**
	 * 
	 * @param depid
	 * @param startpage
	 * @param pagesize
	 * @description 获取单位收文岗案件
	 * @author ZhangHuaRong
	 * @update 2017年4月8日 上午10:38:36
	 */
	public PageFinder<TaskVO> getreceivecase(Integer depid, int startpage, int pagesize, int state) {
		List<Integer> ids = tzmPetitionService.selectSwgInfoByDwId(depid);
		if (ids.isEmpty()) {
			throw BizException.DB_LIST_IS_NULL;
		}
		PageFinder<TaskVO> result = activitiService.getreceivecase(ids.get(0).toString(), startpage, pagesize, state);
		return result;
	}

	public PageFinder<FdCase> getreturncase(Integer userId, int startpage, int pagesize) {
		List<Integer> caseids = fdCaseFeedbackAllRepository.getreturncase(userId, HandleState.RETURN.getValue());
		List<FdCase> result = new ArrayList<FdCase>();
		if (caseids.isEmpty()) {
			throw BizException.DB_LIST_IS_NULL;
		}
		for (Integer id : caseids) {
			FdCase fdCase = caseService.getFdCaseById(id);
			result.add(fdCase);
		}
		PageFinder<FdCase> pag = new PageFinder<FdCase>(startpage, pagesize, caseids.size(), result);
		return pag;
	}

	public List<FdCaseFeedbackVO> flowdetails(int caseid) {
		List<FdCaseFeedbackVO> lists = new ArrayList<FdCaseFeedbackVO>();

		List<FdDepCase> deps = fdDepCaseRepository.findByCaseId(caseid);
		for (FdDepCase dep : deps) {
			FdCaseFeedbackVO vo = new FdCaseFeedbackVO();
			List<FdCaseFeedbackAll> result = fdCaseFeedbackAllRepository.findByCaseIdAndDepId(caseid, dep.getDepId());
			List<FlowVO> flow = new ArrayList<FlowVO>();
			int i = 0;
			Date starttime = null;

			for (FdCaseFeedbackAll detail : result) {
				FlowVO v1 = new FlowVO();
				if (i == 0) {
					starttime = detail.getCreateTime();
				}
				if (i == (result.size() - 1)) {
					vo.setNodeProceedTime(DateUtils.getDatePoor(starttime, detail.getCreateTime()));
					vo.setNodeUser(detail.getCreater());
					vo.setNodeUserPosition(detail.getCreaterRole());
					vo.setStartTime(starttime);
					vo.setNodeUserUnit(detail.getCreaterDep());
					vo.setState(HandleState.getName(detail.getType()));
					vo.setNodeUserId(detail.getCreaterId().toString());
					vo.setNodeUserHeadSrc(detail.getUserurl());
				}

				v1.setCurrentCostTime(DateUtils.getDatePoor(starttime, detail.getCreateTime()));
				v1.setNodeCostTime(detail.getTaketime());
				v1.setNodeHandleState(HandleState.getName(detail.getType()));
				v1.setNodeUserId(detail.getCaseId().toString());
				v1.setNodeUser(detail.getCreater());
				v1.setNodeUserHeadSrc(detail.getUserurl());
				v1.setNodeUserUnit(detail.getCreaterDep());
				v1.setNodeUserPosition(detail.getCreaterRole());
				v1.setNodeHandleTime(detail.getCreateTime());
				v1.setFiles(detail.getEnclosure1() + ";" + detail.getEnclosure2() + ";" + detail.getEnclosure3());
				flow.add(v1);
				i++;
			}

			FdCase fdcase = caseService.getCaseById(caseid);
			vo.setFdCse(fdcase);
			vo.setFlow(flow);
			if (vo.getState() != null) {
				lists.add(vo);
			}
		}

		return lists;
	}

	public List<FdCaseFeedbackVO> flowdetailsbytimenew(Integer caseid) throws ParseException {
		List<FdCaseFeedbackVO> lists = new ArrayList<FdCaseFeedbackVO>();
		List<FdCaseFeedbackAll> result = fdCaseFeedbackAllRepository.findCaseFeedbackAllById(caseid);
		List<FdCaseFeedbackAll> remove = new ArrayList<FdCaseFeedbackAll> ();
		List<FdDepCase> depcases = fdDepCaseRepository.findByCaseId(caseid);
		FdCase fdCase = caseService.getFdCaseById(caseid);
		if(fdCase.getHandleFlowType()==null){
			return flowdetailsbytime(caseid);
		}
		if(fdCase.getHandleFlowType().intValue()!=1 && fdCase.getHandleFlowType() !=2 && fdCase.getHandleFlowType() != 3){//不是交办的未处理还是按照以前的
			return flowdetailsbytime(caseid);
		}
		Date starttime = fdCase.getGmtCreate();
		int n=1;
		boolean flag = true;
		for(FdDepCase fddep:depcases){
			print(fddep,result);
			FdCaseFeedbackVO vo = new FdCaseFeedbackVO();
			if (fddep.getUpdateTime() != null) {
				vo.setNodeProceedTime(
						DateUtils.getDatePoor(fddep.getCreateTime(), fddep.getUpdateTime()));
			} else {
				vo.setNodeProceedTime(
						DateUtils.getDatePoor(fddep.getCreateTime(), fddep.getCreateTime()));
			}
			
			vo.setStartTime(fddep.getCreateTime());
		
			if (fddep.getEndTime() != null) {
				vo.setNodeUnitCostTime(DateUtils.formatDateTime(fddep.getEndTime()));
			}
			vo.setState(fddep.getNote());
			
			vo.setDepId(fddep.getDepId());
			List<FlowVO> flow = new ArrayList<FlowVO>();
			FdCaseFeedbackAll last=null;
			int m=1;
			
			for(FdCaseFeedbackAll detail:result){
				if(detail.getDepId().intValue()!=fddep.getDepId().intValue()){
					break;
				}
				FlowVO v1= new FlowVO();
				
				if(n==1&&m==2){//登记阶段
					starttime=detail.getCreateTime();
					v1.setCurrentCostTime(DateUtils.getDatePoor(starttime,detail.getCreateTime()));
					v1.setNodeUnitCostTime(fddep.getLimitTime().toString());
					v1.setNodeCostTime(DateUtils.getDatePoor(starttime,detail.getCreateTime()));
					
				}else if(n==1&&m==3){//转交办阶段
					v1.setCurrentCostTime(DateUtils.getDatePoor(starttime,detail.getCreateTime()));
					if(fddep.getLimitTime()!=null){
						v1.setNodeUnitCostTime(fddep.getLimitTime().toString());
					}else{
						v1.setNodeUnitCostTime(0+"");
					}
					v1.setNodeCostTime(DateUtils.getDatePoor(starttime,detail.getCreateTime()));
				}else{
					v1.setCurrentCostTime(DateUtils.getDatePoor(starttime,detail.getCreateTime()));
					if(fddep.getLimitTime()!=null){
						v1.setNodeUnitCostTime(fddep.getLimitTime().toString());
					}else{
						v1.setNodeUnitCostTime(0+"");
					}
					
					v1.setNodeCostTime(DateUtils.getDatePoor(fddep.getCreateTime(),detail.getCreateTime()));
				}
				
				m++;
				
				
				v1.setNodeHandleState(HandleState.getName(detail.getType()));
				v1.setNodeUserId(detail.getCaseId().toString());
				v1.setNodeUser(detail.getCreater());
				v1.setNodeUserHeadSrc(detail.getUserurl());
				v1.setNodeUserUnit(detail.getCreaterDep());
				v1.setNodeUserPosition(detail.getCreaterRole());
				v1.setNodeHandleTime(detail.getCreateTime());
				v1.setId(detail.getId());
				v1.setCreateTime(detail.getCreateTime());
				StringBuffer sb=new StringBuffer();
				if(!StringUtils.isEmpty(detail.getEnclosure1())){
					sb.append(detail.getEnclosure1()).append(";");
				}
				if(!StringUtils.isEmpty(detail.getEnclosure2())){
					sb.append(detail.getEnclosure2()).append(";");
				}
	           if(!StringUtils.isEmpty(detail.getEnclosure3())){
					sb.append(detail.getEnclosure3()).append(";");
				}
				v1.setFiles(sb.toString());
				last=detail;
				if(detail.getType().intValue()==9&&flag){//出具责任告知书
					flag=false;
					break;
				}
				remove.add(detail);
				
				flow.add(v1);
			}
			result.removeAll(remove);
			
			if(last!=null){
				vo.setNodeUser(last.getCreater());
				vo.setNodeUserPosition(last.getCreaterRole());
				vo.setNodeUserUnit(last.getCreaterDep());
				vo.setNodeUserId(last.getCreaterId().toString());
				vo.setNodeUserHeadSrc(last.getUserurl());
			}else{
				
				
			}
			
			vo.setFlow(flow);
			lists.add(vo);
			n++;
		}
		

		return lists;
	}

	private void print(FdDepCase fddep, List<FdCaseFeedbackAll> result) {
		System.out.println("==========================");
		System.out.println("当前节点："+fddep);
		for(FdCaseFeedbackAll detail:result){
			System.out.println(detail);
		}
	}

	public List<FdCaseFeedbackVO> flowdetailsbytime(Integer caseid) throws ParseException {
		List<FdCaseFeedbackVO> lists = new ArrayList<FdCaseFeedbackVO>();
		List<FdCaseFeedbackAll> result = fdCaseFeedbackAllRepository.findCaseFeedbackAllById(caseid);
		for (int i = 0; i < result.size(); i++) {
			if (i > 0) {
				FdCaseFeedbackAll parent = result.get(i - 1);
				FdCaseFeedbackAll detail = result.get(i);
				System.out.println("================================");
				System.out.println(parent.getDepId().intValue());
				System.out.println(detail.getDepId().intValue());
				if (detail.getDepId().intValue() == parent.getDepId().intValue()) {
					FlowVO v1 = new FlowVO();
					StringBuffer sb = new StringBuffer();

					FdCaseFeedbackVO vo = null;
					v1.setCurrentCostTime(null);
					
					v1.setNodeCostTime(detail.getTaketime());
					System.out.println("id:"+detail.getId()+"   ;taketime : "+detail.getTaketime());
					
					v1.setNodeHandleState(HandleState.getName(detail.getType()));
					v1.setNodeUserId(detail.getCaseId().toString());
					v1.setNodeUser(detail.getCreater());
					v1.setNodeUserHeadSrc(detail.getUserurl());
					v1.setNodeUserUnit(detail.getCreaterDep());
					v1.setNodeUserPosition(detail.getCreaterRole());
					v1.setNodeHandleTime(detail.getCreateTime());

					if (!StringUtils.isEmpty(detail.getEnclosure1())) {
						sb.append(detail.getEnclosure1()).append(";");
					}
					if (!StringUtils.isEmpty(detail.getEnclosure2())) {
						sb.append(detail.getEnclosure2()).append(";");
					}
					if (!StringUtils.isEmpty(detail.getEnclosure3())) {
						sb.append(detail.getEnclosure3()).append(";");
					}
					v1.setFiles(sb.toString());

					for (int j = (lists.size() - 1); j >= 0; j--) {

						if (lists.get(j).getDepId().intValue() == detail.getDepId().intValue()) {
							vo = lists.get(j);
							break;
						}
					}
					if (vo.getNodeUnitCostTime() == null) {// 当前部门的办理时限
						List<FdDepCase> casedeps = fdDepCaseRepository
								.findByCaseIdAndDepId(detail.getCaseId().longValue(), detail.getDepId());
						if (!casedeps.isEmpty()) {
							vo.setNodeUnitCostTime(casedeps.get(0).getLimitTime().toString());
						}
					}

					v1.setNodeUnitCostTime(vo.getNodeUnitCostTime());
					vo.setNodeProceedTime(DateUtils.getDatePoor(vo.getStartTime(), detail.getCreateTime()));
					vo.setNodeUser(detail.getCreater());
					vo.setNodeUserPosition(detail.getCreaterRole());
					vo.setStartTime(detail.getCreateTime());
					vo.setNodeUserUnit(detail.getCreaterDep());
					vo.setState(HandleState.getName(detail.getType()));
					vo.setNodeUserId(detail.getCreaterId().toString());
					vo.setNodeUserHeadSrc(detail.getUserurl());
					vo.setDepId(detail.getDepId());
					vo.getFlow().add(v1);

				} else {
					FdCaseFeedbackVO vo = new FdCaseFeedbackVO();
					vo.setNodeProceedTime(null);
					vo.setNodeUser(detail.getCreater());
					vo.setNodeUserPosition(detail.getCreaterRole());
					vo.setStartTime(detail.getCreateTime());
					vo.setNodeUserUnit(detail.getCreaterDep());
					vo.setState(HandleState.getName(detail.getType()));
					vo.setNodeUserId(detail.getCreaterId().toString());
					vo.setNodeUserHeadSrc(detail.getUserurl());
					vo.setDepId(detail.getDepId());

					List<FlowVO> flow = new ArrayList<FlowVO>();
					if (vo.getNodeUnitCostTime() == null) {// 当前部门的办理时限
						List<FdDepCase> casedeps = fdDepCaseRepository
								.findByCaseIdAndDepId(detail.getCaseId().longValue(), detail.getDepId());
						if (!casedeps.isEmpty()) {
							vo.setNodeUnitCostTime(casedeps.get(0).getLimitTime().toString());
						}
					}
					FlowVO v1 = new FlowVO();

					v1.setCurrentCostTime(null);
					v1.setNodeCostTime(detail.getTaketime());
					v1.setNodeHandleState(HandleState.getName(detail.getType()));
					v1.setNodeUserId(detail.getCaseId().toString());
					v1.setNodeUser(detail.getCreater());
					v1.setNodeUserHeadSrc(detail.getUserurl());
					v1.setNodeUserUnit(detail.getCreaterDep());
					v1.setNodeUserPosition(detail.getCreaterRole());
					v1.setNodeHandleTime(detail.getCreateTime());
					StringBuffer sb = new StringBuffer();
					if (!StringUtils.isEmpty(detail.getEnclosure1())) {
						sb.append(detail.getEnclosure1()).append(";");
					}
					if (!StringUtils.isEmpty(detail.getEnclosure2())) {
						sb.append(detail.getEnclosure2()).append(";");
					}
					if (!StringUtils.isEmpty(detail.getEnclosure3())) {
						sb.append(detail.getEnclosure3()).append(";");
					}
					v1.setFiles(sb.toString());
					// v1.setFiles(detail.getEnclosure1()+";"+detail.getEnclosure2()+";"+detail.getEnclosure3());
					v1.setNodeUnitCostTime(vo.getNodeUnitCostTime());
					flow.add(v1);
					vo.setFlow(flow);

					lists.add(vo);

				}

			} else {
				FdCaseFeedbackAll detail = result.get(i);
				FdCaseFeedbackVO vo = new FdCaseFeedbackVO();
				vo.setNodeProceedTime(null);
				vo.setNodeUser(detail.getCreater());
				vo.setNodeUserPosition(detail.getCreaterRole());
				vo.setStartTime(detail.getCreateTime());
				vo.setNodeUserUnit(detail.getCreaterDep());
				vo.setState(HandleState.getName(detail.getType()));
				vo.setNodeUserId(detail.getCreaterId().toString());
				vo.setNodeUserHeadSrc(detail.getUserurl());
				vo.setDepId(detail.getDepId());

				List<FlowVO> flow = new ArrayList<FlowVO>();
				if (vo.getNodeUnitCostTime() == null) {// 当前部门的办理时限
					List<FdDepCase> casedeps = fdDepCaseRepository.findByCaseIdAndDepId(detail.getCaseId().longValue(),
							detail.getDepId());
					if (!casedeps.isEmpty()) {
						vo.setNodeUnitCostTime(casedeps.get(0).getLimitTime().toString());
					}
				}
				FlowVO v1 = new FlowVO();

				v1.setCurrentCostTime(null);
				v1.setNodeCostTime(detail.getTaketime());
				v1.setNodeHandleState(HandleState.getName(detail.getType()));
				v1.setNodeUserId(detail.getCaseId().toString());
				v1.setNodeUser(detail.getCreater());
				v1.setNodeUserHeadSrc(detail.getUserurl());
				v1.setNodeUserUnit(detail.getCreaterDep());
				v1.setNodeUserPosition(detail.getCreaterRole());
				v1.setNodeHandleTime(detail.getCreateTime());
				v1.setFiles(detail.getEnclosure1() + ";" + detail.getEnclosure2() + ";" + detail.getEnclosure3());
				v1.setNodeUnitCostTime(vo.getNodeUnitCostTime());
				flow.add(v1);
				vo.setFlow(flow);

				lists.add(vo);
			}

		}

		return lists;
	}

	public FdDepCase fddepcase(long caseid, int depid) {

		List<FdDepCase> result = fdDepCaseRepository.findByCaseIdAndDepId(caseid, depid);
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

}
