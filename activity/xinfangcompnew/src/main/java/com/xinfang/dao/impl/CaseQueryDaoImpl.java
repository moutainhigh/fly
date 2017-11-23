package com.xinfang.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xinfang.dao.CaseQueryDao;
import com.xinfang.log.ApiLog;

/**
 * 
 * @author sunbjx Date: 2017年6月5日上午10:32:42
 */
@Repository
public class CaseQueryDaoImpl implements CaseQueryDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> customConditionQuery(List<Map<String, Object>> conditions, int caseTimeTag) {
		String sql = "select \n" + "	c.petition_code,\n"
				+ "    (select username from fd_guest where ID = c.guest_id) as petition_name,\n"
				+ "    (select head_pic from fd_guest where ID = c.guest_id) as petition_headsrc,\n"
				+ "    (select name from fd_code where code = c.petition_way) as petition_way,\n"
				+ "    (select name from fd_code where code =c.question_belonging_to) as case_belongto,\n"
				+ "    c.case_desc,\n"
				+ "    (select DW_MC from fc_dwb where DW_ID = c.current_handle_unitid) as handle_unit,\n"
				+ "    (select RY_MC from fc_ryb where RY_ID = c.current_handle_personalid) as handle_user,\n"
				+ "	(select RY_IMG from fc_ryb where RY_ID = c.current_handle_personalid) as handle_user_headsrc,\n"
				+ "	c.gmt_create,\n" + "    c.current_handle_state,\n"
				+ "    if(ifnull((select is_department from fp_appraise where case_id = c.id), 0) = 0, '否', '是') as is_ok,\n"
				+ "    if((select count(*) from fd_dep_case where  type in (1) and UPDATE_TIME<=END_TIME and find_in_set(c.ID, CASE_ID)) = 0, '否', '是') as is_instant_handle,\n"
				+ "    if(ifnull(c.case_handle_state, 0) <> 2003, '否', '是') as is_ontime_close,\n"
				+ "    c.id as case_id\n" + "from fd_case c where 1 = 1\n";

		// 办理中
		if (caseTimeTag == 1) {
			sql += " and c.case_handle_state <> 2003 \n";
		}
		// 20天内逾期
//		if (caseTimeTag == 2) {
//			sql += " and";
//		}
		// 逾期未办结
		if (caseTimeTag == 2) {
			sql += " and c.case_handle_state <> 2003 and now() > c.handle_period_end \n";
		}
		// 按期办结
		if (caseTimeTag == 3) {
			sql += " and c.case_handle_state = 2003 and  c.handle_period_end > c.handle_fact_end_time \n";
		}

		if (conditions != null && conditions.size() > 0) {
			for (Map<String, Object> map : conditions) {
				sql += "\u0020" + map.get("relation");
				sql += "\u0020" + map.get("singleColumn");
				sql += "\u0020" + map.get("singleWhere");
				sql += "\u0020" + map.get("singleColumnValue");
			}
		}

		List<Map<String, Object>> result = null;
		try {
			result = jdbcTemplate.queryForList(sql);
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return result;
	}

}
