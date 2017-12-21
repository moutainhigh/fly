package com.xinfang.foursectionsdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.VO.ExpertVo;
import com.xinfang.VO.HomeListVO;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.foursectionsdata.dao.ExpertCaseRepository;
import com.xinfang.foursectionsdata.model.ExpertCase;
import com.xinfang.foursectionsdata.service.ExpertCaseService;
import com.xinfang.log.ApiLog;

@Service
public class ExpertcaseServiceImpl implements ExpertCaseService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ExpertCaseRepository expertCaseRepository;
	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Override
	public Map<String, Object> getCaseList(final String fuzzy, final String startTime,
			final String endTime, final Integer petitionWay, final Integer belongTo,
			final Integer PetitionDepartment, final Integer department,Integer startPage,Integer pageCount) {
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
		final String unitSQL = "  AND current_handle_unitid = " + department;
		final String petitionDepartmentSql=" AND  create_unitid = "+PetitionDepartment ;
		final String petitionWaySql = " AND  petition_way = " + petitionWay;
		final String timeSql=" AND gmt_create BETWEEN '"+startTime+"' AND '"+endTime+"'";
		String nSql="\n";
		class getCaseListConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = questionBelongtoSQL;
				String cSql = petitionWaySql;
				String eSql = unitSQL;
				String dSql=timeSql;
				String fsql=petitionDepartmentSql; 
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
				if (department != 0) {
					filterSql = aSql + cSql + bSql + eSql;
				} else {
					eSql = "";
				}
				if(PetitionDepartment !=0){
					filterSql = aSql + cSql + bSql + eSql+fsql;
				}else{
					fsql="";
				}
				if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
					filterSql = aSql + cSql + bSql + eSql+dSql+fsql;
				}else{
					dSql="";
				}
				return filterSql;
			}
		}
		String filterSql=new getCaseListConcatSQl().result(nSql);
		String sql = "SELECT f.ID,f.petition_names,f.guest_id,f.petition_code,f.create_unitid,"
				+ "f.case_desc,f.is_satisfied,f.petition_time,f.gmt_create,f.state,"
				+ "(select RY_SFZ FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as rysfz,"
				+ "(select RY_IMG FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryimg,"
				+ "(select name from fd_code where code = f.petition_way) as way,"
				+ "(SELECT head_pic FROM fd_guest WHERE ID=f.guest_id) as petitionHeadSrc,"
				+ "(select COUNT(ID) FROM fh_case_database where id is not null "
				+ filterSql
				+ " ) as total,"
				+ "(SELECT dw_mc from fc_dwb where DW_ID = f.current_handle_unitid) as dw_name,"
				+" (select dw_mc from fc_dwb where Dw_id = f.create_unitid) as creatUnit,"
				+ "(select ry_mc FROM fc_ryb WHERE RY_ID =f.current_handle_personalid) as ryname,"
				+ "(select name from fd_code WHERE `code`=f.question_belonging_to) as belongto "
				+ "FROM fh_case_database as f where id is not null "
				+ filterSql + "\n" + "ORDER BY f.ID asc LIMIT " + startPage
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
					homeListVo.setCreateUnit(StringUtils.isEmpty(list.get(
							i).get("creatUnit")) ? null : list.get(i)
							.get("creatUnit").toString());
					homeListVo.setCreateUnitId(StringUtils.isEmpty(list.get(i).get(
							"create_unitid")) ? 0 : Integer.valueOf(list.get(i)
							.get("create_unitid").toString()));
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
	public Map<String, Object> getExpertList(final String fuzzy, final String record,
			final Integer terrytory, final Integer ksId, final Integer departmentId,Integer startPage,Integer pageCount) {
		final String fuzzySql = "AND (district like '%"
				+ fuzzy
				+ "%' OR  unit LIKE '%"
				+ fuzzy
				+ "%' OR offices LIKE '%"
				+ fuzzy
				+ "%' OR specialist_name LIKE '%"
				+ fuzzy + "%')";
		final String recordSQL= " AND   culture LIKE '%"
				+record+"%'";
		final String unitSQL = "  AND unit_id = " + departmentId;
		final String terrytorySql=" AND  territory_id = "+terrytory ;
		final String ksIdSql = " AND  offices_id = " + ksId;
		String nSql="\n";
		class getCaseListConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = recordSQL;
				String cSql = terrytorySql;
				String eSql = unitSQL;
				String dSql=ksIdSql;
				String filterSql = "\n";

				if (!StringUtils.isEmpty(fuzzy)) {

					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (!StringUtils.isEmpty(record)) {
					filterSql = aSql + bSql;
				} else {
					bSql = "";
				}
				if (terrytory != 0) {
					filterSql = aSql + cSql + bSql;
				} else {
					cSql = "";
				}
				if (departmentId != 0) {
					filterSql = aSql + cSql + bSql + eSql;
				} else {
					eSql = "";
				}
				if(ksId !=0){
					filterSql = aSql + cSql + bSql + eSql+dSql;
				}else{
					dSql="";
				}
			
				return filterSql;
			}
		}
		String filterSql=new getCaseListConcatSQl().result(nSql);
		String sql = "SELECT FD.* ,"
				+ "(select COUNT(specialist_id) FROM fh_specialist_database  where specialist_id is not null "
				+ filterSql
				+ " ) as total"
				+ " FROM fh_specialist_database AS FD where specialist_id is not null "
				+ filterSql + "\n" + " ORDER BY FD.specialist_id asc LIMIT " + startPage
				* pageCount + "," + pageCount;
		Map<String, Object> map=new HashMap<>();
		try {
			System.err.println(sql);
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if(list.size()>0&&!StringUtils.isEmpty(list)){
				List<ExpertVo> expertList =new ArrayList<>();
				for(int i=0;i<list.size();i++){
					ExpertVo ex=new ExpertVo();
					ex.setSpecialistId(StringUtils.isEmpty(list.get(i).get("specialist_id"))?0:Integer.valueOf(list.get(i).get("specialist_id").toString()));
					ex.setSpecialistName(StringUtils.isEmpty(list.get(i).get("specialist_name"))?null:list.get(i).get("specialist_name").toString());
					ex.setImage(StringUtils.isEmpty(list.get(i).get("image"))?null:list.get(i).get("image").toString());
					ex.setSex(StringUtils.isEmpty(list.get(i).get("sex"))?null:list.get(i).get("sex").toString());
					ex.setAge(StringUtils.isEmpty(list.get(i).get("age"))?0:Integer.valueOf(list.get(i).get("age").toString()));
					ex.setCulture(StringUtils.isEmpty(list.get(i).get("culture"))?null:list.get(i).get("culture").toString());
					ex.setPhone(StringUtils.isEmpty(list.get(i).get("phone"))?null:list.get(i).get("phone").toString());
					ex.setDistrictId(StringUtils.isEmpty(list.get(i).get("district_id"))?null:list.get(i).get("district_id").toString());
					ex.setDistrictName(StringUtils.isEmpty(list.get(i).get("district"))?null:list.get(i).get("district").toString());
					ex.setUnitName(StringUtils.isEmpty(list.get(i).get("unit"))?null:list.get(i).get("unit").toString());
					ex.setUnitId(StringUtils.isEmpty(list.get(i).get("unit_id"))?null:list.get(i).get("unit_id").toString());
					ex.setKsName(StringUtils.isEmpty(list.get(i).get("offices"))?null:list.get(i).get("offices").toString());
					ex.setKsId(StringUtils.isEmpty(list.get(i).get("offices_id"))?null:list.get(i).get("offices_id").toString());
					ex.setKszwName(StringUtils.isEmpty(list.get(i).get("duties"))?null:list.get(i).get("duties").toString());
					ex.setKszwId(StringUtils.isEmpty(list.get(i).get("duties_id"))?null:list.get(i).get("duties_id").toString());
					ex.setTerritoryId(StringUtils.isEmpty(list.get(i).get("territory_id"))?null:list.get(i).get("territory_id").toString());
					ex.setTerritoryName(StringUtils.isEmpty(list.get(i).get("territory"))?null:list.get(i).get("territory").toString());
					ex.setLimitYear(StringUtils.isEmpty(list.get(i).get("limityear"))?null:list.get(i).get("limityear").toString());
					ex.setIntroduce(StringUtils.isEmpty(list.get(i).get("introduce"))?null:list.get(i).get("introduce").toString());
					expertList.add(ex);
				}
				map.put("data", expertList);
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
				
			}else{
				map.put("message", "没有满足条件的信息");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}
		
		
		return map;
	}

	@Override
	public Map<String, Object> getCase(Integer id) {
		Map<String, Object> map =new HashMap<>();
		ExpertCase ex = null;
		
		try {
			ex = expertCaseRepository.getExpertCaseById(id);
			Integer questionBlong = ex.getQuestionBelongingTo();
			Integer questionType = ex.getQuestionType();
			Integer questionHot = ex.getQuestionHot();
			Integer caseType = ex.getCaseType();
			Integer blongToSys = ex.getBelongToSys();
			Integer thirteenCategories = ex.getThirteenCategories();
			Integer petitionWhy = ex.getPetitionWhy();
			Integer petitionPurpose = ex.getPetitionPurpose();
			Integer petitionNature = ex.getPetitionNature();
			Integer exposeObject = ex.getExposeObject();
			Integer petitionCounty = ex.getPetitionCounty();
			Integer petitionWay = ex.getPetitionWay();

			ex.setStrQuestionBelongTo(questionBlong != null ? fdCaseRepository
					.findNameByCode(questionBlong).getName() : null);
			ex.setStrBelongToSys(blongToSys!=null?fdCaseRepository.findNameByCode(
					ex.getBelongToSys()).getName():null);
			ex.setStrCaseType(caseType!=null?fdCaseRepository.findNameByCode(
					ex.getCaseType()).getName():null);
			ex.setStrExposeObject(exposeObject!=null?fdCaseRepository.findNameByCode(
					ex.getExposeObject()).getName():null);
			ex.setStrPetitionCountry(petitionCounty!=null?fdCaseRepository.findNameByCode(
					ex.getPetitionCounty()).getName():null);
			ex.setStrPetitionNature(petitionNature!=null?fdCaseRepository.findNameByCode(
					ex.getPetitionNature()).getName():null);
			ex.setStrPetitionPurpose(petitionPurpose!=null?fdCaseRepository.findNameByCode(
					ex.getPetitionPurpose()).getName():null);
			ex.setStrPetitionWay(petitionWay!=null?fdCaseRepository.findNameByCode(
					ex.getPetitionWay()).getName():null);
			ex.setStrPetitionWhy(petitionWhy!=null?fdCaseRepository.findNameByCode(
					ex.getPetitionWhy()).getName():null);
			ex.setStrQuestionHot(questionHot!=null?fdCaseRepository.findNameByCode(
					ex.getQuestionHot()).getName():null);
			ex.setStrQusetionType(questionType!=null?fdCaseRepository.findNameByCode(
					ex.getQuestionType()).getName():null);
			ex.setStrThirteenCategories(thirteenCategories!=null?fdCaseRepository.findNameByCode(
					ex.getThirteenCategories()).getName():null);

			map.put("data", ex);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

}
