package com.xinfang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.log.ApiLog;
import com.xinfang.service.ExternalService;

@Service
public class ExternalServiceImpl implements ExternalService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> listZhengFa(String startTime, String endTime) {
		String sql = "select \n" + "	c.ID as case_id,\n" + "	c.guest_id,\n" + "	g.username,\n" + "	g.birthday,\n"
				+ "	(select name from fd_code where code = g.card_type) as card_type,\n"
				+ "	(select SQ_MC from fc_sqb where SQ_ID = g.ca_city) as ca_city,\n"
				+ "	(select XJ_MC from fc_xjb where XJ_ID = g.ca_county) as ca_county,\n"
				+ "	(select SF_MC from fc_sfb where SF_ID = g.ca_province) as ca_province,\n"
				+ "	g.census_register,\n" + "	g.contact_address,\n"
				+ "	(select name from fd_code where code = g.employed_Info) as employed_Info,\n" + "	g.ethnic,\n"
				+ "	g.final_tel,\n" + "	g.head_pic,\n" + "	g.idcard,\n"
				+ "	if(ifnull(g.is_anonymity, 0) = 0, '否', '是') as is_anonymity,\n"
				+ "	if(ifnull(g.is_focus, 0) = 0, '否', '是') as is_focus,\n" + "	g.nationality,\n"
				+ "    (select SQ_MC from fc_sqb where SQ_ID = g.na_city) as na_city,\n"
				+ "	(select XJ_MC from fc_xjb where XJ_ID = g.na_county) as na_county,\n"
				+ "	(select SF_MC from fc_sfb where SF_ID = g.na_province) as na_province,\n" + "	g.now_address,\n"
				+ "	g.phone,\n" + "	if(g.sex = 1, '男', '女') as sex,\n" + "	g.zip_code,\n" + "	c.approve_id,\n"
				+ "	'' as attach_case_desc,\n" + "	'' as attach_case_ids,\n"
				+ "	(select name from fd_code where code = c.belong_to_sys) as belong_to_sys,\n"
				+ "	c.case_address,\n" + "	c.case_demand,\n" + "	c.case_desc,\n"
				+ "    case when c.case_handle_state = 1000\n" + "    then '待办'\n"
				+ "    when c.case_handle_state = 2000\n" + "    then '正办'\n" + "    when c.case_handle_state = 3000\n"
				+ "    then '已办'\n" + "    else '已办结' end as case_handle_state,\n"
				+ "   date_format(c.case_time, '%Y-%m-%d') as case_time,\n"
				+ "	(select name from fd_code where code = c.case_type) as case_type,\n"
				+ "	date_format(c.create_time,  '%Y-%m-%d') as create_time,\n"
				+ "	(select DW_MC from fc_dwb where DW_ID = c.create_unitid) as create_unitid,\n"
				+ "	(select RY_MC from fc_ryb where RY_ID = c.creator_id) as creator_id,\n"
				+ "	(select RY_MC from fc_ryb where RY_ID = c.current_handle_personalid) as current_handle_personalid,\n"
				+ "	c.current_handle_state,\n"
				+ "	(select DW_MC from fc_dwb where DW_ID = c.current_handle_unitid) as current_handle_unitid,\n"
				+ "	c.expose_object,\n" + "	c.file_src,\n"
				+ "	(select group_concat(username) from fd_guest where ID in (c.follow_guest_ids)) as follow_guest_ids,\n"
				+ "	c.form,\n" + "	date_format(c.gmt_create, '%Y-%m-%d') as gmt_create,\n" + "	c.handle_days,\n"
				+ "	c.handle_duration,\n"
				+ "	date_format(c.handle_fact_end_time, '%Y-%m-%d') as handle_fact_end_time,\n"
				+ "	case when c.handle_flow_type = 1\n" + "    then '交办'\n" + "    when c.handle_flow_type = 2\n"
				+ "    then '转办'\n" + "    when c.handle_flow_type = 3\n" + "    then '受理'\n"
				+ "    else '' end as handle_flow_type,\n"
				+ "	date_format(c.handle_period_end, '%Y-%m-%d') as handle_period_end,\n"
				+ "	date_format(c.handle_period_start, '%Y-%m-%d') as handle_period_start,\n"
				+ "	case when c.handle_type = 1\n" + "		then '交办'\n" + "        when c.handle_type = 2\n"
				+ "		then '转办'\n" + "        when c.handle_type = 3\n" + "		then '受理'\n"
				+ "        when c.handle_type = 4\n" + "		then '直接回复'\n" + "        when c.handle_type = 5\n"
				+ "		then '不予受理'\n" + "        when c.handle_type = 6\n" + "		then '不再受理'\n"
				+ "        when c.handle_type = 7\n" + "		then '直接存档'\n" + "		when c.handle_type = 8\n"
				+ "		then '退回'\n" + "        when c.handle_type = 9\n" + "		then '申请办结'\n"
				+ "        when c.handle_type = 10\n" + "		then '申请延期'\n" + "        when c.handle_type = 2001\n"
				+ "		then '审批通过'\n" + "        when c.handle_type = 2002\n" + "		then '审批不通过'\n"
				+ "		when c.handle_type = 2003\n" + "		then '收文岗派发'\n" + "        when c.handle_type = 1001\n"
				+ "		then '联系上访人'\n" + "        when c.handle_type = 1002\n" + "		then '调查取证'\n"
				+ "        when c.handle_type = 1003\n" + "		then '沟通协调'\n" + "        when c.handle_type = 1004\n"
				+ "		then '延期申请'\n" + "        when c.handle_type = 1005\n" + "		then '案件退回'\n"
				+ "        when c.handle_type = 1006\n" + "		then '案件办结'\n" + "        when c.handle_type = 11\n"
				+ "		then '待审'\n" + "        when c.handle_type = 12\n" + "		then '待处理'\n"
				+ "        when c.handle_type = 13\n" + "		then '待办结信访件' \n"
				+ "        else '' end as handle_type,\n"
				+ "	(select RY_MC from fc_ryb where RY_ID = c.handle_userid) as handle_userid,\n"
				+ "	c.petition_code,\n"
				+ "	(select name from fd_code where code = c.petition_county) as petition_county,\n"
				+ "	c.petition_names,\n"
				+ "	(select name from fd_code where code = c.petition_nature) as petition_nature,\n"
				+ "	c.petition_numbers,\n"
				+ "	(select name from fd_code where code = c.petition_purpose) as petition_purpose,\n"
				+ "	date_format(c.petition_time, '%Y-%m-%d') as petition_time,\n" + "	c.petition_unit,\n"
				+ "	(select name from fd_code where code = c.petition_way) as petition_way,\n"
				+ "	(select name from fd_code where code = c.petition_why) as petition_why,\n"
				+ "	(select name from fd_code where code = c.question_belonging_to) as question_belonging_to,\n"
				+ "	(select name from fd_code where code = c.question_hot) as question_hot,\n"
				+ "	(select name from fd_code where code = c.question_type) as question_type,\n"
				+ "	(select RY_MC from fc_ryb where RY_ID = c.receive_personalid) as receive_personalid,\n"
				+ "	(select DW_MC from fc_dwb where DW_ID = c.receive_unitid) as receive_unitid,\n"
				+ "	(select name from fd_code where code = c.thirteen_categories) as thirteen_categories,\n"
				+ "	c.title,\n"
				+ "	(select RY_MC from fc_ryb where RY_ID = c.transfer_personalid) as transfer_personalid,\n"
				+ "	(select DW_MC from fc_dwb where DW_ID = c.transfer_unitid) as transfer_unitid,\n"
				+ "	c.truste_name,\n" + "	if(ifnull(c.type, 0) = 2, '群访', '个访') as type,\n" + "	c.window_number,\n"
				+ "	if(ifnull(c.is_above, 0) = 0, '否', '是') as is_above,\n"
				+ "	if(ifnull(c.is_across_from, 0) = 0, '否', '是') as is_across_from,\n"
				+ "	if(ifnull(c.is_backlog, 0) = 0, '否', '是') as is_backlog,\n"
				+ "	if(ifnull(c.is_degree, 0) = 0, '否', '是') as is_degree,\n"
				+ "	if(ifnull(c.is_flow, 0) = 0, '否', '是') as is_flow,\n"
				+ "	if(ifnull(c.is_leader_approve, 0) = 0, '否', '是') as is_leader_approve,\n"
				+ "	if(ifnull(c.is_leader_limited, 0) = 0, '否', '是') as is_leader_limited,\n"
				+ "	if(ifnull(c.is_leader_package_case, 0) = 0, '否', '是') as is_leader_package_case,\n"
				+ "	if(ifnull(c.is_prominent, 0) = 0, '否', '是') as is_prominent,\n"
				+ "	if(ifnull(c.is_repeat_petition, 0) = 0, '否', '是') as is_repeat_petition,\n"
				+ "	if(ifnull(c.is_review, 0) = 0, '否', '是') as is_review,\n"
				+ "	if(ifnull(c.is_satisfied, 0) = 0, '否', '是') as is_satisfied,\n"
				+ "	if(ifnull(c.is_suit_abroad, 0) = 0, '否', '是') as is_suit_abroad,\n"
				+ "	if(ifnull(c.is_suit_aomen, 0) = 0, '否', '是') as is_suit_aomen,\n"
				+ "	if(ifnull(c.is_suit_hongkong, 0) = 0, '否', '是') as is_suit_hongkong,\n"
				+ "	if(ifnull(c.is_suit_outer, 0) = 0, '否', '是') as is_suit_outer,\n"
				+ "	if(ifnull(c.is_suit_taiwan, 0) = 0, '否', '是') as is_suit_taiwan,\n"
				+ "	if(ifnull(c.is_suit_threaten, 0) = 0, '否', '是') as is_suit_threaten,\n"
				+ "	if(ifnull(c.is_suit_wade, 0) = 0, '否', '是') as is_suit_wade,\n" + "	'' as case_satisfied\n"
				+ "from fd_case c\n" + "left join fd_guest g on c.guest_id = g.ID";

		if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
			sql += "\n where date_format(c.gmt_create, '%Y-%m-%d') > '" + startTime + "'";
		}
		if (!StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
			sql += "\n where date_format(c.gmt_create, '%Y-%m-%d') < '" + endTime + "'";
		}
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			sql += "\n where date_format(c.gmt_create, '%Y-%m-%d') between '" + startTime + "' and '" + endTime + "'";
		}

		List<Map<String, Object>> result = null;
		try {
			result = jdbcTemplate.queryForList(sql);
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return result;
		}
		return result;
	}

}
