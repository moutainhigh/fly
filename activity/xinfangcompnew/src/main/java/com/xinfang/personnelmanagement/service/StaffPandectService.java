package com.xinfang.personnelmanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.VO.StaffPandectVO;
import com.xinfang.dao.FcKszwbRepository;
import com.xinfang.log.ApiLog;

/**
 * 
 * @author zhangbo
 * 
 */
@Service
public class StaffPandectService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FcKszwbRepository fcKszwbRepository;

	public Map<String, Object> staffPandect(int countyId, int unitId) {
		List<StaffPandectVO> staffPandectList = new ArrayList<>();
		JSONArray json = new JSONArray();
		Map<String, Object> Vomap = new HashMap<>();
		String sql = "SELECT\n" + "  r.RY_IMG     AS img,r.ry_sfz AS sfz,\n"
				+ "  r.RY_MC      AS username,\n"
				+ "  k.KS_MC      AS department,\n"
				+ "  z.KSZW_MC    AS post,\n" + "  z.parent_id,z.kszw_id,\n"
				+ "  z.profession_level\n" + "FROM fc_ryb r\n"
				+ "  LEFT JOIN fc_qxsb q ON r.QXS_ID = q.QXS_ID\n"
				+ "  LEFT JOIN fc_dwb d ON r.DW_ID = d.DW_ID\n"
				+ "  LEFT JOIN fc_ksb k ON r.KS_ID = k.KS_ID\n"
				+ "  LEFT JOIN fc_kszwb z ON r.zw_ID = z.KSzw_ID\n"
				+ "WHERE q.QXS_ID = " + countyId + "      AND d.DW_ID = "
				+ unitId;

		// 暂不显示科室下的人员总览
		// if (departmentId != 0) {
		// sql += "   and k.KS_ID = " + departmentId;
		// }

		StaffPandectVO staffPandectVo = new StaffPandectVO();
		try {

			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if (list != null && list.size() > 0) {

				// 获取全部对象
				for (int i = 0; i < list.size(); i++) {
					StaffPandectVO staffPandect = new StaffPandectVO();
					staffPandect.setzId(StringUtils.isEmpty(list.get(i).get(
							"kszw_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("kszw_id").toString()));
					staffPandect.setpId(StringUtils.isEmpty(list.get(i).get(
							"parent_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("parent_id").toString()));
					staffPandect.setImg(StringUtils.isEmpty(list.get(i).get(
							"img")) ? list.get(i).get("sfz").toString() : list
							.get(i).get("img").toString());
					staffPandect.setUsername(StringUtils.isEmpty(list.get(i)
							.get("username")) ? null : list.get(i)
							.get("username").toString());
					staffPandect.setDepartment(StringUtils.isEmpty(list.get(i)
							.get("department")) ? null : list.get(i)
							.get("department").toString());
					staffPandect.setPost(StringUtils.isEmpty(list.get(i).get(
							"post")) ? null : list.get(i).get("post")
							.toString());
					staffPandectList.add(staffPandect);
				}
				// 获取根部ID
				json = JSONArray.fromObject(staffPandectList);
				Integer parent = fcKszwbRepository.getZwById(unitId);

				// staffPandectVo = generateTreeNode(parent, staffPandectList);
				// Vomap.put("message", staffPandectVo);
				JSONArray jsonArray = treeMenuList(json, parent);
				staffPandectVo = getNodeById(parent, staffPandectList);
				staffPandectVo.setChildren(jsonArray);
				Vomap.put("message", staffPandectVo);

			} else {
				Vomap.put("message", "该单位下为空");
			}
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			Vomap.put("message", "获取信息失败");

		}

		return Vomap;
	}

	// 获取根对象
	public StaffPandectVO getNodeById(int zId,
			List<StaffPandectVO> staffPandectList) {
		StaffPandectVO staffPandect = new StaffPandectVO();
		for (StaffPandectVO item : staffPandectList) {
			if (item.getzId() == zId) {
				staffPandect = item;
				break;
			}
		}
		return staffPandect;
	}

	/*
	 * public List<StaffPandectVO> getChildrenNodeById(int zId,
	 * List<StaffPandectVO> staffPandectList) { List<StaffPandectVO>
	 * childrenStaffPandectVO = new ArrayList<StaffPandectVO>(); for
	 * (StaffPandectVO item : staffPandectList) { if (item.getpId() == zId) {
	 * childrenStaffPandectVO.add(item); } } return childrenStaffPandectVO; }
	 * 
	 * public StaffPandectVO generateTreeNode(int rootId, List<StaffPandectVO>
	 * staffPandectList) {
	 * 
	 * StaffPandectVO root = this.getNodeById(rootId, staffPandectList);
	 * List<StaffPandectVO> childrenTreeNode = this.getChildrenNodeById( rootId,
	 * staffPandectList); for (StaffPandectVO item : childrenTreeNode) {
	 * StaffPandectVO node = this.generateTreeNode(item.getzId(),
	 * staffPandectList);
	 * 
	 * root.getChildren().add(node);
	 * 
	 * } return root; }
	 */
	public JSONArray treeMenuList(JSONArray menuList, int parentId) {
		JSONArray childMenu = new JSONArray();
		for (Object object : menuList) {
			JSONObject jsonMenu = JSONObject.fromObject(object);
			int menuId = jsonMenu.getInt("zId");
			int pid = jsonMenu.getInt("pId");
			if (parentId == pid) {
				JSONArray c_node = treeMenuList(menuList, menuId);
				jsonMenu.put("childNode", c_node);
				childMenu.add(jsonMenu);
			}
		}
		return childMenu;
	}

	public Map<String, Object> staffTree(int unitId) {
		Map<String, Object> Vomap=new HashMap<>();
		String sql = "SELECT  k.KS_MC      AS department,"
				+ "z.KSZW_MC    AS post,  "
				+ "z.parent_id,z.kszw_id,"
				+ "z.profession_level FROM fc_kszwb z		"
				+ "LEFT JOIN fc_ksb k ON z.KS_ID = k.KS_ID "
				+ "LEFT JOIN fc_dwb d ON k.DW_ID = d.DW_ID"
				+ " WHERE d.DW_ID ="+unitId;
		try {
			
			List<StaffPandectVO> staffPandectList = new ArrayList<>();
			JSONArray json = new JSONArray();
			StaffPandectVO staffPandectVo = new StaffPandectVO();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if (list != null && list.size() > 0) {

				// 获取全部对象
				for (int i = 0; i < list.size(); i++) {
					StaffPandectVO staffPandect = new StaffPandectVO();
					staffPandect.setzId(StringUtils.isEmpty(list.get(i).get(
							"kszw_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("kszw_id").toString()));
					staffPandect.setpId(StringUtils.isEmpty(list.get(i).get(
							"parent_id")) ? 0 : Integer.valueOf(list.get(i)
							.get("parent_id").toString()));
					staffPandect.setDepartment(StringUtils.isEmpty(list.get(i)
							.get("department")) ? null : list.get(i)
							.get("department").toString());
					staffPandect.setPost(StringUtils.isEmpty(list.get(i).get(
							"post")) ? null : list.get(i).get("post")
							.toString());
					staffPandectList.add(staffPandect);
				}
				// 获取根部ID
				json = JSONArray.fromObject(staffPandectList);
				Integer parent = fcKszwbRepository.getZwById(unitId);

				
				JSONArray jsonArray = treeMenuList(json, parent);
				staffPandectVo = getNodeById(parent, staffPandectList);
				staffPandectVo.setChildren(jsonArray);
				Vomap.put("message", staffPandectVo);

			} else {
				Vomap.put("message", "该单位下为空");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			Vomap.put("message", "获取信息出错");
		}
		return Vomap;
	}

}
