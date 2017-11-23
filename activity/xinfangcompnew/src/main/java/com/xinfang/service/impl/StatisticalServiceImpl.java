package com.xinfang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.activiti.engine.TaskService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.TsWindowPersonRepository;
import com.xinfang.log.ApiLog;
import com.xinfang.service.StatisticalService;

/**
 * Created by sunbjx on 2017/4/17.
 */
@Service("statisticalService")
public class StatisticalServiceImpl implements StatisticalService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EntityManager em;
	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Autowired
	private TsWindowPersonRepository tsWindowPersonRepository;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public List<Map<String, Object>> packaging(int authFlag, Integer userId, String startDate, String endDate,
			int handleType, int registerType) {

		// 案件处理类型
		String handleTypeSQL = "SELECT \n" + "    (SELECT \n" + "            COUNT(*)\n" + "        FROM\n"
				+ "            fd_case\n" + "        WHERE\n" + "            handle_type = 1) AS assign,\n"
				+ "    (SELECT \n" + "            COUNT(*)\n" + "        FROM\n" + "            fd_case\n"
				+ "        WHERE\n" + "            handle_type = 2) AS turn,\n" + "    (SELECT \n"
				+ "            COUNT(*)\n" + "        FROM\n" + "            fd_case\n" + "        WHERE\n"
				+ "            handle_type = 3) AS allon,\n" + "    (SELECT \n" + "            COUNT(*)\n"
				+ "        FROM\n" + "            fd_case\n" + "        WHERE\n"
				+ "            handle_type = 4) AS reply,\n" + "    (SELECT \n" + "            COUNT(*)\n"
				+ "        FROM\n" + "            fd_case\n" + "        WHERE\n"
				+ "            handle_type = 5) AS refuse,\n" + "    (SELECT \n" + "            COUNT(*)\n"
				+ "        FROM\n" + "            fd_case\n" + "        WHERE\n"
				+ "            handle_type = 6) AS nolonger,\n" + "    (SELECT \n" + "            COUNT(*)\n"
				+ "        FROM\n" + "            fd_case\n" + "        WHERE\n"
				+ "            handle_type = 7) AS save,\n" + "    (SELECT \n" + "            COUNT(*)\n"
				+ "        FROM\n" + "            fd_case\n" + "        WHERE\n"
				+ "            handle_type = 11) AS audit\n" + "FROM\n" + "    fd_case c\n" + "WHERE\n"
				+ "    ((SELECT \n" + "            COUNT(*)\n" + "        FROM\n" + "            FD_CASE_FEEDBACK\n"
				+ "        WHERE\n" + "            CASE_ID = c.id AND CREATER_ID = " + userId + ") <> 0\n"
				+ "        OR c.current_handle_personalid = " + userId + "\n" + "        OR window_number = 0)\n"
				+ "        AND handle_type NOT IN (2001 , 2002, 2003)\n"
				+ "        AND IFNULL(c.gmt_create, '') <> ''\n";

		// 案件相关情况
		class CommonSQL {

			private String strHandleDeadLineSQL(String handleDeadLineCommonSQL) {
				String strSQL = "SELECT cast(concat((\n" + handleDeadLineCommonSQL
						+ "                          AND ifnull(case_handle_state, 0) <> 2003 AND NOW() > handle_period_end), ',',\n"
						+ "\n" + "                   (\n" + handleDeadLineCommonSQL
						+ "                          AND ifnull(case_handle_state, 0) = 2003 AND c.handle_fact_end_time > handle_period_end), ',',\n"
						+ "\n" + "                   ((\n" + handleDeadLineCommonSQL
						+ "                           AND ifnull(case_handle_state, 0) <> 2003 AND\n"
						+ "                           NOW() > date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY)) +\n"
						+ "                    (\n" + handleDeadLineCommonSQL
						+ "                           AND ifnull(case_handle_state, 0) = 2003 AND\n"
						+ "                           handle_fact_end_time > date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY))), ',',\n"
						+ "\n" + "                   (\n" + handleDeadLineCommonSQL
						+ "                          AND c.handle_userid = 1829 AND ifnull(c.is_flow, '') = '' AND c.is_handle != 2), ',',\n"
						+ "\n" + "                   (\n" + handleDeadLineCommonSQL
						+ "                          AND c.case_handle_state != 2003\n"
						+ "                          AND (SELECT count(*)\n"
						+ "                               FROM act_hi_taskinst\n"
						+ "                               WHERE ASSIGNEE_ = 1829 AND ifnull(END_TIME_, '') = ''\n"
						+ "                                     AND PROC_INST_ID_ = (SELECT sum(ifnull(PROC_INST_ID_, 0))\n"
						+ "                                                          FROM act_hi_varinst\n"
						+ "                                                          WHERE NAME_ = 'caseid' AND LONG_ = c.id))), ',',\n"
						+ "                   (\n" + handleDeadLineCommonSQL
						+ "                          AND ifnull(case_handle_state, 0) = 2003 AND handle_fact_end_time < handle_period_end)) AS\n"
						+ "            CHAR) AS countStr";

				return strSQL;
			}

			private String strHandleRelatedSQL(String whatDay, int dayOfWeek, String asType, String whereTime) {

				String strSQL = "select * from \n" + "(SELECT \n" + "\t'" + whatDay + "'\tas what_day,\n" + "    \n"
						+ "    (select count(*) from fd_case where is_handle = 1 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as register,\n" + "    \n"
						+ "\t(select count(*) from fd_case where is_handle = 2 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as handle,\n" + "\n"
						+ "\t(select count(*) from fd_case where case_handle_state = 2003 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as end_close,\n" + "\n"
						+ "    (select count(*) from fd_case where handle_type = 8 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as back,\n" + "\t\n"
						+ "\t(select count(*) from fd_case where handle_type = 4 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as reply,\n" + "\t\n"
						+ "\t(select count(*) from fd_case where handle_type = 5 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as no_accept,\n" + "\n"
						+ "    (select count(*) from fd_case where handle_type = 6 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as no_longer,\n" + "\n"
						+ "    (select count(*) from fd_case where handle_type = 7 and (SELECT  dayofweek(gmt_create) = "
						+ dayOfWeek + ")) as save\n" + "FROM\n" + "    fd_case c\n" + "WHERE\n"
						+ "    ((SELECT COUNT(*) FROM FD_CASE_FEEDBACK WHERE CASE_ID = c.id AND CREATER_ID = 1829) <> 0 OR c.current_handle_personalid = 1829 OR window_number = 0)\n"
						+ whereTime + "    limit 0, 1) as " + asType;

				return strSQL;
			}

			private String strTimeProgressSQL(String a, String b, String whereTime) {
				String strSQL = "SELECT count(*)\n" + "            FROM fd_case c\n"
						+ "            WHERE ((SELECT count(*)\n" + "                    FROM FD_CASE_FEEDBACK\n"
						+ "                    WHERE CASE_ID = c.id AND CREATER_ID = " + userId + ") <> 0\n"
						+ "                   OR c.current_handle_personalid = " + userId + " OR window_number = 0)\n"
						+ whereTime
						+ "                  AND (NOW() >= date_add(c.gmt_create, INTERVAL floor(handle_days * " + a
						+ ") DAY) AND\n"
						+ "                       NOW() <= date_add(c.gmt_create, INTERVAL floor(handle_days * " + b
						+ ") DAY)\n" + "                       AND ifnull(case_handle_state, 0) <> 2003\n"
						+ "                       OR\n"
						+ "                       handle_fact_end_time >= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ a + ") DAY)\n" + "                       AND\n"
						+ "                       handle_fact_end_time <= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ b + ") DAY)\n" + "                       AND ifnull(case_handle_state, 0) = 2003)";

				return strSQL;
			}

			private String strRegisterSQL(String field, String whereTime) {
				String strSQL = "SELECT \n" + "    (SELECT \n" + "            name\n" + "        FROM\n"
						+ "            fd_code\n" + "        WHERE\n" + "            code = " + field + ") AS type,\n"
						+ "    COUNT(" + field + ") AS nums\n" + "FROM\n" + "    fd_case c\n" + "WHERE\n"
						+ "    ((SELECT \n" + "            COUNT(*)\n" + "        FROM\n"
						+ "            FD_CASE_FEEDBACK\n" + "        WHERE\n"
						+ "            CASE_ID = c.id AND CREATER_ID = " + userId + ") <> 0\n"
						+ "        OR c.current_handle_personalid = " + userId + "\n"
						+ "        OR window_number = 0)\n" + "        AND IFNULL(" + field + ", 0) != 0\n" + whereTime
						+ "GROUP BY " + field;

				return strSQL;
			}
		}

		// 案件处理状态
		String handleStateSQL = "";

		// 登记时间
		String gmtCreateStartSQL = " AND date_format(c.gmt_create, '%Y-%m-%d') >= " + startDate;
		String gmtCreateEndSQL = " AND date_format(c.gmt_create, '%Y-%m-%d') <= " + endDate;
		String gmtCreateBetweenSQL = " AND date_format(c.gmt_create, '%Y-%m-%d') BETWEEN '" + startDate + "'" + " AND '"
				+ endDate + "'";
		String sql = "";

		if (handleType == 1) {
			// 案件办理期限监控
			String handleDeadLineCommonSQL = "SELECT count(*)\n" + "FROM fd_case c\n" + "WHERE ((SELECT count(*)\n"
					+ "        FROM FD_CASE_FEEDBACK\n" + "        WHERE CASE_ID = c.id AND CREATER_ID = " + userId
					+ ") <> 0\n" + "       OR c.current_handle_personalid = " + userId + " OR window_number = 0)\n";

			if (!StringUtils.isEmpty(startDate)) {
				handleDeadLineCommonSQL += gmtCreateStartSQL;
			}
			if (!StringUtils.isEmpty(endDate)) {
				handleDeadLineCommonSQL += gmtCreateEndSQL;
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				handleDeadLineCommonSQL += gmtCreateBetweenSQL;
			}

			sql = new CommonSQL().strHandleDeadLineSQL(handleDeadLineCommonSQL);
		}
		if (handleType == 2) {
			sql = handleTypeSQL + "\nLIMIT 0 , 1";
			if (!StringUtils.isEmpty(startDate)) {
				sql = handleTypeSQL + gmtCreateStartSQL + "\nLIMIT 0 , 1";
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = handleTypeSQL + gmtCreateEndSQL + "\nLIMIT 0 , 1";
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = handleTypeSQL + gmtCreateBetweenSQL + "\nLIMIT 0 , 1";
			}
		}
		if (handleType == 3) {
			String handleRelatedSQL = new CommonSQL().strHandleRelatedSQL("周一", 2, "a", "") + "  union all \n";
			handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周二", 3, "b", "") + "  union all \n";
			handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周三", 4, "c", "") + "  union all \n";
			handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周四", 5, "d", "") + "  union all \n";
			handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周五", 6, "e", "") + "  union all \n";
			handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周六", 7, "f", "") + "  union all \n";
			handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周日", 1, "g", "");
			if (!StringUtils.isEmpty(startDate)) {
				handleRelatedSQL = new CommonSQL().strHandleRelatedSQL("周一", 2, "a", gmtCreateStartSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周二", 3, "b", gmtCreateStartSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周三", 4, "c", gmtCreateStartSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周四", 5, "d", gmtCreateStartSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周五", 6, "e", gmtCreateStartSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周六", 7, "f", gmtCreateStartSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周日", 1, "g", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				handleRelatedSQL = new CommonSQL().strHandleRelatedSQL("周一", 2, "a", gmtCreateEndSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周二", 3, "b", gmtCreateEndSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周三", 4, "c", gmtCreateEndSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周四", 5, "d", gmtCreateEndSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周五", 6, "e", gmtCreateEndSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周六", 7, "f", gmtCreateEndSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周日", 1, "g", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				handleRelatedSQL = new CommonSQL().strHandleRelatedSQL("周一", 2, "a", gmtCreateBetweenSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周二", 3, "b", gmtCreateBetweenSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周三", 4, "c", gmtCreateBetweenSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周四", 5, "d", gmtCreateBetweenSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周五", 6, "e", gmtCreateBetweenSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周六", 7, "f", gmtCreateBetweenSQL)
						+ "  union all \n";
				handleRelatedSQL += new CommonSQL().strHandleRelatedSQL("周日", 1, "g", gmtCreateBetweenSQL);
			}
			sql = handleRelatedSQL;
		}
		if (handleType == 4) {
			if (!StringUtils.isEmpty(startDate)) {

			}
			if (!StringUtils.isEmpty(endDate)) {

			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {

			}
			sql = handleStateSQL;
		}
		if (handleType == 5) {
			// 案件时间监控
			String handleTimeMonitoringSQL = "SELECT cast(concat(("
					+ new CommonSQL().strTimeProgressSQL("1.0", "0.5", "") + "), ',',\n" + "                       ("
					+ new CommonSQL().strTimeProgressSQL("0.5", "0.75", "") + "), ',',\n" + "                       ("
					+ new CommonSQL().strTimeProgressSQL("0.75", "0.875", "") + "), ',',\n" + "                       ("
					+ new CommonSQL().strTimeProgressSQL("0.875", "1", "") + "), ',',\n" + "                       ("
					+ new CommonSQL().strTimeProgressSQL("1", "100", "") + ")) AS char) AS strCount";
			if (!StringUtils.isEmpty(startDate)) {
				handleTimeMonitoringSQL = "SELECT cast(concat(("
						+ new CommonSQL().strTimeProgressSQL("1.0", "0.5", gmtCreateStartSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.5", "0.75", gmtCreateStartSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.75", "0.875", gmtCreateStartSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.875", "1", gmtCreateStartSQL) + "), ',',\n"
						+ "                       (" + new CommonSQL().strTimeProgressSQL("1", "100", gmtCreateStartSQL)
						+ ")) AS char) AS strCount";
			}
			if (!StringUtils.isEmpty(endDate)) {
				handleTimeMonitoringSQL = "SELECT cast(concat(("
						+ new CommonSQL().strTimeProgressSQL("1.0", "0.5", gmtCreateEndSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.5", "0.75", gmtCreateEndSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.75", "0.875", gmtCreateEndSQL) + "), ',',\n"
						+ "                       (" + new CommonSQL().strTimeProgressSQL("0.875", "1", gmtCreateEndSQL)
						+ "), ',',\n" + "                       ("
						+ new CommonSQL().strTimeProgressSQL("1", "100", gmtCreateEndSQL) + ")) AS char) AS strCount";
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				handleTimeMonitoringSQL = "SELECT cast(concat(("
						+ new CommonSQL().strTimeProgressSQL("1.0", "0.5", gmtCreateBetweenSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.5", "0.75", gmtCreateBetweenSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.75", "0.875", gmtCreateBetweenSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("0.875", "1", gmtCreateBetweenSQL) + "), ',',\n"
						+ "                       ("
						+ new CommonSQL().strTimeProgressSQL("1", "100", gmtCreateBetweenSQL)
						+ ")) AS char) AS strCount";
			}
			sql = handleTimeMonitoringSQL;
		}
		if (handleType == 6) {
			sql = new CommonSQL().strRegisterSQL(" \tc.question_belonging_to\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_belonging_to\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_belonging_to\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_belonging_to\t ", gmtCreateBetweenSQL);
			}

		}

		// 登记-信访方式
		if (registerType == 1) {
			sql = new CommonSQL().strRegisterSQL(" \tc.petition_way\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_way\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_way\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_way\t ", gmtCreateBetweenSQL);
			}
		}
		// 登记-信访性质
		if (registerType == 2) {
			sql = new CommonSQL().strRegisterSQL(" \tc.petition_nature\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_nature\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_nature\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_nature\t ", gmtCreateBetweenSQL);
			}
		}
		// 登记-13大类
		if (registerType == 3) {
			sql = new CommonSQL().strRegisterSQL(" \tc.thirteen_categories\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.thirteen_categories\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.thirteen_categories\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.thirteen_categories\t ", gmtCreateBetweenSQL);
			}

		}
		// 登记-信访原因
		if (registerType == 4) {
			sql = new CommonSQL().strRegisterSQL(" \tc.petition_why\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_why\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_why\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_why\t ", gmtCreateBetweenSQL);
			}

		}
		// 登记-信访目的
		if (registerType == 5) {
			sql = new CommonSQL().strRegisterSQL(" \tc.petition_purpose\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_purpose\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_purpose\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.petition_purpose\t ", gmtCreateBetweenSQL);
			}

		}
		// 登记-热点问题
		if (registerType == 6) {
			sql = new CommonSQL().strRegisterSQL(" \tc.question_hot\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_hot\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_hot\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_hot\t ", gmtCreateBetweenSQL);
			}

		}
		// 登记-内容分类
		if (registerType == 7) {
			sql = new CommonSQL().strRegisterSQL(" \tc.question_type\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_type\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_type\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.question_type\t ", gmtCreateBetweenSQL);
			}
		}
		// 登记-所属系统
		if (registerType == 8) {
			sql = new CommonSQL().strRegisterSQL(" \tc.belong_to_sys\t ", "");

			if (!StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.belong_to_sys\t ", gmtCreateStartSQL);
			}
			if (!StringUtils.isEmpty(endDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.belong_to_sys\t ", gmtCreateEndSQL);
			}
			if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
				sql = new CommonSQL().strRegisterSQL(" \tc.belong_to_sys\t ", gmtCreateBetweenSQL);
			}

		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (!StringUtils.isEmpty(sql)) {
				list = jdbcTemplate.queryForList(sql);
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return list;
		}
		return list;
	}

	@Override
	public Map<String, Object> typeNums(Integer userId) {
		int a = 1;
		int b = 0;

		// int windowNumber = 0;
		Integer windowId = tsWindowPersonRepository.getWindowByUserId(userId);

		String windowNumber = windowId != null && windowId != 0 ? "  or window_number in (" + windowId + ")  " : "";
		// 收文岗
		long dispatches = taskService.createTaskQuery().taskCandidateOrAssigned(userId.toString()).count();

		// String strDis = "'" + "/%收文岗/%" + "'";
		StringBuffer sb = new StringBuffer();
		sb.append("'%").append("收文岗").append("%'");
		String strDis = sb.toString();
		// 表示窗口人员录入在待办中可以看到分流室录入的
		// Integer windowId =
		// tsWindowPersonRepository.getWindowByUserId(userId);
		// List<FdCase> listFdCase2 =
		// fdCaseRepository.getCaseByWindowNumber(windowId);
		// if (listFdCase2 != null && listFdCase2.size() > 0) {
		// windowNumber = windowId;
		// currentTodo += " or window_number = " + windowNumber;
		// }

		String sql = getsql(strDis, userId.toString(), windowNumber);

		System.out.println(sql);

		// String key = "homeCountTag:" + userId;
		// HashOperations<String, Object, Object> hash =
		// redisTemplate.opsForHash();

		try {
			// boolean exists = redisTemplate.hasKey(key);
			// // 如果有数据则直接返回缓存的数据
			// if (exists) {
			// return (Map) hash.entries(key);
			// }

			// 缓存,到后端查询数据
			Query query = em.createNativeQuery(sql);
			String result = null;
			try {
				result = query.getSingleResult().toString();
			} catch (Exception e) {
				ApiLog.chargeLog1(e.getMessage());
				return null;
			} finally {
				em.close();
			}

			String[] numbers = result.split(",");
			Map<String, Object> map = new HashedMap();
			map.put("overdueNoFinish", numbers[0].toString());
			map.put("timeLimitMoreThanHalf", numbers[1].toString());
			map.put("didNotDeal", numbers[2].toString());
			map.put("dealWithIn", numbers[3].toString());
			map.put("overdueFinish", numbers[4].toString());
			map.put("onTimeFinish", numbers[5].toString());
			map.put("todo", numbers[6].toString());
			// map.put("isTodo", numbers[7]);
			// map.put("hasTodo", numbers[8]);
			map.put("isTodo", "0");
			map.put("hasTodo", "0");
			map.put("dispatch", numbers[7].toString());
			map.put("hasBack", numbers[8].toString());

			// hash.putAll(key, map);
			// 设置实效
			// Calendar rightNow = Calendar.getInstance();
			// rightNow.setTime(new Date());
			// rightNow.add(Calendar.SECOND, 60);
			// Date dt = rightNow.getTime();
			// redisTemplate.opsForHash().getOperations().expireAt(key, dt);

			// redisTemplate.opsForHash().getOperations().expire(key, 5,
			// TimeUnit.MINUTES);

			return map;

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
	}

	private String getsql(String strDis, String string, String windowNumber) {
		String sql = "\n" + "SELECT \n" + "    CAST(CONCAT(SUM(YQWJ),\n" + "                ',',\n"
				+ "                SUM(QXGB),\n" + "                ',',\n" + "                SUM(WBL),\n"
				+ "                ',',\n" + "                SUM(BLZ),\n" + "                ',',\n"
				+ "                SUM(YQBJ),\n" + "                ',',\n" + "                SUM(AQBJ),\n"
				+ "                ',',\n" + "                SUM(DB),\n" + "                ',',\n"
				+ "                SUM(SWG),\n" + "                ',',\n" + "                SUM(YTH))\n"
				+ "        AS CHAR) AS countStr\n" + "FROM\n" + "    (SELECT \n" + "        form,\n"
				+ "            petition_names,\n" + "            fd_case.ID,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > handle_period_end\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END YQWJ,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(gmt_create, INTERVAL FLOOR(handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END QXGB,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(window_number, 0) = 0\n"
				+ "                        AND IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND IFNULL(is_flow, 0) = 0\n" + "                        AND is_handle = 0\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(window_number, 0) <> 0\n"
				+ "                        AND IFNULL(handle_userid, 0) = 0\n"
				+ "                        AND IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND IFNULL(is_flow, 0) = 0\n"
				+ "                        AND is_handle <> 2\n" + "                THEN\n" + "                    1\n"
				+ "                ELSE 0\n" + "            END WBL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(case_handle_state, 0) = 2001\n"
				+ "                        AND is_handle = 1\n" + "                THEN\n" + "                    1\n"
				+ "                WHEN\n" + "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND is_handle = 2\n" + "                THEN\n" + "                    1\n"
				+ "                ELSE 0\n" + "            END BLZ,\n" + "            case_handle_state,\n"
				+ "            is_handle,\n" + "            is_flow,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(case_handle_state, 0) = 2003\n"
				+ "                        AND handle_fact_end_time > handle_period_end\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END YQBJ,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(case_handle_state, 0) = 2003\n"
				+ "                        AND handle_fact_end_time <= handle_period_end\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END AQBJ,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND creator_id= (" + string + ") AND form = 6\n"
				+ "                        AND IFNULL(window_number, 0) = 0\n"
				+ "                        AND IFNULL(handle_userid, 0) = 0\n"
				+ "                        AND IFNULL(is_flow, '') = ''\n"
				+ "                        AND is_handle != 2\n" + "                THEN\n" + "                    1\n"
				+ "                WHEN\n" + "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND handle_userid = (" + string + ")\n"
				+ "                        AND IFNULL(is_flow, '') = ''\n"
				+ "                        AND is_handle != 2\n" + "                THEN\n" + "                    1\n"
				+ "                WHEN\n" + "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND (SELECT \n" + "                            COUNT(id)\n"
				+ "                        FROM\n" + "                            FD_CASE_FEEDBACK\n"
				+ "                        WHERE\n"
				+ "                            CASE_ID = fd_case.id AND type = 102\n"
				+ "                                AND CREATER_ID IN (" + string + ")) = 0\n"
				+ "                        AND IFNULL(handle_userid, 0) = 0\n"
				+ "                        AND IFNULL(is_flow, '') = ''\n"
				+ "                        AND is_handle != 2\n" + "                THEN\n" + "                    1\n"
				+ "                WHEN\n" + "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            act_hi_taskinst\n"
				+ "                        WHERE\n" + "                            ASSIGNEE_ IN (" + string + ")\n"
				+ "                                AND NAME_ NOT LIKE " + strDis + "\n"
				+ "                                AND IFNULL(END_TIME_, '') = ''\n"
				+ "                                AND PROC_INST_ID_ = (SELECT \n"
				+ "                                    MAX(IFNULL(PROC_INST_ID_, 0))\n"
				+ "                                FROM\n" + "                                    act_hi_varinst\n"
				+ "                                WHERE\n"
				+ "                                    NAME_ = 'caseid' AND LONG_ = fd_case.id)) > 0\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END DB,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND IFNULL(is_flow, '') = ''\n"
				+ "                        AND is_handle != 2\n"
				+ "                        AND IFNULL(is_dispatcher_receive, 0) = 0\n"
				+ "                        AND IFNULL(dispatcher_to_userid, 0) = " + string + "\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(case_handle_state, 0) <> 2003\n"
				+ "                        AND (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            act_hi_taskinst\n"
				+ "                        WHERE\n" + "                            ASSIGNEE_ IN (" + string + ")\n"
				+ "                                AND NAME_ LIKE " + strDis + "\n"
				+ "                                AND IFNULL(END_TIME_, '') = ''\n"
				+ "                                AND PROC_INST_ID_ = (SELECT \n"
				+ "                                    MAX(IFNULL(CAST(PROC_INST_ID_ AS SIGNED), 0))\n"
				+ "                                FROM\n" + "                                    act_hi_varinst\n"
				+ "                                WHERE\n"
				+ "                                    NAME_ = 'caseid' AND LONG_ = fd_case.id)) > 0\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END SWG,\n" + "            0 YTH,\n" + "            window_number,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FD_CASE_FEEDBACK\n" + "                WHERE\n"
				+ "                    CASE_ID = fd_case.id\n" + "                        AND CREATER_ID IN (" + string
				+ ")) AS 流程中是否出现,\n" + "            creator_id,\n" + "            current_handle_personalid AS 当前处理人,\n"
				+ "            0 AS ce\n" + "    FROM\n" + "        fd_case\n" + "    WHERE\n"
				+ "        1 = 1 and state <> -1) dd\n" + "WHERE\n" + "    1 = 1\n" + "        AND (流程中是否出现 > 0\n"
				+ "        OR 当前处理人 IN (" + string + ") " + windowNumber + " )\n" + "GROUP BY ce";
		return sql;
	}

	@Override
	public List<Map<String, Object>> dailyReport(String startTime, String endTime, String keywords) {
		String sql = "\n" + "SELECT \n" + "    CASE\n" + "        WHEN ct = 0 THEN 0\n"
				+ "        ELSE ROUND(BJ_XJ_JC / ct * 100, 2)\n" + "    END BJ_BJL,\n" + "    CASE\n"
				+ "        WHEN BJ_XJ_JC = 0 THEN 0\n" + "        ELSE ROUND(BJ_GF_JC / BJ_XJ_JC * 100, 2)\n"
				+ "    END BJ_GF_BJL,\n" + "    CASE\n" + "        WHEN BJ_XJ_JC = 0 THEN 0\n"
				+ "        ELSE ROUND(BJ_JT_JC / BJ_XJ_JC * 100, 2)\n" + "    END BJ_JT_BJL,\n" + "    CASE\n"
				+ "        WHEN XFMY_YES + XFMY_NO = 0 THEN ''\n"
				+ "        ELSE CAST(ROUND(XFMY_YES / (XFMY_YES + XFMY_NO) * 100, 2)\n" + "            AS CHAR)\n"
				+ "    END XFMY_MYL,\n" + "    CASE\n" + "        WHEN ZRMY_YES + ZRMY_NO = 0 THEN ''\n"
				+ "        ELSE CAST(ROUND(ZRMY_YES / (ZRMY_YES + ZRMY_NO) * 100, 2)\n" + "            AS CHAR)\n"
				+ "    END ZRMY_MYL,\n" + "    CASE\n" + "        WHEN JGMY_YES + JGMY_NO = 0 THEN ''\n"
				+ "        ELSE CAST(ROUND(JGMY_YES / (JGMY_YES + JGMY_NO) * 100, 2)\n" + "            AS CHAR)\n"
				+ "    END JGMY_MYL,\n" + "    CASE\n" + "        WHEN CP_YES + CP_NO = 0 THEN ''\n"
				+ "        ELSE CAST(ROUND(CP_YES / (CP_YES + CP_NO) * 100, 2)\n" + "            AS CHAR)\n"
				+ "    END CP_CPL,\n" + "    CASE\n" + "        WHEN JSSL_YES + JSSL_NO = 0 THEN ''\n"
				+ "        ELSE CAST(ROUND(JSSL_YES / (JSSL_YES + JSSL_NO) * 100, 2)\n" + "            AS CHAR)\n"
				+ "    END JSSL_JSL,\n" + "    CASE\n" + "        WHEN ZRJSSL_YES + ZRJSSL_NO = 0 THEN ''\n"
				+ "        ELSE CAST(ROUND(ZRJSSL_YES / (ZRJSSL_YES + ZRJSSL_NO) * 100,\n" + "                    2)\n"
				+ "            AS CHAR)\n" + "    END ZRJSSL_JSL,\n" + "    ff.*\n" + "FROM\n" + "    (SELECT \n"
				+ "        @rowid:=@rowid + 1 AS blmc, ee.*\n" + "    FROM\n" + "        (SELECT \n"
				+ "        30 AS xh1,\n" + "            window_number,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(window_name), '')\n" + "                FROM\n"
				+ "                    ts_window\n" + "                WHERE\n"
				+ "                    org_id = 761 AND id = DD.window_number) xmmc,\n" + "            SUM(ct) AS ct,\n"
				+ "            SUM(petition_numbers) AS petition_numbers,\n" + "            SUM(CFXJ) AS CFXJ,\n"
				+ "            SUM(CFRC) AS CFRC,\n" + "            SUM(CF_GF_JC) AS CF_GF_JC,\n"
				+ "            SUM(CF_GF_RC) AS CF_GF_RC,\n" + "            SUM(CF_JT_JC) AS CF_JT_JC,\n"
				+ "            SUM(CF_JT_RC) AS CF_JT_RC,\n" + "            SUM(FFXJ) AS FFXJ,\n"
				+ "            SUM(FFRC) AS FFRC,\n" + "            SUM(FF_GF_JC) AS FF_GF_JC,\n"
				+ "            SUM(FF_GF_RC) AS FF_GF_RC,\n" + "            SUM(FF_JT_JC) AS FF_JT_JC,\n"
				+ "            SUM(FF_JT_RC) AS FF_JT_RC,\n" + "            SUM(YJQK_ZC) AS YJQK_ZC,\n"
				+ "            SUM(YJQK_50) AS YJQK_50,\n" + "            SUM(YJQK_75) AS YJQK_75,\n"
				+ "            SUM(YJQK_100) AS YJQK_100,\n" + "            SUM(BJ_XJ_JC) AS BJ_XJ_JC,\n"
				+ "            SUM(BJ_XJ_RC) AS BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            SUM(BJ_CQ) AS BJ_CQ,\n" + "            SUM(BJ_GF_JC) AS BJ_GF_JC,\n"
				+ "            SUM(BJ_GF_RC) AS BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n"
				+ "            SUM(BJ_GF_CQ) AS BJ_GF_CQ,\n" + "            SUM(BJ_JT_JC) AS BJ_JT_JC,\n"
				+ "            SUM(BJ_JT_RC) AS BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n"
				+ "            SUM(BJ_JT_CQ) AS BJ_JT_CQ,\n" + "            SUM(XFMY_YES) AS XFMY_YES,\n"
				+ "            SUM(XFMY_NO) AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            SUM(ZRMY_YES) AS ZRMY_YES,\n" + "            SUM(ZRMY_NO) AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            SUM(JGMY_YES) AS JGMY_YES,\n"
				+ "            SUM(JGMY_NO) AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            SUM(CP_YES) AS CP_YES,\n" + "            SUM(CP_NO) AS CP_NO,\n"
				+ "            0 AS CP_CPL,\n" + "            SUM(JSSL_YES) AS JSSL_YES,\n"
				+ "            SUM(JSSL_NO) AS JSSL_NO,\n" + "            0 AS JSSL_JSL,\n"
				+ "            SUM(ZRJSSL_YES) AS ZRJSSL_YES,\n" + "            SUM(ZRJSSL_NO) AS ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            JB\n" + "    FROM\n" + "        (SELECT \n"
				+ "        1 AS ct,\n" + "            A.ID AS ID,\n" + "            creator_id,\n"
				+ "            create_unitid,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.window_number, 0) = - 1 THEN 0\n"
				+ "                ELSE IFNULL(A.window_number, 0)\n" + "            END window_number,\n"
				+ "            IFNULL(A.petition_numbers, 0) AS petition_numbers,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN 1\n" + "                ELSE 0\n"
				+ "            END CFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END CFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_JT_RC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN 1\n" + "                ELSE 0\n"
				+ "            END FFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END FFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_JT_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_ZC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_50,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_75,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_100,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN 1\n" + "                ELSE 0\n"
				+ "            END BJ_XJ_JC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_GF_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_JT_CQ,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND department = '满意') AS XFMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND department = '不满意') AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND unit = '满意') AS ZRMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id AND unit = '不满意') AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '满意') AS JGMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '不满意') AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n" + "                        AND (IFNULL(unit, '') <> ''\n"
				+ "                        OR IFNULL(case_satisfied, '') <> ''\n"
				+ "                        OR IFNULL(department, '') <> '')) AS CP_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND (IFNULL(unit, '') = ''\n"
				+ "                        AND IFNULL(case_satisfied, '') = ''\n"
				+ "                        AND IFNULL(department, '') = '')) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            fd_dep_case\n"
				+ "                        WHERE\n" + "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            3 AS JB\n" + "    FROM\n" + "        fd_case A\n"
				+ "    WHERE\n" + "        1 = 1\n"
				+ "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime
				+ "', '%Y-%m-%d')\n" + "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') <= DATE_FORMAT('"
				+ endTime + "', '%Y-%m-%d')) DD\n" + "    WHERE\n" + "        create_unitid = 761\n"
				+ "            AND window_number <> 0\n" + "    GROUP BY window_number\n"
				+ "    ORDER BY ct DESC) ee, (SELECT @rowid:=0) AS init UNION SELECT \n"
				+ "        @rowid2:=@rowid2 + 1 AS blmc, ee.*\n" + "    FROM\n" + "        (SELECT \n"
				+ "        60 AS xh1,\n" + "            create_unitid AS window_number,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(dw_mc), '')\n" + "                FROM\n"
				+ "                    fc_dwb\n" + "                WHERE\n"
				+ "                    dw_id = DD.create_unitid) xmmc,\n" + "            SUM(ct) AS ct,\n"
				+ "            SUM(petition_numbers) AS petition_numbers,\n" + "            SUM(CFXJ) AS CFXJ,\n"
				+ "            SUM(CFRC) AS CFRC,\n" + "            SUM(CF_GF_JC) AS CF_GF_JC,\n"
				+ "            SUM(CF_GF_RC) AS CF_GF_RC,\n" + "            SUM(CF_JT_JC) AS CF_JT_JC,\n"
				+ "            SUM(CF_JT_RC) AS CF_JT_RC,\n" + "            SUM(FFXJ) AS FFXJ,\n"
				+ "            SUM(FFRC) AS FFRC,\n" + "            SUM(FF_GF_JC) AS FF_GF_JC,\n"
				+ "            SUM(FF_GF_RC) AS FF_GF_RC,\n" + "            SUM(FF_JT_JC) AS FF_JT_JC,\n"
				+ "            SUM(FF_JT_RC) AS FF_JT_RC,\n" + "            SUM(YJQK_ZC) AS YJQK_ZC,\n"
				+ "            SUM(YJQK_50) AS YJQK_50,\n" + "            SUM(YJQK_75) AS YJQK_75,\n"
				+ "            SUM(YJQK_100) AS YJQK_100,\n" + "            SUM(BJ_XJ_JC) AS BJ_XJ_JC,\n"
				+ "            SUM(BJ_XJ_RC) AS BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            SUM(BJ_CQ) AS BJ_CQ,\n" + "            SUM(BJ_GF_JC) AS BJ_GF_JC,\n"
				+ "            SUM(BJ_GF_RC) AS BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n"
				+ "            SUM(BJ_GF_CQ) AS BJ_GF_CQ,\n" + "            SUM(BJ_JT_JC) AS BJ_JT_JC,\n"
				+ "            SUM(BJ_JT_RC) AS BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n"
				+ "            SUM(BJ_JT_CQ) AS BJ_JT_CQ,\n" + "            SUM(XFMY_YES) AS XFMY_YES,\n"
				+ "            SUM(XFMY_NO) AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            SUM(ZRMY_YES) AS ZRMY_YES,\n" + "            SUM(ZRMY_NO) AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            SUM(JGMY_YES) AS JGMY_YES,\n"
				+ "            SUM(JGMY_NO) AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            SUM(CP_YES) AS CP_YES,\n" + "            SUM(CP_NO) AS CP_NO,\n"
				+ "            0 AS CP_CPL,\n" + "            SUM(JSSL_YES) AS JSSL_YES,\n"
				+ "            SUM(JSSL_NO) AS JSSL_NO,\n" + "            0 AS JSSL_JSL,\n"
				+ "            SUM(ZRJSSL_YES) AS ZRJSSL_YES,\n" + "            SUM(ZRJSSL_NO) AS ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            JB\n" + "    FROM\n" + "        (SELECT \n"
				+ "        1 AS ct,\n" + "            A.ID AS ID,\n" + "            creator_id,\n"
				+ "            create_unitid,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.window_number, 0) = - 1 THEN 0\n"
				+ "                ELSE IFNULL(A.window_number, 0)\n" + "            END window_number,\n"
				+ "            IFNULL(A.petition_numbers, 0) AS petition_numbers,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN 1\n" + "                ELSE 0\n"
				+ "            END CFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END CFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_JT_RC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN 1\n" + "                ELSE 0\n"
				+ "            END FFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END FFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_JT_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_ZC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_50,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_75,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_100,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN 1\n" + "                ELSE 0\n"
				+ "            END BJ_XJ_JC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_GF_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_JT_CQ,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND department = '满意') AS XFMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND department = '不满意') AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND unit = '满意') AS ZRMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id AND unit = '不满意') AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '满意') AS JGMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '不满意') AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n" + "                        AND (IFNULL(unit, '') <> ''\n"
				+ "                        OR IFNULL(case_satisfied, '') <> ''\n"
				+ "                        OR IFNULL(department, '') <> '')) AS CP_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND (IFNULL(unit, '') = ''\n"
				+ "                        AND IFNULL(case_satisfied, '') = ''\n"
				+ "                        AND IFNULL(department, '') = '')) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            fd_dep_case\n"
				+ "                        WHERE\n" + "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(org_grade_type), 0)\n" + "                FROM\n"
				+ "                    fc_dwb\n" + "                WHERE\n"
				+ "                    DW_ID = a.create_unitid) AS dwlb,\n" + "            3 AS JB\n" + "    FROM\n"
				+ "        fd_case A\n" + "    WHERE\n" + "        1 = 1\n"
				+ "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime
				+ "', '%Y-%m-%d')\n" + "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') <= DATE_FORMAT('"
				+ endTime + "', '%Y-%m-%d')) DD\n" + "    WHERE\n" + "        create_unitid <> 761 AND dwlb = 5\n"
				+ "    GROUP BY create_unitid\n"
				+ "    ORDER BY ct DESC) ee, (SELECT @rowid2:=0) AS init2 UNION SELECT \n"
				+ "        '二' AS blmc, ee.*\n" + "    FROM\n" + "        (SELECT \n" + "        40 AS xh1,\n"
				+ "            create_unitid AS window_number,\n" + "            '市信访其他办信' xmmc,\n"
				+ "            SUM(ct) AS ct,\n" + "            SUM(petition_numbers) AS petition_numbers,\n"
				+ "            SUM(CFXJ) AS CFXJ,\n" + "            SUM(CFRC) AS CFRC,\n"
				+ "            SUM(CF_GF_JC) AS CF_GF_JC,\n" + "            SUM(CF_GF_RC) AS CF_GF_RC,\n"
				+ "            SUM(CF_JT_JC) AS CF_JT_JC,\n" + "            SUM(CF_JT_RC) AS CF_JT_RC,\n"
				+ "            SUM(FFXJ) AS FFXJ,\n" + "            SUM(FFRC) AS FFRC,\n"
				+ "            SUM(FF_GF_JC) AS FF_GF_JC,\n" + "            SUM(FF_GF_RC) AS FF_GF_RC,\n"
				+ "            SUM(FF_JT_JC) AS FF_JT_JC,\n" + "            SUM(FF_JT_RC) AS FF_JT_RC,\n"
				+ "            SUM(YJQK_ZC) AS YJQK_ZC,\n" + "            SUM(YJQK_50) AS YJQK_50,\n"
				+ "            SUM(YJQK_75) AS YJQK_75,\n" + "            SUM(YJQK_100) AS YJQK_100,\n"
				+ "            SUM(BJ_XJ_JC) AS BJ_XJ_JC,\n" + "            SUM(BJ_XJ_RC) AS BJ_XJ_RC,\n"
				+ "            0 AS BJ_BJL,\n" + "            SUM(BJ_CQ) AS BJ_CQ,\n"
				+ "            SUM(BJ_GF_JC) AS BJ_GF_JC,\n" + "            SUM(BJ_GF_RC) AS BJ_GF_RC,\n"
				+ "            0 AS BJ_GF_BJL,\n" + "            SUM(BJ_GF_CQ) AS BJ_GF_CQ,\n"
				+ "            SUM(BJ_JT_JC) AS BJ_JT_JC,\n" + "            SUM(BJ_JT_RC) AS BJ_JT_RC,\n"
				+ "            0 AS BJ_JT_BJL,\n" + "            SUM(BJ_JT_CQ) AS BJ_JT_CQ,\n"
				+ "            SUM(XFMY_YES) AS XFMY_YES,\n" + "            SUM(XFMY_NO) AS XFMY_NO,\n"
				+ "            0 AS XFMY_MYL,\n" + "            SUM(ZRMY_YES) AS ZRMY_YES,\n"
				+ "            SUM(ZRMY_NO) AS ZRMY_NO,\n" + "            0 AS ZRMY_MYL,\n"
				+ "            SUM(JGMY_YES) AS JGMY_YES,\n" + "            SUM(JGMY_NO) AS JGMY_NO,\n"
				+ "            0 AS JGMY_MYL,\n" + "            SUM(CP_YES) AS CP_YES,\n"
				+ "            SUM(CP_NO) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            SUM(JSSL_YES) AS JSSL_YES,\n" + "            SUM(JSSL_NO) AS JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            SUM(ZRJSSL_YES) AS ZRJSSL_YES,\n"
				+ "            SUM(ZRJSSL_NO) AS ZRJSSL_NO,\n" + "            0 AS ZRJSSL_JSL,\n" + "            JB\n"
				+ "    FROM\n" + "        (SELECT \n" + "        1 AS ct,\n" + "            A.ID AS ID,\n"
				+ "            creator_id,\n" + "            create_unitid,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.window_number, 0) = - 1 THEN 0\n"
				+ "                ELSE IFNULL(A.window_number, 0)\n" + "            END window_number,\n"
				+ "            IFNULL(A.petition_numbers, 0) AS petition_numbers,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN 1\n" + "                ELSE 0\n"
				+ "            END CFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END CFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_JT_RC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN 1\n" + "                ELSE 0\n"
				+ "            END FFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END FFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_JT_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_ZC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_50,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_75,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_100,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN 1\n" + "                ELSE 0\n"
				+ "            END BJ_XJ_JC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_GF_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_JT_CQ,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND department = '满意') AS XFMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND department = '不满意') AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND unit = '满意') AS ZRMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id AND unit = '不满意') AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '满意') AS JGMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '不满意') AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n" + "                        AND (IFNULL(unit, '') <> ''\n"
				+ "                        OR IFNULL(case_satisfied, '') <> ''\n"
				+ "                        OR IFNULL(department, '') <> '')) AS CP_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND (IFNULL(unit, '') = ''\n"
				+ "                        AND IFNULL(case_satisfied, '') = ''\n"
				+ "                        AND IFNULL(department, '') = '')) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            fd_dep_case\n"
				+ "                        WHERE\n" + "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            3 AS JB\n" + "    FROM\n" + "        fd_case A\n"
				+ "    WHERE\n" + "        1 = 1\n"
				+ "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime
				+ "', '%Y-%m-%d')\n" + "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') <= DATE_FORMAT('"
				+ endTime + "', '%Y-%m-%d')) DD\n" + "    WHERE\n" + "        create_unitid = 761\n"
				+ "            AND IFNULL(window_number, 0) = 0\n" + "    GROUP BY JB\n"
				+ "    ORDER BY ct DESC) ee UNION SELECT \n" + "        '三' AS blmc, ee.*\n" + "    FROM\n"
				+ "        (SELECT \n" + "        50 AS xh1,\n" + "            create_unitid AS window_number,\n"
				+ "            '区县市信封局' xmmc,\n" + "            SUM(ct) AS ct,\n"
				+ "            SUM(petition_numbers) AS petition_numbers,\n" + "            SUM(CFXJ) AS CFXJ,\n"
				+ "            SUM(CFRC) AS CFRC,\n" + "            SUM(CF_GF_JC) AS CF_GF_JC,\n"
				+ "            SUM(CF_GF_RC) AS CF_GF_RC,\n" + "            SUM(CF_JT_JC) AS CF_JT_JC,\n"
				+ "            SUM(CF_JT_RC) AS CF_JT_RC,\n" + "            SUM(FFXJ) AS FFXJ,\n"
				+ "            SUM(FFRC) AS FFRC,\n" + "            SUM(FF_GF_JC) AS FF_GF_JC,\n"
				+ "            SUM(FF_GF_RC) AS FF_GF_RC,\n" + "            SUM(FF_JT_JC) AS FF_JT_JC,\n"
				+ "            SUM(FF_JT_RC) AS FF_JT_RC,\n" + "            SUM(YJQK_ZC) AS YJQK_ZC,\n"
				+ "            SUM(YJQK_50) AS YJQK_50,\n" + "            SUM(YJQK_75) AS YJQK_75,\n"
				+ "            SUM(YJQK_100) AS YJQK_100,\n" + "            SUM(BJ_XJ_JC) AS BJ_XJ_JC,\n"
				+ "            SUM(BJ_XJ_RC) AS BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            SUM(BJ_CQ) AS BJ_CQ,\n" + "            SUM(BJ_GF_JC) AS BJ_GF_JC,\n"
				+ "            SUM(BJ_GF_RC) AS BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n"
				+ "            SUM(BJ_GF_CQ) AS BJ_GF_CQ,\n" + "            SUM(BJ_JT_JC) AS BJ_JT_JC,\n"
				+ "            SUM(BJ_JT_RC) AS BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n"
				+ "            SUM(BJ_JT_CQ) AS BJ_JT_CQ,\n" + "            SUM(XFMY_YES) AS XFMY_YES,\n"
				+ "            SUM(XFMY_NO) AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            SUM(ZRMY_YES) AS ZRMY_YES,\n" + "            SUM(ZRMY_NO) AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            SUM(JGMY_YES) AS JGMY_YES,\n"
				+ "            SUM(JGMY_NO) AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            SUM(CP_YES) AS CP_YES,\n" + "            SUM(CP_NO) AS CP_NO,\n"
				+ "            0 AS CP_CPL,\n" + "            SUM(JSSL_YES) AS JSSL_YES,\n"
				+ "            SUM(JSSL_NO) AS JSSL_NO,\n" + "            0 AS JSSL_JSL,\n"
				+ "            SUM(ZRJSSL_YES) AS ZRJSSL_YES,\n" + "            SUM(ZRJSSL_NO) AS ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            JB\n" + "    FROM\n" + "        (SELECT \n"
				+ "        1 AS ct,\n" + "            A.ID AS ID,\n" + "            creator_id,\n"
				+ "            create_unitid,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.window_number, 0) = - 1 THEN 0\n"
				+ "                ELSE IFNULL(A.window_number, 0)\n" + "            END window_number,\n"
				+ "            IFNULL(A.petition_numbers, 0) AS petition_numbers,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN 1\n" + "                ELSE 0\n"
				+ "            END CFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END CFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_JT_RC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN 1\n" + "                ELSE 0\n"
				+ "            END FFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END FFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_JT_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_ZC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_50,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_75,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_100,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN 1\n" + "                ELSE 0\n"
				+ "            END BJ_XJ_JC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_GF_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_JT_CQ,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND department = '满意') AS XFMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND department = '不满意') AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND unit = '满意') AS ZRMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id AND unit = '不满意') AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '满意') AS JGMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '不满意') AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n" + "                        AND (IFNULL(unit, '') <> ''\n"
				+ "                        OR IFNULL(case_satisfied, '') <> ''\n"
				+ "                        OR IFNULL(department, '') <> '')) AS CP_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND (IFNULL(unit, '') = ''\n"
				+ "                        AND IFNULL(case_satisfied, '') = ''\n"
				+ "                        AND IFNULL(department, '') = '')) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            fd_dep_case\n"
				+ "                        WHERE\n" + "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(org_grade_type), 0)\n" + "                FROM\n"
				+ "                    fc_dwb\n" + "                WHERE\n"
				+ "                    DW_ID = a.create_unitid) AS dwlb,\n" + "            3 AS JB\n" + "    FROM\n"
				+ "        fd_case A\n" + "    WHERE\n" + "        1 = 1\n"
				+ "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime
				+ "', '%Y-%m-%d')\n" + "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') <= DATE_FORMAT('"
				+ endTime + "', '%Y-%m-%d')) DD\n" + "    WHERE\n" + "        create_unitid <> 761 AND dwlb = 5\n"
				+ "    GROUP BY JB\n" + "    ORDER BY ct DESC) ee UNION SELECT \n" + "        '一' AS blmc, ee.*\n"
				+ "    FROM\n" + "        (SELECT \n" + "        20 AS xh1,\n"
				+ "            create_unitid AS window_number,\n" + "            '中心窗口' xmmc,\n"
				+ "            SUM(ct) AS ct,\n" + "            SUM(petition_numbers) AS petition_numbers,\n"
				+ "            SUM(CFXJ) AS CFXJ,\n" + "            SUM(CFRC) AS CFRC,\n"
				+ "            SUM(CF_GF_JC) AS CF_GF_JC,\n" + "            SUM(CF_GF_RC) AS CF_GF_RC,\n"
				+ "            SUM(CF_JT_JC) AS CF_JT_JC,\n" + "            SUM(CF_JT_RC) AS CF_JT_RC,\n"
				+ "            SUM(FFXJ) AS FFXJ,\n" + "            SUM(FFRC) AS FFRC,\n"
				+ "            SUM(FF_GF_JC) AS FF_GF_JC,\n" + "            SUM(FF_GF_RC) AS FF_GF_RC,\n"
				+ "            SUM(FF_JT_JC) AS FF_JT_JC,\n" + "            SUM(FF_JT_RC) AS FF_JT_RC,\n"
				+ "            SUM(YJQK_ZC) AS YJQK_ZC,\n" + "            SUM(YJQK_50) AS YJQK_50,\n"
				+ "            SUM(YJQK_75) AS YJQK_75,\n" + "            SUM(YJQK_100) AS YJQK_100,\n"
				+ "            SUM(BJ_XJ_JC) AS BJ_XJ_JC,\n" + "            SUM(BJ_XJ_RC) AS BJ_XJ_RC,\n"
				+ "            0 AS BJ_BJL,\n" + "            SUM(BJ_CQ) AS BJ_CQ,\n"
				+ "            SUM(BJ_GF_JC) AS BJ_GF_JC,\n" + "            SUM(BJ_GF_RC) AS BJ_GF_RC,\n"
				+ "            0 AS BJ_GF_BJL,\n" + "            SUM(BJ_GF_CQ) AS BJ_GF_CQ,\n"
				+ "            SUM(BJ_JT_JC) AS BJ_JT_JC,\n" + "            SUM(BJ_JT_RC) AS BJ_JT_RC,\n"
				+ "            0 AS BJ_JT_BJL,\n" + "            SUM(BJ_JT_CQ) AS BJ_JT_CQ,\n"
				+ "            SUM(XFMY_YES) AS XFMY_YES,\n" + "            SUM(XFMY_NO) AS XFMY_NO,\n"
				+ "            0 AS XFMY_MYL,\n" + "            SUM(ZRMY_YES) AS ZRMY_YES,\n"
				+ "            SUM(ZRMY_NO) AS ZRMY_NO,\n" + "            0 AS ZRMY_MYL,\n"
				+ "            SUM(JGMY_YES) AS JGMY_YES,\n" + "            SUM(JGMY_NO) AS JGMY_NO,\n"
				+ "            0 AS JGMY_MYL,\n" + "            SUM(CP_YES) AS CP_YES,\n"
				+ "            SUM(CP_NO) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            SUM(JSSL_YES) AS JSSL_YES,\n" + "            SUM(JSSL_NO) AS JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            SUM(ZRJSSL_YES) AS ZRJSSL_YES,\n"
				+ "            SUM(ZRJSSL_NO) AS ZRJSSL_NO,\n" + "            0 AS ZRJSSL_JSL,\n" + "            JB\n"
				+ "    FROM\n" + "        (SELECT \n" + "        1 AS ct,\n" + "            A.ID AS ID,\n"
				+ "            creator_id,\n" + "            create_unitid,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.window_number, 0) = - 1 THEN 0\n"
				+ "                ELSE IFNULL(A.window_number, 0)\n" + "            END window_number,\n"
				+ "            IFNULL(A.petition_numbers, 0) AS petition_numbers,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN 1\n" + "                ELSE 0\n"
				+ "            END CFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END CFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_JT_RC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN 1\n" + "                ELSE 0\n"
				+ "            END FFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END FFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_JT_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_ZC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_50,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_75,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_100,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN 1\n" + "                ELSE 0\n"
				+ "            END BJ_XJ_JC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_GF_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_JT_CQ,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND department = '满意') AS XFMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND department = '不满意') AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND unit = '满意') AS ZRMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id AND unit = '不满意') AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '满意') AS JGMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '不满意') AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n" + "                        AND (IFNULL(unit, '') <> ''\n"
				+ "                        OR IFNULL(case_satisfied, '') <> ''\n"
				+ "                        OR IFNULL(department, '') <> '')) AS CP_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND (IFNULL(unit, '') = ''\n"
				+ "                        AND IFNULL(case_satisfied, '') = ''\n"
				+ "                        AND IFNULL(department, '') = '')) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            fd_dep_case\n"
				+ "                        WHERE\n" + "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(org_grade_type), 0)\n" + "                FROM\n"
				+ "                    fc_dwb\n" + "                WHERE\n"
				+ "                    DW_ID = a.create_unitid) AS dwlb,\n" + "            3 AS JB\n" + "    FROM\n"
				+ "        fd_case A\n" + "    WHERE\n" + "        1 = 1\n"
				+ "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime
				+ "', '%Y-%m-%d')\n" + "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') <= DATE_FORMAT('"
				+ endTime + "', '%Y-%m-%d')) DD\n" + "    WHERE\n" + "        create_unitid = 761\n"
				+ "            AND IFNULL(window_number, 0) <> 0\n" + "    GROUP BY JB\n"
				+ "    ORDER BY ct DESC) ee UNION SELECT \n" + "        '' AS blmc, ee.*\n" + "    FROM\n"
				+ "        (SELECT \n" + "        20 AS xh1,\n" + "            create_unitid AS window_number,\n"
				+ "            '总合计' xmmc,\n" + "            SUM(ct) AS ct,\n"
				+ "            SUM(petition_numbers) AS petition_numbers,\n" + "            SUM(CFXJ) AS CFXJ,\n"
				+ "            SUM(CFRC) AS CFRC,\n" + "            SUM(CF_GF_JC) AS CF_GF_JC,\n"
				+ "            SUM(CF_GF_RC) AS CF_GF_RC,\n" + "            SUM(CF_JT_JC) AS CF_JT_JC,\n"
				+ "            SUM(CF_JT_RC) AS CF_JT_RC,\n" + "            SUM(FFXJ) AS FFXJ,\n"
				+ "            SUM(FFRC) AS FFRC,\n" + "            SUM(FF_GF_JC) AS FF_GF_JC,\n"
				+ "            SUM(FF_GF_RC) AS FF_GF_RC,\n" + "            SUM(FF_JT_JC) AS FF_JT_JC,\n"
				+ "            SUM(FF_JT_RC) AS FF_JT_RC,\n" + "            SUM(YJQK_ZC) AS YJQK_ZC,\n"
				+ "            SUM(YJQK_50) AS YJQK_50,\n" + "            SUM(YJQK_75) AS YJQK_75,\n"
				+ "            SUM(YJQK_100) AS YJQK_100,\n" + "            SUM(BJ_XJ_JC) AS BJ_XJ_JC,\n"
				+ "            SUM(BJ_XJ_RC) AS BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            SUM(BJ_CQ) AS BJ_CQ,\n" + "            SUM(BJ_GF_JC) AS BJ_GF_JC,\n"
				+ "            SUM(BJ_GF_RC) AS BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n"
				+ "            SUM(BJ_GF_CQ) AS BJ_GF_CQ,\n" + "            SUM(BJ_JT_JC) AS BJ_JT_JC,\n"
				+ "            SUM(BJ_JT_RC) AS BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n"
				+ "            SUM(BJ_JT_CQ) AS BJ_JT_CQ,\n" + "            SUM(XFMY_YES) AS XFMY_YES,\n"
				+ "            SUM(XFMY_NO) AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            SUM(ZRMY_YES) AS ZRMY_YES,\n" + "            SUM(ZRMY_NO) AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            SUM(JGMY_YES) AS JGMY_YES,\n"
				+ "            SUM(JGMY_NO) AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            SUM(CP_YES) AS CP_YES,\n" + "            SUM(CP_NO) AS CP_NO,\n"
				+ "            0 AS CP_CPL,\n" + "            SUM(JSSL_YES) AS JSSL_YES,\n"
				+ "            SUM(JSSL_NO) AS JSSL_NO,\n" + "            0 AS JSSL_JSL,\n"
				+ "            SUM(ZRJSSL_YES) AS ZRJSSL_YES,\n" + "            SUM(ZRJSSL_NO) AS ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            JB\n" + "    FROM\n" + "        (SELECT \n"
				+ "        1 AS ct,\n" + "            A.ID AS ID,\n" + "            creator_id,\n"
				+ "            create_unitid,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.window_number, 0) = - 1 THEN 0\n"
				+ "                ELSE IFNULL(A.window_number, 0)\n" + "            END window_number,\n"
				+ "            IFNULL(A.petition_numbers, 0) AS petition_numbers,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN 1\n" + "                ELSE 0\n"
				+ "            END CFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END CFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END CF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END CF_JT_RC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN 1\n" + "                ELSE 0\n"
				+ "            END FFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END FFRC,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) <= 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_GF_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END FF_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.petition_numbers, 0) > 4\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END FF_JT_RC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() < DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_ZC,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_50,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_75,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END YJQK_100,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN 1\n" + "                ELSE 0\n"
				+ "            END BJ_XJ_JC,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN IFNULL(A.petition_numbers, 0)\n"
				+ "                ELSE 0\n" + "            END BJ_XJ_RC,\n" + "            0 AS BJ_BJL,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_GF_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_GF_RC,\n" + "            0 AS BJ_GF_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 0\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_GF_CQ,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END BJ_JT_JC,\n"
				+ "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    IFNULL(A.petition_numbers, 0)\n" + "                ELSE 0\n"
				+ "            END BJ_JT_RC,\n" + "            0 AS BJ_JT_BJL,\n" + "            CASE\n"
				+ "                WHEN\n" + "                    IFNULL(A.is_repeat_petition, 0) = 1\n"
				+ "                        AND IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    1\n" + "                ELSE 0\n"
				+ "            END BJ_JT_CQ,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND department = '满意') AS XFMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND department = '不满意') AS XFMY_NO,\n" + "            0 AS XFMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id AND unit = '满意') AS ZRMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id AND unit = '不满意') AS ZRMY_NO,\n"
				+ "            0 AS ZRMY_MYL,\n" + "            (SELECT \n" + "                    COUNT(*)\n"
				+ "                FROM\n" + "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '满意') AS JGMY_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND case_satisfied = '不满意') AS JGMY_NO,\n" + "            0 AS JGMY_MYL,\n"
				+ "            (SELECT \n" + "                    COUNT(*)\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id\n" + "                        AND (IFNULL(unit, '') <> ''\n"
				+ "                        OR IFNULL(case_satisfied, '') <> ''\n"
				+ "                        OR IFNULL(department, '') <> '')) AS CP_YES,\n" + "            (SELECT \n"
				+ "                    COUNT(*)\n" + "                FROM\n" + "                    FP_APPRAISE\n"
				+ "                WHERE\n" + "                    case_id = A.id\n"
				+ "                        AND (IFNULL(unit, '') = ''\n"
				+ "                        AND IFNULL(case_satisfied, '') = ''\n"
				+ "                        AND IFNULL(department, '') = '')) AS CP_NO,\n" + "            0 AS CP_CPL,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END JSSL_NO,\n"
				+ "            0 AS JSSL_JSL,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            fd_dep_case\n"
				+ "                        WHERE\n" + "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME > END_TIME) > 0\n" + "                THEN\n"
				+ "                    1\n" + "                ELSE 0\n" + "            END ZRJSSL_NO,\n"
				+ "            0 AS ZRJSSL_JSL,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(org_grade_type), 0)\n" + "                FROM\n"
				+ "                    fc_dwb\n" + "                WHERE\n"
				+ "                    DW_ID = a.create_unitid) AS dwlb,\n" + "            3 AS JB\n" + "    FROM\n"
				+ "        fd_case A\n" + "    WHERE\n" + "        1 = 1\n"
				+ "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime
				+ "', '%Y-%m-%d')\n" + "            AND DATE_FORMAT(A.gmt_create, '%Y-%m-%d') <= DATE_FORMAT('"
				+ endTime + "', '%Y-%m-%d')) DD\n" + "    WHERE\n" + "        dwlb = 5\n" + "    GROUP BY JB\n"
				+ "    ORDER BY ct DESC) ee) ff\n" + "ORDER BY xh1 , blmc";
		if (!StringUtils.isEmpty(keywords)) {
			sql += "";
		}
		try {
			List<Map<String, Object>> rList = jdbcTemplate.queryForList(sql);
			if (rList != null && rList.size() > 0) {
				return rList;
			}
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> dailyReportDetail(Integer createUnitId, Integer creatorId, String startTime,
			String endTime, String keywords) {

		String sql = "SELECT @rowid:=@rowid + 1 AS blmc,\n" + "    XFR_MC AS xmmc,\n" + "    ct,\n"
				+ "    petition_numbers,\n" + "    XFR_MC,\n" + "    XFR_SFZ,\n" + "    XFR_DH,\n" + "    XFR_HJD,\n"
				+ "    XFR_XJZD,\n" + "    case_desc,\n" + "    receive_unitid,\n" + "    sd,\n" + "    CFXJ,\n"
				+ "    FFXJ,\n" + "    BJ_XJ_JC,\n" + "    SFGF,\n" + "    SFJTF,\n" + "    HS_day,\n"
				+ "    YJQK_50,\n" + "    XFMY_YES,\n" + "    ZRMY_YES,\n" + "    JGMY_YES,\n" + "    JSSL_YES,\n"
				+ "    ZRJSSL_YES,\n" + "    id\n" + "FROM\n" + "    (SELECT \n" + "        1 AS ct,\n"
				+ "            A.ID AS ID,\n" + "            gmt_create,\n" + "            creator_id,\n"
				+ "            create_unitid,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.window_number, 0) = - 1 THEN 0\n"
				+ "                ELSE IFNULL(A.window_number, 0)\n" + "            END window_number,\n"
				+ "            IFNULL(A.petition_numbers, 0) AS petition_numbers,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(username), '')\n" + "                FROM\n"
				+ "                    fd_guest\n" + "                WHERE\n"
				+ "                    id = A.guest_id) AS XFR_MC,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(head_pic), '')\n" + "                FROM\n"
				+ "                    fd_guest\n" + "                WHERE\n"
				+ "                    id = A.guest_id) AS XFR_IMG,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(idcard), '')\n" + "                FROM\n"
				+ "                    fd_guest\n" + "                WHERE\n"
				+ "                    id = A.guest_id) AS XFR_SFZ,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(phone), '')\n" + "                FROM\n"
				+ "                    fd_guest\n" + "                WHERE\n"
				+ "                    id = A.guest_id) AS XFR_DH,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(census_register), '')\n" + "                FROM\n"
				+ "                    fd_guest\n" + "                WHERE\n"
				+ "                    id = A.guest_id) AS XFR_HJD,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(now_address), '')\n" + "                FROM\n"
				+ "                    fd_guest\n" + "                WHERE\n"
				+ "                    id = A.guest_id) AS XFR_XJZD,\n" + "            case_desc,\n"
				+ "            receive_unitid,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(dw_mc), '')\n" + "                FROM\n"
				+ "                    fc_dwb\n" + "                WHERE\n"
				+ "                    id = A.receive_unitid) AS ZRDWMC,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(name), '')\n" + "                FROM\n"
				+ "                    FD_CODE\n" + "                WHERE\n"
				+ "                    code = A.question_belonging_to) AS sd,\n" + "            A.guest_id,\n"
				+ "            CASE\n" + "                WHEN IFNULL(A.is_repeat_petition, 0) = 0 THEN 1\n"
				+ "                ELSE 0\n" + "            END CFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.is_repeat_petition, 0) = 1 THEN 1\n" + "                ELSE 0\n"
				+ "            END FFXJ,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.petition_numbers, 0) <= 4 THEN 1\n" + "                ELSE 0\n"
				+ "            END SFGF,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.petition_numbers, 0) > 4 THEN 1\n" + "                ELSE 0\n"
				+ "            END SFJTF,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n" + "                THEN\n"
				+ "                    TIMESTAMPDIFF(DAY, A.gmt_create, handle_fact_end_time)\n"
				+ "                WHEN\n" + "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n" + "                THEN\n"
				+ "                    TIMESTAMPDIFF(DAY, A.gmt_create, NOW())\n" + "                ELSE 0\n"
				+ "            END HS_day,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    '超期'\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL A.handle_days DAY)\n"
				+ "                THEN\n" + "                    '超期'\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    '期限过75%'\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.75) HOUR)\n"
				+ "                THEN\n" + "                    '期限过75%'\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.handle_fact_end_time, '') <> ''\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) = 2003\n"
				+ "                        AND A.handle_fact_end_time > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    '期限过50%'\n" + "                WHEN\n"
				+ "                    IFNULL(A.handle_days, 0) <> 0\n"
				+ "                        AND IFNULL(A.case_handle_state, 0) <> 2003\n"
				+ "                        AND NOW() > DATE_ADD(A.gmt_create, INTERVAL FLOOR(A.handle_days * 24 * 0.5) HOUR)\n"
				+ "                THEN\n" + "                    '期限过50%'\n" + "                ELSE '正常'\n"
				+ "            END YJQK_50,\n" + "            CASE\n"
				+ "                WHEN IFNULL(A.case_handle_state, 0) = 2003 THEN 1\n" + "                ELSE 0\n"
				+ "            END BJ_XJ_JC,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(department), '')\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id) AS XFMY_YES,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(unit), '')\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id) AS ZRMY_YES,\n" + "            (SELECT \n"
				+ "                    IFNULL(MAX(case_satisfied), '')\n" + "                FROM\n"
				+ "                    FP_APPRAISE\n" + "                WHERE\n"
				+ "                    case_id = A.id) AS JGMY_YES,\n" + "            CASE\n" + "                WHEN\n"
				+ "                    (SELECT \n" + "                            COUNT(*)\n"
				+ "                        FROM\n" + "                            fd_dep_case\n"
				+ "                        WHERE\n" + "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.create_unitid\n"
				+ "                                AND type = 1\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    '及时'\n" + "                ELSE '不及时'\n" + "            END JSSL_YES,\n"
				+ "            CASE\n" + "                WHEN\n" + "                    (SELECT \n"
				+ "                            COUNT(*)\n" + "                        FROM\n"
				+ "                            fd_dep_case\n" + "                        WHERE\n"
				+ "                            CASE_ID = A.ID\n"
				+ "                                AND DEP_ID = A.receive_unitid\n"
				+ "                                AND type = 3\n"
				+ "                                AND UPDATE_TIME <= END_TIME) > 0\n" + "                THEN\n"
				+ "                    '及时'\n" + "                ELSE '不及时'\n" + "            END ZRJSSL_YES,\n"
				+ "            3 AS JB\n" + "    FROM\n" + "        fd_case A) dd,\n"
				+ "    (SELECT @rowid:=0) AS init\n" + "WHERE\n" + "    1 = 1 AND create_unitid = " + createUnitId;

		if (creatorId != 0) {
			sql += " AND creator_id = " + creatorId;
		}

		if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
			sql += " AND DATE_FORMAT(gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime + "', '%Y-%m-%d')\n";
		}
		if (!StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
			sql += " AND DATE_FORMAT(gmt_create, '%Y-%m-%d') <= DATE_FORMAT('" + endTime + "', '%Y-%m-%d')\n";
		}
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			sql += " AND DATE_FORMAT(gmt_create, '%Y-%m-%d') >= DATE_FORMAT('" + startTime + "', '%Y-%m-%d')\n"
					+ "        AND DATE_FORMAT(gmt_create, '%Y-%m-%d') <= DATE_FORMAT('" + endTime + "', '%Y-%m-%d')\n";
		}

		if (!StringUtils.isEmpty(keywords)) {
			sql += " AND (XFR_MC LIKE '%" + keywords + "%'\n" + "        OR XFR_SFZ LIKE '%" + keywords + "%'\n"
					+ "        OR XFR_DH LIKE '%" + keywords + "%'\n" + "        OR XFR_HJD LIKE '%" + keywords + "%'\n"
					+ "        OR XFR_XJZD LIKE '%" + keywords + "%'\n" + "        OR case_desc LIKE '%" + keywords
					+ "%')";
		}

		sql += " ORDER BY gmt_create DESC";

		try {
			List<Map<String, Object>> rList = jdbcTemplate.queryForList(sql);
			if (rList != null && rList.size() > 0) {
				return rList;
			}
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> zhtj(String fl, String msg, String ct1, String ct2, String ct3, String ct4,
			String ct5, String ct6, String ct7, String ct8, String ct9) {
		String sql = "call GC_ZHTJ('" + fl + "','" + msg + "','" + ct1 + "','" + ct2 + "','" + ct3 + "','" + ct4 + "','"
				+ ct5 + "','" + ct6 + "','" + ct7 + "','" + ct8 + "','" + ct9 + "')";

//		System.out.println(">>>>>>>>>>>>>>\n" + sql);
		
		try {
			List<Map<String, Object>> rList = jdbcTemplate.queryForList(sql);
			if (rList != null && rList.size() > 0) {
				return rList;
			}
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return null;
	}

}
