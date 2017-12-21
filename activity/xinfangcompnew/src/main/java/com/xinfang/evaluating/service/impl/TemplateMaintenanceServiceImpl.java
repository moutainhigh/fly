package com.xinfang.evaluating.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.xinfang.VO.TemplateMaintenanceVO;
import com.xinfang.evaluating.dao.TemplateMaintenanceDao;
import com.xinfang.evaluating.model.TemplateMaintenanceEntity;
import com.xinfang.evaluating.service.TemplateMaintenanceService;
import com.xinfang.log.ApiLog;

@Service
public class TemplateMaintenanceServiceImpl implements
		TemplateMaintenanceService {
	@Autowired
	private TemplateMaintenanceDao templateMaintenanceDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> getTemplateMaintenanceList(Integer type) {
		List<TemplateMaintenanceVO> temList = new ArrayList<TemplateMaintenanceVO>();
		Map<String, Object> map = new HashMap<>();
		JSONArray json = new JSONArray();
		String sql = "select * from fp_check_templet where  templet_type = "
				+ type;
		try {

			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < list.size(); i++) {
				TemplateMaintenanceVO tem = new TemplateMaintenanceVO();
				tem.setCofficient(StringUtils.isEmpty(list.get(i).get(
						"coefficient")) ? 0 : Float.valueOf(list.get(i)
						.get("coefficient").toString()));
				tem.setControl(StringUtils.isEmpty(list.get(i).get("control"))?0:list.get(i).get("control").equals(true)?1:0);
				tem.setDesc(StringUtils.isEmpty(list.get(i).get("descs")) ? null
						: list.get(i).get("descs").toString());
				tem.setInType(StringUtils.isEmpty(list.get(i).get("entering_type"))?0:list.get(i).get("entering_type").equals(true)?1:0);
				tem.setLevel(StringUtils.isEmpty(list.get(i).get("levels")) ? null
						: list.get(i).get("levels").toString());
				tem.setNumber(StringUtils.isEmpty(list.get(i).get("number")) ? 0
						: Integer.valueOf(list.get(i).get("number").toString()));
				tem.setPid(StringUtils.isEmpty(list.get(i).get("FID")) ? 0
						: Integer.valueOf(list.get(i).get("FID").toString()));
				tem.setSetvalue(StringUtils
						.isEmpty(list.get(i).get("setvalue")) ? 0 :Float
						.valueOf(list.get(i).get("setvalue").toString()));
				tem.setSort(StringUtils.isEmpty(list.get(i).get("sort")) ? 0
						: Integer.valueOf(list.get(i).get("sort").toString()));
				tem.setStandard(StringUtils
						.isEmpty(list.get(i).get("standard")) ? null : list
						.get(i).get("standard").toString());
				tem.setTemplateId(StringUtils.isEmpty(list.get(i).get(
						"templet_id")) ? 0 : Integer.valueOf(list.get(i)
						.get("templet_id").toString()));
				tem.setTemplateNumber(StringUtils.isEmpty(list.get(i).get(
						"templet_number")) ? null : list.get(i)
						.get("templet_number").toString());
				temList.add(tem);
				
			}
			json = JSONArray.fromObject(temList);

			Integer parent = templateMaintenanceDao
					.getTemplateMaintenance(type);
			JSONArray js = treeMenuList(json, parent);
			sql = "select * from fp_check_templet where  fid is null";
			List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < lists.size(); i++) {
				System.out.println(lists.get(i).get("control") + "\t"
						+ lists.get(i).get("FID"));
				TemplateMaintenanceVO tem = new TemplateMaintenanceVO();
				tem.setCofficient(StringUtils.isEmpty(lists.get(i).get(
						"coefficient")) ? 0 : Float.valueOf(lists.get(i)
						.get("coefficient").toString()));
				tem.setControl(StringUtils.isEmpty(lists.get(i).get("control"))?0:lists.get(i).get("control").equals(true)?1:0);
				tem.setDesc(StringUtils.isEmpty(lists.get(i).get("descs")) ? null
						: lists.get(i).get("descs").toString());
				tem.setInType(StringUtils.isEmpty(lists.get(i).get("entering_type"))?0:lists.get(i).get("entering_type").equals(true)?1:0);
				tem.setLevel(StringUtils.isEmpty(lists.get(i).get("levels")) ? null
						: lists.get(i).get("levels").toString());
				tem.setNumber(StringUtils.isEmpty(lists.get(i).get("number")) ? 0
						: Integer.valueOf(lists.get(i).get("number").toString()));
				tem.setPid(StringUtils.isEmpty(lists.get(i).get("FID")) ? 0
						: Integer.valueOf(lists.get(i).get("FID").toString()));
				tem.setSetvalue(StringUtils
						.isEmpty(lists.get(i).get("setvalue")) ? 0 :Float
						.valueOf(lists.get(i).get("setvalue").toString()));
				tem.setSort(StringUtils.isEmpty(lists.get(i).get("sort")) ? 0
						: Integer.valueOf(lists.get(i).get("sort").toString()));
				tem.setStandard(StringUtils
						.isEmpty(lists.get(i).get("standard")) ? null : lists
						.get(i).get("standard").toString());
				tem.setTemplateId(StringUtils.isEmpty(lists.get(i).get(
						"templet_id")) ? 0 : Integer.valueOf(lists.get(i)
						.get("templet_id").toString()));
				tem.setTemplateNumber(StringUtils.isEmpty(lists.get(i).get(
						"templet_number")) ? null : lists.get(i)
						.get("templet_number").toString());
				temList.add(tem);
				
			}			
		TemplateMaintenanceVO templateMaintenanceVO = getNodeById(parent,
					temList);
			templateMaintenanceVO.setChildren(js);
			map.put("data", templateMaintenanceVO);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}

	@Override
	public Map<String, Object> saveAndupdateTemlate(
			TemplateMaintenanceEntity tem) {
		Map<String, Object> map = new HashMap<>();
		TemplateMaintenanceEntity tm = new TemplateMaintenanceEntity();
		try {
			TemplateMaintenanceEntity result = templateMaintenanceDao
					.findOne(tem.getTemplateId());
			if (StringUtils.isEmpty(result)) {
				tm.setTemplateId(tem.getTemplateId());
				tm.setCofficient(tem.getCofficient());
				tm.setControl(tem.getControl());
				tm.setDesc(tem.getDesc());
				tm.setInType(tem.getInType());
				tm.setLevel(tem.getLevel());
				tm.setNumber(tem.getNumber());
				tm.setSetvalue(tem.getSetvalue());
				tm.setSort(tem.getSort());
				tm.setStandard(tem.getStandard());
				tm.setTemplateNumber(tem.getTemplateNumber());
				tm.setTemplateType(tem.getTemplateType());
				tm.setpId(tem.getpId());
				templateMaintenanceDao.save(tm);
				map.put("message", "添加成功");
			} else {
				result.setInType(tem.getInType());
				result.setNumber(tem.getNumber());
				result.setDesc(tem.getDesc());
				result.setControl(tem.getControl());
				result.setCofficient(tem.getCofficient());
				result.setSetvalue(tem.getSetvalue());
				result.setStandard(tem.getStandard());
				result.setpId(tem.getpId());
				templateMaintenanceDao.save(result);
				map.put("message", "修改成功");
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteTemlate(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			templateMaintenanceDao.deleteTemplate(id);
			map.put("message", "删除成功");
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "删除失败");

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
				jsonMenu.put("childNode", c_node);
				childMenu.add(jsonMenu);
			}
		}
		return childMenu;
	}

	// 获取根对象
	public TemplateMaintenanceVO getNodeById(int templateId,
			List<TemplateMaintenanceVO> temList) {
		TemplateMaintenanceVO tem = new TemplateMaintenanceVO();
		for (TemplateMaintenanceVO item : temList) {
			if (item.getTemplateId() == templateId) {
				tem = item;
				break;
			}
		}
		return tem;
	}
}
