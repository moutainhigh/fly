package com.xinfang.personnelmanagement.service.impl;


import com.xinfang.dao.*;
import com.xinfang.enums.OrganizationStateEnum;
import com.xinfang.enums.OrgnizationLevelEnum;
import com.xinfang.log.ApiLog;
import com.xinfang.model.*;
import com.xinfang.personnelmanagement.service.OrganizationService;
import com.xinfang.utils.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;

@Service("organizationService")
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationRepository organizationRepository;
	@Autowired
	private FcQxsbRepository fcQxsbRepository;
	@Autowired
	private FcDwbRepository fcDwbRepository;
	@Autowired
	private FcKsbRepository fcKsbRepository;
	@Autowired
	private FcKszwbRepository fcKszwbRepository;
	@Autowired
	private TsWindowRepository tsWindowRepository;
	@Autowired
	private TsWindowPersonRepository tsWindowPersonRepository;
	@Autowired
	private FcRybAllFieldRepository fcRybAllFieldRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> getdeptByQXSId(int QxsId, String fuzzy,
			Integer startPage, Integer pagecount) {
		String fuzzySql = "";
		String fuzzySqlCount = "";

		if (!StringUtils.isEmpty(fuzzy)) {
			int state = OrganizationStateEnum.getValue(fuzzy);
			int level = OrgnizationLevelEnum.getValue(fuzzy);
			fuzzySql = "AND (d.DW_MC like '%"
					+ fuzzy
					+ "%' OR (select f.DWLX_MC FROM fc_dwlxb as f WHERE d.DW_TYPE = f.DWLX_id) LIKE '%"
					+ fuzzy + "%' OR d.org_code LIKE '%" + fuzzy
					+ "%' OR d.QYZT LIKE '%" + state
					+ "%' OR d.org_level LIKE '%" + level + "%')";
			fuzzySqlCount = "AND (DW_MC like '%"
					+ fuzzy
					+ "%' OR (select f.DWLX_MC FROM fc_dwlxb as f WHERE d.DW_TYPE = f.DWLX_id) LIKE '%"
					+ fuzzy + "%' OR org_code LIKE '%" + fuzzy
					+ "%'OR d.QYZT LIKE '%" + state
					+ "%' OR d.org_level LIKE '%" + level + "%')";
		}

		String sql = "SELECT d.DW_MC ,d.XH,d.DW_ID,d.org_code,d.org_grade_type,d.org_level,(select f.DWLX_MC FROM fc_dwlxb as f WHERE d.DW_TYPE = f.DWLX_id)AS DW_TYPE ,(select count(*) from fc_dwb where qxs_id="
				+ QxsId
				+ "\n"
				+ fuzzySqlCount
				+ ") as total,d.QYZT from fc_dwb as d where QXS_ID = "
				+ QxsId
				+ "\n" + fuzzySql;
		String page = "ORDER BY d.XH,d.dw_id ASC limit \n" + startPage * pagecount
				+ "," + pagecount;

		List<FcDwb> fcDwbList = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			fcDwbList = new ArrayList<FcDwb>();
			list = jdbcTemplate.queryForList(sql + "\n" + page);
			for (int i = 0; i < list.size(); i++) {
				FcDwb fcDwb = new FcDwb();
				fcDwb.setXh(StringUtils.isEmpty(list.get(i).get("XH"))?null:Double.valueOf(list.get(i).get("XH").toString()));
				fcDwb.setDwId(StringUtils.isEmpty(list.get(i).get("DW_ID")) ? 0
						: Integer.valueOf(list.get(i).get("DW_ID").toString()));
				fcDwb.setDwMc(StringUtils.isEmpty(list.get(i).get("DW_MC")) ? null
						: list.get(i).get("DW_MC").toString());
				fcDwb.setStrDwType(StringUtils.isEmpty(list.get(i).get(
						"DW_Type")) ? null : list.get(i).get("DW_Type")
						.toString());
				fcDwb.setQyzt(StringUtils.isEmpty(list.get(i).get("QYZT")) ? -1
						: Integer.valueOf(list.get(i).get("QYZT").toString()));
				fcDwb.setOrgCode(StringUtils.isEmpty(list.get(i)
						.get("org_code")) ? null : list.get(i).get("org_code")
						.toString());
				fcDwb.setOrgGradeType(StringUtils.isEmpty(list.get(i).get(
						"org_grade_type")) ? -1 : Integer.valueOf(list.get(i)
						.get("org_grade_type").toString()));
				fcDwb.setOrgLevel(StringUtils.isEmpty(list.get(i).get(
						"org_level")) ? -1 : Integer.valueOf(list.get(i)
						.get("org_level").toString()));
				fcDwbList.add(fcDwb);
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		map.put("data", fcDwbList);
		map.put("Resulttotal",
				StringUtils.isEmpty(list.get(0).get("total")) ? 0 : Integer
						.valueOf(list.get(0).get("total").toString()));
		map.put("pageIndex", startPage);
		map.put("pageSize", pagecount);
		map.put("pageNum", Integer.valueOf(list.get(0).get("total").toString())
				% pagecount == 0 ? Integer.valueOf(list.get(0)
				.get("total").toString())
				/ pagecount : Integer.valueOf(list.get(0)
				.get("total").toString())
				/ pagecount + 1);

		return map;
	}

	@Override
	public Map<String, Object> updateOrAddQxs(FcQxsb fcQxs, String cjsj,
			String xgsj) {
		Map<String, Object> map = new HashMap<>();
		try {
			FcQxsb fcqxs = new FcQxsb();
			FcQxsb result = fcQxsbRepository.findOne(fcQxs.getQxsId());
			if (StringUtils.isEmpty(result)) {
				fcqxs.setQxsMc(fcQxs.getQxsMc());
				fcqxs.setXh(fcQxs.getXh());
				fcqxs.setQxsCode(fcQxs.getQxsCode());
				fcqxs.setCjsj(DateUtils.parseDateTime(cjsj));
				fcqxs.setQyzt(fcQxs.getQyzt());
				fcQxsbRepository.save(fcqxs);
				map.put("message", "添加信息成功");
			} else {
				result.setQxsMc(fcQxs.getQxsMc());
				result.setXh(fcQxs.getXh());
				result.setQxsCode(fcQxs.getQxsCode());
				result.setQyzt(fcQxs.getQyzt());
				result.setXgsj(DateUtils.parseDateTime(xgsj));
				fcQxsbRepository.save(result);
				map.put("message", "修改信息成功");
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> getQxsList(Integer startPage, Integer pageCount,
			String fuzzy) {
		String fuzzySql = "";
		int state = OrganizationStateEnum.getValue(fuzzy);
		if (!StringUtils.isEmpty(fuzzy)) {
			fuzzySql = "where QXS_MC LIKE '%" + fuzzy + "%' or QXS_CODE LIKE '"
					+ fuzzy + "%' or QYZT LIKE '%" + state + "%'";
		}
		String sql = "select *,(select count(*) from fc_qxsb " + "\n"
				+ fuzzySql + " ) as total from fc_qxsb" + "\n" + fuzzySql;
		String page = " order by xh,qxs_id ASC limit" + "\n" + startPage
				* pageCount + "," + pageCount;
		List<Map<String, Object>> list = null;
		Map<String, Object> map = new HashMap<>();
		try {
			list = jdbcTemplate.queryForList(sql + "\n" + page);
			map.put("data", list);
			map.put("Resulttotal",
					StringUtils.isEmpty(list.get(0).get("total")) ? 0 : Integer
							.valueOf(list.get(0).get("total").toString()));
			map.put("pageIndex", startPage);
			map.put("pageSize", pageCount);
			map.put("pagenum",
					Integer.valueOf(list.get(0).get("total").toString())
							% pageCount == 0 ? Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount : Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount + 1);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}

		return map;
	}

	@Override
	public Map<String, Object> updateOrAddDept(FcDwb fcDwb, String cjsj,
			String xgsj) {
		Map<String, Object> map = new HashMap<>();
		try {
			FcDwb fcdwb = new FcDwb();
			FcDwb result = fcDwbRepository.findOne(fcDwb.getDwId());
			if (StringUtils.isEmpty(result)) {
				fcdwb.setDwMc(fcDwb.getDwMc());
				fcdwb.setQxsId(fcDwb.getQxsId());
				fcdwb.setDwType(fcDwb.getDwType());
				fcdwb.setOrgCode(StringUtils.isEmpty(fcDwb.getOrgCode())?null:fcDwb.getOrgCode());
				fcdwb.setOrgGradeType(fcDwb.getOrgGradeType());
				fcdwb.setOrgLevel(fcDwb.getOrgLevel());
				fcdwb.setXh(fcDwb.getXh());
				fcdwb.setCjsj(DateUtils.parseDateTime(cjsj));
				fcdwb.setQyzt(fcDwb.getQyzt());
				fcDwbRepository.save(fcdwb);
				map.put("message", "添加信息成功");
			} else {
				result.setDwMc(fcDwb.getDwMc());
				result.setQxsId(fcDwb.getQxsId());
				result.setDwType(fcDwb.getDwType());
				result.setOrgCode(StringUtils.isEmpty(fcDwb.getOrgCode())?null:fcDwb.getOrgCode());
				result.setOrgGradeType(fcDwb.getOrgGradeType());
				result.setOrgLevel(fcDwb.getOrgLevel());
				result.setXh(fcDwb.getXh());
				result.setXgsj(DateUtils.parseDateTime(xgsj));
				result.setQyzt(fcDwb.getQyzt());
				fcDwbRepository.save(result);
				map.put("message", "修改信息成功");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> getTsWindowByDeptId(int deptId,
			Integer startPage, Integer pageCount, String fuzzy) {
		String fuzzySql = "";
		String fuzzySqlCount = "";
		if (!StringUtils.isEmpty(fuzzy)) {
			int state = OrganizationStateEnum.getValue(fuzzy);
			fuzzySql = " WHERE t.window_name like '%" + fuzzy
					+ "%' OR t.description like '%" + fuzzy
					+ "%'   OR t.status LIKE '%" + state + "%'";
			fuzzySqlCount = "AND (window_name like '%" + fuzzy
					+ "%' OR description like '%" + fuzzy
					+ "%'   OR status LIKE '%" + state + "%')";
		}
		String sql = "SELECT  t.id,(SELECT dw_mc FROM fc_dwb where DW_ID="
				+ deptId
				+ ") as dwname,(SELECT COUNT(w.org_id) FROM ts_window as w WHERE w.org_id= "+deptId
				+ "\n"
				+ fuzzySqlCount
				+ ") as windowCount,t.description,t.window_name,t.xh,t.status,GROUP_CONCAT(t.user_id) as listid ,GROUP_CONCAT(t.ry_mc) as list,COUNT(t.ry_mc) as total from (SELECT  t.id, t.description,t.window_name,t.xh,t.status,p.user_id,f.RY_MC  from ts_window  as t  LEFT JOIN ts_window_person as p on t.id = p.window_id left JOIN fc_ryb as f ON f.RY_ID=p.user_id where t.org_id="
				+ deptId + ") AS t " + fuzzySql
				+ "GROUP BY t.window_name,t.xh  asc limit " + startPage
				* pageCount + "," + pageCount;

		List<Map<String, Object>> list = null;
		List<TsWindow> windowList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		try {
			System.err.println(sql);
			list = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < list.size(); i++) {
				TsWindow tsWindow = new TsWindow();
				tsWindow.setId(StringUtils.isEmpty(list.get(i).get("id")) ? -1
						: Integer.valueOf(list.get(i).get("id").toString()));
				tsWindow.setWindowName(StringUtils.isEmpty(list.get(i).get(
						"window_name")) ? null : list.get(i).get("window_name")
						.toString());
				tsWindow.setDescription(StringUtils.isEmpty(list.get(i).get(
						"description")) ? null : list.get(i).get("description")
						.toString());
				tsWindow.setStatus(StringUtils.isEmpty(list.get(i)
						.get("status")) ? -1 : Integer.valueOf(list.get(i)
						.get("status").toString()));
				tsWindow.setXh(StringUtils.isEmpty(list.get(i).get("xh")) ? -1
						: Integer.valueOf(list.get(i).get("xh").toString()));
				tsWindow.setOrgName(StringUtils.isEmpty(list.get(i).get(
						"dwname")) ? null : list.get(i).get("dwname")
						.toString());
				tsWindow.setRyCount(StringUtils.isEmpty(list.get(i)
						.get("total")) ? 0 : Integer.valueOf(list.get(i)
						.get("total").toString()));
				if (StringUtils.isEmpty(list.get(i).get("list"))) {
					tsWindow.setRyName(null);
				} else {
					List<String> ryNameList = new ArrayList<>();
					String idString = list.get(i).get("list").toString();
					String[] sourceStrArray = idString.split(",");
					for (int s = 0; s < sourceStrArray.length; s++) {
						ryNameList.add(sourceStrArray[s]);
					}
					tsWindow.setRyName(ryNameList);
				}
				if (StringUtils.isEmpty(list.get(i).get("listid"))) {
					tsWindow.setRid(null);
				} else {
					List<String> ryIdList = new ArrayList<>();
					String idString = list.get(i).get("listid").toString();
					String[] sourceStrArray = idString.split(",");
					for (int s = 0; s < sourceStrArray.length; s++) {
						ryIdList.add(sourceStrArray[s]);
					}
					tsWindow.setRid(ryIdList);
				}
				windowList.add(tsWindow);
			}
			map.put("data", windowList);
			map.put("Resulttotal",
					StringUtils.isEmpty(list.get(0).get("windowCount")) ? 0
							: Integer.valueOf(list.get(0).get("windowCount")
									.toString()));
			map.put("pageIndex", startPage);
			map.put("pageSize", pageCount);
			map.put("pageNum",
					windowList.size() % pageCount == 0 ? windowList.size()
							/ pageCount : windowList.size() / pageCount + 1);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> updateOrAddKs(FcKsb fcKsb, String cjsj,
			String xgsj) {
		Map<String, Object> map = new HashMap<>();
		try {
			FcKsb fcksb = new FcKsb();
			FcKsb result = fcKsbRepository.findOne(fcKsb.getKsId());
			if (StringUtils.isEmpty(result)) {
				fcksb.setDwId(fcKsb.getDwId());
				fcksb.setKsMc(fcKsb.getKsMc());
				fcksb.setQyzt(fcKsb.getQyzt());
				fcksb.setXh(StringUtils.isEmpty(fcKsb.getXh())?null:fcKsb.getXh());
				fcksb.setCjsj(DateUtils.parseDateTime(cjsj));
				fcksb.setQxsId(fcKsb.getQxsId());
				fcKsbRepository.save(fcksb);
				map.put("message", "添加信息成功");
			} else {
				result.setKsMc(fcKsb.getKsMc());
				result.setQyzt(fcKsb.getQyzt());
				result.setXgsj(DateUtils.parseDateTime(xgsj));
				result.setXh(StringUtils.isEmpty(fcKsb.getXh())?null:fcKsb.getXh());
				result.setQxsId(fcKsb.getQxsId());
				fcKsbRepository.save(result);
				map.put("message", "修改信息成功");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> getKsListByDeptId(int deptId, Integer startPage,
			Integer pageCount, String fuzzy) {
		String fuzzySql = "";
		int state = OrganizationStateEnum.getValue(fuzzy);
		if (!StringUtils.isEmpty(fuzzy)) {
			fuzzySql = "AND(ks_mc like '%" + fuzzy + "%' OR qyzt =" + state
					+ ")";
		}
		String sql = "select *,(select count(*) from fc_ksb where dw_id="
				+ deptId + "\n" + fuzzySql
				+ ") as total from fc_ksb where dw_id=" + deptId + "\n"
				+ fuzzySql + "order by xh asc limit \n" + startPage * pageCount
				+ "," + pageCount;
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = null;
		try {
			list = jdbcTemplate.queryForList(sql);
			map.put("data", list);
			map.put("Resulttotal",
					StringUtils.isEmpty(list.get(0).get("total")) ? 0 : Integer
							.valueOf(list.get(0).get("total").toString()));
			map.put("pageIndex", startPage);
			map.put("pageSize", pageCount);
			map.put("pagenum",
					Integer.valueOf(list.get(0).get("total").toString())
							% pageCount == 0 ? Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount : Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount + 1);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> updateOrAddZw(FcKszwb fcKszwb, String cjsj,
			String xgsj) {
		Map<String, Object> map = new HashMap<>();
		try {
			FcKszwb fckszwb = new FcKszwb();
			FcKszwb result = fcKszwbRepository.findOne(fcKszwb.getKszwId());
			if (StringUtils.isEmpty(result)) {
				fckszwb.setKsId(fcKszwb.getKsId());
				fckszwb.setKszwMc(fcKszwb.getKszwMc());
				fckszwb.setParentId(StringUtils.isEmpty(fcKszwb.getParentId())?null:fcKszwb.getParentId());
				fckszwb.setProfessionLevel(fcKszwb.getProfessionLevel());
				fckszwb.setCjsj(Timestamp.valueOf(cjsj));
				fckszwb.setXh(StringUtils.isEmpty(fcKszwb.getXh())?null:fcKszwb.getXh());
				fckszwb.setQyzt(fcKszwb.getQyzt());
				fcKszwbRepository.save(fckszwb);
				map.put("message", "添加信息成功");
			} else {
				result.setKszwMc(fcKszwb.getKszwMc());
				result.setParentId(StringUtils.isEmpty(fcKszwb.getParentId())?null:fcKszwb.getParentId());
				result.setProfessionLevel(fcKszwb.getProfessionLevel());
				result.setXh(fcKszwb.getXh());
				result.setXgsj(Timestamp.valueOf(xgsj));
				result.setQyzt(fcKszwb.getQyzt());
				fcKszwbRepository.save(result);
				map.put("message", "修改信息成功");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> getZwListByKsId(int ksId, Integer startPage,
			Integer pageCount, String fuzzy) {
		String fuzzySql = "";
		String fuzzySqlCount = "";
		if (!StringUtils.isEmpty(fuzzy)) {
			int state = OrganizationStateEnum.getValue(fuzzy);
			int level = OrganizationStateEnum.getValue(fuzzy);
			fuzzySql = "And(z.kszw_mc like '%"
					+ fuzzy
					+ "%' or (select ks_mc from fc_ksb where ks_id=z.ks_id) like '%"
					+ fuzzy
					+ "%' or (select kszw_mc from fc_kszwb where kszw_id = z.parent_id) like '%"
					+ fuzzy + "%' or z.qyzt = " + state
					+ " or z.profession_level = " + level + ")";
			fuzzySqlCount = "And(kszw_mc like '%"
					+ fuzzy
					+ "%' or (select ks_mc from fc_ksb where ks_id=z.ks_id) like '%"
					+ fuzzy
					+ "%' or (select kszw_mc from fc_kszwb where kszw_id = z.parent_id) like '%"
					+ fuzzy + "%' or qyzt = " + state
					+ " or profession_level = " + level + ")";
		}
		String sql = "select z.kszw_id,z.kszw_mc,z.xh,z.cjsj,z.xgsj,z.qyzt,z.profession_level,z.parent_id,(select kszw_mc from fc_kszwb where kszw_id = z.parent_id) as parentname,(select ks_mc from fc_ksb where ks_id = z.ks_id) as ksname,(select count(*) from fc_kszwb where ks_id="
				+ ksId
				+ "\n"
				+ fuzzySqlCount
				+ ") as total from fc_kszwb  as z where ks_id ="
				+ ksId
				+ "\n"
				+ fuzzySql
				+ "\n"
				+ "order by z.xh,z.kszw_id asc limit \n"
				+ startPage
				* pageCount + "," + pageCount;
		Map<String, Object> map = new HashMap<>();
		List<FcKszwb> fcKszwbList = new ArrayList<>();
		List<Map<String, Object>> list = null;
		try {
			System.err.println(sql);
			list = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < list.size(); i++) {
				FcKszwb fcKszwb = new FcKszwb();
				fcKszwb.setKszwId(StringUtils.isEmpty(list.get(i)
						.get("kszw_id")) ? 0 : Integer.valueOf(list.get(i)
						.get("kszw_id").toString()));
				fcKszwb.setKszwMc(StringUtils.isEmpty(list.get(i)
						.get("kszw_mc")) ? null : list.get(i).get("kszw_mc")
						.toString());
				fcKszwb.setXh(StringUtils.isEmpty(list.get(i).get("xh")) ? 0
						: Double.valueOf(list.get(i).get("xh").toString()));
				fcKszwb.setCjsj(StringUtils.isEmpty(list.get(i).get("cjsj")) ? null
						: Timestamp.valueOf(list.get(i).get("cjsj").toString()));
				fcKszwb.setXgsj(StringUtils.isEmpty(list.get(i).get("xgsj")) ? null
						: Timestamp.valueOf(list.get(i).get("xgsj").toString()));
				fcKszwb.setQyzt(StringUtils.isEmpty(list.get(i).get("qyzt")) ? 0
						: Integer.valueOf(list.get(i).get("qyzt").toString()));
				fcKszwb.setProfessionLevel(StringUtils.isEmpty(list.get(i).get(
						"profession_level")) ? 0 : Integer.valueOf(list.get(i)
						.get("profession_level").toString()));
				fcKszwb.setParentName(StringUtils.isEmpty(list.get(i).get(
						"parentname")) ? null : list.get(i).get("parentname")
						.toString());
				fcKszwb.setKsName(StringUtils
						.isEmpty(list.get(i).get("ksname")) ? null : list
						.get(i).get("ksname").toString());
				fcKszwbList.add(fcKszwb);
			}
		    int	unitId=fcKsbRepository.getUnitIdByDepartmentId(ksId);
		    int qxsId=fcDwbRepository.findOne(unitId).getQxsId();
			map.put("unitId",unitId );
			map.put("unitName", fcDwbRepository.findOne(unitId).getDwMc());
			map.put("QxsId", qxsId);
			map.put("qxsName", fcQxsbRepository.findOne(qxsId).getQxsMc());
			map.put("data", list);
			map.put("Resulttotal",
					StringUtils.isEmpty(list.get(0).get("total")) ? 0 : Integer
							.valueOf(list.get(0).get("total").toString()));
			map.put("pageIndex", startPage);
			map.put("pageSize", pageCount);
			map.put("pagenum",
					Integer.valueOf(list.get(0).get("total").toString())
							% pageCount == 0 ? Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount : Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageCount + 1);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> updateOrAddWindow(TsWindow tsWindow,
			List<Integer> rIds, String cjsj, String xgsj) {
		Map<String, Object> map = new HashMap<>();
		try {
			TsWindow tswindow = new TsWindow();
			TsWindow result = tsWindowRepository.findOne(tsWindow.getId());
			if (StringUtils.isEmpty(result)) {
				tswindow.setWindowName(tsWindow.getWindowName());
				tswindow.setDescription(StringUtils.isEmpty(tsWindow.getDescription())?null:tsWindow.getDescription());
				tswindow.setOrgId(tsWindow.getOrgId());
				tswindow.setCreatedAt(Timestamp.valueOf(cjsj));
				tswindow.setXh(StringUtils.isEmpty(tsWindow.getXh())?null:tsWindow.getXh());
				tswindow.setStatus(tsWindow.getStatus());
				tsWindowRepository.save(tswindow);
				if (!StringUtils.isEmpty(rIds)) {
					Integer windowId = tsWindowRepository
							.getWindowIdByWindowName(tsWindow.getWindowName(),tsWindow.getOrgId());
					if(getTsWindowPersonByRyId(rIds).size()>0){
						for(int i=0;i<getTsWindowPersonByRyId(rIds).size();i++){
						map.put("rid", getTsWindowPersonByRyId(rIds).get(i).getUserId());
						map.put("ryName",fcRybAllFieldRepository.getRyNameById(getTsWindowPersonByRyId(rIds).get(i).getUserId()));
						map.put("alreadywindowId", getTsWindowPersonByRyId(rIds).get(i).getWindowId());
						map.put("alreadywindowName", tsWindowRepository.getWindowNameByWindowId(getTsWindowPersonByRyId(rIds).get(i).getWindowId()));
						map.put("windowId", windowId);
						map.put("message","已经关联窗口" );
						
							}
						return map;	
	             			}
					
					for (int i = 0; i < rIds.size(); i++) {
						TsWindowPerson tsWindowPerson = new TsWindowPerson();
						tsWindowPerson.setWindowId(windowId);
						tsWindowPerson.setUserId(rIds.get(i));

						tsWindowPersonRepository.save(tsWindowPerson);

					}

				}
				map.put("message", "获取信息成功");
			} else {
				result.setDescription(StringUtils.isEmpty(tsWindow.getDescription())?null:tsWindow.getDescription());
				result.setWindowName(tsWindow.getWindowName());
				result.setUpdatedAt(Timestamp.valueOf(xgsj));
				result.setXh(StringUtils.isEmpty(tsWindow.getXh())?null:tsWindow.getXh());
				if (!StringUtils.isEmpty(rIds)) {
					
				 if(getTsWindowPersonByRyId2(rIds,result.getId()).size()>0){
					for(int i=0;i<getTsWindowPersonByRyId(rIds).size();i++){
					map.put("rid", getTsWindowPersonByRyId(rIds).get(i).getUserId());
					map.put("ryName",fcRybAllFieldRepository.getRyNameById(getTsWindowPersonByRyId(rIds).get(i).getUserId()));
					map.put("alreadywindowId", getTsWindowPersonByRyId(rIds).get(i).getWindowId());
					map.put("alreadywindowName", tsWindowRepository.getWindowNameByWindowId(getTsWindowPersonByRyId(rIds).get(i).getWindowId()));
					map.put("message","已经关联窗口" );
					
						}
					return map;	
             			}
						List<TsWindowPerson> tsWindowPersons = tsWindowPersonRepository
							.findByWindowId(result.getId());
					if (!StringUtils.isEmpty(tsWindowPersons)) {
						for (int i = 0; i < tsWindowPersons.size(); i++) {
							tsWindowPersonRepository
									.deleteWindowById(tsWindowPersons.get(i)
											.getId());
						}
						for (int i = 0; i < rIds.size(); i++) {
							TsWindowPerson tsWindowPerson = new TsWindowPerson();
							tsWindowPerson.setWindowId(result.getId());
							tsWindowPerson.setUserId(rIds.get(i));
							tsWindowPersonRepository.save(tsWindowPerson);
						}
					} else {
						for (int i = 0; i < rIds.size(); i++) {
							
							TsWindowPerson tsWindowPerson = new TsWindowPerson();
							tsWindowPerson.setWindowId(result.getId());
							tsWindowPerson.setUserId(rIds.get(i));
							tsWindowPersonRepository.save(tsWindowPerson);
						}
							

					}

				} else {
					List<TsWindowPerson> tsWindowPersons = tsWindowPersonRepository
							.findByWindowId(result.getId());
					for (int i = 0; i < tsWindowPersons.size(); i++) {
						tsWindowPersonRepository
								.deleteWindowById(tsWindowPersons.get(i)
										.getId());
					}
				}
				tsWindowRepository.save(result);
				map.put("message", "获取信息成功");
				}
			
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}

		return map;
	}
	private List<TsWindowPerson> getTsWindowPersonByRyId(List<Integer> rIds){
		List<TsWindowPerson> list = new ArrayList<TsWindowPerson>();
		for(int i = 0;i<rIds.size();i++){
			TsWindowPerson ts=tsWindowPersonRepository.findByUserId(rIds.get(i));
			if(!StringUtils.isEmpty(ts)){
				list.add(ts);
			}
		}
		return list;
		
	}
	private List<TsWindowPerson> getTsWindowPersonByRyId2(List<Integer> rIds,Integer windowId){
		List<TsWindowPerson> list = new ArrayList<TsWindowPerson>();
		for(int i = 0;i<rIds.size();i++){
			TsWindowPerson ts=tsWindowPersonRepository.findByUserId(rIds.get(i));
			if(!StringUtils.isEmpty(ts)&&!windowId.equals(ts.getWindowId())){
				list.add(ts);
			}
		}
		return list;
		
	}



	@Override
	public Map<String, Object> staffPandect(int countyId, int unitId,
			int departmentId) {
		String sql = "SELECT\n" + "  r.RY_IMG     AS img,\n"
				+ "  r.RY_MC      AS username,\n"
				+ "  k.KS_MC      AS department,\n"
				+ "  z.KSZW_MC    AS post,\n" + "  z.parent_id,\n"
				+ "  z.profession_level\n" + "FROM fc_ryb r\n"
				+ "  LEFT JOIN fc_qxsb q ON r.QXS_ID = q.QXS_ID\n"
				+ "  LEFT JOIN fc_dwb d ON r.DW_ID = d.DW_ID\n"
				+ "  LEFT JOIN fc_ksb k ON r.KS_ID = k.KS_ID\n"
				+ "  LEFT JOIN fc_kszwb z ON r.KS_ID = z.KS_ID\n"
				+ "WHERE q.QXS_ID = " + countyId + "      AND d.DW_ID = "
				+ unitId;

		if (departmentId != 0) {
			sql += "   and k.KS_ID = " + departmentId;
		}

		// level 最大的排前面 有多少不同的级别就有多少个数组
		// parent 和 level相同的为一个数组
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		JSONObject valueArry = new JSONObject();

		Set<String> levelSet = new LinkedHashSet<String>();
		try {
			System.err.println(sql);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if (list != null && list.size() > 0) {
				String level = "";
				int parent = 0;
				// 筛选层级
				for (Map<String, Object> map : list) {
					levelSet.add(map.get("profession_level") == null ? null
							: map.get("profession_level").toString());
				}
				// 添加层级
				for (String str : levelSet) {
					result.put(str, str);
					valueArry.put(str, new com.alibaba.fastjson.JSONArray());
				}

				JSONArray array = null;
				for (String str : levelSet) {
					array = (JSONArray) valueArry.get(str);
					for (Map<String, Object> map : list) {
						JSONObject voMap = new JSONObject();
						if (map.get("profession_level") != null
								&& Integer.parseInt(map.get("profession_level")
										.toString()) > 0) {
							level = map.get("profession_level").toString();
							if (str.equals(level)) {

								voMap.put("img", map.get("img") == null ? ""
										: map.get("img").toString());
								voMap.put("username",
										map.get("username") == null ? "" : map
												.get("username").toString());
								voMap.put("department",
										map.get("department") == null ? ""
												: map.get("department")
														.toString());
								voMap.put("post", map.get("post") == null ? ""
										: map.get("post").toString());
								array.add(voMap);
							}
						}
					}
					result.put(str, array);
				}
			}
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return result;
	}

	@Override
	public Map<String, Object> getDeptListByDeptType(int typeId, int startPage,
			int pageCount, String fuzzy) {
		String fuzzySql = "";
		String fuzzySqlCount = "";

		if (!StringUtils.isEmpty(fuzzy)) {
			int state = OrganizationStateEnum.getValue(fuzzy);
			int level = OrgnizationLevelEnum.getValue(fuzzy);
			fuzzySql = "AND (d.DW_MC like '%"
					+ fuzzy
					+ "%' OR (select f.DWLX_MC FROM fc_dwlxb as f WHERE d.DW_TYPE = f.DWLX_id) LIKE '%"
					+ fuzzy + "%' OR d.org_code LIKE '%" + fuzzy
					+ "%' OR d.QYZT LIKE '%" + state
					+ "%' OR d.org_level LIKE '%" + level + "%')";
			fuzzySqlCount = "AND (DW_MC like '%"
					+ fuzzy
					+ "%' OR (select f.DWLX_MC FROM fc_dwlxb as f WHERE d.DW_TYPE = f.DWLX_id) LIKE '%"
					+ fuzzy + "%' OR org_code LIKE '%" + fuzzy
					+ "%'OR d.QYZT LIKE '%" + state
					+ "%' OR d.org_level LIKE '%" + level + "%')";
		}

		String sql = "SELECT d.DW_MC ,d.DW_ID,d.org_code,d.org_grade_type,d.org_level,(select f.DWLX_MC FROM fc_dwlxb as f WHERE d.DW_TYPE = f.DWLX_id)AS DW_TYPE ,(select count(*) from fc_dwb where DW_TYPE="
				+ typeId
				+ "\n"
				+ fuzzySqlCount
				+ ") as total,d.QYZT from fc_dwb as d where DW_TYPE = "
				+ typeId + "\n" + fuzzySql;
		String page = "ORDER BY d.XH ASC limit \n" + startPage * pageCount
				+ "," + pageCount;
		List<FcDwb> fcDwbList = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			fcDwbList = new ArrayList<FcDwb>();
			System.err.println(sql);
			list = jdbcTemplate.queryForList(sql + "\n" + page);
			for (int i = 0; i < list.size(); i++) {
				FcDwb fcDwb = new FcDwb();
				fcDwb.setDwId(StringUtils.isEmpty(list.get(i).get("DW_ID")) ? 0
						: Integer.valueOf(list.get(i).get("DW_ID").toString()));
				fcDwb.setDwMc(StringUtils.isEmpty(list.get(i).get("DW_MC")) ? null
						: list.get(i).get("DW_MC").toString());
				fcDwb.setStrDwType(StringUtils.isEmpty(list.get(i).get(
						"DW_Type")) ? null : list.get(i).get("DW_Type")
						.toString());
				fcDwb.setQyzt(StringUtils.isEmpty(list.get(i).get("QYZT")) ? -1
						: Integer.valueOf(list.get(i).get("QYZT").toString()));
				fcDwb.setOrgCode(StringUtils.isEmpty(list.get(i)
						.get("org_code")) ? null : list.get(i).get("org_code")
						.toString());
				fcDwb.setOrgGradeType(StringUtils.isEmpty(list.get(i).get(
						"org_grade_type")) ? -1 : Integer.valueOf(list.get(i)
						.get("org_grade_type").toString()));
				fcDwb.setOrgLevel(StringUtils.isEmpty(list.get(i).get(
						"org_level")) ? -1 : Integer.valueOf(list.get(i)
						.get("org_level").toString()));
				fcDwbList.add(fcDwb);
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		map.put("data", fcDwbList);
		map.put("Resulttotal",
				StringUtils.isEmpty(list.get(0).get("total")) ? 0 : Integer
						.valueOf(list.get(0).get("total").toString()));
		map.put("pageIndex", startPage);
		map.put("pageSize", pageCount);
		map.put("pageNum", fcDwbList.size() % pageCount == 0 ? fcDwbList.size()
				/ pageCount : fcDwbList.size() / pageCount + 1);

		return map;
	}

	private JSONArray autoCreare() {

		return new JSONArray();
	}

	@Override
	public Map<String, Object> getObjectByTypeIdAndId(int typeId, int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object object = null;
		try {
			if (typeId == 1) {
				object = fcQxsbRepository.findOne(id);
			}
			if (typeId == 2) {
				FcDwb fcDwb = fcDwbRepository.findOne(id);
				object = fcDwb;

			}
			if (typeId == 3) {
				FcKsb fcKsb = fcKsbRepository.findOne(id);

				FcDwb fcDwb = fcDwbRepository.findOne(fcKsb.getDwId());
				fcKsb.setStrDwName(fcDwb.getDwMc());
				FcQxsb fcQxsb = fcQxsbRepository.findOne(fcDwb.getQxsId());
				fcKsb.setStrQxsName(fcQxsb.getQxsMc());
				object = fcKsb;
			}
			if (typeId == 4) {
				String sql = "SELECT  t.id,t.description,t.window_name,t.xh,t.status,GROUP_CONCAT(t.user_id) as listid ,GROUP_CONCAT(t.ry_mc) as list  from (SELECT  t.id, t.description,t.window_name,t.xh,t.status,p.user_id,f.RY_MC  from ts_window  as t  LEFT JOIN ts_window_person as p on t.id = p.window_id left JOIN fc_ryb as f ON f.RY_ID=p.user_id where  t.id="
						+ id + ") AS t GROUP BY t.window_name";
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
				TsWindow tsWindow = new TsWindow();
				tsWindow.setId(StringUtils.isEmpty(list.get(0).get("id")) ? -1
						: Integer.valueOf(list.get(0).get("id").toString()));
				tsWindow.setWindowName(StringUtils.isEmpty(list.get(0).get(
						"window_name")) ? null : list.get(0).get("window_name")
						.toString());
				tsWindow.setDescription(StringUtils.isEmpty(list.get(0).get(
						"description")) ? null : list.get(0).get("description")
						.toString());
				tsWindow.setStatus(StringUtils.isEmpty(list.get(0)
						.get("status")) ? -1 : Integer.valueOf(list.get(0)
						.get("status").toString()));
				tsWindow.setXh(StringUtils.isEmpty(list.get(0).get("xh")) ? -1
						: Integer.valueOf(list.get(0).get("xh").toString()));
				if (StringUtils.isEmpty(list.get(0).get("list"))) {
					tsWindow.setRyName(null);
				} else {
					List<String> ryNameList = new ArrayList<>();
					String idString = list.get(0).get("list").toString();
					String[] sourceStrArray = idString.split(",");
					for (int s = 0; s < sourceStrArray.length; s++) {
						ryNameList.add(sourceStrArray[s]);
					}
					tsWindow.setRyName(ryNameList);
				}
				if (StringUtils.isEmpty(list.get(0).get("listid"))) {
					tsWindow.setRid(null);
				} else {
					List<String> ryIdList = new ArrayList<>();
					String idString = list.get(0).get("listid").toString();
					String[] sourceStrArray = idString.split(",");
					for (int s = 0; s < sourceStrArray.length; s++) {
						ryIdList.add(sourceStrArray[s]);
					}
					tsWindow.setRid(ryIdList);
				}
				object = tsWindow;
			}
			if (typeId == 5) {
				FcKszwb fcKszwb = new FcKszwb();
				String sql = "select z.kszw_id,z.kszw_mc,z.ks_id,z.xh,z.qyzt,z.profession_level,z.parent_id,(select kszw_mc from fc_kszwb where kszw_id = z.parent_id) as parentname,(select ks_mc from fc_ksb where ks_id = z.ks_id) as ksname from fc_kszwb  as z where KSZW_ID="
						+ id;
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
				fcKszwb.setKszwId(StringUtils.isEmpty(list.get(0)
						.get("kszw_id")) ? 0 : Integer.valueOf(list.get(0)
						.get("kszw_id").toString()));
				fcKszwb.setKszwMc(StringUtils.isEmpty(list.get(0)
						.get("kszw_mc")) ? null : list.get(0).get("kszw_mc")
						.toString());
				fcKszwb.setXh(StringUtils.isEmpty(list.get(0).get("xh")) ? 0
						: Double.valueOf(list.get(0).get("xh").toString()));
				fcKszwb.setQyzt(StringUtils.isEmpty(list.get(0).get("qyzt")) ? -1
						: Integer.valueOf(list.get(0).get("qyzt").toString()));
				fcKszwb.setProfessionLevel(StringUtils.isEmpty(list.get(0).get(
						"profession_level")) ? 0 : Integer.valueOf(list.get(0)
						.get("profession_level").toString()));
				fcKszwb.setParentName(StringUtils.isEmpty(list.get(0).get(
						"parentname")) ? null : list.get(0).get("parentname")
						.toString());
				fcKszwb.setKsName(StringUtils
						.isEmpty(list.get(0).get("ksname")) ? null : list
						.get(0).get("ksname").toString());
				fcKszwb.setParentId(StringUtils
						.isEmpty(list.get(0).get("parent_id")) ? null : Integer.valueOf(list
						.get(0).get("parent_id").toString()));
				FcKsb fcKsb = fcKsbRepository.findOne(Integer.valueOf(list
						.get(0).get("ks_id").toString()));
				FcDwb fcDwb = fcDwbRepository.findOne(fcKsb.getDwId());
				FcQxsb fcQxsb = fcQxsbRepository.findOne(fcDwb.getQxsId());
				fcKszwb.setDwName(fcDwb.getDwMc());
				fcKszwb.setQxsName(fcQxsb.getQxsMc());
				fcKszwb.setDwCode(fcDwb.getOrgCode());
				object = fcKszwb;
				map.put("unitId", fcDwb.getDwId());
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}
		map.put("data", object);
		return map;
	}

	@Override
	public List<Map> getKsListByUnitId(int unitId) {		
		List<Map> list = new ArrayList<Map>();
		try {			
		List<FcKsb> ksList = fcKsbRepository.getKsByUnitId(unitId);
		for (FcKsb fcKsb : ksList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departmentNmae", fcKsb.getKsMc());
		map.put("departmentId", fcKsb.getKsId());
		List<FcKszwb> zwList = fcKszwbRepository.getZwByKsId(fcKsb.getKsId());
		 List<Map> childrenList = new ArrayList<>();
		for(FcKszwb fcKszwb:zwList){
			Map<String, Object> vomap= new HashMap<String, Object>();
			vomap.put("post", fcKszwb.getKszwMc());
			vomap.put("postId", fcKszwb.getKszwId());
			childrenList.add(vomap);
		}
        map.put("children", childrenList);
        list.add(map);
		}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return list;
	}

	@Override
	public Map<String, Object> deleteObject(Integer id, Integer type) {
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			
		
		if(type==1){
			fcQxsbRepository.deleteFcQxsb(id);
		}else if(type==2){
			fcDwbRepository.deleteFcDwb(id);
		}else if(type==3){
			fcKsbRepository.deleteFcKsb(id);
		}else if(type==4){
			tsWindowRepository.deleteWindow(id);
			tsWindowPersonRepository.deleteWindowByWindowId(id);
		}else if(type==5){
			fcKszwbRepository.deleteFcKszwb(id);
		}
		map.put("message", "删除成功");
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "删除失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> getParentByUnitId(Integer unitId) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			
			Integer parentId=fcKszwbRepository.getZwById(unitId);
			System.err.println(parentId);
			if(!StringUtils.isEmpty(parentId.toString())){
				map.put("data", true);
			}else{
				map.put("data", false);
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
			
		}
		
		
		return map;
	}

	@Override
	public Map<String, Object> getRyIdListByWindowId(Integer windowId,Integer unitId ) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			List<TsWindow> tsWindowList=tsWindowRepository.getwindowListByUnitId(Double.valueOf(unitId.toString()));
			List<Integer> windowIdList=new ArrayList<Integer>();
			for(TsWindow ts:tsWindowList){				
				if(!windowId.equals(ts.getId())&&!windowId.equals(0)){
				windowIdList.add(ts.getId());
				}
			}
		if(windowIdList.size()>0&&!StringUtils.isEmpty(windowIdList)){
			String windowString=org.apache.commons.lang.StringUtils.strip(windowIdList.toString(), "[]");
		String SQL="SELECT GROUP_CONCAT(user_id) as ryIds FROM ts_window_person WHERE window_id in("+windowString+");";
		System.err.println(SQL);
		List<Map<String, Object>>list=jdbcTemplate.queryForList(SQL);
		map.put("data", list);
		}else{
			map.put("data", windowIdList);
		}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("messge", "获取信息出错");
		}
		return map;
	}

	@Override
	public Map<String, Object> getWindowIdBysignUserId(Integer signUserId) {
		Map<String, Object> map=new HashMap<String, Object>();
		TsWindowPerson tsWindowPerson=tsWindowPersonRepository.findByUserId(signUserId);
		if(StringUtils.isEmpty(tsWindowPerson)){
			map.put("windowId", 0);
		}else{
			map.put("windowId", tsWindowPerson.getWindowId());
		}
		return map;
	}


}
