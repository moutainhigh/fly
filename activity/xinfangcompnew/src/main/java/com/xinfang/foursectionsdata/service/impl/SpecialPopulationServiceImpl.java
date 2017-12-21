package com.xinfang.foursectionsdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.dao.FcQxsbRepository;

import com.xinfang.log.ApiLog;

@Service
public class SpecialPopulationServiceImpl implements
		com.xinfang.foursectionsdata.service.SpecialPopulationService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FcQxsbRepository fcQxsbRepository;
	/*@Override
	public Map<String, Object> contradictionPopulation(List<Integer> QxsId,
			String fuzzy, Integer startPage, Integer pageCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> samePopulation(List<Integer> QxsId,
			String fuzzy, Integer startPage, Integer pageCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> importantPopulation(List<Integer> QxsId,
			String fuzzy, Integer startPage, Integer pageCount) {
		String fuzzySql = "";
		PageRequest page = new PageRequest(startPage, pageCount);

		Map<String, Object> map = new HashMap<String, Object>();

		
		 * Page<ImportantPopulation> list = null; if (StringUtils.isEmpty(QxsId)
		 * && StringUtils.isEmpty(fuzzy)) { list =
		 * importantPopulationRepository.getImportantPopulationList( page);
		 * 
		 * } else if (QxsId.size() > 0 && !StringUtils.isEmpty(QxsId) &&
		 * !StringUtils.isEmpty(fuzzy)) { list = importantPopulationRepository
		 * .getImportantPopulationByFuzzyAndQxsId(QxsId, fuzzy, page); fuzzySql
		 * = " where FD.QXS_ID in (" + QxsId + ") AND FD.XM like CONCAT('%" +
		 * fuzzy + "%')"; } else if (QxsId.size() > 0 &&
		 * !StringUtils.isEmpty(QxsId) && StringUtils.isEmpty(fuzzy)) { list =
		 * importantPopulationRepository
		 * .getImportantPopulationListByQsxIds(QxsId, page); fuzzySql =
		 * " where FD.QXS_ID in (" + QxsId + ")"; } else if
		 * (StringUtils.isEmpty(QxsId) && !StringUtils.isEmpty(fuzzy)) { list =
		 * importantPopulationRepository.getImportantPopulationByFuzzy( fuzzy,
		 * page); fuzzySql = " where FD.XM like CONCAT('%" + fuzzy + "%')"; }
		 
		if (QxsId.size() > 0 && !StringUtils.isEmpty(QxsId)
				&& !StringUtils.isEmpty(fuzzy)) {
			fuzzySql = " where FD.QXS_ID in (" + QxsId
					+ ") AND FD.XM like CONCAT('%" + fuzzy + "%')";
		} else if (QxsId.size() > 0 && !StringUtils.isEmpty(QxsId)
				&& StringUtils.isEmpty(fuzzy)) {
			fuzzySql = " where FD.QXS_ID in (" + QxsId + ")";
		} else if (StringUtils.isEmpty(QxsId) && !StringUtils.isEmpty(fuzzy)) {
			fuzzySql = " where FD.XM like CONCAT('%" + fuzzy + "%')";
		}
		String sql = "select FD.* , (select Q.QXS_MC "
				+ " from FC_QXSB Q where Q.QXS_ID=FD.QXS_ID)" + " QXS_MC ,"
				+ " (SELECT COUNT(*) FROM fd_wkdxb " + fuzzySql + ") AS total,"
				+ " (select M.MZ_MC from"
				+ " FC_MZB M where M.MZ_ID=FD.MZ_ID) MZ_MC"
				+ " from FD_WKDXB FD " + fuzzySql
				+ " ORDER BY FD.XH asc LIMIT " + startPage * pageCount + ","
				+ pageCount;
		try {
			System.err.println(sql);
			List<ImportantPopulation> ipList=new ArrayList<ImportantPopulation>();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if (list.size() > 0 && !StringUtils.isEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					ImportantPopulation ip = new ImportantPopulation();
					ip.setWkdxId(StringUtils
							.isEmpty(list.get(i).get("WKDX_ID")) ? 0 : Integer
							.valueOf(list.get(i).get("WKDX_ID").toString()));
					ip.setXm(StringUtils.isEmpty(list.get(i).get("XM")) ? null
							: list.get(i).get("XM").toString());
					ip.setSfz(StringUtils.isEmpty(list.get(i).get("SFZ")) ? null
							: list.get(i).get("SFZ").toString());
					ip.setSjhm(StringUtils.isEmpty(list.get(i).get("SJHM")) ? null
							: list.get(i).get("SJHM").toString());
					ip.setQxsId(StringUtils.isEmpty(list.get(i).get("QXS_ID")) ? 0
							: Integer.valueOf(list.get(i).get("QXS_ID")
									.toString()));
					ip.setMzId(StringUtils.isEmpty(list.get(i).get("MZ_ID")) ? 0
							: Integer.valueOf(list.get(i).get("MZ_ID")
									.toString()));
					ip.setXlId(StringUtils.isEmpty(list.get(i).get("XL_ID")) ? 0
							: Integer.valueOf(list.get(i).get("XL_ID")
									.toString()));
					ip.setSfId(StringUtils.isEmpty(list.get(i).get("SF_ID")) ? 0
							: Integer.valueOf(list.get(i).get("SF_ID")
									.toString()));
					ip.setZzmmId(StringUtils
							.isEmpty(list.get(i).get("ZZMM_ID")) ? 0 : Integer
							.valueOf(list.get(i).get("ZZMM_ID").toString()));
					ip.setSqId(StringUtils.isEmpty(list.get(i).get("SQ_ID")) ? 0
							: Integer.valueOf(list.get(i).get("SQ_ID")
									.toString()));
					ip.setRyImg(StringUtils.isEmpty(list.get(i).get("RY_IMG")) ? null
							: list.get(i).get("RY_IMG").toString());
					ip.setXh(StringUtils.isEmpty(list.get(i).get("XH")) ? 0
							: Float.valueOf(list.get(i).get("XH").toString()));
					ip.setXgsj(StringUtils.isEmpty(list.get(i).get("XGSJ")) ? null
							: DateUtils.strToDate(list.get(i).get("XGSJ")
									.toString()));
					ip.setQyzt(StringUtils.isEmpty(list.get(i).get("QYZT")) ? 0
							: Integer.valueOf(list.get(i).get("QYZT")
									.toString()));
					ip.setZdryXzId(StringUtils.isEmpty(list.get(i).get(
							"ZDRYXZ_ID")) ? 0 : Integer.valueOf(list.get(i)
							.get("ZDRYXZ_ID").toString()));
					ip.setRyXb(StringUtils.isEmpty(list.get(i).get("RY_XB")) ? null
							: list.get(i).get("RY_XB").toString());
					ip.setZyhdw(StringUtils.isEmpty(list.get(i).get("ZYHDW")) ? null
							: list.get(i).get("ZYHDW").toString());
					ip.setXffs(StringUtils.isEmpty(list.get(i).get("XFFS")) ? null
							: list.get(i).get("XFFS").toString());
					ip.setXfrs(StringUtils.isEmpty(list.get(i).get("XFRS")) ? null
							: list.get(i).get("XFRS").toString());
					ip.setSsxt(StringUtils.isEmpty(list.get(i).get("SSXT")) ? null
							: list.get(i).get("SSXT").toString());
					ip.setXfmd(StringUtils.isEmpty(list.get(i).get("XFMD")) ? null
							: list.get(i).get("XFMD").toString());
					ip.setHjd(StringUtils.isEmpty(list.get(i).get("HJD")) ? null
							: list.get(i).get("HJD").toString());
					ip.setCzd(StringUtils.isEmpty(list.get(i).get("CJD")) ? null
							: list.get(i).get("CJD").toString());
					ip.setWtsd(StringUtils.isEmpty(list.get(i).get("WTSD")) ? null
							: list.get(i).get("WTSD").toString());
					ip.setJtcyjzyshgx(StringUtils.isEmpty(list.get(i).get(
							"JTCYJZYSHGX")) ? null : list.get(i)
							.get("JTCYJZYSHGX").toString());
					ip.setSfrxmjsfzhm(StringUtils.isEmpty(list.get(i).get(
							"SFRXMJSFZHM")) ? null : list.get(i)
							.get("SFRXMJSFZHM").toString());
					ip.setCcsfsj(StringUtils.isEmpty(list.get(i).get("CCSFSJ")) ? null
							: list.get(i).get("CCSFSJ").toString());
					ip.setLcsfqk(StringUtils.isEmpty(list.get(i).get("LCSFQK")) ? null
							: list.get(i).get("LCSFQK").toString());
					ip.setBald(StringUtils.isEmpty(list.get(i).get("BALD")) ? null
							: list.get(i).get("BALD").toString());
					ip.setZrdwjzrrlxdh(StringUtils.isEmpty(list.get(i).get(
							"ZRDWJZRRLXDH")) ? null : list.get(i)
							.get("ZRDWJZRRLXDH").toString());
					ip.setZysqjclqk(StringUtils.isEmpty(list.get(i).get(
							"ZYSQJCLQK")) ? null : list.get(i).get("ZYSQJCLQK")
							.toString());
					ip.setXfcj(StringUtils.isEmpty(list.get(i).get("XFCJ")) ? null
							: list.get(i).get("XFCJ").toString());
					ip.setXfxz(StringUtils.isEmpty(list.get(i).get("XFXZ")) ? null
							: list.get(i).get("XFXZ").toString());
					ip.setSfzf(StringUtils.isEmpty(list.get(i).get("SFZF")) ? null
							: list.get(i).get("SFZF").toString());
					ip.setSfsksfl(StringUtils.isEmpty(list.get(i)
							.get("SFSKSFL")) ? null : list.get(i)
							.get("SFSKSFL").toString());
					ip.setSfyjf(StringUtils.isEmpty(list.get(i).get("SFYJF")) ? null
							: list.get(i).get("SFYJF").toString());
					ip.setXfja(StringUtils.isEmpty(list.get(i).get("XFJA")) ? null
							: list.get(i).get("XFJA").toString());
					ip.setHjqk(StringUtils.isEmpty(list.get(i).get("HJQK")) ? null
							: list.get(i).get("HJQK").toString());
					ip.setWkqk(StringUtils.isEmpty(list.get(i).get("WKQK")) ? null
							: list.get(i).get("WKQK").toString());
					ip.setXjId(StringUtils.isEmpty(list.get(i).get("XJ_ID")) ? 0
							: Integer.valueOf(list.get(i).get("XJ_ID")
									.toString()));
					ip.setHjdWz(StringUtils.isEmpty(list.get(i).get("HJD_WZ")) ? null
							: list.get(i).get("HJD_WZ").toString());
					ip.setWtsdWz(StringUtils
							.isEmpty(list.get(i).get("WTSD_WZ")) ? null : list
							.get(i).get("WTSD_WZ").toString());
					ip.setCzrId(StringUtils.isEmpty(list.get(i).get("CZR_ID")) ? 0
							: Integer.valueOf(list.get(i).get("CZR_ID")
									.toString()));
					ip.setCzyQxsId(StringUtils.isEmpty(list.get(i).get(
							"CZY_QXSID")) ? 0 : Integer.valueOf(list.get(i)
							.get("CZY_QXSID").toString()));
					ip.setCzyDwId(StringUtils.isEmpty(list.get(i).get(
							"CZY_DWID")) ? 0 : Integer.valueOf(list.get(i)
							.get("CZY_DWID").toString()));
					ip.setQxsName(StringUtils
							.isEmpty(list.get(i).get("QXS_MC")) ? null : list
							.get(i).get("QXS_MC").toString());
					ip.setMcName(StringUtils.isEmpty(list.get(i).get("MZ_MC")) ? null
							: list.get(i).get("MZ_MC").toString());
					ipList.add(ip);
				}
				map.put("data", ipList);
				map.put("pageIndex", startPage);
				map.put("pagecount", pageCount);
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
			} else {
				map.put("message", "没有满足条件的信息");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			map.put("message", "获取信息出错");
		}

		return map;
	}
*/
	@Override
	public Map<String, Object> countPopulation(Integer QxsId,Integer Populationtype,Integer sex) {		
		List<Map> list=new ArrayList<>();
		Map<String, Object> returnMap =new HashMap<>();
		String sql="";
		try {
			
		
		if(QxsId!=0&& Populationtype == 0&& sex==0){
			sql="select  (SELECT qxs_mc FROM fc_qxsb as f WHERE f.qxs_id = "+QxsId+") AS qxsName ,"
					+ " (select sum(case when QXS_ID ="+QxsId+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = 1)  as Zdtotal,"
					+ " (select sum(case when QXS_ID ="+QxsId+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = 2)  as ybtotal,"
					+ " (select sum(case when QXS_ID ="+QxsId+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = 3) as mdtotal"
					+ " from FD_WKDXB";		
			List<Map<String, Object>> lists=jdbcTemplate.queryForList(sql);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("area", StringUtils.isEmpty(lists.get(0).get("qxsName"))?null:lists.get(0).get("qxsName").toString());
			map.put("importantTotal", StringUtils.isEmpty(lists.get(0).get("Zdtotal"))?0:Integer.valueOf(lists.get(0).get("Zdtotal").toString()));
			map.put("nomalTotal", StringUtils.isEmpty(lists.get(0).get("ybtotal"))?0:Integer.valueOf(lists.get(0).get("ybtotal").toString()));
			map.put("abnormaltotal", StringUtils.isEmpty(lists.get(0).get("mdtotal"))?0:Integer.valueOf(lists.get(0).get("mdtotal").toString()));
			list.add(map);
		}
		
		
		if(QxsId == 0 && Populationtype == 0&& sex==0){
			List<Integer> QxsIdList=fcQxsbRepository.getQxsId();
		for(int i=0;i<QxsIdList.size();i++){
			sql="select  (SELECT qxs_mc FROM fc_qxsb as f WHERE f.qxs_id = "+QxsIdList.get(i)+") AS qxsName ,"
					+ " (select sum(case when QXS_ID ="+QxsIdList.get(i)+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = 1)  as Zdtotal,"
					+ " (select sum(case when QXS_ID ="+QxsIdList.get(i)+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = 2)  as ybtotal,"
					+ " (select sum(case when QXS_ID ="+QxsIdList.get(i)+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = 3)  as mdtotal"
					+ " from FD_WKDXB";		
			List<Map<String, Object>> lists=jdbcTemplate.queryForList(sql);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("area", StringUtils.isEmpty(lists.get(0).get("qxsName"))?null:lists.get(0).get("qxsName").toString());
			map.put("importantTotal", StringUtils.isEmpty(lists.get(0).get("Zdtotal"))?0:Integer.valueOf(lists.get(0).get("Zdtotal").toString()));
			map.put("nomalTotal", StringUtils.isEmpty(lists.get(0).get("ybtotal"))?0:Integer.valueOf(lists.get(0).get("ybtotal").toString()));
			map.put("abnormaltotal", StringUtils.isEmpty(lists.get(0).get("mdtotal"))?0:Integer.valueOf(lists.get(0).get("mdtotal").toString()));
			list.add(map);
		}	
		}
		if(QxsId == 0 && Populationtype != 0&& sex==0){
			List<Integer> QxsIdList=fcQxsbRepository.getQxsId();
			for(int i=0;i<QxsIdList.size();i++){
			sql="select  (SELECT qxs_mc FROM fc_qxsb as f WHERE f.qxs_id = "+QxsIdList.get(i)+") AS qxsName ,"
					+ " (select sum(case when QXS_ID ="+QxsIdList.get(i)+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = "+Populationtype+")  as total"
					+ " from FD_WKDXB";				
				
			List<Map<String, Object>> lists=jdbcTemplate.queryForList(sql);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("area", StringUtils.isEmpty(lists.get(0).get("qxsName"))?null:lists.get(0).get("qxsName").toString());
			if(Populationtype==1){
			map.put("importantTotal", StringUtils.isEmpty(lists.get(0).get("total"))?0:Integer.valueOf(lists.get(0).get("total").toString()));
			}else if(Populationtype==2){
			map.put("nomalTotal", StringUtils.isEmpty(lists.get(0).get("total"))?0:Integer.valueOf(lists.get(0).get("total").toString()));
			}else if(Populationtype==3){
			map.put("abnormaltotal", StringUtils.isEmpty(lists.get(0).get("total"))?0:Integer.valueOf(lists.get(0).get("total").toString()));
			}
			list.add(map);
			}
		}
		if(QxsId == 0 && Populationtype != 0&& sex!=0){
			List<Integer> QxsIdList=fcQxsbRepository.getQxsId();
			for(int i=0;i<QxsIdList.size();i++){
			sql="select  (SELECT qxs_mc FROM fc_qxsb as f WHERE f.qxs_id = "+QxsIdList.get(i)+") AS qxsName ,"
					+ " (select sum(case when QXS_ID ="+QxsIdList.get(i)+" then 1 end)  from FD_WKDXB where ZDRYXZ_ID = "+Populationtype+")  as total,"
					+ "(select sum(case when QXS_ID ="+QxsIdList.get(i)+" then 1 end) from FD_WKDXB where ZDRYXZ_ID = "+Populationtype+" AND RY_XB = 0) as famaleTotal,"
					+ "(select sum(case when QXS_ID ="+QxsIdList.get(i)+" then 1 end) from FD_WKDXB where ZDRYXZ_ID = "+Populationtype+" AND RY_XB = 1) as maleTotal"
					+ " from FD_WKDXB";				
				
			System.err.println(sql);
			List<Map<String, Object>> lists=jdbcTemplate.queryForList(sql);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("area", StringUtils.isEmpty(lists.get(0).get("qxsName"))?null:lists.get(0).get("qxsName").toString());
			map.put("famaleTotal",  StringUtils.isEmpty(lists.get(0).get("famaleTotal"))?0:Integer.valueOf(lists.get(0).get("famaleTotal").toString()));
			map.put("maleTotal",  StringUtils.isEmpty(lists.get(0).get("maleTotal"))?0:Integer.valueOf(lists.get(0).get("maleTotal").toString()));
			if(Populationtype==1){
			map.put("importantTotal", StringUtils.isEmpty(lists.get(0).get("total"))?0:Integer.valueOf(lists.get(0).get("total").toString()));
			}else if(Populationtype==2){
			map.put("nomalTotal", StringUtils.isEmpty(lists.get(0).get("total"))?0:Integer.valueOf(lists.get(0).get("total").toString()));
			}else if(Populationtype==3){
			map.put("abnormaltotal", StringUtils.isEmpty(lists.get(0).get("total"))?0:Integer.valueOf(lists.get(0).get("total").toString()));
			}
			list.add(map);
			}
		}
		if(QxsId != 0 && Populationtype == 0 && sex!=0){	
			for(int i=1;i<4;i++){
			sql="select  (SELECT qxs_mc FROM fc_qxsb as f WHERE f.qxs_id = "+QxsId+") AS qxsName ,"				
					+ "(select sum(case when QXS_ID ="+QxsId+" then 1 end) from FD_WKDXB where ZDRYXZ_ID = "+i+" AND RY_XB = 0) as famaleTotal,"
					+ "(select sum(case when QXS_ID ="+QxsId+" then 1 end) from FD_WKDXB where ZDRYXZ_ID = "+i+" AND RY_XB = 1) as maleTotal"
					+ " from FD_WKDXB";				
				
			System.err.println(sql);
			List<Map<String, Object>> lists=jdbcTemplate.queryForList(sql);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("area", StringUtils.isEmpty(lists.get(0).get("qxsName"))?null:lists.get(0).get("qxsName").toString());
			map.put("famaleTotal",  StringUtils.isEmpty(lists.get(0).get("famaleTotal"))?0:Integer.valueOf(lists.get(0).get("famaleTotal").toString()));
			map.put("maleTotal",  StringUtils.isEmpty(lists.get(0).get("maleTotal"))?0:Integer.valueOf(lists.get(0).get("maleTotal").toString()));
			if(i==1){
				map.put("tag", "重点人员");
			}if(i==2){
				map.put("tag", "一般信访人员");
			}if(i==3){
				map.put("tag", "矛盾纠纷人员");
			}
			list.add(map);
			}
		}
		returnMap.put("data", list);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			returnMap.put("message","获取信息出错");
		}
		
		
		return returnMap;
	}

}
