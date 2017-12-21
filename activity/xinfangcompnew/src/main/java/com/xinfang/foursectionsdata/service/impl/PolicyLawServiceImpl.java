package com.xinfang.foursectionsdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.VO.StorkxfSikulawinfoVO;
import com.xinfang.foursectionsdata.service.PolicyLawService;
import com.xinfang.log.ApiLog;

@Service
public class PolicyLawServiceImpl implements PolicyLawService {
	/*@Autowired
	private StorkxfsikulawinfoRepository storkxfsikulawinfoRepository;*/
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> getLawList(Integer startPage, Integer pageCount,
			final Integer lawType, final String fuzzy, final String startTime,
			final String endTime) {
		Map<String, Object> map =new HashMap<>();
		final String fuzzySql = " AND (`name`like '%" + fuzzy
				+ "%' OR send_apartment LIKE '%" + fuzzy + "%')";
		final String lawTypeSql = " AND category_id = " + lawType;
		final String timeSql = " AND time BETWEEN '" + startTime + "' AND '"
				+ endTime + "'";
		String nSql = "\n";
		class getLawListConcatSQl {
			public String result(String nSql) {
				String aSql = fuzzySql;
				String bSql = lawTypeSql;
				String cSql = timeSql;
				String filterSql = "\n";

				if (!StringUtils.isEmpty(fuzzy)) {

					filterSql = aSql;
				} else {
					aSql = "";
				}
				if (lawType != 0) {
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


			return filterSql;
			}
		}
		String filterSql = new getLawListConcatSQl().result(nSql);
		String sql = "SELECT  category, id,name,send_apartment,time,(SELECT COUNT(*) FROM stork_xf_siku_law_info  WHERE id is not null  "
				+ filterSql
				+ ") as total FROM stork_xf_siku_law_info WHERE id is not null "
				+ filterSql+" order By xh asc limit "+startPage*pageCount+","+pageCount;
		try {
			System.err.println(sql);
			List<Map<String, Object>> list =jdbcTemplate.queryForList(sql);
			if(list!=null&&list.size()>0){
			List<StorkxfSikulawinfoVO> skList=new ArrayList<>();
			for(int i=0;i<list.size();i++){
				StorkxfSikulawinfoVO sk=new StorkxfSikulawinfoVO();
				sk.setName(StringUtils.isEmpty(list.get(i).get("name")) ? null
						: list.get(i).get("name").toString());
				sk.setCategory(StringUtils.isEmpty(list.get(i).get("category")) ? null
						: list.get(i).get("category").toString());
				sk.setSendApartment(StringUtils.isEmpty(list.get(i).get(
						"send_apartment")) ? null : list.get(i)
						.get("send_apartment").toString());
				sk.setStrTime(StringUtils.isEmpty(list.get(i).get("time")) ? null
						: list.get(i).get("time").toString());
				sk.setId(StringUtils.isEmpty(list.get(i).get("id"))?0:Integer.valueOf(list.get(i).get("id").toString()));
				skList.add(sk);
			}
			map.put("data", skList);
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
				map.put("message", "满足条件的信息为空");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}
		
		
		
		
		return map;
	}

	@Override
	public Map<String, Object> getLawDetailsById(Integer lawId) {
		String sql = "SELECT f.name,f.content, f.category, f.send_apartment ,f.time  FROM stork_xf_siku_law_info as f where id= "
				+ lawId;
		Map<String, Object> map = new HashMap<>();
		try {		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		System.err.println(list.size());
		List<StorkxfSikulawinfoVO> sklist = new ArrayList<StorkxfSikulawinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			StorkxfSikulawinfoVO sk = new StorkxfSikulawinfoVO();
			sk.setName(StringUtils.isEmpty(list.get(i).get("name")) ? null
					: list.get(i).get("name").toString());
			sk.setContent(StringUtils.isEmpty(list.get(i).get("content")) ? null
					: list.get(i).get("content").toString());
			sk.setCategory(StringUtils.isEmpty(list.get(i).get("category")) ? null
					: list.get(i).get("category").toString());
			sk.setSendApartment(StringUtils.isEmpty(list.get(i).get(
					"send_apartment")) ? null : list.get(i)
					.get("send_apartment").toString());
			sk.setStrTime(StringUtils.isEmpty(list.get(i).get("time")) ? null
					: list.get(i).get("time").toString());
			sklist.add(sk);
		}
		
		map.put("data", sklist);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> getTypeList() {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT DISTINCT category, category_id FROM stork_xf_siku_law_info ORDER BY xh ASC";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		List<StorkxfSikulawinfoVO> sklist = new ArrayList<StorkxfSikulawinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			StorkxfSikulawinfoVO sk = new StorkxfSikulawinfoVO();
			sk.setCategory(StringUtils.isEmpty(list.get(i).get("category")) ? null
					: list.get(i).get("category").toString());
			sk.setCategoryid(StringUtils
					.isEmpty(list.get(i).get("category_id")) ? null : Integer
					.valueOf(list.get(i).get("category_id").toString()));
			sklist.add(sk);
		}
		map.put("data", sklist);
		// List<String> list =storkxfsikulawinfoRepository.getTypeList();
		// map.put("type", list);
		return map;
	}

}

