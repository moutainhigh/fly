package com.xinfang.evaluating.service.impl;

import com.xinfang.VO.TemplateMaintenanceVO;
import com.xinfang.context.PageFinder;
import com.xinfang.dao.UserRepository;
import com.xinfang.evaluating.dao.FpAssessDao;
import com.xinfang.evaluating.dao.FpAssessScoreDao;
import com.xinfang.evaluating.dao.FpAssessUnitDao;
import com.xinfang.evaluating.model.FpAssessEntity;
import com.xinfang.evaluating.model.FpAssessScoreEntity;
import com.xinfang.evaluating.model.FpAssessUnitEntity;
import com.xinfang.evaluating.service.AssessService;
import com.xinfang.evaluating.vo.AssessCountVO;
import com.xinfang.evaluating.vo.AssessReportVO;
import com.xinfang.evaluating.vo.AssessVO;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FcRyb;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import java.util.*;

/**
 * Created by sunbjx on 2017/5/16.
 */
@Service
@Transactional
public class AssessServiceImple implements AssessService {

	@Autowired
	private FpAssessDao assessDao;
	@Autowired
	private FpAssessUnitDao assessUnitDao;
	@Autowired
	private FpAssessScoreDao fpAssessScoreDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void assessSave(FpAssessEntity assess, List<Map> unitTypeA,
			List<Map> unitTypeB) {

		try {
			assess.setGmtCreate(new Date());
			assess.setState(0);
			FpAssessEntity assessEntity = assessDao.save(assess);

			/*
			 * if (unitTypeA != null) { Iterator a =
			 * unitTypeA.entrySet().iterator(); while (a.hasNext()) { Object obj
			 * = a.next(); String key = obj.toString(); String[] unitA =
			 * key.split("=");
			 * 
			 * FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
			 * assessUnitEntity.setAssessId(assessEntity.getAssessId());
			 * assessUnitEntity.setUnitId(Integer.valueOf(unitA[0]));
			 * assessUnitEntity.setAssessUnitType((byte) 0);
			 * assessUnitEntity.setAssessUnitName(unitA[1]);
			 * assessUnitDao.save(assessUnitEntity); } }
			 * 
			 * if (unitTypeB != null) { Iterator b =
			 * unitTypeB.entrySet().iterator();
			 * 
			 * while (b.hasNext()) { Object obj = b.next(); String key =
			 * obj.toString(); String[] unitB = key.split("=");
			 * 
			 * FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
			 * assessUnitEntity.setAssessId(assessEntity.getAssessId());
			 * assessUnitEntity
			 * .setUnitId(Integer.valueOf(Integer.valueOf(unitB[0])));
			 * assessUnitEntity.setAssessUnitType((byte) 1);
			 * assessUnitEntity.setAssessUnitName(unitB[1]);
			 * assessUnitDao.save(assessUnitEntity); } }
			 */
			//去重操作
			List<Map> listB = new ArrayList<Map>();
			List<Map> listA = new ArrayList<Map>();
			Set<Map> setA = new HashSet<>();
			Set<Map> setB = new HashSet<>();
			for (int i = 0; i < unitTypeA.size(); i++) {
				Map map = new HashMap();
				map.put("dwName", unitTypeA.get(i).get("dwName"));
				map.put("dwId", unitTypeA.get(i).get("dwId"));
				setA.add(map);
			}
			for (int i = 0; i < unitTypeB.size(); i++) {
				Map map = new HashMap();
				map.put("dwName", unitTypeB.get(i).get("dwName"));
				map.put("dwId", unitTypeB.get(i).get("dwId"));
				setB.add(map);
			}

			for (Map map : setA) {
				Map listMap = new HashMap();
				listMap.put("dwName", map.get("dwName"));
				listMap.put("dwId", map.get("dwId"));
				listA.add(listMap);
			}
			for (Map map : setB) {
				Map listMap = new HashMap();
				listMap.put("dwName", map.get("dwName"));
				listMap.put("dwId", map.get("dwId"));
				listB.add(map);
			}
			//循环添加A类单位（考核单位关联表）
			if (listA.size() > 0) {
				for (int i = 0; i < listA.size(); i++) {
					FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
					assessUnitEntity.setAssessId(assessEntity.getAssessId());
					assessUnitEntity.setUnitId(Integer.valueOf(Integer
							.valueOf(listA.get(i).get("dwId").toString())));
					assessUnitEntity.setAssessUnitType( 0);
					assessUnitEntity.setAssessUnitName(listA.get(i)
							.get("dwName").toString());
					assessUnitDao.save(assessUnitEntity);
				}
			}
			//循环添加B类单位（考核单位关联表）
			if (listB.size() > 0) {
				for (int i = 0; i < listB.size(); i++) {
					FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
					assessUnitEntity.setAssessId(assessEntity.getAssessId());
					assessUnitEntity.setUnitId(Integer.valueOf(Integer
							.valueOf(listB.get(i).get("dwId").toString())));
					assessUnitEntity.setAssessUnitType( 1);
					assessUnitEntity.setAssessUnitName(listB.get(i)
							.get("dwName").toString());
					assessUnitDao.save(assessUnitEntity);
				}
			}
			//为每个考核项目里面的考核单位配置考核模板
			for (int i = 0; i < listA.size(); i++) {
				List<TemplateMaintenanceVO> list = getScoreList(0);
				for (TemplateMaintenanceVO T : list) {
					FpAssessScoreEntity f = new FpAssessScoreEntity();
					f.setAssessUnitId(Integer.valueOf(Integer.valueOf(listA
							.get(i).get("dwId").toString())));
					f.setAssessId(assessEntity.getAssessId());
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
					f.setRealityScore(StringUtils.isEmpty(T.getRealityScore()) ? null
							: T.getRealityScore());
					fpAssessScoreDao.save(f);
				}
			}
			for (int i = 0; i < listB.size(); i++) {
				List<TemplateMaintenanceVO> list = getScoreList(1);
				for (TemplateMaintenanceVO T : list) {
					FpAssessScoreEntity f = new FpAssessScoreEntity();
					f.setAssessUnitId(Integer.valueOf(Integer.valueOf(listB
							.get(i).get("dwId").toString())));
					f.setAssessId(assessEntity.getAssessId());
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
					f.setRealityScore(StringUtils.isEmpty(T.getRealityScore()) ? null
							: T.getRealityScore());
					fpAssessScoreDao.save(f);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			ApiLog.chargeLog1(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> assessUpdate(Integer assessId, FpAssessEntity assess,
			List<Map> unitTypeA, List<Map> unitTypeB) {
		Map<String, Object> maps=new HashMap<String, Object>();
		try {
			
			FpAssessEntity assessEntity = assessDao.findOne(assessId);
			
			assessEntity.setItemName(assess.getItemName());
			
			assessEntity.setStartTime(assess.getStartTime());
			
			assessEntity.setEndTime(assess.getEndTime());
			
			assessEntity.setSponsorUnitId(assess.getSponsorUnitId());
			assessEntity.setDescription(assess.getDescription());
			assessEntity.setGmtModified(new Date());
			
			assessDao.save(assessEntity);
		
			/*
			 * if (unitTypeA != null) {
			 * assessUnitDao.removeByAssessIdAndAssessUnitType(assessId, 1);
			 * Iterator a = unitTypeA.entrySet().iterator(); while (a.hasNext())
			 * { Object obj = a.next(); String key = obj.toString(); String[]
			 * unitA = key.split("=");
			 * 
			 * FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
			 * assessUnitEntity.setAssessId(assessId);
			 * assessUnitEntity.setUnitId(Integer.valueOf(unitA[0]));
			 * assessUnitEntity.setAssessUnitType((byte) 1);
			 * assessUnitEntity.setAssessUnitName(unitA[1]);
			 * assessUnitDao.save(assessUnitEntity); } } if (unitTypeB != null)
			 * { assessUnitDao.removeByAssessIdAndAssessUnitType(assessId, 2);
			 * Iterator b = unitTypeB.entrySet().iterator(); while (b.hasNext())
			 * { Object obj = b.next(); String key = obj.toString(); String[]
			 * unitB = key.split("=");
			 * 
			 * FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
			 * assessUnitEntity.setAssessId(assessId);
			 * assessUnitEntity.setUnitId(Integer.valueOf(unitB[0]));
			 * assessUnitEntity.setAssessUnitType((byte) 1);
			 * assessUnitEntity.setAssessUnitName(unitB[1]);
			 * assessUnitDao.save(assessUnitEntity); }
			 */
			 
				List<Map> listB = new ArrayList<Map>();
				List<Map> listA = new ArrayList<Map>();
				Set<Map> setA = new HashSet<>();
				Set<Map> setB = new HashSet<>();
				for (int i = 0; i < unitTypeA.size(); i++) {
					Map map = new HashMap();
					map.put("dwName", unitTypeA.get(i).get("dwName"));
					map.put("dwId", unitTypeA.get(i).get("dwId"));
					setA.add(map);
				}
				for (int i = 0; i < unitTypeB.size(); i++) {
					Map map = new HashMap();
					map.put("dwName", unitTypeB.get(i).get("dwName"));
					map.put("dwId", unitTypeB.get(i).get("dwId"));
					setB.add(map);
				}

				for (Map map : setA) {
					Map listMap = new HashMap();
					listMap.put("dwName", map.get("dwName"));
					listMap.put("dwId", map.get("dwId"));
					listA.add(listMap);
				}
				for (Map map : setB) {
					Map listMap = new HashMap();
					listMap.put("dwName", map.get("dwName"));
					listMap.put("dwId", map.get("dwId"));
					listB.add(map);
				}

			if (listA.size() > 0) {
				System.out.println(listA);
				assessUnitDao.removeByAssessIdAndAssessUnitType(assessEntity.getAssessId(), 0);
				for (int i = 0; i < listA.size(); i++) {
					
					FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
					assessUnitEntity.setAssessId(assessEntity.getAssessId());
					assessUnitEntity.setUnitId(Integer.valueOf(Integer
							.valueOf(unitTypeA.get(i).get("dwId").toString())));
					assessUnitEntity.setAssessUnitType(0);
					assessUnitEntity.setAssessUnitName(unitTypeA.get(i)
							.get("dwName").toString());
					assessUnitDao.save(assessUnitEntity);
					if(StringUtils.isEmpty(fpAssessScoreDao.getParentObjectById(assessEntity.getAssessId(), Integer
							.valueOf(unitTypeA.get(i).get("dwId").toString())))){
						List<TemplateMaintenanceVO> list = getScoreList(0);
						for (TemplateMaintenanceVO T : list) {
							FpAssessScoreEntity f = new FpAssessScoreEntity();
							f.setAssessUnitId(Integer.valueOf(Integer
									.valueOf(unitTypeA.get(i).get("dwId").toString())));
							f.setAssessId(assessEntity.getAssessId());
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
				}
			}
			if (listB.size() > 0) {
				System.out.println(listB);
				assessUnitDao.removeByAssessIdAndAssessUnitType(assessEntity.getAssessId(), 1);
				for (int i = 0; i < listB.size(); i++) {
					FpAssessUnitEntity assessUnitEntity = new FpAssessUnitEntity();
					assessUnitEntity.setAssessId(assessEntity.getAssessId());
					assessUnitEntity.setUnitId(Integer.valueOf(Integer
							.valueOf(unitTypeB.get(i).get("dwId").toString())));
					assessUnitEntity.setAssessUnitType(1);
					assessUnitEntity.setAssessUnitName(unitTypeB.get(i)
							.get("dwName").toString());
					assessUnitDao.save(assessUnitEntity);
					if(StringUtils.isEmpty(fpAssessScoreDao.getParentObjectById(assessEntity.getAssessId(), Integer.valueOf(unitTypeB.get(i).get("dwId").toString())))){
						List<TemplateMaintenanceVO> list = getScoreList(1);
						for (TemplateMaintenanceVO T : list) {
							FpAssessScoreEntity f = new FpAssessScoreEntity();
							f.setAssessUnitId(Integer.valueOf(Integer.valueOf(unitTypeB
									.get(i).get("dwId").toString())));
							f.setAssessId(assessEntity.getAssessId());
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
				}
			}
			maps.put("message", "修改成功");
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			ApiLog.chargeLog1(e.getMessage());
			maps.put("message", "修改失败");
		}
		return maps;

	}

	@Override
	public void remove(Integer assessId) {
		try {
			assessDao.updateState(assessId);
			assessUnitDao.removeByAssessId(assessId);
			fpAssessScoreDao.deleteScoreByAssessId(assessId);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
	}

	@Override
	public FpAssessEntity assessDetail(Integer assessId) {
		FpAssessEntity assessVO = null;
		try {
			assessVO = assessDao.findOne(assessId);
			List<FpAssessUnitEntity> unitAList = assessUnitDao
					.getByAssessIdAndAssessUnitType(assessVO.getAssessId(),
							 0);
			List<FpAssessUnitEntity> unitBList = assessUnitDao
					.getByAssessIdAndAssessUnitType(assessVO.getAssessId(),
							1);

			Map<String, Object> mapA = new HashMap<String, Object>();
			List<String> listA = new ArrayList<String>();
			List<String> listB = new ArrayList<String>();
			Map<String, Object> mapB = new HashMap<String, Object>();
			// List<Map> listMapA=null;
			// List<Map> listMapB=null;
			if (unitAList != null && unitAList.size() > 0) {
				for (FpAssessUnitEntity unitA : unitAList) {
					/*
					 * mapA.put("dwId:"+unitA.getAssessUnitId().toString(),
					 * "dwName:"+unitA.getAssessUnitName());
					 */
					String unitTypeA = "{" + "\"" + "dwId" + "\"" + ":" + "\""
							+ unitA.getUnitId().toString() + "\"" + ","
							+ "\"" + "dwName" + "\"" + ":" + "\""
							+ unitA.getAssessUnitName() + "\"" + "}";

					listA.add(unitTypeA);
				}
				// listMapA=JSON.parseArray(listA.toString(), Map.class);

			}

			if (unitBList != null && unitBList.size() > 0) {
				for (FpAssessUnitEntity unitB : unitBList) {
					/*
					 * mapB.put(unitB.getAssessUnitId().toString(),
					 * unitB.getAssessUnitName());
					 */
					String unitTypeB = "{" + "\"" + "dwId" + "\"" + ":" + "\""
							+ unitB.getUnitId().toString() + "\"" + ","
							+ "\"" + "dwName" + "\"" + ":" + "\""
							+ unitB.getAssessUnitName() + "\"" + "}";
					listB.add(unitTypeB);
				}
				// listMapB=JSON.parseArray(listB.toString(),Map.class);
			}

			assessVO.setUnitTypeA(listA.toString());
			assessVO.setUnitTypeB(listB.toString());
			// assessVO.setUnitA(mapA);
			// assessVO.setUnitB(mapB);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return assessVO;
	}

	@Override
	public PageFinder<AssessVO> assessList(Integer signInUserId,
			String startTime, String endTime, Integer petitionUnitId,
			int startPage, int pageSize) {
		Page<FpAssessEntity> assessEntityPage = assessPage(signInUserId,
				startTime, endTime, petitionUnitId, startPage, pageSize);
		int assessCount = 0;
		List<AssessVO> assessVOList = new ArrayList<AssessVO>();
		try {
			assessCount = (int) assessCount(signInUserId, startTime, endTime,
					petitionUnitId);
			Map<String, Object> assessUnitNameMap = new LinkedHashMap<String, Object>();
			Map<String, Object> assessUnitCount = new LinkedHashMap<>();
			Map<String, Object> assessScoreCount = new LinkedHashMap<String, Object>();
			if (assessEntityPage != null && assessCount > 0) {
				for (FpAssessEntity assess : assessEntityPage) {
					AssessVO assessVO = new AssessVO();
					assessVO.setItemName(assess.getItemName());
					assessVO.setInitiatorName(assess.getInitiatorName());
					assessVO.setInitiatorHeadsrc(assess.getInitiatorHeadsrc());
					/*
					 * assessVO.setStartTime(assess.getStartTime());
					 * assessVO.setEndTime(assess.getEndTime());
					 */
					assessVO.setSponsorUnitName(assess.getSponsorUnitName());
					List<FpAssessUnitEntity> unitEntityList = assessUnitDao
							.getByAssessId(assess.getAssessId());
					if (unitEntityList != null && unitEntityList.size() > 0) {
						for (FpAssessUnitEntity unit : unitEntityList) {
							assessUnitNameMap.put(unit.getAssessUnitId()
									.toString(), unit.getAssessUnitName());
						}
					}
					/* assessVO.setAssessUnitName(assessUnitNameMap); */
					// TODO 考核单位总数量／总分， A 类单位总数量／总分， B 类单位总数量／总分
					Map<String, Object> unitTotalMap = assessUnitDao
							.getUnitCountByAssessId(assessVO.getAssessId());
					if (unitTotalMap != null && unitTotalMap.size() > 0) {
						assessUnitCount.put("AUnitTotal", StringUtils
								.isEmpty(unitTotalMap.get("atotal")) ? null
								: unitTotalMap.get("atotal").toString());
						assessUnitCount.put("BUnitTotal", StringUtils
								.isEmpty(unitTotalMap.get("btotal")) ? null
								: unitTotalMap.get("btotal").toString());
						assessUnitCount.put("UnitTotal", StringUtils
								.isEmpty(unitTotalMap.get("total")) ? null
								: unitTotalMap.get("total").toString());
					}
					assessVO.setAssessUnitCount(assessUnitCount);
					Map<String, Object> scoreTotalMap = assessUnitDao
							.getScoreCountByAssessId(assessVO.getAssessId());
					if (scoreTotalMap != null && scoreTotalMap.size() > 0) {
						assessScoreCount.put("AscoreTotal", StringUtils
								.isEmpty(scoreTotalMap.get("atotal")) ? null
								: scoreTotalMap.get("atotal").toString());
						assessScoreCount.put("BscoreTotal", StringUtils
								.isEmpty(scoreTotalMap.get("btotal")) ? null
								: scoreTotalMap.get("btotal").toString());
						assessScoreCount.put("scoreTotal", StringUtils
								.isEmpty(scoreTotalMap.get("total")) ? null
								: scoreTotalMap.get("total").toString());
					}
					assessVO.setAssessUnitScore(assessScoreCount);
					assessVOList.add(assessVO);
				}
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return new PageFinder<AssessVO>(startPage, pageSize, assessCount,
				assessVOList);

	}

	@Override
	public Map<String, Object> assessStatistics(Integer assessId,
			Integer unitType, Integer unitId, String itemType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			if (unitId == 0) {
				List<Integer> unitIdList = assessUnitDao
						.getUnitIdByAssessId(assessId);
				if (unitIdList.size() == 0) {
					map.put("data", listMap);
					return map;
				}
				String unitIdListString = org.apache.commons.lang.StringUtils
						.strip(unitIdList.toString(), "[]");
				String unitTypeSql = " AND templet_type =" + unitType;
				String itemTypeSql = " AND  fid in((SELECT templet_id FROM fp_assess_score WHERE fid is NULL AND assess_id ="
						+ assessId
						+ ")) AND standard LIKE '%"
						+ itemType
						+ "%'";
				String filterSql = "";
				if (unitType == -1 && StringUtils.isEmpty(itemType)) {
					filterSql = " AND fid is null";
				} else if (unitType != -1 && StringUtils.isEmpty(itemType)) {
					filterSql = " AND fid is null " + unitTypeSql;
				} else if (unitType == -1 && !StringUtils.isEmpty(itemType)) {
					filterSql = itemTypeSql;
				} else if (unitType != -1 && !StringUtils.isEmpty(itemType)) {
					filterSql = unitTypeSql + itemTypeSql;
				}

				String sql = "select reality_score,templet_type,assess_unit_id,"
						+ "(select dw_mc from fc_dwb where dw_id = assess_unit_id) as dwName,"
						+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE assess_id  ="+assessId+"  AND assess_unit_id  IN ("
						+ unitIdListString
						+ ") "
						+ filterSql
						+ "  AND templet_type=0) as atotal, "
						+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE assess_id  ="+assessId+"  AND assess_unit_id  IN ("
						+ unitIdListString
						+ ") "
						+ filterSql
						+ "  AND templet_type=1) as btotal, "
						+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE assess_id  ="+assessId+"  AND assess_unit_id  IN ("
						+ unitIdListString
						+ ") "
						+ filterSql
						+ " ) as total "
						+ " from fp_assess_score WHERE assess_id  ="+assessId+" AND assess_unit_id  IN ("
						+ unitIdListString + ") " + filterSql;
				System.err.println(sql);
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> mapCount = new HashMap<String, Object>();
					mapCount.put(
							"realityScore",
							StringUtils.isEmpty(list.get(i)
									.get("reality_score")) ? 0 : Float
									.valueOf(list.get(i).get("reality_score")
											.toString()));
					mapCount.put("templetType", list.get(i).get("templet_type")
							.equals(true) ? 1 : 0);
					mapCount.put("dwName", StringUtils.isEmpty(list.get(i).get(
							"dwName")) ? null : list.get(i).get("dwName")
							.toString());
					mapCount.put(
							"dwId",
							StringUtils.isEmpty(list.get(i).get(
									"assess_unit_id")) ? 0 : Integer
									.valueOf(list.get(i).get("assess_unit_id")
											.toString()));
					mapCount.put(
							"atotal",
							StringUtils.isEmpty(list.get(i).get("atotal")) ? 0
									: Float.valueOf(list.get(i).get("atotal")
											.toString()));
					mapCount.put(
							"btotal",
							StringUtils.isEmpty(list.get(i).get("btotal")) ? 0
									: Float.valueOf(list.get(i).get("btotal")
											.toString()));
					mapCount.put(
							"total",
							StringUtils.isEmpty(list.get(i).get("total")) ? 0
									: Float.valueOf(list.get(i).get("total")
											.toString()));
					listMap.add(mapCount);
				}

				map.put("data", listMap);
			} else if ((unitId != 0) || (unitId != 0 && (unitType != -1))) {
				String itemTypeSql = "AND  (standard LIKE '%"
						+ itemType
						+ "%' OR fid=(SELECT templet_id FROM fp_assess_score WHERE assess_unit_id= "
						+ unitId
						+ " and assess_id ="
						+ assessId
						+ " AND  standard LIKE '%"
						+ itemType
						+ "%' ANd  fid = (SELECT templet_id FROM fp_assess_score WHERE fid IS NULL And assess_id="
						+ assessId + " And assess_unit_id=" + unitId + ")))";
				if (StringUtils.isEmpty(itemType)) {
					itemTypeSql = "";
				}
				String sql = "SELECT * ,(SELECT dw_mc FROM fc_dwb where DW_ID =assess_unit_id) as dwName FROM fp_assess_score WHERE assess_unit_id= "
						+ unitId
						+ " and assess_id ="
						+ assessId
						+ "\n"
						+ itemTypeSql;
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
				List<AssessCountVO> asList = new ArrayList<AssessCountVO>();
				JSONArray json = new JSONArray();
				for (int i = 0; i < list.size(); i++) {
					AssessCountVO as = new AssessCountVO();
					as.setId(StringUtils.isEmpty(list.get(i).get("templet_id")) ? 0
							: Integer.valueOf(list.get(i).get("templet_id")
									.toString()));
					as.setpId(StringUtils.isEmpty(list.get(i).get("fid")) ? 0
							: Integer
									.valueOf(list.get(i).get("fid").toString()));
					as.setName(StringUtils.isEmpty(list.get(i).get("standard")) ? null
							: list.get(i).get("standard").toString());
					as.setRelatityScore(StringUtils.isEmpty(list.get(i).get(
							"reality_score")) ? 0 : Float.valueOf(list.get(i)
							.get("reality_score").toString()));
					asList.add(as);
				}
				if (StringUtils.isEmpty(itemType)) {
					Integer parentId = fpAssessScoreDao.getParentIdByUnitId(
							unitId, assessId);
					json = JSONArray.fromObject(asList);
					AssessCountVO as = getNodeById(parentId, asList);
					as.setChildren(treeMenuList(json, parentId));
					map.put("data", as);
					map.put("dwName", StringUtils.isEmpty(list.get(0).get(
							"dwName")) ? null : list.get(0).get("dwName")
							.toString());
				} else if (!StringUtils.isEmpty(itemType)) {
					Integer parentId = fpAssessScoreDao.getParent(itemType,
							assessId, unitId);
					json = JSONArray.fromObject(asList);
					AssessCountVO as = getNodeById(parentId, asList);
					as.setChildren(treeMenuList(json, parentId));
					map.put("data", as);
					map.put("dwName", StringUtils.isEmpty(list.get(0).get(
							"dwName")) ? null : list.get(0).get("dwName")
							.toString());

					map.put("totalName",
							fpAssessScoreDao.getParentObjectById(assessId,
									unitId).getStandard());
					map.put("Total",
							fpAssessScoreDao.getParentObjectById(assessId,
									unitId).getRealityScore());
				}
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}

	// 遍历出相应的子集
	public JSONArray treeMenuList(JSONArray menuList, int parentId) {
		JSONArray childMenu = new JSONArray();
		for (Object object : menuList) {
			JSONObject jsonMenu = JSONObject.fromObject(object);
			int menuId = jsonMenu.getInt("id");
			int pid = jsonMenu.getInt("pId");
			if (parentId == pid) {
				JSONArray c_node = treeMenuList(menuList, menuId);
				jsonMenu.put("children", c_node);
				childMenu.add(jsonMenu);
			}
		}
		return childMenu;
	}

	// 获取根对象
	public AssessCountVO getNodeById(int parentId, List<AssessCountVO> temList) {
		AssessCountVO tem = new AssessCountVO();
		for (AssessCountVO item : temList) {
			if (item.getId() == parentId) {
				tem = item;
				break;
			}

		}

		return tem;
	}

	@Override
	public Map<String, Object> assessReport(Integer assessId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT DISTINCT "
				+ " (SELECT SUM(reality_score) FROM fp_assess_score WHERE fid is NULL AND assess_id="
				+ assessId
				+ ") as total,"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE fid is NULL AND assess_id="
				+ assessId
				+ " AND templet_type=0) as atotal,"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE fid is NULL AND assess_id="
				+ assessId
				+ " AND templet_type=1) as btotal,"
				+ "(SELECT dw_mc FROM fc_dwb WHERE DW_ID = assess_unit_id)AS dwName ,"
				+ "(SELECT templet_type FROM fp_assess_score WHERE fid is NULL AND assess_unit_id=f.assess_unit_id AND assess_id="+assessId+") as type,"
				+ "(SELECT reality_score FROM fp_assess_score WHERE fid is NULL AND assess_unit_id=f.assess_unit_id AND assess_id="+assessId+") AS unitTotal,"
				+ "(SELECT reality_score FROM fp_assess_score WHERE  standard ='领导重视' AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as leader,"
				+ "(SELECT reality_score FROM fp_assess_score WHERE standard = '基层基础' AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as base,"
				+ "(SELECT reality_score FROM fp_assess_score WHERE standard = '非访治理' AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as manager,"
				+ "(SELECT reality_score FROM fp_assess_score WHERE standard = '信访事项办理' AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as deal,"
				+ "(SELECT reality_score FROM fp_assess_score WHERE standard = '加分' AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+")as bonus,"
				+ "(SELECT reality_score FROM fp_assess_score WHERE standard = '减分' AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+")as deduction"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE standard = '领导重视' AND assess_id="+assessId+" AND assess_unit_id =f.assess_unit_id) as leadertotal,"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE standard = '基层基础' AND assess_id="+assessId+" AND assess_unit_id =f.assess_unit_id) as basetotal,"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE standard = '非访治理' AND assess_id="+assessId+" AND assess_unit_id =f.assess_unit_id) as managertotal,"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE standard = '信访事项办理' AND assess_id="+assessId+" AND assess_unit_id =f.assess_unit_id) as dealtotal,"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE standard = '加分' AND assess_id="+assessId+" AND assess_unit_id =f.assess_unit_id) as bonustotal,"
				+ "(SELECT SUM(reality_score) FROM fp_assess_score WHERE standard = '减分' AND assess_id="+assessId+" AND assess_unit_id =f.assess_unit_id) as deductiontotal"
				+ " FROM  fp_assess_score as f WHERE assess_id= "
				+ assessId
				+ "  order by f.sort asc";
		try {
			System.err.println(sql);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			List<AssessReportVO> asList = new ArrayList<AssessReportVO>();
			List<AssessReportVO> bsList = new ArrayList<AssessReportVO>();
			for (int i = 0; i < list.size(); i++) {
				AssessReportVO as = new AssessReportVO();
				as.setAssessUnit(StringUtils.isEmpty(list.get(i).get("dwName")) ? null
						: list.get(i).get("dwName").toString());
				as.setBase(StringUtils.isEmpty(list.get(i).get("base")) ? 0
						: Float.valueOf(list.get(i).get("base").toString()));
				as.setBonus(StringUtils.isEmpty(list.get(i).get("bonus")) ? 0
						: Float.valueOf(list.get(i).get("bonus").toString()));
				as.setDeal(StringUtils.isEmpty(list.get(i).get("deal")) ? 0
						: Float.valueOf(list.get(i).get("deal").toString()));
				as.setDeduction(StringUtils.isEmpty(list.get(i)
						.get("deduction")) ? 0 : Float.valueOf(list.get(i)
						.get("deduction").toString()));
				as.setLeader(StringUtils.isEmpty(list.get(i).get("leader")) ? 0
						: Float.valueOf(list.get(i).get("leader").toString()));
				as.setTemlateType(Integer.valueOf(list.get(i).get("type")
						.toString()));
				as.setRealityScore(StringUtils.isEmpty(list.get(i).get(
						"unitTotal")) ? 0 : Float.valueOf(list.get(i)
						.get("unitTotal").toString()));
				as.setManager(StringUtils.isEmpty(list.get(i).get("manager")) ? 0
						: Float.valueOf(list.get(i).get("manager").toString()));
				if (as.getTemlateType() == 0) {
					asList.add(as);
				}
				if (as.getTemlateType() == 1) {
					bsList.add(as);
				}
			}
			map.put("Adata", asList);
			map.put("Bdata", bsList);
			map.put("aTotal",
					StringUtils.isEmpty(list.get(0).get("atotal")) ? 0 : Float
							.valueOf(list.get(0).get("atotal").toString()));
			map.put("bTotal",
					StringUtils.isEmpty(list.get(0).get("btotal")) ? 0 : Float
							.valueOf(list.get(0).get("btotal").toString()));
			map.put("total", StringUtils.isEmpty(list.get(0).get("total")) ? 0
					: Float.valueOf(list.get(0).get("total").toString()));
			map.put("leaderTotal",
					StringUtils.isEmpty(list.get(0).get("leadertotal")) ? 0
							: Float.valueOf(list.get(0).get("leadertotal")
									.toString()));
			map.put("baseTotal",
					StringUtils.isEmpty(list.get(0).get("basetotal")) ? 0
							: Float.valueOf(list.get(0).get("basetotal")
									.toString()));
			map.put("managerTotal",
					StringUtils.isEmpty(list.get(0).get("managertotal")) ? 0
							: Float.valueOf(list.get(0).get("managertotal")
									.toString()));
			map.put("dealTotal",
					StringUtils.isEmpty(list.get(0).get("dealtotal")) ? 0
							: Float.valueOf(list.get(0).get("dealtotal")
									.toString()));
			map.put("bonusTotal",
					StringUtils.isEmpty(list.get(0).get("bonustotal")) ? 0
							: Float.valueOf(list.get(0).get("bonustotal")
									.toString()));
			map.put("deductionTotal",
					StringUtils.isEmpty(list.get(0).get("deductiontotal")) ? 0
							: Float.valueOf(list.get(0).get("deductiontotal")
									.toString()));

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.err.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}

	private Page<FpAssessEntity> assessPage(final Integer signInUserId,
			final String startTime, final String endTime,
			final Integer petitionUnitId, int startPage, int pageSize) {
		return assessDao.findAll(new Specification<FpAssessEntity>() {
			@Override
			public Predicate toPredicate(Root<FpAssessEntity> root,
					CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(criteriaBuilder.equal(root.get("initiatorId")
						.as(Integer.class), signInUserId));
				// 开始时间
				if (!StringUtils.isEmpty(startTime)
						&& StringUtils.isEmpty(endTime)) {
					predicates.add(criteriaBuilder
							.greaterThan(
									root.get("gmtModified").as(String.class),
									startTime));
				}
				// 结束时间
				if (!StringUtils.isEmpty(endTime)
						&& StringUtils.isEmpty(startTime)) {
					predicates.add(criteriaBuilder.lessThan(
							root.get("gmtModified").as(String.class), endTime));
				}
				if (!StringUtils.isEmpty(startTime)
						&& !StringUtils.isEmpty(endTime)) {
					predicates.add(criteriaBuilder.between(
							root.get("gmtModified").as(String.class),
							startTime, endTime));
				}
				if (petitionUnitId != 0) {
					predicates.add(criteriaBuilder.equal(
							root.get("sponsorUnitId"), petitionUnitId));
				}

				Predicate[] pre = new Predicate[predicates.size()];
				return criteriaQuery.where(predicates.toArray(pre))
						.getRestriction();
			}
		}, new PageRequest(startPage - 1, pageSize));
	}

	private long assessCount(final Integer signInUserId,
			final String startTime, final String endTime,
			final Integer petitionUnitId) {
		return assessDao.count(new Specification<FpAssessEntity>() {
			@Override
			public Predicate toPredicate(Root<FpAssessEntity> root,
					CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(criteriaBuilder.equal(root.get("initiatorId")
						.as(Integer.class), signInUserId));
				// 开始时间
				if (!StringUtils.isEmpty(startTime)
						&& StringUtils.isEmpty(endTime)) {
					predicates.add(criteriaBuilder
							.greaterThan(
									root.get("gmtModified").as(String.class),
									startTime));
				}
				// 结束时间
				if (!StringUtils.isEmpty(endTime)
						&& StringUtils.isEmpty(startTime)) {
					predicates.add(criteriaBuilder.lessThan(
							root.get("gmtModified").as(String.class), endTime));
				}
				if (!StringUtils.isEmpty(startTime)
						&& !StringUtils.isEmpty(endTime)) {
					predicates.add(criteriaBuilder.between(
							root.get("gmtModified").as(String.class),
							startTime, endTime));
				}
				if (petitionUnitId != 0) {
					predicates.add(criteriaBuilder.equal(
							root.get("sponsorUnitId"), petitionUnitId));
				}

				Predicate[] pre = new Predicate[predicates.size()];
				return criteriaQuery.where(predicates.toArray(pre))
						.getRestriction();
			}
		});
	}

	@Override
	public Map<String, Object> getAssessUnit(Integer assessId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		String typeSQL = "";
		if (type != -1) {
			typeSQL = " AND assess_unit_type = " + type;
		}
		String sql = "select unit_id,assess_unit_name from fp_assess_unit where assess_Id = "
				+ assessId + typeSQL;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put(
						"unitName",
						StringUtils
								.isEmpty(list.get(i).get("assess_unit_name")) ? null
								: list.get(i).get("assess_unit_name")
										.toString());
				maps.put(
						"unitId",
						StringUtils.isEmpty(list.get(i).get("unit_id")) ? null
								: Integer.valueOf(list.get(i).get("unit_id")
										.toString()));
				mapList.add(maps);
			}
			map.put("data", mapList);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	@Override
	public Map<String, Object> getAssessList(Integer signInUserId,
			final String startTime, final String endTime,
			Integer petitionUnitId, Integer startPage, Integer pageSize,
			final String fuzzy) {
		Map<String, Object> map = new HashMap<String, Object>();
		FcRyb fcRyb = userRepository.getOne(signInUserId);
		String dwSql = "";
		if (fcRyb.getDwId() == 761 && petitionUnitId == 0) {
			dwSql = "";
		} else if (fcRyb.getDwId() != 761) {
			dwSql = " AND sponsor_unit_id =" + fcRyb.getDwId();
		} else if (fcRyb.getDwId() == 761 && petitionUnitId != 0) {
			dwSql = " AND sponsor_unit_id =" + petitionUnitId;
		}
		final String fuzzySql = " AND ( sponsor_unit_name like '%" + fuzzy
				+ "%'  OR initiator_name like '%" + fuzzy + "%' )";

		final String timeTypeSql = " AND  gmt_create BETWEEN '" + startTime
				+ "' AND '" + endTime + " 23:59:59'";
		String nSql = "\n";
		class getLawListConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = timeTypeSql;
				String filterSql = "\n";
				if (!StringUtils.isEmpty(fuzzy)) {
					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (!StringUtils.isEmpty(startTime)
						&& !StringUtils.isEmpty(endTime)) {
					filterSql = aSql + bSql;
				} else {
					bSql = "";
				}
				return filterSql;
			}
		}
		String filterSql = new getLawListConcatSQl().result(nSql);

		String sql = "select * ,(SELECT COUNT(*) FROM fp_assess where state <> -1 "+dwSql+")as total from fp_assess where state <> -1 "
				+ dwSql
				+ "\n"
				+ filterSql
				+" order by gmt_create desc "
				+ " limit "
				+ startPage
				* pageSize
				+ "," + pageSize;
		try {
			System.err.println(sql+"\n"+"\n");
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

			List<AssessVO> asList = new ArrayList<AssessVO>();
			for (int i = 0; i < list.size(); i++) {
				AssessVO as = new AssessVO();
				as.setAssessId(StringUtils
						.isEmpty(list.get(i).get("assess_id")) ? 0 : Integer
						.valueOf(list.get(i).get("assess_id").toString()));
				as.setEndTime(StringUtils.isEmpty(list.get(i).get("end_time")) ? null
						: list.get(i).get("end_time").toString());
				as.setInitiatorHeadsrc(StringUtils.isEmpty(list.get(i).get(
						"initiator_headsrc")) ? null : list.get(i)
						.get("initiator_headsrc").toString());
				as.setInitiatorName(StringUtils.isEmpty(list.get(i).get(
						"initiator_name")) ? null : list.get(i)
						.get("initiator_name").toString());
				as.setItemName(StringUtils
						.isEmpty(list.get(i).get("item_name")) ? null : list
						.get(i).get("item_name").toString());
				as.setSponsorUnitName(StringUtils.isEmpty(list.get(i).get(
						"sponsor_unit_name")) ? null : list.get(i)
						.get("sponsor_unit_name").toString());
				as.setStartTime(StringUtils.isEmpty(list.get(i).get(
						"start_time")) ? null : list.get(i).get("start_time")
						.toString());
				as.setGmtCreat(StringUtils.isEmpty(list.get(i)
						.get("gmt_create")) ? null : list.get(i)
						.get("gmt_create").toString());
				List<FpAssessUnitEntity> unitEntityList = assessUnitDao
						.getByAssessId(Integer.valueOf(list.get(i)
								.get("assess_id").toString()));
				List<String> unitNameMap = new ArrayList<String>();
				if (unitEntityList != null && unitEntityList.size() > 0) {

					for (FpAssessUnitEntity unit : unitEntityList) {

						unitNameMap.add(unit.getAssessUnitName());

					}
				}
				as.setAssessUnitName(unitNameMap);
				Map<String, Object> unitCountMap = getunitCountMap(Integer
						.valueOf(list.get(i).get("assess_id").toString()));
				Map<String, Object> unitTotalMap = new HashMap<String, Object>();
				if (unitCountMap != null && unitCountMap.size() > 0) {

					unitTotalMap.put("AUnitTotal", StringUtils
							.isEmpty(unitCountMap.get("atotal")) ? null
							: unitCountMap.get("atotal").toString());

					unitTotalMap.put("BUnitTotal", StringUtils
							.isEmpty(unitCountMap.get("btotal")) ? null
							: unitCountMap.get("btotal").toString());

					unitTotalMap.put("UnitTotal", StringUtils
							.isEmpty(unitCountMap.get("total")) ? null
							: unitCountMap.get("total").toString());

				}
				as.setAssessUnitCount(unitTotalMap);
				Map<String, Object> scoreCountMap = getscoreCountMap(Integer
						.valueOf(list.get(i).get("assess_id").toString()));
				Map<String, Object> scoreTotalMap = new HashMap<String, Object>();
				
					scoreTotalMap
							.put("AscoreTotal",
									StringUtils.isEmpty(scoreCountMap
											.get("atotal")) ? 0 : Float
											.valueOf(scoreCountMap
													.get("atotal").toString()));
					scoreTotalMap
							.put("BscoreTotal",
									StringUtils.isEmpty(scoreCountMap
											.get("btotal")) ? 0 : Float
											.valueOf(scoreCountMap
													.get("btotal").toString()));
					scoreTotalMap
							.put("scoreTotal",
									StringUtils.isEmpty(scoreCountMap
											.get("total")) ? 0 : Float
											.valueOf(scoreCountMap.get("total")
													.toString()));
				
				as.setAssessUnitScore(scoreTotalMap);

				Map<String, Object> mapcount = assessStatistics(
						Integer.valueOf(list.get(i).get("assess_id").toString()),
						-1, 0, "");
				List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> mapTotal = (List<Map<String, Object>>) mapcount
						.get("data");
				if (mapTotal.size() > 0 && mapTotal != null) {
					for (int j = 0; j < mapTotal.size(); j++) {
						Map<String, Object> mapCount = new HashMap<String, Object>();
						mapCount.put(
								"realityScore",
								StringUtils.isEmpty(mapTotal.get(j).get(
										"realityScore")) ? 0
										: Float.valueOf(mapTotal.get(j)
												.get("realityScore").toString()));
						mapCount.put(
								"templetType",
								mapTotal.get(j).get("templetType").equals(true) ? 1
										: 0);
						mapCount.put(
								"dwName",
								StringUtils.isEmpty(mapTotal.get(j).get(
										"dwName")) ? null : mapTotal.get(j)
										.get("dwName").toString());
						mapCount.put(
								"dwId",
								StringUtils
										.isEmpty(mapTotal.get(j).get("dwId")) ? 0
										: Integer.valueOf(mapTotal.get(j)
												.get("dwId").toString()));
						mapCount.put(
								"atotal",
								StringUtils.isEmpty(mapTotal.get(j).get(
										"atotal")) ? 0 : Float.valueOf(mapTotal
										.get(j).get("atotal").toString()));
						mapCount.put(
								"btotal",
								StringUtils.isEmpty(mapTotal.get(j).get(
										"btotal")) ? 0 : Float.valueOf(mapTotal
										.get(j).get("btotal").toString()));
						mapCount.put(
								"total",
								StringUtils.isEmpty(mapTotal.get(j)
										.get("total")) ? 0 : Float
										.valueOf(mapTotal.get(j).get("total")
												.toString()));
						listMap.add(mapCount);
					}
					as.setAssessCount(listMap);
				}
				asList.add(as);

			}
			map.put("data", asList);
			map.put("pageIndex", startPage);
			map.put("pageNum",
					Integer.valueOf(list.get(0).get("total").toString())
							% pageSize == 0 ? Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageSize : Integer.valueOf(list.get(0)
							.get("total").toString())
							/ pageSize + 1);
			map.put("resultTotal",
					StringUtils.isEmpty(list.get(0).get("total")) ? 0 : Integer
							.valueOf(list.get(0).get("total").toString()));
			map.put("pageSize", pageSize);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.err.println(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;

	}

	private Map<String, Object> getunitCountMap(Integer assessId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT (SELECT COUNT(*) FROM fp_assess_unit where assess_unit_type =1 AND assess_id ="
				+ assessId
				+ ")as btotal,"
				+ " (SELECT COUNT(*) FROM fp_assess_unit where assess_unit_type =0 AND assess_id ="
				+ assessId
				+ ")as atotal,count(*) AS total "
				+ " FROM fp_assess_unit WHERE  assess_id =" + assessId;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		map.put("btotal", StringUtils.isEmpty(list.get(0).get("btotal")) ? 0
				: Integer.valueOf(list.get(0).get("btotal").toString()));
		map.put("atotal", StringUtils.isEmpty(list.get(0).get("atotal")) ? 0
				: Integer.valueOf(list.get(0).get("atotal").toString()));
		map.put("total", StringUtils.isEmpty(list.get(0).get("total")) ? 0
				: Integer.valueOf(list.get(0).get("total").toString()));
		return map;

	}

	private Map<String, Object> getscoreCountMap(Integer assessId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT (SELECT sum(reality_score) FROM fp_assess_score where templet_type =1 AND fid is null AND assess_id = "
				+ assessId
				+ ")as btotal,"
				+ "(SELECT sum(reality_score) FROM fp_assess_score where templet_type =0 AND  fid is null AND assess_id="
				+ assessId
				+ ")as atotal,"
				+ "sum(reality_score) AS total FROM fp_assess_score WHERE fid is null  AND assess_id="
				+ assessId;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		map.put("btotal", StringUtils.isEmpty(list.get(0).get("btotal")) ? 0
				: Float.valueOf(list.get(0).get("btotal").toString()));
		map.put("atotal", StringUtils.isEmpty(list.get(0).get("atotal")) ? 0
				: Float.valueOf(list.get(0).get("atotal").toString()));
		map.put("total", StringUtils.isEmpty(list.get(0).get("total")) ? 0
				: Float.valueOf(list.get(0).get("total").toString()));
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
						"entering_type")) ? -1 : Integer.valueOf(lists.get(i)
						.get("entering_type").toString()));
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
				tem.setRealityScore(StringUtils.isEmpty(lists.get(i)
						.get("reality_score")) ? 0 : Float.valueOf(lists.get(i)
						.get("reality_score").toString()));
				temList.add(tem);

			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.err.println(e.getMessage());
		}

		return temList;
	}

	@Override
	public Map<String, Object> getAunitList() {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			String sql="SELECT d.* FROM fc_dwb as d LEFT JOIN fc_qxsb as f "
					+ " ON d.QXS_ID=f.QXS_ID WHERE d.DW_TYPE=7 AND d.QXS_ID in "
					+ " (SELECT qxs_id FROM fc_qxsb WHERE tsbz=1 ORDER BY xh) ORDER BY f.XH";
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		List<Map<String, Object>> unitList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> unitMap=new HashMap<String, Object>();
			unitMap.put("dwMc", StringUtils.isEmpty(list.get(i).get("DW_MC"))?null:list.get(i).get("DW_MC").toString());
			unitMap.put("dwId", StringUtils.isEmpty(list.get(i).get("DW_ID"))?null:Integer.valueOf(list.get(i).get("DW_ID").toString()));
			unitList.add(unitMap);
		}
			map.put("data", unitList);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.out.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}

	@Override
	public Map<String, Object> assessReport2(Integer assessId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		/*String sql = "SELECT DISTINCT "
					+" (SELECT dw_mc FROM fc_dwb WHERE DW_ID = assess_unit_id)AS dwName ,templet_type,"
					+" (SELECT reality_score FROM fp_assess_score WHERE fid is NULL AND assess_unit_id=f.assess_unit_id AND assess_id="+assessId+") AS unitTotal,"
					+" (SELECT reality_score FROM fp_assess_score WHERE templet_id=if(templet_type=0,188,366) AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as leader,"
					+" (SELECT reality_score FROM fp_assess_score WHERE templet_id=if(templet_type=0,201,387) AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as base,"
					+" (SELECT reality_score FROM fp_assess_score WHERE templet_id=if(templet_type=0,233,409) AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as manager,"
					+" (SELECT reality_score FROM fp_assess_score WHERE templet_id=if(templet_type=0,250,420) AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+") as deal,"
					+" (SELECT reality_score FROM fp_assess_score WHERE templet_id=if(templet_type=0,283,444) AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+")as bonus,"
					+" (SELECT reality_score FROM fp_assess_score WHERE templet_id=if(templet_type=0,302,460) AND assess_unit_id =f.assess_unit_id AND assess_id="+assessId+")as deduction" 
					+" FROM  fp_assess_score as f WHERE assess_id= "+assessId+"  order by f.sort asc";*/
		String sql2="select"

					+" dw_mc as dwName,"
					
					+" (select ifnull(sum(reality_score),0) from fp_assess_score where IFNULL(fid,0)=0 AND  assess_unit_id=fc_dwb.DW_ID "
					+" and assess_id= "+assessId
					+") as unitTotal,"
					+" (select templet_type from fp_assess_score where IFNULL(fid,0)=0 AND   assess_unit_id =fc_dwb.DW_ID"
					+" and assess_id= "+assessId


					+") as templet_type,"
					+" (select ifnull(sum(reality_score),0) from fp_assess_score where assess_unit_id=fc_dwb.DW_ID" 
					+" and assess_id= "+assessId+" and templet_id in (0,188,366)"

 					+" ) as leader,"

					+" (select ifnull(sum(reality_score),0) from fp_assess_score where assess_unit_id=fc_dwb.DW_ID" 
					+" and assess_id=  "+assessId+"  and templet_id in (0,201,387)"

 					+" ) as base,"


					+" (select ifnull(sum(reality_score),0) from fp_assess_score where assess_unit_id=fc_dwb.DW_ID" 
					+" and assess_id=  "+assessId+"  and templet_id in (0,233,409)"

					+" ) as manager,"


					+" (select ifnull(sum(reality_score),0) from fp_assess_score where assess_unit_id=fc_dwb.DW_ID" 
					+" and assess_id=  "+assessId+"  and templet_id in (0,250,420)"

					+" ) as deal,"



					+" (select ifnull(sum(reality_score),0) from fp_assess_score where assess_unit_id=fc_dwb.DW_ID" 
					+" and assess_id=  "+assessId+"  and templet_id in (0,283,444)"

 					+" ) as bonus,"



					+" (select ifnull(sum(reality_score),0) from fp_assess_score where assess_unit_id=fc_dwb.DW_ID "
					+" and assess_id=  "+assessId+"  and templet_id in (0,302,460)"

					+"  ) as deduction "

					+" from fc_dwb "
					+" where dw_id in (select unit_id from FP_ASSESS_UNIT where assess_id= "+assessId+" )";
		
		
		
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql2);
			List<AssessReportVO> asList = new ArrayList<AssessReportVO>();
			List<AssessReportVO> bsList = new ArrayList<AssessReportVO>();
			
			float total=0;float leadertotal=0;float basetotal=0;float managertotal=0;
			float dealtotal=0; float bonustotal=0; float deductiontotal=0;
			
			float Atotal=0;float Aleadertotal=0;float Abasetotal=0;float Amanagertotal=0;
			float Adealtotal=0; float Abonustotal=0; float Adeductiontotal=0;
			
			float Btotal=0;float Bleadertotal=0;float Bbasetotal=0;float Bmanagertotal=0;
			float Bdealtotal=0; float Bbonustotal=0; float Bdeductiontotal=0;
		 
			for (int i = 0; i < list.size(); i++) {
				AssessReportVO as = new AssessReportVO();
				as.setAssessUnit(StringUtils.isEmpty(list.get(i).get("dwName")) ? null
						: list.get(i).get("dwName").toString());
				as.setBase(StringUtils.isEmpty(list.get(i).get("base")) ? 0
						: Float.valueOf(list.get(i).get("base").toString()));
			
				basetotal+=StringUtils.isEmpty(list.get(i).get("base")) ? 0
						: Float.valueOf(list.get(i).get("base").toString());
				
				as.setBonus(StringUtils.isEmpty(list.get(i).get("bonus")) ? 0
						: Float.valueOf(list.get(i).get("bonus").toString()));
				bonustotal+=StringUtils.isEmpty(list.get(i).get("bonus")) ? 0
						: Float.valueOf(list.get(i).get("bonus").toString());
				
				as.setDeal(StringUtils.isEmpty(list.get(i).get("deal")) ? 0
						: Float.valueOf(list.get(i).get("deal").toString()));
				dealtotal+=StringUtils.isEmpty(list.get(i).get("deal")) ? 0
						: Float.valueOf(list.get(i).get("deal").toString());
				
				as.setDeduction(StringUtils.isEmpty(list.get(i)
						.get("deduction")) ? 0 : Float.valueOf(list.get(i)
						.get("deduction").toString()));
				deductiontotal+=StringUtils.isEmpty(list.get(i)
						.get("deduction")) ? 0 : Float.valueOf(list.get(i)
						.get("deduction").toString());
				
				as.setLeader(StringUtils.isEmpty(list.get(i).get("leader")) ? 0
						: Float.valueOf(list.get(i).get("leader").toString()));
				leadertotal+=StringUtils.isEmpty(list.get(i).get("leader")) ? 0
						: Float.valueOf(list.get(i).get("leader").toString());

				as.setTemlateType(list.get(i).get("templet_type")
						.toString().equals("false")?0:1);
				as.setRealityScore(StringUtils.isEmpty(list.get(i).get(
						"unitTotal")) ? 0 : Float.valueOf(list.get(i)
						.get("unitTotal").toString()));
				as.setManager(StringUtils.isEmpty(list.get(i).get("manager")) ? 0
						: Float.valueOf(list.get(i).get("manager").toString()));
				managertotal+=StringUtils.isEmpty(list.get(i).get("manager")) ? 0
						: Float.valueOf(list.get(i).get("manager").toString());
				total+=StringUtils.isEmpty(list.get(i).get(
						"unitTotal")) ? 0 : Float.valueOf(list.get(i)
						.get("unitTotal").toString());
				
				if (as.getTemlateType() == 0) {
					Atotal+=StringUtils.isEmpty(list.get(i).get(
							"unitTotal")) ? 0 : Float.valueOf(list.get(i)
							.get("unitTotal").toString());
					Amanagertotal+=StringUtils.isEmpty(list.get(i).get("manager")) ? 0
							: Float.valueOf(list.get(i).get("manager").toString());
					Aleadertotal+=StringUtils.isEmpty(list.get(i).get("leader")) ? 0
							: Float.valueOf(list.get(i).get("leader").toString());
					Adeductiontotal+=StringUtils.isEmpty(list.get(i)
							.get("deduction")) ? 0 : Float.valueOf(list.get(i)
							.get("deduction").toString());
					Adealtotal+=StringUtils.isEmpty(list.get(i).get("deal")) ? 0
							: Float.valueOf(list.get(i).get("deal").toString());
					Abonustotal+=StringUtils.isEmpty(list.get(i).get("bonus")) ? 0
							: Float.valueOf(list.get(i).get("bonus").toString());
					Abasetotal+=StringUtils.isEmpty(list.get(i).get("base")) ? 0
							: Float.valueOf(list.get(i).get("base").toString());
					asList.add(as);
				}
				if (as.getTemlateType() == 1) {
					Btotal+=StringUtils.isEmpty(list.get(i).get(
							"unitTotal")) ? 0 : Float.valueOf(list.get(i)
							.get("unitTotal").toString());
					Bmanagertotal+=StringUtils.isEmpty(list.get(i).get("manager")) ? 0
							: Float.valueOf(list.get(i).get("manager").toString());
					Bleadertotal+=StringUtils.isEmpty(list.get(i).get("leader")) ? 0
							: Float.valueOf(list.get(i).get("leader").toString());
					Bdeductiontotal+=StringUtils.isEmpty(list.get(i)
							.get("deduction")) ? 0 : Float.valueOf(list.get(i)
							.get("deduction").toString());
					Bdealtotal+=StringUtils.isEmpty(list.get(i).get("deal")) ? 0
							: Float.valueOf(list.get(i).get("deal").toString());
					Bbonustotal+=StringUtils.isEmpty(list.get(i).get("bonus")) ? 0
							: Float.valueOf(list.get(i).get("bonus").toString());
					Bbasetotal+=StringUtils.isEmpty(list.get(i).get("base")) ? 0
							: Float.valueOf(list.get(i).get("base").toString());
					bsList.add(as);
				}
			}
			
			map.put("Adata", asList);
			map.put("Bdata", bsList);
			Map<String, Object> totalMap=new HashMap<String, Object>();
			totalMap.put("total", total);
			totalMap.put("leadertotal", leadertotal);
			totalMap.put("basetotal", basetotal);
			totalMap.put("managertotal", managertotal);
			totalMap.put("dealtotal", dealtotal);
			totalMap.put("bonustotal", bonustotal);
			totalMap.put("deductiontotal", deductiontotal);
			map.put("TOTAL", totalMap);
			Map<String, Object> AtotalMap=new HashMap<String, Object>();
			AtotalMap.put("Atotal", Atotal);
			AtotalMap.put("Aleadertotal", Aleadertotal);
			AtotalMap.put("Abasetotal", Abasetotal);
			AtotalMap.put("Amanagertotal", Amanagertotal);
			AtotalMap.put("Adealtotal", Adealtotal);
			AtotalMap.put("Abonustotal", Abonustotal);
			AtotalMap.put("Adeductiontotal", Adeductiontotal);
			map.put("ATOTAL", AtotalMap);
			Map<String, Object> BtotalMap=new HashMap<String, Object>();
			BtotalMap.put("Btotal", Btotal);
			BtotalMap.put("Bleadertotal", Bleadertotal);
			BtotalMap.put("Bbasetotal", Bbasetotal);
			BtotalMap.put("Bmanagertotal", Bmanagertotal);
			BtotalMap.put("Bdealtotal", Bdealtotal);
			BtotalMap.put("Bbonustotal", Bbonustotal);
			BtotalMap.put("Bdeductiontotal", Bdeductiontotal);
			map.put("BTOTAL", BtotalMap);
			
	
			
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			System.err.println(e.getMessage());
			map.put("message", "获取信息出错");
		}
		return map;
	}
}
