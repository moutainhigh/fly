package com.xinfang.evaluating.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xinfang.VO.TemplateMaintenanceVO;
import com.xinfang.evaluating.dao.FpAssessScoreDao;
import com.xinfang.evaluating.dao.FpAssessUnitDao;
import com.xinfang.evaluating.dao.TemplateMaintenanceDao;
import com.xinfang.evaluating.model.FpAssessScoreEntity;
import com.xinfang.evaluating.service.ScoreService;
import com.xinfang.evaluating.vo.AssessReportVO;
import com.xinfang.log.ApiLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by sunbjx on 2017/5/16.
 */
@Service
public class ScoreServiceImpl implements ScoreService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FpAssessScoreDao fpAssessScoreDao;
	@Autowired
	private FpAssessUnitDao fpAssessUnitDao;
	@Autowired
	private TemplateMaintenanceDao templateMaintenanceDao;

	@Override
	public Map<String, Object> getScoreList(Integer assessUnitId,
			Integer assessId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TemplateMaintenanceVO> temList = new ArrayList<TemplateMaintenanceVO>();
		JSONArray json = new JSONArray();
		String sql = "SELECT f.*,a.initiator_headsrc,a.initiator_name,a.gmt_create,"
				+ "(SELECT DW_MC FROM fc_dwb WHERE dw_id = f.assess_unit_id) as assessUnit,"
				+ "a.sponsor_unit_name,a.item_name FROM fp_assess_score as f LEFT JOIN "
				+ " fp_assess as a ON f.assess_id=a.assess_id WHERE f.assess_unit_id="
				+ assessUnitId + " AND f.assess_id = " + assessId;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < list.size(); i++) {
				TemplateMaintenanceVO tem = new TemplateMaintenanceVO();

				tem.setCofficient(StringUtils.isEmpty(list.get(i).get(
						"coefficient")) ? 0 : Float.valueOf(list.get(i)
						.get("coefficient").toString()));
				tem.setControl(StringUtils.isEmpty(list.get(i).get("control")) ? 0
						: list.get(i).get("control").equals(true) ? 1 : 0);
				tem.setDesc(StringUtils.isEmpty(list.get(i).get(
						"templet_describe")) ? null : list.get(i)
						.get("templet_describe").toString());
				tem.setInType(StringUtils.isEmpty(list.get(i).get(
						"entering_type")) ? 0 : list.get(i)
						.get("entering_type").equals(true) ? 1 : 0);
				tem.setLevel(StringUtils.isEmpty(list.get(i).get("level")) ? null
						: list.get(i).get("level").toString());
				tem.setNumber(StringUtils.isEmpty(list.get(i).get("number")) ? 0
						: Integer.valueOf(list.get(i).get("number").toString()));
				tem.setPid(StringUtils.isEmpty(list.get(i).get("FID")) ? 0
						: Integer.valueOf(list.get(i).get("FID").toString()));
				tem.setSetvalue(StringUtils
						.isEmpty(list.get(i).get("setvalue")) ? 0 : Float
						.valueOf(list.get(i).get("setvalue").toString()));
				tem.setSort(StringUtils.isEmpty(list.get(i).get("sort")) ? 0
						: Integer.valueOf(list.get(i).get("sort").toString()));
				tem.setStandard(StringUtils
						.isEmpty(list.get(i).get("standard")) ? null : list
						.get(i).get("standard").toString());
				tem.setTemplateId(StringUtils.isEmpty(list.get(i).get(
						"templet_id")) ? 0 : Integer.valueOf(list.get(i)
						.get("templet_id").toString()));
				tem.setScoreId(StringUtils.isEmpty(list.get(i).get("score_id")) ? 0
						: Integer.valueOf(list.get(i).get("score_id")
								.toString()));
				tem.setTemplateNumber(StringUtils.isEmpty(list.get(i).get(
						"templet_number")) ? null : list.get(i)
						.get("templet_number").toString());
				tem.setRealityScore(StringUtils.isEmpty(list.get(i).get(
						"reality_score")) ? 0 : Float.valueOf(list.get(i)
						.get("reality_score").toString()));
				tem.setAssessName(StringUtils.isEmpty(list.get(i).get(
						"item_name")) ? null : list.get(i).get("item_name")
						.toString());
				tem.setAssessUnit(StringUtils.isEmpty(list.get(i).get(
						"assessUnit")) ? null : list.get(i).get("assessUnit")
						.toString());
				tem.setGmtTime(StringUtils.isEmpty(list.get(i)
						.get("gmt_create")) ? null : list.get(i)
						.get("gmt_create").toString());
				tem.setSponsorUnitName(StringUtils.isEmpty(list.get(i).get(
						"sponsor_unit_name")) ? null : list.get(i)
						.get("sponsor_unit_name").toString());
				tem.setInitiatorHeadsrc(StringUtils.isEmpty(list.get(i).get(
						"initiator_headsrc")) ? null : list.get(i)
						.get("initiator_headsrc").toString());
				tem.setNitiatorName(StringUtils.isEmpty(list.get(i).get(
						"initiator_name")) ? null : list.get(i)
						.get("initiator_name").toString());
				temList.add(tem);

			}
			Integer parentId = fpAssessScoreDao
					.getParentIdByUnitId(assessUnitId,assessId);
			TemplateMaintenanceVO tem = getNodeById(parentId, temList);
			json = JSONArray.fromObject(temList);
			JSONArray js = treeMenuList(json, parentId);
			tem.setChildren(js);
			map.put("data", tem);
			map.put("assessName", StringUtils.isEmpty(list.get(0).get(
					"item_name")) ? null : list.get(0).get("item_name")
					.toString());
			map.put("assessUnit", StringUtils.isEmpty(list.get(0).get(
					"assessUnit")) ? null : list.get(0).get("assessUnit")
					.toString());
			map.put("gmtTime", StringUtils.isEmpty(list.get(0)
					.get("gmt_create")) ? null : list.get(0).get("gmt_create")
					.toString());
			map.put("sponsorUnitName",
					StringUtils.isEmpty(list.get(0).get("sponsor_unit_name")) ? null
							: list.get(0).get("sponsor_unit_name").toString());
			map.put("initiatorHeadsrc",
					StringUtils.isEmpty(list.get(0).get("initiator_headsrc")) ? null
							: list.get(0).get("initiator_headsrc").toString());
			map.put("initiatorName",
					StringUtils.isEmpty(list.get(0).get("initiator_name")) ? null
							: list.get(0).get("initiator_name").toString());
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	// 遍历出相应的子集
	public JSONArray treeMenuList(JSONArray menuList, int parentId) {
		JSONArray childMenu = new JSONArray();
		for (Object object : menuList) {
			JSONObject jsonMenu = JSONObject.fromObject(object);
			int menuId = jsonMenu.getInt("templateId");
			int pid = jsonMenu.getInt("pid");
			if (parentId == pid) {
				JSONArray c_node = treeMenuList(menuList, menuId);
				jsonMenu.put("children", c_node);
				childMenu.add(jsonMenu);
			}
		}
		return childMenu;
	}

	// 获取根对象
	public TemplateMaintenanceVO getNodeById(int parentId,
			List<TemplateMaintenanceVO> temList) {
		TemplateMaintenanceVO tem = new TemplateMaintenanceVO();
		for (TemplateMaintenanceVO item : temList) {
			if (item.getTemplateId() == parentId) {
				tem = item;
				break;
			}
		}
		return tem;
	}

	@Override
	public Map<String, Object> getAssessDetail(Integer assessId) {
		Map<String, Object> map = new HashMap<>();
		String sql = "";
		try {
			List<Map<String, Object>> assessLists = new ArrayList<>();
			List<Integer> unitList = fpAssessUnitDao
					.getUnitIdByAssessId(assessId);
			for (int i = 0; i < unitList.size(); i++) {
				List<AssessReportVO> assessList = new ArrayList<>();
				Map<String, Object> assessMap = new HashMap<>();
				sql = "SELECT DISTINCT standard,reality_score,templet_type,assess_unit_id, "
						+ " (SELECT reality_score FROM fp_assess_score "
						+ " WHERE fid is null and assess_unit_id="
						+ unitList.get(i)
						+ " AND assess_id = "+assessId+") as total,"
						+ " (SELECT dw_MC FROM fc_dwb WHERE dw_id = assess_unit_id) as dwName"
						+ " FROM fp_assess_score "
						+ " WHERE fid=(SELECT templet_id FROM fp_assess_score "
						+ " WHERE fid IS NULL AND assess_unit_id = "
						+ unitList.get(i) + " AND assess_id ="+assessId+") AND assess_unit_id ="+unitList.get(i)
						+" AND assess_id="+assessId;
				System.err.println(sql);
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
				for (int l = 0; l < list.size(); l++) {
					AssessReportVO as = new AssessReportVO();
					as.setAtandard(StringUtils.isEmpty(list.get(l).get(
							"standard")) ? null : list.get(l).get("standard")
							.toString());
					as.setAssessUnit(StringUtils.isEmpty(list.get(l).get(
							"dwName")) ? null : list.get(l).get("dwName")
							.toString());
					as.setRealityScore(StringUtils.isEmpty(list.get(l).get(
							"total")) ? 0 : Float.valueOf(list.get(l)
							.get("total").toString()));
					as.setBase(StringUtils.isEmpty(list.get(l).get(
							"reality_score")) ? 0 : Float.valueOf(list.get(l)
							.get("reality_score").toString()));
					as.setTemlateType(StringUtils.isEmpty(list.get(l).get(
							"templet_type")) ? -1 : list.get(l)
							.get("templet_type").equals(true) ? 1 : 0);

					assessList.add(as);
					assessMap.put("dataDetail", assessList);
					assessMap.put("templet_type", StringUtils.isEmpty(list.get(l).get(
							"templet_type")) ? -1 : list.get(l)
							.get("templet_type").equals(true) ? 1 : 0);
					assessMap.put("dwName", StringUtils.isEmpty(list.get(l)
							.get("dwName")) ? null : list.get(l).get("dwName")
							.toString());
					assessMap.put("dwId", StringUtils.isEmpty(list.get(l)
							.get("assess_unit_id"))?null:Integer.valueOf(list.get(l)
							.get("assess_unit_id").toString()));
					assessMap.put(
							"realityScore",
							StringUtils.isEmpty(list.get(l)
									.get("reality_score")) ? 0 : Float
									.valueOf(list.get(l).get("reality_score")
											.toString()));
				}

				assessLists.add(assessMap);

			}

			map.put("data", assessLists);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}

	@Override
	public Map<String, Object> scoreList(Integer assessId, Integer unitId,
			Integer unitType) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (getScoreIdByunitId(unitId, assessId)) {

				List<TemplateMaintenanceVO> list = getScoreList(unitType);

				for (TemplateMaintenanceVO T : list) {
					FpAssessScoreEntity f = new FpAssessScoreEntity();
					f.setAssessUnitId(unitId);
					f.setAssessId(assessId);
					f.setTempletType(T.getTemplateType());
					f.setCoefficient(T.getCofficient() == -1 ? null
							: (double) T.getCofficient());
					f.setControl(T.getControl() == -1 ? null : T.getControl());
					f.setEnteringType(T.getInType() == -1 ? null : T
							.getInType());
					f.setFid(StringUtils.isEmpty(T.getPid()) ? null : T
							.getPid());
					f.setLevel(StringUtils.isEmpty(T.getLevel()) ? null : T
							.getLevel());
					f.setNumber(StringUtils.isEmpty(T.getNumber()) ? null : T
							.getNumber());
					f.setSetvalue(T.getSetvalue() == -1 ? null : (double) T
							.getSetvalue());
					f.setStandard(StringUtils.isEmpty(T.getStandard()) ? null
							: T.getStandard());
					f.setTempletNumber(StringUtils.isEmpty(T
							.getTemplateNumber()) ? null : T
							.getTemplateNumber());
					f.setTempletId(StringUtils.isEmpty(T.getTemplateId()) ? null
							: T.getTemplateId());
					f.setSort(StringUtils.isEmpty(T.getSort()) ? null : T
							.getSort());
					f.setTempletDescribe(StringUtils.isEmpty(T.getDesc()) ? null
							: T.getDesc());
					fpAssessScoreDao.save(f);
				}

			}
			map.put("data", scoreList2(assessId, unitId));
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	private List<TemplateMaintenanceVO> getScoreList(Integer unitType) {
		String sql = "select * from fp_check_templet where  templet_type ="
				+ unitType;
		List<TemplateMaintenanceVO> temList = new ArrayList<TemplateMaintenanceVO>();
		try {

			List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql);

			for (int i = 0; i < lists.size(); i++) {
				TemplateMaintenanceVO tem = new TemplateMaintenanceVO();
				tem.setCofficient(StringUtils.isEmpty(lists.get(i).get(
						"coefficient")) ? -1 : Float.valueOf(lists.get(i)
						.get("coefficient").toString()));
				tem.setControl(StringUtils.isEmpty(lists.get(i).get("control")) ? -1
						: lists.get(i).get("control").equals(true) ? 1 : 0);
				tem.setDesc(StringUtils.isEmpty(lists.get(i).get("descs")) ? null
						: lists.get(i).get("descs").toString());
				tem.setInType(StringUtils.isEmpty(lists.get(i).get(
						"entering_type")) ? -1 : lists.get(i)
						.get("entering_type").equals(true) ? 1 : 0);
				tem.setTemplateType(StringUtils.isEmpty(lists.get(i).get(
						"templet_type")) ? -1 : lists.get(i)
						.get("templet_type").equals(true) ? 1 : 0);
				tem.setLevel(StringUtils.isEmpty(lists.get(i).get("levels")) ? null
						: lists.get(i).get("levels").toString());
				tem.setNumber(StringUtils.isEmpty(lists.get(i).get("number")) ? null
						: Integer
								.valueOf(lists.get(i).get("number").toString()));
				tem.setPid(StringUtils.isEmpty(lists.get(i).get("fid")) ? null
						: Integer.valueOf(lists.get(i).get("fid").toString()));
				tem.setSetvalue(StringUtils.isEmpty(lists.get(i)
						.get("setvalue")) ? -1 : Float.valueOf(lists.get(i)
						.get("setvalue").toString()));
				tem.setSort(StringUtils.isEmpty(lists.get(i).get("sort")) ? null
						: Integer.valueOf(lists.get(i).get("sort").toString()));
				tem.setStandard(StringUtils.isEmpty(lists.get(i)
						.get("standard")) ? null : lists.get(i).get("standard")
						.toString());
				tem.setTemplateId(StringUtils.isEmpty(lists.get(i).get(
						"templet_id")) ? null : Integer.valueOf(lists.get(i)
						.get("templet_id").toString()));
				tem.setTemplateNumber(StringUtils.isEmpty(lists.get(i).get(
						"templet_number")) ? null : lists.get(i)
						.get("templet_number").toString());
				temList.add(tem);

			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.err.println(e.getMessage());
		}

		return temList;
	}

	private boolean getScoreIdByunitId(Integer unitId, Integer assessId) {
		String sql = "select * from fp_assess_score where  assess_unit_id ="
				+ unitId + " AND assess_id=" + assessId;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if (list.size() > 0) {
			return false;
		}
		if (list.size() == 0) {
			return true;
		}
		return false;

	}

	@Override
	public Map<String, Object> scoreList2(Integer assessId, Integer unitId) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<TemplateMaintenanceVO> temList = new ArrayList<TemplateMaintenanceVO>();
		String sql = "SELECT f.*,a.initiator_headsrc,a.initiator_name,a.gmt_create,"
				+ "(SELECT DW_MC FROM fc_dwb WHERE dw_id = f.assess_unit_id) as assessUnit,"
				+ "a.sponsor_unit_name,a.item_name FROM fp_assess_score as f LEFT JOIN "
				+ " fp_assess as a ON f.assess_id=a.assess_id WHERE f.assess_unit_id="
				+ unitId
				+ " AND f.assess_id = "
				+ assessId
				+ " Order BY f.sort asc";
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < list.size(); i++) {
				TemplateMaintenanceVO tem = new TemplateMaintenanceVO();

				tem.setCofficient(StringUtils.isEmpty(list.get(i).get(
						"coefficient")) ? 0 : Float.valueOf(list.get(i)
						.get("coefficient").toString()));
				tem.setControl(StringUtils.isEmpty(list.get(i).get("control")) ? 0
						: list.get(i).get("control").equals(true) ? 1 : 0);
				tem.setDesc(StringUtils.isEmpty(list.get(i).get(
						"templet_describe")) ? null : list.get(i)
						.get("templet_describe").toString());
				tem.setInType(StringUtils.isEmpty(list.get(i).get(
						"entering_type")) ? 0 : Integer.valueOf(list.get(i)
						.get("entering_type").toString()));
				tem.setLevel(StringUtils.isEmpty(list.get(i).get("level")) ? null
						: list.get(i).get("level").toString());
				tem.setNumber(StringUtils.isEmpty(list.get(i).get("number")) ? 0
						: Integer.valueOf(list.get(i).get("number").toString()));
				tem.setPid(StringUtils.isEmpty(list.get(i).get("FID")) ? 0
						: Integer.valueOf(list.get(i).get("FID").toString()));
				tem.setSetvalue(StringUtils
						.isEmpty(list.get(i).get("setvalue")) ? 0 : Float
						.valueOf(list.get(i).get("setvalue").toString()));
				tem.setSort(StringUtils.isEmpty(list.get(i).get("sort")) ? 0
						: Integer.valueOf(list.get(i).get("sort").toString()));
				tem.setStandard(StringUtils
						.isEmpty(list.get(i).get("standard")) ? null : list
						.get(i).get("standard").toString());
				tem.setTemplateId(StringUtils.isEmpty(list.get(i).get(
						"templet_id")) ? 0 : Integer.valueOf(list.get(i)
						.get("templet_id").toString()));
				tem.setScoreId(StringUtils.isEmpty(list.get(i).get("score_id")) ? 0
						: Integer.valueOf(list.get(i).get("score_id")
								.toString()));
				tem.setTemplateNumber(StringUtils.isEmpty(list.get(i).get(
						"templet_number")) ? null : list.get(i)
						.get("templet_number").toString());
				tem.setRealityScore(StringUtils.isEmpty(list.get(i).get(
						"reality_score")) ? 0 : Float.valueOf(list.get(i)
						.get("reality_score").toString()));
				tem.setAssessName(StringUtils.isEmpty(list.get(i).get(
						"item_name")) ? null : list.get(i).get("item_name")
						.toString());
				tem.setAssessUnit(StringUtils.isEmpty(list.get(i).get(
						"assessUnit")) ? null : list.get(i).get("assessUnit")
						.toString());
				tem.setGmtTime(StringUtils.isEmpty(list.get(i)
						.get("gmt_create")) ? null : list.get(i)
						.get("gmt_create").toString());
				tem.setSponsorUnitName(StringUtils.isEmpty(list.get(i).get(
						"sponsor_unit_name")) ? null : list.get(i)
						.get("sponsor_unit_name").toString());
				tem.setInitiatorHeadsrc(StringUtils.isEmpty(list.get(i).get(
						"initiator_headsrc")) ? null : list.get(i)
						.get("initiator_headsrc").toString());
				tem.setNitiatorName(StringUtils.isEmpty(list.get(i).get(
						"initiator_name")) ? null : list.get(i)
						.get("initiator_name").toString());
				temList.add(tem);

			}
			map.put("data", temList);
			map.put("assessName", StringUtils.isEmpty(list.get(0).get(
					"item_name")) ? null : list.get(0).get("item_name")
					.toString());
			map.put("assessUnit", StringUtils.isEmpty(list.get(0).get(
					"assessUnit")) ? null : list.get(0).get("assessUnit")
					.toString());
			map.put("gmtTime", StringUtils.isEmpty(list.get(0)
					.get("gmt_create")) ? null : list.get(0).get("gmt_create")
					.toString());
			map.put("sponsorUnitName",
					StringUtils.isEmpty(list.get(0).get("sponsor_unit_name")) ? null
							: list.get(0).get("sponsor_unit_name").toString());
			map.put("initiatorHeadsrc",
					StringUtils.isEmpty(list.get(0).get("initiator_headsrc")) ? null
							: list.get(0).get("initiator_headsrc").toString());
			map.put("initiatorName",
					StringUtils.isEmpty(list.get(0).get("initiator_name")) ? null
							: list.get(0).get("initiator_name").toString());
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	@Override
	public Map<String, Object> updateScore(List<Map> listMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			for (int i = 0; i < listMap.size(); i++) {
				if (!StringUtils.isEmpty(listMap.get(i).get("scoreId"))) {
					FpAssessScoreEntity fpAssessScoreEntity = fpAssessScoreDao
							.findOne(Integer.valueOf(listMap.get(i)
									.get("scoreId").toString()));
					if (!StringUtils.isEmpty(fpAssessScoreEntity)) {
						if (!StringUtils
								.isEmpty(listMap.get(i).get("fileName"))) {
							fpAssessScoreEntity.setFileName(listMap.get(i)
									.get("fileName").toString());
						}
						if (!StringUtils.isEmpty(listMap.get(i).get("desc"))) {
							fpAssessScoreEntity.setDesc(listMap.get(i)
									.get("desc").toString());
						}
						if (!StringUtils.isEmpty(listMap.get(i).get("number"))) {
							fpAssessScoreEntity.setNumber(Integer
									.valueOf(listMap.get(i).get("number")
											.toString()));
						}
						if (!StringUtils.isEmpty(listMap.get(i).get(
								"realityScore"))) {
							fpAssessScoreEntity.setRealityScore(Float
									.valueOf(listMap.get(i).get("realityScore")
											.toString()));
						}
						fpAssessScoreDao.save(fpAssessScoreEntity);
					} else {
						map.put("message", "修改信息失败");
					}
				}

			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.err.println(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	@Override
	public Map<String, Object> getScoreDetail(Integer scoreId) {
		String sql = "SELECT  f.*,"
				+ "(SELECT standard FROM fp_assess_score WHERE templet_id=f.fid AND assess_id=f.assess_id AND assess_unit_id=f.assess_unit_id) as onestandard,"
				+ "a.initiator_headsrc,a.initiator_name,a.gmt_create,"
				+ " (SELECT DW_MC FROM fc_dwb WHERE dw_id = f.assess_unit_id) as assessUnit,"
				+ " a.sponsor_unit_name,a.item_name "
				+ " FROM fp_assess_score as f LEFT JOIN  fp_assess as a ON f.assess_id=a.assess_id WHERE  "
				+ " f.score_id =" + scoreId;
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<TemplateMaintenanceVO> temList = new ArrayList<TemplateMaintenanceVO>();
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < list.size(); i++) {
				TemplateMaintenanceVO tem = new TemplateMaintenanceVO();
				tem.setDesc(StringUtils.isEmpty(list.get(i).get(
						"templet_describe")) ? null : list.get(i)
						.get("templet_describe").toString());
				tem.setStandard(StringUtils
						.isEmpty(list.get(i).get("standard")) ? null : list
						.get(i).get("standard").toString());
				tem.setTemplateId(StringUtils.isEmpty(list.get(i).get(
						"templet_id")) ? 0 : Integer.valueOf(list.get(i)
						.get("templet_id").toString()));
				tem.setScoreId(StringUtils.isEmpty(list.get(i).get("score_id")) ? 0
						: Integer.valueOf(list.get(i).get("score_id")
								.toString()));
				tem.setAssessName(StringUtils.isEmpty(list.get(i).get(
						"item_name")) ? null : list.get(i).get("item_name")
						.toString());
				tem.setAssessUnit(StringUtils.isEmpty(list.get(i).get(
						"assessUnit")) ? null : list.get(i).get("assessUnit")
						.toString());
				tem.setGmtTime(StringUtils.isEmpty(list.get(i)
						.get("gmt_create")) ? null : list.get(i)
						.get("gmt_create").toString());
				tem.setSponsorUnitName(StringUtils.isEmpty(list.get(i).get(
						"sponsor_unit_name")) ? null : list.get(i)
						.get("sponsor_unit_name").toString());
				tem.setInitiatorHeadsrc(StringUtils.isEmpty(list.get(i).get(
						"initiator_headsrc")) ? null : list.get(i)
						.get("initiator_headsrc").toString());
				tem.setNitiatorName(StringUtils.isEmpty(list.get(i).get(
						"initiator_name")) ? null : list.get(i)
						.get("initiator_name").toString());
				tem.setParentStandard(StringUtils.isEmpty(list.get(i).get(
						"onestandard")) ? null : list.get(i)
						.get("onestandard").toString());
				temList.add(tem);
			}
			map.put("data", temList);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	@Override
	public Map<String, Object> getScoreBetailById(Integer scoreId) {
		Map<String, Object> map = new HashMap<>();
		String sql = " SELECT f.score_id,f.standard,(SELECT standard FROM fp_assess_score WHERE templet_id=f.fid AND assess_id=f.assess_id AND assess_unit_id=f.assess_unit_id ) as two,"
					+" (SELECT standard FROM fp_assess_score WHERE templet_id=(SELECT fid FROM fp_assess_score WHERE templet_id=f.fid AND assess_id=f.assess_id AND assess_unit_id=f.assess_unit_id)AND  assess_id=f.assess_id AND assess_unit_id=f.assess_unit_id) as one,"
					+ " f.describe,f.file_name FROM fp_assess_score as f WHERE f.score_id ="
				    + scoreId;
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			map.put("OneStandard",
					StringUtils.isEmpty(list.get(0).get("one")) ? null : list
							.get(0).get("one").toString());
			map.put("TwoStandard",
					StringUtils.isEmpty(list.get(0).get("two")) ? null : list
							.get(0).get("two").toString());
			map.put("threeStandard", StringUtils.isEmpty(list.get(0).get(
					"standard")) ? null : list.get(0).get("standard")
					.toString());
			map.put("describe", StringUtils
					.isEmpty(list.get(0).get("describe")) ? null : list.get(0)
					.get("describe").toString());
			map.put("file_name", StringUtils.isEmpty(list.get(0).get(
					"file_name")) ? null : list.get(0).get("file_name")
					.toString());
			map.put("scoreId",  StringUtils.isEmpty(list.get(0).get(
					"score_id")) ? null : Integer.valueOf(list.get(0).get("score_id")
					.toString()));
		} catch (Exception e) {
			map.put("message", "获取信息出错");
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
		}

		return map;
	}

	@Override
	public Map<String, Object> updateFileNameAndDesc(String fileName,
			String desc, Integer scoreId) {
		Map<String, Object>  map=new HashMap<>();
		try {
			FpAssessScoreEntity fp=fpAssessScoreDao.findOne(scoreId);
			if(!StringUtils.isEmpty(fileName)){
				fp.setFileName(fileName);
			}
			if(!StringUtils.isEmpty(desc)){
				fp.setDesc(desc);
			}
			fpAssessScoreDao.save(fp);
			map.put("message", "修改成功");
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "修改失败");
		}
		return map;
	}

}
