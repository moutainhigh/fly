package com.xinfang.DataDocking.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.DataDocking.service.DataDockingInformationService;
import com.xinfang.DataDocking.vo.ContradictionProblem;
import com.xinfang.DataDocking.vo.IntellgenceInformation;
import com.xinfang.DataDocking.vo.KeyPerson;
import com.xinfang.log.ApiLog;


@Service("dataDockingInformationService")
public class DataDockingInformationServiceImpl implements
		DataDockingInformationService {
		@Autowired
		private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> getKeyPersonInformation(String startTime,
			String endTime, Integer keyPersonId, Integer startPage,
			Integer pageCount) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			String fileterSql="";
			String timeSql="";
			if(StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
				timeSql="";
			}else{
				timeSql=" AND DATE_FORMAT(addTime,'%Y-%m-%d-%H-%i-%s') BETWEEN '"+startTime+"' AND '"+endTime+" 23:59:59'";
			} 
				String IdSql="";
				if(!keyPersonId.equals(0)){
					IdSql=" AND keyperson_id = "+keyPersonId;
			}else {
				IdSql="";
			}
				fileterSql=timeSql+IdSql;
			String sql=" select *,(select COUNT(*) FROM jk_zf_keyperson where keyperson_id is not null "+fileterSql+") as total from jk_zf_keyperson WHERE keyperson_id is not null "+fileterSql+" order by keyperson_id asc limit "+startPage*pageCount+","+pageCount;
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<KeyPerson> keyPerson=new ArrayList<KeyPerson>();
			/*for(int i=0;i<list.size();i++){
				KeyPerson k=new KeyPerson();
				k.setAddBy(StringUtils.isEmpty(list.get(i).get("addBy"))?null:list.get(i).get("addBy").toString());
				k.setAddTime(StringUtils.isEmpty(list.get(i).get("addTime"))?null:list.get(i).get("addTime").toString());
				k.setAsk(StringUtils.isEmpty(list.get(i).get("ask"))?null:list.get(i).get("ask").toString());
				k.setAttachment(StringUtils.isEmpty(list.get(i).get("attachment"))?null:list.get(i).get("attachment").toString());
				k.setBelongtoAdress(StringUtils.isEmpty(list.get(i).get("territory"))?null:list.get(i).get("territory").toString());
				k.setBornDate(StringUtils.isEmpty(list.get(i).get("birth"))?null:list.get(i).get("birth").toString());
				k.setCommitStatus(StringUtils.isEmpty(list.get(i).get("commitStatus"))?null:list.get(i).get("commitStatus").toString());
				k.setContactInformation(StringUtils.isEmpty(list.get(i).get("contactInformation"))?null:list.get(i).get("contactInformation").toString());
				k.setDef2(StringUtils.isEmpty(list.get(i).get("def2"))?null:list.get(i).get("def2").toString());
				k.setDef3(StringUtils.isEmpty(list.get(i).get("def3"))?null:list.get(i).get("def3").toString());
				k.setDeparment(StringUtils.isEmpty(list.get(i).get("directorDepartment"))?null:list.get(i).get("directorDepartment").toString());
				k.setDynamic(StringUtils.isEmpty(list.get(i).get("dynamics"))?null:list.get(i).get("dynamics").toString());
				k.setFileSrc(StringUtils.isEmpty(list.get(i).get("FJ"))?null:list.get(i).get("FJ").toString());
				k.setIdCard(StringUtils.isEmpty(list.get(i).get("idNum"))?null:list.get(i).get("idNum").toString());
				k.setIsDelete(StringUtils.isEmpty(list.get(i).get("isDelete"))?null:list.get(i).get("isDelete").toString());
				k.setIssueContent(StringUtils.isEmpty(list.get(i).get("issueContent"))?null:list.get(i).get("issueContent").toString());
				k.setJobName(StringUtils.isEmpty(list.get(i).get("jobName"))?null:list.get(i).get("jobName").toString());
				k.setKeyId(StringUtils.isEmpty(list.get(i).get("keyperson_id"))?null:Integer.valueOf(list.get(i).get("keyperson_id").toString()));
				k.setLastupdateBy(StringUtils.isEmpty(list.get(i).get("lastUpdateBy"))?null:list.get(i).get("lastUpdateBy").toString());
				k.setLastUpdateTime(StringUtils.isEmpty(list.get(i).get("lastUpdateTime"))?null:list.get(i).get("lastUpdateTime").toString());
				k.setLeader(StringUtils.isEmpty(list.get(i).get("leaderId"))?null:list.get(i).get("leaderId").toString());
				k.setLevel(StringUtils.isEmpty(list.get(i).get("level"))?null:list.get(i).get("level").toString());
				k.setName(StringUtils.isEmpty(list.get(i).get("name"))?null:list.get(i).get("name").toString());
				k.setNation(StringUtils.isEmpty(list.get(i).get("nation"))?null:list.get(i).get("nation").toString());
				k.setNowAdress(StringUtils.isEmpty(list.get(i).get("address"))?null:list.get(i).get("address").toString());
				k.setPeopleDom(StringUtils.isEmpty(list.get(i).get("popedom"))?null:list.get(i).get("popedom").toString());
				k.setPhoto(StringUtils.isEmpty(list.get(i).get("photo"))?null:list.get(i).get("photo").toString());
				k.setResponsiblePeople(StringUtils.isEmpty(list.get(i).get("responsibleId"))?null:list.get(i).get("responsibleId").toString());
				k.setSex(StringUtils.isEmpty(list.get(i).get("sex"))?null:list.get(i).get("sex").toString());
				k.setState(StringUtils.isEmpty(list.get(i).get("status"))?null:list.get(i).get("status").toString());
				k.setState0(StringUtils.isEmpty(list.get(i).get("status0"))?null:list.get(i).get("status0").toString());
				k.setState2(StringUtils.isEmpty(list.get(i).get("status1"))?null:list.get(i).get("status1").toString());
				k.setType(StringUtils.isEmpty(list.get(i).get("category"))?null:list.get(i).get("category").toString());
				keyPerson.add(k);
			}*/
			map.put("data", list);
			map.put("pageIndex", startPage);
			map.put("pageNum",
					Integer.valueOf(list.get(0).get("total").toString())
							% pageCount == 0 ? Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount : Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount + 1);
			map.put("resultTotal",
					StringUtils.isEmpty(list.get(0).get("total")) ? 0
							: Integer.valueOf(list.get(0).get("total")
									.toString()));
			map.put("pageSize", pageCount);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}

	@Override
	public Map<String, Object> getContradictionInformation(String startTime,
			String endTime, Integer keyPersonId, Integer startPage,
			Integer pageCount) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			String fileterSql="";
			String timeSql="";
			if(StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
				timeSql="";
			}else{
				timeSql=" AND DATE_FORMAT(addTime,'%Y-%m-%d-%H-%i-%s') BETWEEN '"+startTime+"' AND '"+endTime+"'";
			} 
				String IdSql="";
				if(!keyPersonId.equals(0)){
					IdSql=" AND conflictproblem_id = "+keyPersonId;
			}else {
				IdSql="";
			}
				fileterSql=timeSql+IdSql;
			String sql=" select *,(select COUNT(*) from jk_zf_conflictproblem where conflictproblem_id is not null "+fileterSql+") as total  from jk_zf_conflictproblem WHERE conflictproblem_id is not null "+fileterSql+" order by conflictproblem_id asc limit "+startPage*pageCount+","+pageCount;
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		System.err.println(sql);
		List<ContradictionProblem> cpList=new ArrayList<>();
		/*if(list.size()>0){
		for(int i=0;i<list.size();i++){
			ContradictionProblem c=new ContradictionProblem();
			c.setAddBy(StringUtils.isEmpty(list.get(i).get("addBy"))?null:list.get(i).get("addBy").toString());
			c.setAddTime(StringUtils.isEmpty(list.get(i).get("addTime"))?null:list.get(i).get("addTime").toString());
			c.setBelongTo(StringUtils.isEmpty(list.get(i).get("territory"))?null:list.get(i).get("territory").toString());
			c.setContradictionName(StringUtils.isEmpty(list.get(i).get("name"))?null:list.get(i).get("name").toString());
			c.setContradictionProblemCode(StringUtils.isEmpty(list.get(i).get("code"))?null:list.get(i).get("code").toString());
			c.setContradictionProblemId(StringUtils.isEmpty(list.get(i).get("conflictproblem_id"))?null:Integer.valueOf(list.get(i).get("conflictproblem_id").toString()));
			c.setDef1(StringUtils.isEmpty(list.get(i).get("def1"))?null:list.get(i).get("def1").toString());
			c.setDef2(StringUtils.isEmpty(list.get(i).get("def2"))?null:list.get(i).get("def2").toString());
			c.setDef3(StringUtils.isEmpty(list.get(i).get("def3"))?null:list.get(i).get("def3").toString());
			c.setDef4(StringUtils.isEmpty(list.get(i).get("def4"))?null:list.get(i).get("def4").toString());
			c.setDef5(StringUtils.isEmpty(list.get(i).get("def5"))?null:list.get(i).get("def5").toString());
			c.setDef6(StringUtils.isEmpty(list.get(i).get("def6"))?null:list.get(i).get("def6").toString());
			c.setDef7(StringUtils.isEmpty(list.get(i).get("def7"))?null:list.get(i).get("def7").toString());
			c.setDynamic(StringUtils.isEmpty(list.get(i).get("dynamics"))?null:list.get(i).get("dynamics").toString());
			c.setFileSrc(StringUtils.isEmpty(list.get(i).get("attachment"))?null:list.get(i).get("attachment").toString());
			c.setIntellgice(StringUtils.isEmpty(list.get(i).get("intelligence"))?null:list.get(i).get("intelligence").toString());
			c.setInvovleCount(StringUtils.isEmpty(list.get(i).get("involvedGroup"))?null:list.get(i).get("involvedGroup").toString());
			c.setIsDelete(StringUtils.isEmpty(list.get(i).get("isDelete"))?null:list.get(i).get("isDelete").toString());
			c.setKeyWord(StringUtils.isEmpty(list.get(i).get("keyword"))?null:list.get(i).get("keyword").toString());
			c.setLastUpdateBy(StringUtils.isEmpty(list.get(i).get("lastUpdateBy"))?null:list.get(i).get("lastUpdateBy").toString());
			c.setLastUpdateTime(StringUtils.isEmpty(list.get(i).get("lastUpdateTime"))?null:list.get(i).get("lastUpdateTime").toString());
			c.setLeaderDepartment(StringUtils.isEmpty(list.get(i).get("directorDepartment"))?null:list.get(i).get("directorDepartment").toString());
			c.setMainQuestion(StringUtils.isEmpty(list.get(i).get("keyProblem"))?null:list.get(i).get("keyProblem").toString());
			c.setMeasure(StringUtils.isEmpty(list.get(i).get("measure"))?null:list.get(i).get("measure").toString());
			c.setPeopleCount(StringUtils.isEmpty(list.get(i).get("peopleCount"))?null:list.get(i).get("peopleCount").toString());
			c.setPersonInformation(StringUtils.isEmpty(list.get(i).get("peopleInfo"))?null:list.get(i).get("peopleInfo").toString());
			c.setQuestionContent(StringUtils.isEmpty(list.get(i).get("issueContent"))?null:list.get(i).get("issueContent").toString());
			c.setQuestionProperty(StringUtils.isEmpty(list.get(i).get("issueProperty"))?null:list.get(i).get("issueProperty").toString());
			c.setRemark(StringUtils.isEmpty(list.get(i).get("remark"))?null:list.get(i).get("remark").toString());
			c.setSovleRecord(StringUtils.isEmpty(list.get(i).get("solveRecord"))?null:list.get(i).get("solveRecord").toString());
			c.setUnsTable(StringUtils.isEmpty(list.get(i).get("unstable"))?null:list.get(i).get("unstable").toString());
			cpList.add(c);
		}
		}
*/		map.put("data", list);
		map.put("pageIndex", startPage);
		map.put("pageNum",
				Integer.valueOf(list.get(0).get("total").toString())
						% pageCount == 0 ? Integer.valueOf(list.get(0)
						.get("total").toString())
						/ pageCount : Integer.valueOf(list.get(0)
						.get("total").toString())
						/ pageCount + 1);
		map.put("resultTotal",
				StringUtils.isEmpty(list.get(0).get("total")) ? 0
						: Integer.valueOf(list.get(0).get("total")
								.toString()));
		map.put("pageSize", pageCount);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}

	@Override
	public Map<String, Object> getIntellgenceInformation(String startTime,
			String endTime, Integer keyPersonId, Integer startPage,
			Integer pageCount) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			String fileterSql="";
			String timeSql="";
			if(StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
				timeSql="";
			}else{
				timeSql=" AND DATE_FORMAT(addTime,'%Y-%m-%d-%H-%i-%s') BETWEEN '"+startTime+"' AND '"+endTime+"'";
			} 
				String IdSql="";
				if(!keyPersonId.equals(0)){
					IdSql=" AND inteligence_id = "+keyPersonId;
			}else {
				IdSql="";
			}
				fileterSql=timeSql+IdSql;
			String sql=" select *,(select COUNT(*) from jk_zf_inteligence WHERE inteligence_id is not null "+fileterSql+" ) as total from jk_zf_inteligence WHERE inteligence_id is not null "+fileterSql+" order by inteligence_id asc limit "+startPage*pageCount+","+pageCount;
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<IntellgenceInformation> intellgenceInformations=new ArrayList<>();
	/*	for(int i=0;i<list.size();i++){
			IntellgenceInformation f=new IntellgenceInformation();
			f.setAddBy(StringUtils.isEmpty(list.get(i).get("addBy"))?null:list.get(i).get("addBy").toString());
			f.setAddTime(StringUtils.isEmpty(list.get(i).get("addTime"))?null:list.get(i).get("addTime").toString());
			f.setBelongTo(StringUtils.isEmpty(list.get(i).get("territory"))?null:list.get(i).get("territory").toString());
			f.setCommitStatus(StringUtils.isEmpty(list.get(i).get("commitStatus"))?null:list.get(i).get("commitStatus").toString());
			f.setDef1(StringUtils.isEmpty(list.get(i).get("def1"))?null:list.get(i).get("def1").toString());
			f.setDef2(StringUtils.isEmpty(list.get(i).get("def2"))?null:list.get(i).get("def2").toString());
			f.setDef4(StringUtils.isEmpty(list.get(i).get("def4"))?null:list.get(i).get("def4").toString());
			f.setDef5(StringUtils.isEmpty(list.get(i).get("def5"))?null:list.get(i).get("def5").toString());
			f.setDeterMine(StringUtils.isEmpty(list.get(i).get("determine"))?null:list.get(i).get("determine").toString());
			f.setFileSrc(StringUtils.isEmpty(list.get(i).get("attachment"))?null:list.get(i).get("attachment").toString());
			f.setGPS(StringUtils.isEmpty(list.get(i).get("GPS"))?null:list.get(i).get("GPS").toString());
			f.setInformationName(StringUtils.isEmpty(list.get(i).get("name"))?null:list.get(i).get("name").toString());
			f.setIntellgenceInformationCode(StringUtils.isEmpty(list.get(i).get("code"))?null:list.get(i).get("code").toString());
			f.setIntellgenceInformationId(StringUtils.isEmpty(list.get(i).get("inteligence_id"))?null:Integer.valueOf(list.get(i).get("inteligence_id").toString()));
			f.setInvovleCount(StringUtils.isEmpty(list.get(i).get("involvedGroup"))?null:list.get(i).get("involvedGroup").toString());
			
			f.setIsDelete(StringUtils.isEmpty(list.get(i).get("isDelete"))?null:list.get(i).get("isDelete").toString());
			f.setIsStaus(StringUtils.isEmpty(list.get(i).get("status"))?null:list.get(i).get("status").toString());
			f.setKeyWord(StringUtils.isEmpty(list.get(i).get("keyword"))?null:list.get(i).get("keyword").toString());
			f.setLastUpdateBy(StringUtils.isEmpty(list.get(i).get("lastUpdateBy"))?null:list.get(i).get("lastUpdateBy").toString());
			f.setLastUpdateTime(StringUtils.isEmpty(list.get(i).get("lastUpdateTime"))?null:list.get(i).get("lastUpdateTime").toString());
			f.setLeaderDeparment(StringUtils.isEmpty(list.get(i).get("directorDepartment"))?null:list.get(i).get("directorDepartment").toString());
			f.setLevelCode(StringUtils.isEmpty(list.get(i).get("levelCode"))?null:list.get(i).get("levelCode").toString());
			f.setMainInformation(StringUtils.isEmpty(list.get(i).get("peopleInfo"))?null:list.get(i).get("peopleInfo").toString());
			f.setMainProblem(StringUtils.isEmpty(list.get(i).get("keyProblem"))?null:list.get(i).get("keyProblem").toString());
			f.setPeopleCount(StringUtils.isEmpty(list.get(i).get("peopleCount"))?null:list.get(i).get("peopleCount").toString());
			f.setQuestionInformation(StringUtils.isEmpty(list.get(i).get("caseClassification"))?null:list.get(i).get("caseClassification").toString());
			f.setQuestionType(StringUtils.isEmpty(list.get(i).get("issueProperty"))?null:list.get(i).get("issueProperty").toString());
			f.setStatus(StringUtils.isEmpty(list.get(i).get("isEmergency"))?null:list.get(i).get("isEmergency").toString());
			intellgenceInformations.add(f);	
		}*/
		map.put("data", list);
		map.put("pageIndex", startPage);
		map.put("pageNum",
				Integer.valueOf(list.get(0).get("total").toString())
						% pageCount == 0 ? Integer.valueOf(list.get(0)
						.get("total").toString())
						/ pageCount : Integer.valueOf(list.get(0)
						.get("total").toString())
						/ pageCount + 1);
		map.put("resultTotal",
				StringUtils.isEmpty(list.get(0).get("total")) ? 0
						: Integer.valueOf(list.get(0).get("total")
								.toString()));
		map.put("pageSize", pageCount);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}

}
