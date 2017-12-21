package com.xinfang.foursectionsdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.VO.HomeListVO;
import com.xinfang.foursectionsdata.service.FourSectionsDataService;
import com.xinfang.log.ApiLog;

@Service
public class FourSectionsDataServiceImpl implements FourSectionsDataService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FdCodeRepository fdCodeRepository;
	

	@Override
      	public Map<String, Object> getListByisRepeatPetition(
			Integer isRepeatPetition, Integer startpage, Integer pageCount,
			final Integer unitId, final Integer belongTo, final String fuzzy, final Integer petitionWay,final String startTime,final String endTime) {
		final String fuzzySql = "AND (petition_names like '%"
				+ fuzzy
				+ "%' OR  petition_code LIKE '%"
				+ fuzzy
				+ "%' OR case_desc LIKE '%"
				+ fuzzy
				+ "%' OR (select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) LIKE '%"
				+ fuzzy + "%')";
		final String questionBelongtoSQL= " AND   question_belonging_to ="
				+ belongTo;
		final String unitSQL = "  AND current_handle_unitid = " + unitId;
		final String petitionWaySql = " AND  petition_way = " + petitionWay;
		final String timeSql=" AND gmt_create BETWEEN '"+startTime+"' AND '"+endTime+"'";
		String nSql="\n";
		class getListByisRepeatPetitionConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = questionBelongtoSQL;
				String cSql = petitionWaySql;
				String eSql = unitSQL;
				String dSql=timeSql;
				String filterSql = "\n";

				if (!StringUtils.isEmpty(fuzzy)) {

					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (petitionWay != 0) {
					filterSql = aSql + cSql;
				} else {
					cSql = "";
				}
				if (belongTo != 0) {
					filterSql = aSql + cSql + bSql;
				} else {
					bSql = "";
				}
				if (unitId != 0) {
					filterSql = aSql + cSql + bSql + eSql;
				} else {
					eSql = "";
				}
				if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
					filterSql = aSql + cSql + bSql + eSql+dSql;
				}else{
					dSql="";
				}
				return filterSql;
			}
		}
		String filterSql=new getListByisRepeatPetitionConcatSQl().result(nSql);
		String sql = "SELECT f.ID,f.petition_names,f.guest_id,f.petition_code,"
				+ "f.case_desc,f.is_satisfied,f.petition_time,f.gmt_create,f.state,"
				+ "(select RY_SFZ FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as rysfz,"
				+ "(select RY_IMG FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryimg,"
				+ "(select name from fd_code where code = f.petition_way) as way,"
				+ "(SELECT head_pic FROM fd_guest WHERE ID=f.guest_id) as petitionHeadSrc,"
				+ "(select COUNT(ID) FROM fd_case where is_repeat_petition="
				+ isRepeatPetition
				+ " "
				+ filterSql
				+ " ) as total,"
				+ "(SELECT dw_mc from fc_dwb where DW_ID = f.current_handle_unitid) as dw_name,"
				+ "(select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryname,"
				+ "(select name from fd_code WHERE `code`=f.question_belonging_to) as belongto "
				+ "FROM fd_case as f where f.is_repeat_petition="
				+ isRepeatPetition + " "
				+ filterSql + "\n" + "ORDER BY f.ID asc LIMIT " + startpage
				* pageCount + "," + pageCount;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.err.println(sql);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			List<HomeListVO> homeList = new ArrayList<HomeListVO>();
			if (!StringUtils.isEmpty(list) && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					HomeListVO homeListVo = new HomeListVO();
					homeListVo.setCaseId(StringUtils.isEmpty(list.get(i).get(
							"ID")) ? 0 : Integer.valueOf(list.get(i).get("ID")
							.toString()));
					homeListVo.setGuestId(StringUtils.isEmpty(list.get(i).get(
							"guest_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("guest_id").toString()));
					homeListVo.setCaseDesc(StringUtils.isEmpty(list.get(i).get(
							"case_desc")) ? null : list.get(i).get("case_desc")
							.toString());
					homeListVo.setPetitionCode(StringUtils.isEmpty(list.get(i)
							.get("petition_code")) ? null : list.get(i)
							.get("petition_code").toString());
					homeListVo.setCurrentHandleUnit(StringUtils.isEmpty(list
							.get(i).get("dw_name")) ? null : list.get(i)
							.get("dw_name").toString());
					homeListVo.setCurrentHandleName(StringUtils.isEmpty(list
							.get(i).get("ryname")) ? null : list.get(i)
							.get("ryname").toString());
					homeListVo.setPetitionName(StringUtils.isEmpty(list.get(i)
							.get("petition_names")) ? null : list.get(i)
							.get("petition_names").toString());
					homeListVo.setGmtCreate(StringUtils.isEmpty(list.get(i)
							.get("gmt_create")) ? null : list.get(i)
							.get("gmt_create").toString());
					homeListVo.setCaseBelongToAddress(StringUtils.isEmpty(list
							.get(i).get("belongto")) ? null : list.get(i)
							.get("belongto").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("rysfz")) ? null : list.get(i).get("rysfz").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("ryimg")) ? null : list.get(i).get("ryimg").toString());
					homeListVo.setPetitionHeadSrc(StringUtils.isEmpty(list.get(
							i).get("petitionHeadSrc")) ? null : list.get(i)
							.get("petitionHeadSrc").toString());
					homeListVo.setPetitionType(StringUtils.isEmpty(list.get(i)
							.get("way")) ? null : list.get(i).get("way")
							.toString());
					homeList.add(homeListVo);

				}
				map.put("data", homeList);
				map.put("pageIndex", startpage);
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
			} else {
				map.put("message", "没有满足条件的信息");
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> getListByType(Integer startPage,
			Integer pageCount, final String fuzzy, final Integer unitId,
			final Integer belongTo, Integer type, final Integer petitionWay,final String startTime,final String endTime) {
		String condition = "";
		if (type == 1) {
			condition = " < 4";
		} else {
			condition = " >= 4";
		}
		final String fuzzySql = "AND (petition_names like '%"
				+ fuzzy
				+ "%' OR  petition_code LIKE '%"
				+ fuzzy
				+ "%' OR case_desc LIKE '%"
				+ fuzzy
				+ "%' OR (select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) LIKE '%"
				+ fuzzy + "%')";
		final String questionBelongtoSQL = " AND   question_belonging_to ="
				+ belongTo;
		final String unitSQL = "  AND current_handle_unitid = " + unitId;
		final String petitionWaySql = " AND  petition_way = " + petitionWay;
		final String timeSql=" AND gmt_create BETWEEN '"+startTime+"' AND '"+endTime+"'";
		String nSql = "\n";
		class getListByTypeConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = questionBelongtoSQL;
				String cSql = petitionWaySql;
				String eSql = unitSQL;
				String dSql=timeSql;
				String filterSql = "\n";

				if (!StringUtils.isEmpty(fuzzy)) {

					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (petitionWay != 0) {
					filterSql = aSql + cSql;
				} else {
					cSql = "";
				}
				if (belongTo != 0) {
					filterSql = aSql + cSql + bSql;
				} else {
					bSql = "";
				}
				if (unitId != 0) {
					filterSql = aSql + cSql + bSql + eSql;
				} else {
					eSql = "";
				}if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
					filterSql = aSql + cSql + bSql + eSql+dSql;	
				}else{
					dSql="";
				}

				return filterSql;
			}
		}

		String filterSql = new getListByTypeConcatSQl().result(nSql);

		String sql = "SELECT f.ID,f.petition_names,f.guest_id,f.petition_code,"
				+ "f.case_desc,f.is_satisfied,f.petition_time,f.gmt_create,f.state,"
				+ "(select RY_SFZ FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as rysfz,"
				+ "(select RY_IMG FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryimg,"
				+ "(select name from fd_code where code = f.petition_way) as way,"
				+ "(SELECT head_pic FROM fd_guest WHERE ID=f.guest_id) as petitionHeadSrc,"
				+ "(select COUNT(ID) FROM fd_case where  petition_numbers "
				+ condition
				+ " "
				+ filterSql
				+ " ) as total,"
				+ "(SELECT dw_mc from fc_dwb where DW_ID = f.current_handle_unitid) as dw_name,"
				+ "(select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryname,"
				+ "(select name from fd_code WHERE `code`=f.question_belonging_to) as belongto "
				+ "FROM fd_case as f where f.petition_numbers " + condition
				+ " " + filterSql + "\n"
				+ "ORDER BY f.ID asc LIMIT " + startPage * pageCount + ","
				+ pageCount;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.err.println(sql);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			List<HomeListVO> homeList = new ArrayList<HomeListVO>();
			if (!StringUtils.isEmpty(list) && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					HomeListVO homeListVo = new HomeListVO();
					homeListVo.setCaseId(StringUtils.isEmpty(list.get(i).get(
							"ID")) ? 0 : Integer.valueOf(list.get(i).get("ID")
							.toString()));
					homeListVo.setGuestId(StringUtils.isEmpty(list.get(i).get(
							"guest_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("guest_id").toString()));
					homeListVo.setCaseDesc(StringUtils.isEmpty(list.get(i).get(
							"case_desc")) ? null : list.get(i).get("case_desc")
							.toString());
					homeListVo.setPetitionCode(StringUtils.isEmpty(list.get(i)
							.get("petition_code")) ? null : list.get(i)
							.get("petition_code").toString());
					homeListVo.setCurrentHandleUnit(StringUtils.isEmpty(list
							.get(i).get("dw_name")) ? null : list.get(i)
							.get("dw_name").toString());
					homeListVo.setCurrentHandleName(StringUtils.isEmpty(list
							.get(i).get("ryname")) ? null : list.get(i)
							.get("ryname").toString());
					homeListVo.setPetitionName(StringUtils.isEmpty(list.get(i)
							.get("petition_names")) ? null : list.get(i)
							.get("petition_names").toString());
					homeListVo.setGmtCreate(StringUtils.isEmpty(list.get(i)
							.get("gmt_create")) ? null : list.get(i)
							.get("gmt_create").toString());
					homeListVo.setCaseBelongToAddress(StringUtils.isEmpty(list
							.get(i).get("belongto")) ? null : list.get(i)
							.get("belongto").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("rysfz")) ? null : list.get(i).get("rysfz").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("ryimg")) ? null : list.get(i).get("ryimg").toString());
					homeListVo.setPetitionHeadSrc(StringUtils.isEmpty(list.get(
							i).get("petitionHeadSrc")) ? null : list.get(i)
							.get("petitionHeadSrc").toString());
					homeListVo.setPetitionType(StringUtils.isEmpty(list.get(i)
							.get("way")) ? null : list.get(i).get("way")
							.toString());
					homeList.add(homeListVo);

				}
				map.put("data", homeList);
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
			} else {
				
				map.put("message", "没有满足条件的信息");
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息失败");
		}

		return map;

	}

	@Override
	public Map<String, Object> getListByPetitionType(Integer startPage,
			Integer pageCount, final String fuzzy, final Integer unitId,
			final Integer belongTo, final Integer petitionWay,
			Integer PetitionType,final String startTime,final String endTime) {
		String PetitionTypeSql = "";
		if (PetitionType == 1) {
			PetitionTypeSql = " petition_way NOT  IN (120000,120001,120100,120102,120201,130001,130000,130102,130201,130301)";
		} else if (PetitionType == 2) {
			PetitionTypeSql = " petition_way  IN (120000,120001,120100,120102,120201,130001,130000,130102,130201,130301)";
		}
		// 模糊搜索
		final String fuzzySql = " AND ( petition_names like '%"
				+ fuzzy
				+ "%' OR   petition_code LIKE '%"
				+ fuzzy
				+ "%' OR  case_desc LIKE '%"
				+ fuzzy
				+ "%' OR (select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) LIKE '%"
				+ fuzzy + "%')";
		// 问题归属地
		final String questionBelongtoSQL = " AND   question_belonging_to ="
				+ belongTo;
		// 单位ID
		final String unitSQL = "  AND current_handle_unitid = " + unitId;
		// 到访方式
		final String petitionWaySql = " AND  petition_way = " + petitionWay;
		final String timeSql=" AND gmt_create BETWEEN '"+startTime+"' AND '"+endTime+"'";
		String nSql = "\n";
		class getListByPetitionTypeConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = questionBelongtoSQL;
				String cSql = petitionWaySql;
				String eSql = unitSQL;
				String dSql=timeSql;
				String filterSql = "\n";

				if (!StringUtils.isEmpty(fuzzy)) {

					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (petitionWay != 0) {
					filterSql = aSql + cSql;
				} else {
					cSql = "";
				}
				if (belongTo != 0) {
					filterSql = aSql + cSql + bSql;
				} else {
					bSql = "";
				}
				if (unitId != 0) {
					filterSql = aSql + cSql + bSql + eSql;
				} else {
					eSql = "";
				}if(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)){
					filterSql = aSql + cSql + bSql + eSql+dSql;
				}else{
					dSql="";
				}

				return filterSql;
			}
		}
		String filterSql = new getListByPetitionTypeConcatSQl().result(nSql);
		String sql = "SELECT f.ID,f.petition_names,f.guest_id,f.petition_code,"
				+ "f.case_desc,f.is_satisfied,f.petition_time,f.gmt_create,f.state,"
				+ "(select RY_SFZ FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as rysfz,"
				+ "(select RY_IMG FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryimg,"
				+ "(select name from fd_code where code = f.petition_way) as way,"
				+ "(SELECT head_pic FROM fd_guest WHERE ID=f.guest_id) as petitionHeadSrc,"
				+ "(select COUNT(ID) FROM fd_case where  "
				+ PetitionTypeSql
				+ filterSql
				+ " ) as total,"
				+ "(SELECT dw_mc from fc_dwb where DW_ID = f.current_handle_unitid) as dw_name,"
				+ "(select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryname,"
				+ "(select name from fd_code WHERE `code`=f.question_belonging_to) as belongto "
				+ "FROM fd_case as f where  " + PetitionTypeSql
				+ filterSql + "\n"
				+ "ORDER BY f.ID asc LIMIT " + startPage * pageCount + ","
				+ pageCount;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.err.println(sql);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			List<HomeListVO> homeList = new ArrayList<HomeListVO>();
			if (!StringUtils.isEmpty(list) && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					HomeListVO homeListVo = new HomeListVO();
					homeListVo.setCaseId(StringUtils.isEmpty(list.get(i).get(
							"ID")) ? 0 : Integer.valueOf(list.get(i).get("ID")
							.toString()));
					homeListVo.setGuestId(StringUtils.isEmpty(list.get(i).get(
							"guest_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("guest_id").toString()));
					homeListVo.setCaseDesc(StringUtils.isEmpty(list.get(i).get(
							"case_desc")) ? null : list.get(i).get("case_desc")
							.toString());
					homeListVo.setPetitionCode(StringUtils.isEmpty(list.get(i)
							.get("petition_code")) ? null : list.get(i)
							.get("petition_code").toString());
					homeListVo.setCurrentHandleUnit(StringUtils.isEmpty(list
							.get(i).get("dw_name")) ? null : list.get(i)
							.get("dw_name").toString());
					homeListVo.setCurrentHandleName(StringUtils.isEmpty(list
							.get(i).get("ryname")) ? null : list.get(i)
							.get("ryname").toString());
					homeListVo.setPetitionName(StringUtils.isEmpty(list.get(i)
							.get("petition_names")) ? null : list.get(i)
							.get("petition_names").toString());
					homeListVo.setGmtCreate(StringUtils.isEmpty(list.get(i)
							.get("gmt_create")) ? null : list.get(i)
							.get("gmt_create").toString());
					homeListVo.setCaseBelongToAddress(StringUtils.isEmpty(list
							.get(i).get("belongto")) ? null : list.get(i)
							.get("belongto").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("rysfz")) ? null : list.get(i).get("rysfz").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("ryimg")) ? null : list.get(i).get("ryimg").toString());
					homeListVo.setPetitionHeadSrc(StringUtils.isEmpty(list.get(
							i).get("petitionHeadSrc")) ? null : list.get(i)
							.get("petitionHeadSrc").toString());
					homeListVo.setPetitionType(StringUtils.isEmpty(list.get(i)
							.get("way")) ? null : list.get(i).get("way")
							.toString());
					homeList.add(homeListVo);

				}
				map.put("data", homeList);
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
			} else {
				map.put("message", "没有满足条件的信息");
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}

		return map;

	}

	@Override
	public Map<String, Object> getListByQuestionHot(Integer startPage,
			Integer pageCount, final String fuzzy, final Integer unitId,
			final Integer belongTo, final Integer questionType,
			final Integer petitionWay,final String startTime,final String endTime) {
		    Map<String, Object> map = new HashMap<String, Object>();
		
		final String fuzzySql = " AND ( petition_names like '%"
				+ fuzzy
				+ "%' OR   petition_code LIKE '%"
				+ fuzzy
				+ "%' OR  case_desc LIKE '%"
				+ fuzzy
				+ "%' OR (select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) LIKE '%"
				+ fuzzy + "%')";
		final String questionBelongtoSQL = " AND   question_belonging_to ="
				+ belongTo;
		final String unitSQL = "  AND current_handle_unitid = " + unitId;
		final String petitionWaySql = " AND  petition_way = " + petitionWay;
		final String questionTypeSql = " AND  question_hot = " + questionType;
		final String timeSql=" AND gmt_create BETWEEN '"+startTime+"' AND '"+endTime+"'";
		String nSql = "\n";
		class getListByQuestionHotConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = questionBelongtoSQL;
				String cSql = petitionWaySql;
				String dSql = questionTypeSql;
				String eSql = unitSQL;
				String fSql=timeSql;
				String filterSql = "\n";

				if (!StringUtils.isEmpty(fuzzy)) {

					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (petitionWay != 0) {
					filterSql = aSql + cSql;
				} else {
					cSql = "";
				}
				if (belongTo != 0) {
					filterSql = aSql + cSql + bSql;
				} else {
					bSql = "";
				}
				if (unitId != 0) {
					filterSql = aSql + cSql + bSql + eSql;
				} else {
					eSql = "";
				}
				if (questionType != 0) {
					filterSql = aSql + cSql + bSql + eSql + dSql;
				}else{
					dSql="";
				}if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
					filterSql = aSql + cSql + bSql + eSql + dSql+fSql;
				}else{
					fSql="";
				}

				return filterSql;
			}
		}

		String filterSql = new getListByQuestionHotConcatSQl().result(nSql);

		String sql = "SELECT f.ID,f.petition_names,f.guest_id,f.petition_code,"
				+ "f.case_desc,f.is_satisfied,f.petition_time,f.gmt_create,f.state,"
				+"(select name from fd_code where code =f.question_hot) as questionName,"
				+ "(select RY_SFZ FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as rysfz,"
				+ "(select RY_IMG FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryimg,"
				+ "(select name from fd_code where code = f.petition_way) as way,"
				+ "(SELECT head_pic FROM fd_guest WHERE ID=f.guest_id) as petitionHeadSrc,"
				+ "(select COUNT(ID) FROM fd_case where  question_hot is not null "
				+ filterSql
				+ " ) as total,"
				+ "(SELECT dw_mc from fc_dwb where DW_ID = f.current_handle_unitid) as dw_name,"
				+ "(select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryname,"
				+ "(select name from fd_code WHERE `code`=f.question_belonging_to) as belongto "
				+ "FROM fd_case as f where f.question_hot is not null "
				+ filterSql + "\n"
				+ "ORDER BY f.ID asc LIMIT " + startPage * pageCount + ","
				+ pageCount;

		
		 try {
			System.err.println(sql);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			List<HomeListVO> homeList = new ArrayList<HomeListVO>();
			if (!StringUtils.isEmpty(list) && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					HomeListVO homeListVo = new HomeListVO();
					homeListVo.setCaseId(StringUtils.isEmpty(list.get(i).get(
							"ID")) ? 0 : Integer.valueOf(list.get(i).get("ID")
							.toString()));
					homeListVo.setGuestId(StringUtils.isEmpty(list.get(i).get(
							"guest_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("guest_id").toString()));
					homeListVo.setQuestionHot(StringUtils.isEmpty(list.get(i).get(
							"questionName")) ? null : list.get(i).get("questionName")
							.toString());
					homeListVo.setCaseDesc(StringUtils.isEmpty(list.get(i).get(
							"case_desc")) ? null : list.get(i).get("case_desc")
							.toString());
					homeListVo.setPetitionCode(StringUtils.isEmpty(list.get(i)
							.get("petition_code")) ? null : list.get(i)
							.get("petition_code").toString());
					homeListVo.setCurrentHandleUnit(StringUtils.isEmpty(list
							.get(i).get("dw_name")) ? null : list.get(i)
							.get("dw_name").toString());
					homeListVo.setCurrentHandleName(StringUtils.isEmpty(list
							.get(i).get("ryname")) ? null : list.get(i)
							.get("ryname").toString());
					homeListVo.setPetitionName(StringUtils.isEmpty(list.get(i)
							.get("petition_names")) ? null : list.get(i)
							.get("petition_names").toString());
					homeListVo.setGmtCreate(StringUtils.isEmpty(list.get(i)
							.get("gmt_create")) ? null : list.get(i)
							.get("gmt_create").toString());
					homeListVo.setCaseBelongToAddress(StringUtils.isEmpty(list
							.get(i).get("belongto")) ? null : list.get(i)
							.get("belongto").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("rysfz")) ? null : list.get(i).get("rysfz").toString());
					homeListVo.setHandleUserHeadSrc(StringUtils.isEmpty(list
							.get(i).get("ryimg")) ? null : list.get(i).get("ryimg").toString());
					homeListVo.setPetitionHeadSrc(StringUtils.isEmpty(list.get(
							i).get("petitionHeadSrc")) ? null : list.get(i)
							.get("petitionHeadSrc").toString());
					homeListVo.setPetitionType(StringUtils.isEmpty(list.get(i)
							.get("way")) ? null : list.get(i).get("way")
							.toString());
					
					homeList.add(homeListVo);

				}
				map.put("data", homeList);
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
			} else {
				map.put("message", "没有满足条件的信息");
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}

		return map;

	}

	@Override
	public Map<String, Object> getQuestionHotList() {
		Map<String, Object> map =new HashMap<>();
		List<Map> questionHotList=fdCodeRepository.getCodeNameByType();
		map.put("data", questionHotList);
		return map;
	}

	
	
	
}