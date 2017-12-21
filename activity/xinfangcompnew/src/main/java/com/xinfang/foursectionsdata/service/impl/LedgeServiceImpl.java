package com.xinfang.foursectionsdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.VO.HomeListVO;
import com.xinfang.dao.FcRybAllFieldRepository;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.dao.FdGuestRepository;
import com.xinfang.foursectionsdata.service.LedgeService;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FcRybAllField;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdGuest;

@Service
public class LedgeServiceImpl implements LedgeService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Autowired
	private FdGuestRepository fdGuestRepository;
	@Autowired
	private FcRybAllFieldRepository fcRybAllFieldRepository;
	@Autowired
	private FdCodeRepository fdCodeRepository;
	@Override
	public Map<String, Object> getLedgeList(final Integer populationType,
			final Integer orgLevel, final String fuzzy, final Integer petitionWay,
			final Integer belongToId, final Integer departmentId, final String startTime,
			final String endTime,Integer startPage,Integer pageCount) {
		Map<String, Object> map =new HashMap<>();
		String level=null;
		if(orgLevel==1){
			level="1200,1119";
		}else if(orgLevel==2){
			level="1201";
		}else if(orgLevel==3){
			level="1202";
		}else if(orgLevel==4){
			level="1303";
		}
		final String fuzzySql = " AND petition_names like '%" + fuzzy
				+ "%'";
		final String petitionWaySql = " AND  petition_way = " + petitionWay;
		final String timeTypeSql = " AND  gmt_create BETWEEN '" + startTime + "' AND '"
				+ endTime + "'";
		final String orgLevelSql=" AND (SELECT petition_way_parent FROM fd_guest WHERE ID = guest_id) IN ("+level+")";
		final String belongToIdSql=" AND  question_belonging_to = "+belongToId;
		final String departmentIdSql=" AND   current_handle_unitid = "+departmentId;
		final String populationTypeSql=" AND (SELECT is_focus FROM fd_guest WHERE ID = guest_id) ="+populationType;
		String nSql = "\n";
		class getLawListConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = petitionWaySql;
				String cSql = timeTypeSql;
				String dSql=orgLevelSql;
				String eSql=belongToIdSql;
				String fSql=departmentIdSql;
				String gSql=populationTypeSql;
				String filterSql = "\n";

				if (!StringUtils.isEmpty(fuzzy)) {

					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (petitionWay!=0) {
					filterSql = aSql + bSql;
				} else {
					bSql = "";
				}
				if (!StringUtils.isEmpty(startTime)
						&& !StringUtils.isEmpty(endTime)) {
					filterSql = aSql + bSql + cSql;
				} else {
					cSql = "";
				}
				if(orgLevel!=0){
					filterSql = aSql + bSql + cSql+dSql;
				}else{
					dSql="";
				}
				if(belongToId!=0){
					filterSql = aSql + bSql + cSql+dSql+eSql;	
				}else{
					eSql="";
				}
				if(departmentId!=0){
					filterSql = aSql + bSql + cSql+dSql+eSql+fSql;
				}else{
					fSql="";
				}if(populationType!=4){
					filterSql = aSql + bSql + cSql+dSql+eSql+fSql+gSql;
				}else{
					gSql="";
				}
			return filterSql;
			}
		}
		String filterSql = new getLawListConcatSQl().result(nSql);
		String sql = "SELECT petition_names,id, guest_id,"
              +"(SELECT head_pic FROM fd_guest WHERE ID = guest_id) AS img,"
              +"(SELECT ethnic FROM fd_guest WHERE ID = guest_id) as ethnic,"
              +"(SELECT sex FROM fd_guest WHERE ID = guest_id) as sex,"
              +"(SELECT idcard FROM fd_guest WHERE ID = guest_id) as idcard,"
              +"(SELECT now_address FROM fd_guest WHERE ID = guest_id) as adress,"
              +"(select name from fd_code where code = question_belonging_to) as belongto,"
              +"(SELECT dw_mc FROM fc_dwb WHERE DW_ID =current_handle_unitid) as dwname,"
              +"(SELECT ry_mc FROM fc_ryb WHERE RY_ID=current_handle_personalid) as ryname,"
              +"(SELECT ry_img FROM fc_ryb WHERE RY_ID=current_handle_personalid) AS ry_img,"
              +"(SELECT ry_sfz FROM fc_ryb WHERE RY_ID=current_handle_personalid) AS RY_SFZ,"
              +"(SELECT ry_sjh FROM fc_ryb WHERE RY_ID =current_handle_personalid) as phone,"
              +"(SELECT name from fd_code where code=petition_way) as petitionWay,"
              +"(SELECT COUNT(*) FROM fd_case WHERE ID is NOT NULL "
              +filterSql
              + ") as total,"
              +"petition_way,question_belonging_to,gmt_create"
              +" FROM fd_case WHERE id is NOT NULL "
				+ filterSql+" order By  ID asc limit "+startPage*pageCount+","+pageCount;
		List<HomeListVO> homeList=new ArrayList<HomeListVO>();
		try {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if(list.size()>0&&!StringUtils.isEmpty(list)){
				for(int i=0;i<list.size();i++){
				HomeListVO hl=new HomeListVO();
				hl.setCaseId(StringUtils.isEmpty(list.get(i).get("id"))?0:Integer.valueOf(list.get(i).get("id").toString()));
				hl.setGuestId(StringUtils.isEmpty(list.get(i).get("guest_id"))?0:Integer.valueOf(list.get(i).get("guest_id").toString()));
				hl.setPetitionName(StringUtils.isEmpty(list.get(i).get("petition_names"))?null:list.get(i).get("petition_names").toString());
				hl.setCurrentHandleUnit(StringUtils.isEmpty(list
						.get(i).get("dwname")) ? null : list.get(i)
						.get("dwname").toString());
				hl.setCurrentHandleName(StringUtils.isEmpty(list
						.get(i).get("ryname")) ? null : list.get(i)
						.get("ryname").toString());
				hl.setGmtCreate(StringUtils.isEmpty(list.get(i)
						.get("gmt_create")) ? null : list.get(i)
						.get("gmt_create").toString());
				hl.setCaseBelongToAddress(StringUtils.isEmpty(list
						.get(i).get("belongto")) ? null : list.get(i)
						.get("belongto").toString());
				hl.setHandleUserHeadSrc(StringUtils.isEmpty(list
						.get(i).get("ry_img")) ? null : list.get(i).get("ry_img").toString());
				hl.setHandleUserHeadSrc(StringUtils.isEmpty(list
						.get(i).get("RY_SFZ")) ? null : list.get(i).get("RY_SFZ").toString());
				hl.setPetitionHeadSrc(StringUtils.isEmpty(list.get(
						i).get("head_pic")) ? null : list.get(i)
						.get("head_pic").toString());
				hl.setPetitionType(StringUtils.isEmpty(list.get(i)
						.get("petitionWay")) ? null : list.get(i).get("petitionWay")
						.toString());		
				hl.setEthnic(StringUtils.isEmpty(list.get(i).get("ethnic")) ? null : list.get(i).get("ethnic").toString());
				hl.setJzd(StringUtils.isEmpty(list.get(i).get("adress")) ? null : list.get(i).get("adress").toString());
				hl.setPetitionIdCard(StringUtils.isEmpty(list.get(i).get("idcard")) ? null : list.get(i).get("idcard").toString());
				homeList.add(hl);
				}
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
			}else{
				map.put("message", "没有满足条件的信息");
			}
		} catch (Exception e) {
		ApiLog.chargeLog1(e.getMessage());
		map.put("message", "获取信息失败");
		}
		return map;
	}
	@Override
	public Map<String, Object> getLedgeImportant(Integer caseId) {
		Map<String, Object> map=new HashMap<>();
		try {
			HomeListVO homeListVO=new HomeListVO();
			FdGuest FollowGuest=null;
			FcRybAllField fcRyb=null;
			FdCase fdCase=fdCaseRepository.findById(caseId);
			FdGuest fdGuest=fdGuestRepository.findOne(fdCase.getGuestId());
			/*if(!StringUtils.isEmpty(fdCase.getFollowGuestIds())){
				FollowGuest=fdGuestRepository.findOne(Integer.valueOf(fdCase.getFollowGuestIds()));	
			}*/
			if(fdCase.getCurrentHandlePersonalid()!=0){
				fcRyb=	fcRybAllFieldRepository.findOne(fdCase.getCurrentHandlePersonalid());
			}
 		 /*homeListVO.setPetitionHeadSrc(fdGuest.getHeadPic());
           homeListVO.setPetitionCode(fdCase.getPetitionCode());
             homeListVO.setPetitionName(fdCase.getPetitionNames()); // 信访人员
            
             homeListVO.setPetitionType(fdCase.getPetitionWay()==0?null:fdCodeRepository.getNameByCode(fdCase.getPetitionWay()));
             homeListVO.setCaseBelongToAddress(fdCase.getQuestionBelongingTo()==0?null:fdCodeRepository.getNameByCode(fdCase.getQuestionBelongingTo()));
             homeListVO.setCaseDesc(fdCase.getCaseDesc());
             homeListVO.setCurrentHandleUnit(fdCase.getCurrentHandleUnitid()==0?null:fdCodeRepository.getNameByCode(fdCase.getCurrentHandleUnitid()));
             homeListVO.setPetitionTime(fdCase.getPetitionTime());
             homeListVO.setCurrentHandleName(fdCase.getCurrentHandlePersonalid()==0?null:fdCodeRepository.getNameByCode(fdCase.getCurrentHandlePersonalid()));
             homeListVO.setHjd(fdGuest.getContactAddress());
             homeListVO.setPetitionIdCard(fdGuest.getPhone());
             homeListVO.setRyPhone(fcRyb.getRySjh());
             homeListVO.setEthnic(fdGuest.getEthnic());
             homeListVO.setSex(fdGuest.getSex()==0?"男":"女");
             homeListVO.setSksl(fdCase.getAssociatedNumbers()==0?"否":"是");
             homeListVO.setSfName(FollowGuest.getUsername());
             homeListVO.setSfidCard(FollowGuest.getIdcard());
             homeListVO.setYj(fdCase.getIsAbove()==0?"否":"是");
             homeListVO.setXfcj(fdGuest.getPetitionWayParent()==0?null:fdCodeRepository.getNameByCode(fdGuest.getPetitionWayParent()));*/
			/*fdCase = fdCaseRepository.findById(caseId);*/
			Integer questionBlong = fdCase.getQuestionBelongingTo();
			Integer questionType = fdCase.getQuestionType();
			Integer questionHot = fdCase.getQuestionHot();
			Integer caseType = fdCase.getCaseType();
			Integer blongToSys = fdCase.getBelongToSys();
			Integer thirteenCategories = fdCase.getThirteenCategories();
			Integer petitionWhy = fdCase.getPetitionWhy();
			Integer petitionPurpose = fdCase.getPetitionPurpose();
			Integer petitionNature = fdCase.getPetitionNature();
			Integer exposeObject = fdCase.getExposeObject();
			Integer petitionCounty = fdCase.getPetitionCounty();
			Integer petitionWay = fdCase.getPetitionWay();

			fdCase.setStrQuestionBelongTo(questionBlong != null ? fdCaseRepository
					.findNameByCode(questionBlong).getName() : null);
			fdCase.setStrBelongToSys(blongToSys!=null?fdCaseRepository.findNameByCode(
					fdCase.getBelongToSys()).getName():null);
			fdCase.setStrCaseType(caseType!=null?fdCaseRepository.findNameByCode(
					fdCase.getCaseType()).getName():null);
			fdCase.setStrExposeObject(exposeObject!=null?fdCaseRepository.findNameByCode(
					fdCase.getExposeObject()).getName():null);
			fdCase.setStrPetitionCountry(petitionCounty!=null?fdCaseRepository.findNameByCode(
					fdCase.getPetitionCounty()).getName():null);
			fdCase.setStrPetitionNature(petitionNature!=null?fdCaseRepository.findNameByCode(
					fdCase.getPetitionNature()).getName():null);
			fdCase.setStrPetitionPurpose(petitionPurpose!=null?fdCaseRepository.findNameByCode(
					fdCase.getPetitionPurpose()).getName():null);
			fdCase.setStrPetitionWay(petitionWay!=null?fdCaseRepository.findNameByCode(
					fdCase.getPetitionWay()).getName():null);
			fdCase.setStrPetitionWhy(petitionWhy!=null?fdCaseRepository.findNameByCode(
					fdCase.getPetitionWhy()).getName():null);
			fdCase.setStrQuestionHot(questionHot!=null?fdCaseRepository.findNameByCode(
					fdCase.getQuestionHot()).getName():null);
			fdCase.setStrQusetionType(questionType!=null?fdCaseRepository.findNameByCode(
					fdCase.getQuestionType()).getName():null);
			fdCase.setStrThirteenCategories(thirteenCategories!=null?fdCaseRepository.findNameByCode(
					fdCase.getThirteenCategories()).getName():null);
    
             map.put("dataFdCase", fdCase);
             map.put("dataFdGuet", fdGuest);
          /*   map.put("FollowGuestName", FollowGuest.getUsername());
             map.put("FollowGuestIdCard", FollowGuest.getIdcard());
             map.put("currentName", fcRyb.getRyMc());*/
			
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}
		
		return map;
	}
	@Override
	public Map<String, Object> getCaseDetailById(Integer caseId) {
		Map<String, Object> map=new HashMap<>();
		HomeListVO homeListVo=new HomeListVO();
		FdGuest fdGuest=null;
		String sql="select * from(SELECT DISTINCT  f.*,"
				    + "(SELECT gmt_create FROM fd_case WHERE guest_id= f.guest_id AND is_repeat_petition =1) as resultTime,"
					+"(SELECT file_src FROM fd_case WHERE guest_id= f.guest_id AND is_repeat_petition =1) as result,"
				    +"(SELECT username FROM fd_guest WHERE id =f.follow_guest_ids) AS sfName,"
					+"(SELECT idcard FROM fd_guest WHERE id=f.follow_guest_ids) as sfidcard,"
					+"(SELECT ry_mc FROM fc_ryb where ry_id = f.current_handle_personalid) as ryname,"
					+"(SELECT ry_sjh FROM fc_ryb where ry_id= f.current_handle_personalid) as ryphone,"
					+"(SELECT dw_mc from fc_dwb WHERE dw_id =f.current_handle_unitid) as dwName"
					+" FROM fd_case as f WHERE f.id="+caseId+") as detail";

		try {
			
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			System.err.println(sql);
 			Integer questionBlong = StringUtils.isEmpty(list.get(0).get("question_belonging_to"))?0:Integer.valueOf(list.get(0).get("question_belonging_to").toString());
			Integer blongToSys = StringUtils.isEmpty(list.get(0).get("belong_to_sys"))?0:Integer.valueOf(list.get(0).get("belong_to_sys").toString());
			Integer petitionPurpose = StringUtils.isEmpty(list.get(0).get("petition_purpose"))?0:Integer.valueOf(list.get(0).get("petition_purpose").toString());
			Integer petitionWay = StringUtils.isEmpty(list.get(0).get("petition_way"))?0:Integer.valueOf(list.get(0).get("petition_way").toString()); 
			Integer petitionNature=StringUtils.isEmpty(list.get(0).get("petition_nature"))?0:Integer.valueOf(list.get(0).get("petition_nature").toString());
			System.err.println(list.get(0).get("is_across_from"));
			if(Integer.valueOf(list.get(0).get("guest_id").toString())!=0){
				fdGuest=fdGuestRepository.getGuestById(Integer.valueOf(list.get(0).get("guest_id").toString()));
				System.err.println(fdGuest.getPetitionWayParent());
			}
			homeListVo.setPetitionName(StringUtils.isEmpty(list.get(0).get("petition_names"))?null:list.get(0).get("petition_names").toString());
			
			homeListVo.setSex(StringUtils.isEmpty(fdGuest.getSex())?null:fdGuest.getSex()==0?"女":"男");
			
			homeListVo.setEthnic(StringUtils.isEmpty(fdGuest.getEthnic())?null:fdGuest.getEthnic());
			
			homeListVo.setPetitionIdCard(StringUtils.isEmpty(fdGuest.getIdcard())?null:fdGuest.getIdcard());
			
			homeListVo.setWork(StringUtils.isEmpty(fdGuest.getEmployedInfo())?null:fdGuest.getEmployedInfo());
			//			homeListVo.setPetitionType(StringUtils.isEmpty(list.get(0).get("petition_way"))?null:fdCodeRepository.getNameByCode(Integer.valueOf(list.get(0).get("petition_way").toString())));
			homeListVo.setPetitionType(petitionWay==0?null:fdCodeRepository.getNameByCode(petitionWay));
			
			homeListVo.setPetitionnumbers(StringUtils.isEmpty(list.get(0).get("petition_numbers"))?null:list.get(0).get("petition_numbers").toString());
 //    		homeListVo.setBelongTosys(StringUtils.isEmpty(list.get(0).get("belong_to_sys"))?null:fdCodeRepository.getNameByCode(Integer.valueOf(list.get(0).get("belong_to_sys").toString())));
			homeListVo.setBelongTosys(blongToSys==0?null:fdCodeRepository.getNameByCode(blongToSys));
			homeListVo.setPetitionPhone(StringUtils.isEmpty(fdGuest.getPhone())?null:fdGuest.getPhone());
// 			homeListVo.setPetitionpurpose(StringUtils.isEmpty(list.get(0).get("petition_purpose"))?null:fdCodeRepository.getNameByCode(Integer.valueOf(list.get(0).get("petition_purpose").toString())));
    		homeListVo.setPetitionpurpose(petitionPurpose==0?null:fdCodeRepository.getNameByCode(petitionPurpose));
			homeListVo.setPetitionImg(StringUtils.isEmpty(fdGuest.getHeadPic())?null:fdGuest.getHeadPic());
			homeListVo.setJzd(StringUtils.isEmpty(fdGuest.getCensusRegister())?null:fdGuest.getCensusRegister());
			homeListVo.setHjd(StringUtils.isEmpty(fdGuest.getContactAddress())?null:fdGuest.getContactAddress());
//     		homeListVo.setCaseBelongToAddress(StringUtils.isEmpty(list.get(0).get("question_belonging_to"))?null:fdCodeRepository.getNameByCode(Integer.valueOf(list.get(0).get("question_belonging_to").toString())));
			homeListVo.setCaseBelongToAddress(questionBlong==0?null:fdCodeRepository.getNameByCode(questionBlong));
			homeListVo.setSfName(StringUtils.isEmpty(list.get(0).get("sfName"))?null:list.get(0).get("sfName").toString());
			homeListVo.setSfidCard(StringUtils.isEmpty(list.get(0).get("sfidcard"))?null:list.get(0).get("sfidcard").toString());
			homeListVo.setHistoryTime(StringUtils.isEmpty(list.get(0).get("resultTime"))?null:list.get(0).get("resultTime").toString());
			homeListVo.setHistoryDesc(StringUtils.isEmpty(list.get(0).get("result"))?null:list.get(0).get("result").toString());
			homeListVo.setFirstTime(StringUtils.isEmpty(list.get(0).get("gmt_create"))?null:list.get(0).get("gmt_create").toString());
			homeListVo.setRyPhone(StringUtils.isEmpty(list.get(0).get("ryphone"))?null:list.get(0).get("ryphone").toString());
			homeListVo.setCurrentHandleName(StringUtils.isEmpty(list.get(0).get("ryname"))?null:list.get(0).get("ryname").toString());
			homeListVo.setCurrentHandleUnit(StringUtils.isEmpty(list.get(0).get("dwName"))?null:list.get(0).get("dwName").toString());
			homeListVo.setCaseDesc(StringUtils.isEmpty(list.get(0).get("case_desc"))?null:list.get(0).get("case_desc").toString());
    		homeListVo.setXfcj(fdGuest.getPetitionWayParent()==null?null:fdCodeRepository.getNameByCode(fdGuest.getPetitionWayParent()));
			homeListVo.setSksl(StringUtils.isEmpty(list.get(0).get("is_across_from"))?0:list.get(0).get("is_across_from").equals(true)?1:0);
			homeListVo.setYj(StringUtils.isEmpty(list.get(0).get("is_above"))?0:list.get(0).get("is_above").equals(true)?1:0);
			homeListVo.setBacklog(StringUtils.isEmpty(list.get(0).get("is_backlog"))?0:list.get(0).get("is_backlog").equals(true)?1:0);
			homeListVo.setPetitionNature(petitionNature==0?null:fdCodeRepository.getNameByCode(petitionNature));
			homeListVo.setDesc(StringUtils.isEmpty(list.get(0).get("case_desc"))?null:list.get(0).get("case_desc").toString());
			map.put("data", homeListVo);
		
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
		
	}
	
	
	
}
