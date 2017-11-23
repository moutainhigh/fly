package com.xinfang.flowcontrol.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FcQxsbRepository;
import com.xinfang.dao.UserRepository;
import com.xinfang.flowcontrol.service.FlowControlService;
import com.xinfang.flowcontrol.vo.CaseDeatilListVO;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FcDwb;
import com.xinfang.model.FcQxsb;
import com.xinfang.model.FcRyb;
import com.xinfang.utils.DateUtils;

@Service("flowControlService")
public class FlowControlServiceImpl implements FlowControlService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FcDwbRepository fcDwbRepository;
	@Autowired
	private FcQxsbRepository fcQxsbRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> flowControlCount(Integer signUserId,
			final String startTime, final String endTime,
			final Integer sponsorUnitId, final Integer handleUnitId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int authority = (int) authority(signUserId).get("type");
			String userSql = "";
			if (authority == 1) {
				userSql = "";
			} else if (authority == 2) {
				userSql = " AND current_handle_unitid IN (SELECT DW_ID FROM fc_dwb WHERE QXS_ID="
						+ Integer.valueOf(authority(signUserId).get("id")
								.toString()) + ")";
			} else if (authority == 3) {
				userSql = " AND current_handle_unitid="
						+ Integer.valueOf(authority(signUserId).get("dwId")
								.toString());
			}
			final String timeSql = " AND gmt_create BETWEEN '" + startTime
					+ "' AND '" + endTime + "'";
			final String sponsorUnitSql = " AND create_unitid ="
					+ sponsorUnitId;
			final String handleUnitSql = " AND current_handle_unitid ="
					+ handleUnitId;
			class getListByisRepeatPetitionConcatSQl {
				public String result() {
					String aSql = timeSql;
					String bSql = sponsorUnitSql;
					String cSql = handleUnitSql;

					String filterSql = " ";

					if (handleUnitId != 0) {
						filterSql = cSql;
					} else {
						cSql = "";
					}
					if (sponsorUnitId != 0) {
						filterSql = cSql + bSql;
					} else {
						bSql = "";
					}

					if (!StringUtils.isEmpty(startTime)
							&& !StringUtils.isEmpty(endTime)) {
						filterSql = aSql + cSql + bSql;
					} else {
						aSql = "";
					}
					return filterSql;
				}
			}
			String filterSql = new getListByisRepeatPetitionConcatSQl()
					.result();
			// 办理中
			String sql = "SELECT COUNT(*)AS total,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.5) DAY)>= NOW() AND case_handle_state<>2003 AND handle_days is not null "+userSql+filterSql+" )  as normaltotal,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.51) DAY)<= NOW() AND DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.75) DAY)>= NOW() AND case_handle_state<>2003 AND handle_days is not null "+userSql+filterSql+" ) as earlywarn,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.76) DAY)<= NOW() AND DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.99) DAY)>= NOW() AND case_handle_state<>2003 AND handle_days is not null "+userSql+filterSql+" ) as warn,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1) DAY)<= NOW() AND DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)>= NOW() AND  case_handle_state<>2003 AND handle_days is not null "+userSql+filterSql+" ) as seriousWarn"
					+ " FROM fd_case WHERE case_handle_state <> 2003 AND handle_days is not null "+userSql+filterSql;
			System.err.println("\n"+sql);
			List<Map<String, Object>> processingList=jdbcTemplate.queryForList(sql);
			Map<String, Object> processingMap=new HashMap<>();
			Integer processingMapTotal=Integer.valueOf( processingList.get(0).get("normaltotal").toString())+
					 Integer.valueOf(processingList.get(0).get("earlywarn").toString())+
					 Integer.valueOf(processingList.get(0).get("warn").toString())
					+ Integer.valueOf(processingList.get(0).get("seriousWarn").toString());
			processingMap.put("total", Integer.valueOf( processingList.get(0).get("normaltotal").toString())+
					 Integer.valueOf(processingList.get(0).get("earlywarn").toString())+
					 Integer.valueOf(processingList.get(0).get("warn").toString())
					+ Integer.valueOf(processingList.get(0).get("seriousWarn").toString()));
			processingMap.put("normaltotal", processingList.get(0).get("normaltotal"));
			processingMap.put("earlywarn", processingList.get(0).get("earlywarn"));
			processingMap.put("warn", processingList.get(0).get("warn"));
			processingMap.put("seriousWarn", processingList.get(0).get("seriousWarn"));
			map.put("processingData", processingMap);
			 sql = "SELECT COUNT(*)AS total,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.5) DAY)>= f.handle_fact_end_time AND case_handle_state=2003 "+userSql+filterSql+" AND handle_days is not null)  as normaltotal,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.51) DAY)<= f.handle_fact_end_time AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.71) DAY)>= f.handle_fact_end_time AND case_handle_state=2003 "+userSql+filterSql+" AND handle_days is not null ) as earlywarn,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.76) DAY)<= f.handle_fact_end_time AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.99) DAY)>= handle_fact_end_time AND case_handle_state=2003 "+userSql+filterSql+" AND handle_days is not null ) as warn,"
					+ " (SELECT COUNT(*) FROM fd_case as f WHERE  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1) DAY)<= handle_fact_end_time AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)>= handle_fact_end_time AND case_handle_state=2003 "+userSql+filterSql+" AND handle_days is not null ) as seriousWarn"
					+ " FROM fd_case WHERE case_handle_state = 2003 AND handle_days is not null AND handle_fact_end_time is not null "+userSql+filterSql;
			 System.err.println("\n"+sql);
			 List<Map<String, Object>> haveGoneThroughList=jdbcTemplate.queryForList(sql);
			Map<String, Object> haveGoneThroughMap=new HashMap<>();
			Integer haveGoneThroughMapTotal=Integer.valueOf(haveGoneThroughList.get(0).get("normaltotal").toString())+
					Integer.valueOf(haveGoneThroughList.get(0).get("earlywarn").toString())+
					Integer.valueOf(haveGoneThroughList.get(0).get("warn").toString())+
					Integer.valueOf(haveGoneThroughList.get(0).get("seriousWarn").toString());
			
			haveGoneThroughMap.put("total", Integer.valueOf(haveGoneThroughList.get(0).get("normaltotal").toString())+
					Integer.valueOf(haveGoneThroughList.get(0).get("earlywarn").toString())+
					Integer.valueOf(haveGoneThroughList.get(0).get("warn").toString())+
					Integer.valueOf(haveGoneThroughList.get(0).get("seriousWarn").toString()));
			haveGoneThroughMap.put("normaltotal", haveGoneThroughList.get(0).get("normaltotal"));
			haveGoneThroughMap.put("earlywarn", haveGoneThroughList.get(0).get("earlywarn"));
			haveGoneThroughMap.put("warn", haveGoneThroughList.get(0).get("warn"));
			haveGoneThroughMap.put("seriousWarn", haveGoneThroughList.get(0).get("seriousWarn"));
			map.put("haveGoneThrough", haveGoneThroughMap);
			/*sql=" SELECT COUNT(*) as total,"
			 + " (SELECT COUNT(*) FROM fd_case WHERE  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)<= NOW() AND "
			 + " current_handle_state<>2003 AND handle_days is not null "+userSql+filterSql+") as processingabnormal,(SELECT COUNT(*) "
			 + " FROM fd_case WHERE  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)<= handle_fact_end_time AND current_handle_state=2003 AND handle_days is not null"+userSql+filterSql+") as "
			 + " haveGoneThroughabnormal FROM fd_case WHERE handle_days is NOT NULL" +userSql+filterSql;
			System.err.println("\n"+sql);
			List<Map<String, Object>> abnormalList=jdbcTemplate.queryForList(sql);*/
		/* map.put("total", abnormalList.get(0).get("total"));
		 map.put("processingabnormal", abnormalList.get(0).get("processingabnormal"));
		 map.put("haveGoneThroughabnormal", abnormalList.get(0).get("haveGoneThroughabnormal"));*/
		Integer processingAbnormal=Integer.valueOf(abnormalList(signUserId, 1, 0, 10000).get("total").toString());
		Integer haveGoneThroughAbnormal=Integer.valueOf(abnormalList(signUserId, 2, 0, 10000).get("total").toString());
		
		map.put("total", processingMapTotal+haveGoneThroughMapTotal+processingAbnormal+haveGoneThroughAbnormal);
		map.put("processingabnormal",processingAbnormal);
		map.put("haveGoneThroughabnormal", haveGoneThroughAbnormal);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	@Override
	public Map<String, Object> caseDeatilList(Integer signUserId, final String fuzzy,
			final String startTime, final String endTime, Integer state,
			final Integer petitionWay, Integer type, Integer startPage,
			Integer pageCount) {
		Map<String, Object> map = new HashMap<>();
		try {
				int authority = (int) authority(signUserId).get("type");
				String userSql = "";
				if (authority == 1) {
					userSql = "";
				} else if (authority == 2) {
					userSql = " AND current_handle_unitid IN (SELECT DW_ID FROM fc_dwb WHERE QXS_ID="
							+ Integer.valueOf(authority(signUserId).get("id")
									.toString()) + ")";
				} else if (authority == 3) {
					userSql = " AND current_handle_unitid="
							+ Integer.valueOf(authority(signUserId).get("dwId")
									.toString());
				}
				String stateSql="";
				String typeSql="";
				if(type.equals(1)){
					 typeSql=" AND case_handle_state<>2003 AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)>= NOW() ";
					 if(state.equals(1)){
							stateSql=" AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.5) DAY)>= NOW() ";
						}else if(state.equals(2)){
							stateSql=" AND   DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.51) DAY)<= NOW() AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.75) DAY)>= NOW() ";
						}else if(state.equals(3)){
							stateSql=" AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.76) DAY)<= NOW() AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.99) DAY)>= NOW() ";
						}else if(state.equals(4)){
							stateSql=" AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1) DAY)<= NOW() ";
						}				
				}else if(type.equals(2)){
					typeSql=" AND case_handle_state=2003 AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)>= handle_fact_end_time ";
					if(state.equals(1)){
					 	stateSql=" AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.5) DAY)>= handle_fact_end_time ";
					}else if(state.equals(2)){
						stateSql=" AND  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.51) DAY)<= handle_fact_end_time  AND DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.75) DAY)>= handle_fact_end_time  ";
					}else if(state.equals(3)){
						stateSql=" AND DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.76) DAY)<= handle_fact_end_time  AND DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*0.99) DAY)>= handle_fact_end_time  ";
					}else if(state.equals(4)){
						stateSql=" AND DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1) DAY)>= handle_fact_end_time  ";
					}
				}
				
			final String timeSql=" AND gmt_create BETWEEN '" + startTime
					+ "' AND '" + endTime + "'";
			final String fuzzySql=" AND (petition_names like '%"+fuzzy+"%' "
					+ " OR petition_code like '%"+fuzzy+"%'"
							+ " OR question_belonging_to in (select code from fd_code where type =15 and name like '%"+fuzzy+"%'))";
			final String petitionWaySql=" AND petition_way = "+petitionWay;
			  
			class getListByisRepeatPetitionConcatSQl {
				public String result() {
					String aSql = timeSql;
					String bSql = fuzzySql;
					String cSql = petitionWaySql;

					String filterSql = " ";

					if (petitionWay != 0) {
						filterSql = cSql;
					} else {
						cSql = "";
					}
					if (!StringUtils.isEmpty(fuzzy)) {
						filterSql = cSql + bSql;
					} else {
						bSql = "";
					}

					if (!StringUtils.isEmpty(startTime)
							&& !StringUtils.isEmpty(endTime)) {
						filterSql = aSql + cSql + bSql;
					} else {
						aSql = "";
					}
					return filterSql;
				}
			}
			String filterSql = new getListByisRepeatPetitionConcatSQl()
					.result();						
			String sql="SELECT id, petition_code,petition_names,handle_fact_end_time,"
					+ " (SELECT COUNT(*) FROM fd_case WHERE  "
					+ "   handle_days is NOT null"+userSql+typeSql+filterSql+stateSql+") as total,"
					+ " (SELECT name FROM fd_code WHERE `code` = petition_way) as petitionWay,gmt_create,handle_days,"
					+ " (SELECT NAME FROM fd_code WHERE `CODE` =question_belonging_to) as adress,case_desc,"
					+ " (SELECT dw_mc FROM fc_dwb WHERE dw_id=current_handle_unitid) as dwName "
					+ " FROM fd_case WHERE  "
					+ "   handle_days is NOT null "+userSql+typeSql+filterSql+stateSql+" order by id LIMIT "+startPage*pageCount+","+pageCount;
			System.err.println(sql);
			List<Map<String, Object>> listMap=jdbcTemplate.queryForList(sql);
			List<CaseDeatilListVO> caseDeatilListVOs=new ArrayList<>();
			if(listMap.size()>0&&!StringUtils.isEmpty(listMap)){
			for(int i=0;i<listMap.size();i++){
				CaseDeatilListVO caseDeatilListVO=new CaseDeatilListVO();
				caseDeatilListVO.setCaseId(StringUtils.isEmpty(listMap.get(i).get("id"))?0:Integer.valueOf(listMap.get(i).get("id").toString()));
				caseDeatilListVO.setBelongtoAdress(StringUtils.isEmpty(listMap.get(i).get("adress"))?null:listMap.get(i).get("adress").toString());
				caseDeatilListVO.setCurrentUnit(StringUtils.isEmpty(listMap.get(i).get("dwName"))?null:listMap.get(i).get("dwName").toString());
				caseDeatilListVO.setDesc(StringUtils.isEmpty(listMap.get(i).get("case_desc"))?null:listMap.get(i).get("case_desc").toString());
				caseDeatilListVO.setGmtTime(StringUtils.isEmpty(listMap.get(i).get("gmt_create"))?null:listMap.get(i).get("gmt_create").toString());
				caseDeatilListVO.setPetitionCode(StringUtils.isEmpty(listMap.get(i).get("petition_code"))?null:listMap.get(i).get("petition_code").toString());
				caseDeatilListVO.setPetitionNames(StringUtils.isEmpty(listMap.get(i).get("petition_names"))?null:listMap.get(i).get("petition_names").toString());
				caseDeatilListVO.setPetitionWays(StringUtils.isEmpty(listMap.get(i).get("petitionWay"))?null:listMap.get(i).get("petitionWay").toString());
				if(type.equals(1)){
				caseDeatilListVO.setState(StringUtils.isEmpty(listMap.get(i).get("handle_days"))?null:getWarnProcessing(Integer.valueOf(listMap.get(i).get("handle_days").toString()),listMap.get(i).get("gmt_create").toString()));
				}else if(type.equals(2)){
					caseDeatilListVO.setState(StringUtils.isEmpty(listMap.get(i).get("handle_days"))?null:getWarnhaveGoneThrough(Integer.valueOf(listMap.get(i).get("handle_days").toString()),listMap.get(i).get("gmt_create").toString(),listMap.get(i).get("handle_fact_end_time").toString()));
				}
				caseDeatilListVOs.add(caseDeatilListVO);
			}
			 map.put("data", caseDeatilListVOs);
			 map.put("pageIndex", startPage);
				map.put("pageNum",
						Integer.valueOf(listMap.get(0).get("total").toString())
								% pageCount == 0 ? Integer.valueOf(listMap.get(0)
								.get("total").toString())
						
								/ pageCount : Integer.valueOf(listMap.get(0)
								.get("total").toString())
								/ pageCount + 1);
				map.put("resultTotal",
						StringUtils.isEmpty(listMap.get(0).get("total")) ? 0
								: Integer.valueOf(listMap.get(0).get("total")
										.toString()));
				map.put("pageSize", pageCount);
			}else{
				map.put("data", "没有满足条件的信息");
			}
		} catch (Exception e) {
		ApiLog.chargeLog1(e.getMessage());
		System.out.println(e.getMessage());
		map.put("message", "获取信息出错");
		}
		return map;
	}

	@Override
	public Map<String, Object> abnormalList(Integer signUserId, Integer type,

			Integer startPage, Integer pageCount) {
		Map<String, Object> map = new HashMap<>();
		try {
			int authority = (int) authority(signUserId).get("type");
			String userSql = "";
			if (authority == 1) {
				userSql = "";
			} else if (authority == 2) {
				userSql = " AND current_handle_unitid IN (SELECT DW_ID FROM fc_dwb WHERE QXS_ID="
						+ Integer.valueOf(authority(signUserId).get("id")
								.toString()) + ")";
			} else if (authority == 3) {
				userSql = " AND current_handle_unitid="
						+ Integer.valueOf(authority(signUserId).get("dwId")
								.toString());
			}
			String typeSql="";
			
			if(type.equals(1)){
				typeSql=" AND case_handle_state<>2003 AND  "
					+ "  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)<= NOW() ";
			}else if(type.equals(2)){
				typeSql=" AND case_handle_state=2003 AND "
				+ "  DATE_ADD(gmt_create,INTERVAL FLOOR(handle_days*1.5) DAY)<= handle_fact_end_time ";

			}
			String sql="SELECT DISTINCT current_handle_unitid,"
					+" (SELECT qxs_mc from fc_qxsb where qxs_id=(select qxs_id from fc_dwb where dw_id=f.create_unitid)) as qxsName,"
					+ " (SELECT GROUP_CONCAT(id) FROM fd_case  WHERE  "
					+ "  handle_days is NOT NULL  "+typeSql+userSql
					+ " AND current_handle_unitid=f.current_handle_unitid) as idlist,"
					+ " (SELECT COUNT(*) FROM fd_case WHERE "
					+ "  handle_days is NOT NULL AND  current_handle_unitid=f.current_handle_unitid "+typeSql+userSql
					+ " )AS total,(SELECT dw_mc FROM fc_dwb WHERE dw_id= current_handle_unitid) as unitname "
					+ " FROM fd_case as f"
					+ " WHERE handle_days is NOT NULL "+typeSql+userSql;
			System.err.println(sql);
			List<Map<String, Object>> abnormalList=jdbcTemplate.queryForList(sql);
			if(abnormalList.size()>0&& !StringUtils.isEmpty(abnormalList)){
				List<Map<String, Object>> list=new ArrayList<>();
				Integer total=0;
				for(int i=0;i<abnormalList.size();i++){
				Map<String, Object> abnormalMap=new HashMap<>();
				abnormalMap.put("caseIdList", StringUtils.isEmpty(abnormalList.get(i).get("idlist"))?null:abnormalList.get(i).get("idlist").toString());
				abnormalMap.put("unitId", StringUtils.isEmpty(abnormalList.get(i).get("current_handle_unitid"))?0:Integer.valueOf(abnormalList.get(i).get("current_handle_unitid").toString()));
				abnormalMap.put("unitName", StringUtils.isEmpty(abnormalList.get(i).get("unitname"))?null:abnormalList.get(i).get("unitname").toString());
				abnormalMap.put("qxsName", StringUtils.isEmpty(abnormalList.get(i).get("qxsName"))?null:abnormalList.get(i).get("qxsName").toString());
				abnormalMap.put("abnormalTotal", StringUtils.isEmpty(abnormalList.get(i).get("total"))?null:Integer.valueOf(abnormalList.get(i).get("total").toString()));
				total+=Integer.valueOf(abnormalList.get(i).get("total").toString());
				list.add(abnormalMap);
				}
				map.put("data", list);
				map.put("total", total);
			}else {
				map.put("data", "没有满足条件的信息");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	@Override
	public Map<String, Object> getDeptListByUserId(Integer signUserId) {
		Map<String, Object> map = new HashMap<>();

		try {
			if ((int) authority(signUserId).get("type") == 1) {
				List<Map> listMap = new ArrayList<>();
				List<FcQxsb> qxsList = fcQxsbRepository.findAll();
				for (FcQxsb fcQxsb : qxsList) {
					Map<String, Object> qxsMap = new HashMap<>();
					qxsMap.put("qxsName", fcQxsb.getQxsMc());
					qxsMap.put("qxsId", fcQxsb.getQxsId());
					List<FcDwb> dwList = fcDwbRepository
							.getDwListByQxsId(fcQxsb.getQxsId());
					List<Map> deptMapList = new ArrayList<Map>();
					for (FcDwb fcDwb : dwList) {
						Map<String, Object> dwMap = new HashMap<>();
						dwMap.put("dwName", fcDwb.getDwMc());
						dwMap.put("dwId", fcDwb.getDwId());
						deptMapList.add(dwMap);
					}
					qxsMap.put("children", deptMapList);
					listMap.add(qxsMap);
				}
				map.put("data", listMap);
			} else if ((int) authority(signUserId).get("type") == 2) {
				List<Map> listMap = new ArrayList<>();
				FcQxsb fcQxsb = fcQxsbRepository.findOne(Integer
						.valueOf(authority(signUserId).get("id").toString()));
				Map<String, Object> qxsMap = new HashMap<>();
				qxsMap.put("qxsName", fcQxsb.getQxsMc());
				qxsMap.put("qxsId", fcQxsb.getQxsId());
				List<FcDwb> dwList = fcDwbRepository.getDwListByQxsId(fcQxsb
						.getQxsId());
				List<Map> deptMapList = new ArrayList<Map>();
				for (FcDwb fcDwb : dwList) {
					Map<String, Object> dwMap = new HashMap<>();
					dwMap.put("dwName", fcDwb.getDwMc());
					dwMap.put("dwId", fcDwb.getDwId());
					deptMapList.add(dwMap);
				}
				qxsMap.put("children", deptMapList);
				listMap.add(qxsMap);
				map.put("data", listMap);
			} else if ((int) authority(signUserId).get("type") == 3) {
				map.put("data", null);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	// 登录人ID判断
	private Map<String, Object> authority(Integer signUserId) {
		Map<String, Object> map = new HashMap<>();
		FcRyb fcRyb = userRepository.findOne(signUserId);
		FcDwb fcDwb = fcDwbRepository.findOne(fcRyb.getDwId());
		if (fcRyb.getDwId() == 761) {
			map.put("type", 1);
			return map;
		} else if (fcDwb.getDwType() == 7) {
			map.put("id", fcDwb.getQxsId());
			map.put("type", 2);
			return map;
		} else if (fcRyb.getDwId() != 761
				&& fcDwbRepository.findOne(fcRyb.getDwId()).getDwType() != 7) {
			map.put("dwId", fcDwbRepository.findOne(fcRyb.getDwId()).getDwId());
			map.put("type", 3);
			return map;
		}

		return map;
	}
	//判断状态（正常 预警  办理中）
	private String getWarnProcessing(Integer handleDays,String gmtCreate){		
		String result =null;
		try {		
		Date now = new Date();
		if(handleDays!=null){
		Float normal = handleDays*0.5f;//正常
		Float warn = handleDays*0.76f;//预警
		Float warn1 = handleDays*0.99f;//警告
		Float warn2 = handleDays*1.5f;//严重警告
		//Float warn3 = limitdaynum;//超期
		Date showdTime = DateUtils.add(DateUtils.parseDateTime(gmtCreate), normal);
		Date showdTime1 = DateUtils.add(DateUtils.parseDateTime(gmtCreate), warn);
		Date showdTime2 = DateUtils.add(DateUtils.parseDateTime(gmtCreate), warn1);
		Date showdTime3 = DateUtils.add(DateUtils.parseDateTime(gmtCreate), warn2);
		//Date showdTime4 = DateUtils.add(DateUtils.strToDate2(startTime), warn3);
		if(now.getTime()<=showdTime.getTime()){
			result="正常";
		}else if(now.getTime()<=showdTime1.getTime()){
			result="预警";
		}else if(now.getTime()<=showdTime2.getTime()){
			result="警告";
		}else if(now.getTime()<=showdTime3.getTime()){
			result="严重警告";
		}else{
			result="异常";
		}
		}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
		}
	return result;
}

	//判断状态（正常 预警  已办结）
	private String getWarnhaveGoneThrough(Integer handleDays,String gmtCreate,String handleEndTime){		
		String result =null;
		try {		
		Date now = new Date();
		if(handleDays!=null){
		Float normal = handleDays*0.5f;//正常
		Float warn = handleDays*0.76f;//预警
		Float warn1 = handleDays*0.99f;//警告
		Float warn2 = handleDays*1.5f;//严重警告
		//Float warn3 = limitdaynum;//超期
		Date showdTime = DateUtils.add(DateUtils.parseDateTime(gmtCreate), normal);
		Date showdTime1 = DateUtils.add(DateUtils.parseDateTime(gmtCreate), warn);
		Date showdTime2 = DateUtils.add(DateUtils.parseDateTime(gmtCreate), warn1);
		Date showdTime3 = DateUtils.add(DateUtils.parseDateTime(gmtCreate), warn2);
		//Date showdTime4 = DateUtils.add(DateUtils.strToDate2(startTime), warn3);
		if(DateUtils.parseDateTime(handleEndTime).getTime()<=showdTime.getTime()){
			result="正常";
		}else if(DateUtils.parseDateTime(handleEndTime).getTime()<=showdTime1.getTime()){
			result="预警";
		}else if(DateUtils.parseDateTime(handleEndTime).getTime()<=showdTime2.getTime()){
			result="警告";
		}else if(DateUtils.parseDateTime(handleEndTime).getTime()<=showdTime3.getTime()){
			result="严重警告";
		}else{
			result="异常";
		}
		}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
		}
	return result;
}
}
