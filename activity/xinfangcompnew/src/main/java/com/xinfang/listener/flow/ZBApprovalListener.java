package com.xinfang.listener.flow;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.VO.FcRybTreeVO;
import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FcRybAllFieldRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.enums.HandleLimitState;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdDepCase;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;

@Component("zBApprovalListener")
public class ZBApprovalListener implements TaskListener {
	private static final long serialVersionUID = 1L;
	@Autowired
	private FcDwbRepository fcDwbRepository;
	@Autowired
	private FdDepCaseRepository fdDepCaseRepository;

	@Autowired
	private FcRybAllFieldRepository fcRybAllFieldRepository;

	@Override
	public void notify(DelegateTask task) {
		try {
			Integer depid = (Integer) task.getVariable("depid");
			// Object dep1 = task.getVariable("dep1");
			Integer handleday = (Integer) task.getVariable("handleday");
			Integer caseid = (Integer) task.getVariable("caseid");
			Integer approval = (Integer) task.getVariable("approval");
			Object processid = task.getVariable("processid");
			// Object createDep = task.getVariable("createDep");
			Integer assignee = Integer.valueOf(task.getAssignee());
			FcRybTreeVO fdRyb = fcRybAllFieldRepository.findRybById(assignee);
			Integer dwType = fdRyb.getDwType();
			Long createrid = (Long) task.getVariable("createrid");
			if (approval < 1) {

			} else {
				List<FdDepCase> depcases = fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
				Date nowtime = new Date();
				if (!(assignee.intValue() == createrid.intValue())) {

					FdDepCase maindep = new FdDepCase();
					maindep.setStartTime(nowtime);
					maindep.setEndTime(
							DateUtils.add(nowtime, handleday * RuleUtil.getRule().getCityApproval() * 0.01f));
					maindep.setLimitTime(handleday * RuleUtil.getRule().getCityApproval() * 0.01f);
					if (dwType.intValue() <= 4) {
						maindep.setType(HandleLimitState.cityApproval.getValue());
						maindep.setNote(HandleLimitState.cityApproval.getName());
					} else {
						maindep.setType(HandleLimitState.areaApproval.getValue());
						maindep.setNote(HandleLimitState.areaApproval.getName());
					}

					maindep.setCaseId((long) caseid);
					maindep.setCreateTime(nowtime);
					maindep.setState(1);
					maindep.setUpdateTime(nowtime);
					if (processid != null) {
						maindep.setProcessid(processid.toString());
					}
					if (fdRyb != null) {
						maindep.setDepId(fdRyb.getDwId());
					}

					fdDepCaseRepository.saveAndFlush(maindep);
				}

				if (depcases != null && !depcases.isEmpty()) {
					FdDepCase dep = depcases.get(0);
					dep.setState(0);
					dep.setUpdateTime(nowtime);
					fdDepCaseRepository.saveAndFlush(dep);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			ApiLog.log("交办领导审批后计算被交办单位限办时间出错：" + e.getMessage());
		}

	}
}
