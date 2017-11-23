package com.xinfang.dao.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xinfang.VO.AuthGroupNewVO;
import com.xinfang.dao.ComplainHomeDao;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.TsWindowPersonRepository;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdCase;
import com.xinfang.personnelmanagement.service.PermissionService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.utils.ExcelUtil;

/**
 * Created by sunbjx on 2017/5/4.
 */
@Repository("complainHomeDao")
public class ComplainHomeDaoImpl implements ComplainHomeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Autowired
	private TsWindowPersonRepository tsWindowPersonRepository;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private TzmPetitionService tzmPetitionService;

	@Override
	public List<Map<String, Object>> packages(int flag, Integer userId, int caseState, int handleState,
			int timeProgress, String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage,
			int pageSize) {

		String limitSQL = "";
		if (startPage != -1 && pageSize != -1) {
			limitSQL = " ORDER BY dd.case_id DESC LIMIT " + (startPage - 1) * pageSize + ", " + pageSize;
		}

		// 模糊搜索
		String fluzzSQL = " AND (g.username LIKE '%" + fuzzy + "%' OR c.petition_code LIKE '%" + fuzzy
				+ "%' OR c.case_desc LIKE '%" + fuzzy + "%'\n"
				+ "      OR c.handle_userid IN (SELECT group_concat(RY_ID) FROM fc_ryb WHERE RY_MC LIKE '%" + fuzzy
				+ "%')) ";
		// 信访方式
		String typeSQL = " AND c.petition_way = " + petitionType;
		// 问题属地
		String questionBelongtoSQL = " AND c.question_belonging_to = " + caseBelongTo;
		// 参与部门
		// String deptSQL = " AND v.NAME_ = 'depid' AND v.LONG_ = " + dept;
		String deptSQL = " AND c.current_handle_unitid = " + dept;

		class ConcatSQl {
			private String result() {
				String vSQL = fluzzSQL;
				String wSQl = typeSQL;
				String xSQL = questionBelongtoSQL;
				String ySQl = deptSQL;
				String sql = "";

				if (!StringUtils.isEmpty(fuzzy)) {
					sql += vSQL;
				} else {
					vSQL = "";
				}
				if (petitionType != 0) {
					sql += wSQl;
				} else {
					wSQl = "";
				}
				if (caseBelongTo != 0) {
					sql += xSQL;
				} else {
					xSQL = "";
				}
				if (dept != 0) {
					sql += ySQl;
				}
				return sql;
			}

			private String base(double a, double b, String windowNumber, String isTodo, String baseCondition) {
				// 根据用户ID获取角色权限
				List<AuthGroupNewVO> result = permissionService.userRoleList(userId, 1, null);

				boolean handleDisputeAuth = false;
				if (result != null && result.size() > 0) {
					// 判断该角色是否有处理矛盾纠纷案件的权限
					for (AuthGroupNewVO authGroupNewVO : result) {
						if (authGroupNewVO.getStatus() == 1 && authGroupNewVO.getTag()
								&& authGroupNewVO.getId() == 14) {
							handleDisputeAuth = true;
							break;
						}
					}
				}
				// 根据用户ID获取单位ID
				Map dwMap = tzmPetitionService.getDwInfoRyId(userId);
				Integer unitId = (dwMap == null) ? 0 : Integer.valueOf(dwMap.get("dwId").toString());

				// 具有处理该单位的矛盾纠纷案件人员
				if (null != unitId && handleDisputeAuth) {
					// TODO 对矛盾纠纷案件的状态查询
				}

				// 获取用户接受信访件的类型
				Map acceptType = permissionService.getPersonCaseTypeList(userId, 1, null);
				String receiveCaseTypes = (acceptType == null) ? null : acceptType.get("receiveCaseTypes").toString();
				String receiveTypes = receiveCaseTypes.substring(1, receiveCaseTypes.length() - 1);

				// 获取单位收文岗人员
				// Map dw = tzmPetitionService.getDwInfoRyId(userId);
				// Integer dwId = (dw == null) ? 0 :
				// Integer.valueOf(dw.get("dwId").toString());
				// Map dispatches =
				// permissionService.getTransferPersonList(dwId, 1, null);
				//
				// List<PermissionTransferPersonVO> dispatchersList =
				// (dispatches == null) ? null :
				// (List<PermissionTransferPersonVO>)
				// dispatches.get("receiveCaseTypes");
				//
				// // PermissionTransferPersonVO sPermissionTransferPersonVO =
				// dispatchersList.get(0);
				// // 收文岗只有一人直接追加所属接受权限内的案件
				// if (dispatchersList != null && dispatchersList.size() == 1 &&
				// dispatchersList.get(0).getReceiverId() == userId) {
				// String subSQL = " OR (c.petition_way IN () and ";
				//
				// }
				// 市信访去可以查看所有单位的案件 区信访局可以查看所属区单位的案件 非信访局只能查看本单位的案件
				// String ryIds = "";
				// // 无权限
				// if (acceptType == null) {
				// // 根据人员id判断人员是否有‘科室信访件查看’权限，如果有则返回该人员所在科室下的所有人员id
				// String department =
				// permissionService.getKsRyIdsByRyId(userId).toString();
				//
				// // 根据人员ID获取下属人员ID列表
				// String subordinate =
				// permissionService.getZwRyIdsByRyId(userId).toString();
				//
				// ryIds = (department != null && department.split(",").length >
				// 0)
				// ? department.substring(1, department.length() - 1)
				// : subordinate.substring(1, subordinate.length() - 1);
				//
				// } else {
				// // 有权限
				// ryIds = userId.toString();
				// }

				String baseSQL = "SELECT *\n" + "FROM (\n" + "       SELECT\n" + "         c.petition_code,\n"
						+ "         g.username,\n" + "         g.head_pic,\n" + "         (\n"
						+ "           SELECT name\n" + "           FROM fd_code\n"
						+ "           WHERE code = c.petition_way\n"
						+ "         )                                                             AS petition_way,\n"
						+ "         (\n" + "           SELECT name\n" + "           FROM fd_code\n"
						+ "           WHERE code = c.question_belonging_to\n"
						+ "         )                                                             AS question_belong_to,\n"
						+ "         c.case_desc,\n" + "         c.petition_time,\n" + "         c.handle_period_end,\n"
						+ "         c.is_degree,\n"
						+ "         DATE_FORMAT(c.gmt_create, '%Y-%m-%d')                         AS gmt_create,\n"
						+ "         c.current_handle_state                                        AS current_state,\n"
						+ "         (\n" + "           SELECT DW_MC\n" + "           FROM fc_dwb\n"
						+ "           WHERE DW_ID = c.current_handle_unitid\n"
						+ "         )                                                             AS current_unit,\n"
						+ "         (\n" + "           SELECT RY_MC\n" + "           FROM fc_ryb\n"
						+ "           WHERE RY_ID = c.current_handle_personalid\n"
						+ "         )                                                             AS current_username,\n"
						+ "         (\n" + "           SELECT RY_SFZ\n" + "           FROM fc_ryb\n"
						+ "           WHERE RY_ID = c.current_handle_personalid\n"
						+ "         )                                                             AS current_userpic,\n"
						+ "         c.ID                                                          AS case_id,\n"
						+ "         c.guest_id,\n" + "\n" + "         (SELECT max(PROC_INST_ID_)\n"
						+ "          FROM act_hi_varinst\n" + "          WHERE NAME_ = 'caseid' AND LONG_ = c.id\n"
						+ "         )                                                             AS proc_inst_id,\n"
						+ "\n" + "         (SELECT max(t.ID_)\n" + "          FROM act_ru_task t, act_hi_varinst v\n"
						+ "          WHERE ASSIGNEE_ = " + userId + " AND t.PROC_INST_ID_ = v.PROC_INST_ID_\n"
						+ "                AND v.NAME_ = 'caseid' AND v.LONG_ = c.id)             AS task_id,\n" + "\n"
						+ "         CASE\n"
						+ "         WHEN ifnull(case_handle_state, 0) <> 2003 AND NOW() > handle_period_end\n"
						+ "           THEN\n" + "             1\n"
						+ "         WHEN ifnull(case_handle_state, 0) = 2003 AND c.handle_fact_end_time > handle_period_end\n"
						+ "           THEN\n" + "             1\n"
						+ "         ELSE 0 END                                                    AS overdue_is_end,\n"
						+ "\n" + "         CASE\n" + "         WHEN ifnull(case_handle_state, 0) <> 2003 AND\n"
						+ "              NOW() > date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY)\n"
						+ "           THEN\n" + "             1\n"
						+ "         WHEN ifnull(case_handle_state, 0) = 2003 AND\n"
						+ "              handle_fact_end_time > date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY)\n"
						+ "           THEN\n" + "             1\n"
						+ "         ELSE 0 END                                                    AS deadline_morethanhalf,\n"
						+ "\n" + "         CASE\n"
						+ "         WHEN NOW() >= date_add(c.gmt_create, INTERVAL floor(handle_days * " + a
						+ ") DAY) AND\n" + "              NOW() <= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ b + ") DAY)\n" + "              AND ifnull(case_handle_state, 0) <> 2003\n"
						+ "           THEN 1\n"
						+ "         WHEN handle_fact_end_time >= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ a + ") DAY) AND\n"
						+ "              handle_fact_end_time <= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ b + ") DAY)\n" + "              AND ifnull(case_handle_state, 0) = 2003\n"
						+ "           THEN 1\n"
						+ "         ELSE 0 END                                                    AS time_progress,\n"
						+ "         c.handle_flow_type                                            AS flow_type,\n"
						+ "         date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY) AS morethanhalf_time,\n"
						+ "         c.handle_fact_end_time                                        AS case_end_time,\n"
						+ "         c.case_handle_state                                           AS case_state,\n"
						+ "         c.handle_days                                                 AS expect_days,\n"
						+ "         c.handle_period_end                                           AS expect_end_time,\n"
						+ "			c.turn_audit_state,\n" + "case when c.creator_id= (" + userId
						+ ") and   c.form=6 AND ifnull(case_handle_state, 0) <> 2003 and IFNULL(c.window_number, 0)=0 and ifnull(c.handle_userid,0) = 0 and ifnull(c.is_flow, '') = '' and c.is_handle != 2 then 1\n"
						+ "\n" + "				when  c.handle_userid = " + userId
						+ " AND ifnull(case_handle_state, 0) <> 2003 and ifnull(c.is_flow, '') = '' and c.is_handle != 2 then 1\n"
						+ "\n"
						+ "				when ifnull(case_handle_state, 0) <> 2003 and (select count(id) from FD_CASE_FEEDBACK where CASE_ID=c.id and type=102 and CREATER_ID = "
						+ userId + " )=0\n"
						+ "				and ifnull(c.handle_userid,0) = 0 and ifnull(c.is_flow, '') = '' and c.is_handle != 2 then 1\n"
						+ "\n"
						+ "				when ifnull(case_handle_state, 0) <> 2003 and (select count(*) FROM act_hi_taskinst where ASSIGNEE_ = "
						+ userId + " and NAME_ NOT like ('%收文岗%') and  ifnull(END_TIME_,'')='' \n"
						+ "                and PROC_INST_ID_ = (select max(ifnull(PROC_INST_ID_,0)) FROM act_hi_varinst where NAME_='caseid' and LONG_=c.id))>0 then 1\n"
						+ "				else 0\n" + "				END is_todo,\n" + "         c.handle_type,\n"
						+ "         c.form,\n" + "         c.window_number,\n" + "         c.is_flow,\n"
						+ "         c.is_handle\n" + "       FROM fd_case c, fd_guest g\n"
						+ "       WHERE c.guest_id = g.ID  and c.state <> -1 \n"
						+ "             AND ((SELECT count(*)\n" + "                   FROM FD_CASE_FEEDBACK\n"
						+ "                   WHERE CASE_ID = c.id AND CREATER_ID = " + userId + ") <> 0\n"
						+ "                  " + "OR c.current_handle_personalid = " + userId + windowNumber + isTodo
						+ ")\n" + baseCondition + ") dd\n" + "WHERE 1 = 1 \n";

				return baseSQL;
			}
		}

		String baseSQL = new ConcatSQl().base(1, 1, "", "", new ConcatSQl().result());
		// 正常
		if (timeProgress == 1) {
			baseSQL = new ConcatSQl().base(1, 0.5, "", "", new ConcatSQl().result());
		}
		// 预警
		if (timeProgress == 2) {
			baseSQL = new ConcatSQl().base(0.5, 0.75, "", "", new ConcatSQl().result());
		}
		// 警告
		if (timeProgress == 3) {
			baseSQL = new ConcatSQl().base(0.75, 0.875, "", "", new ConcatSQl().result());
		}
		// 严重警告
		if (timeProgress == 4) {
			baseSQL = new ConcatSQl().base(0.875, 1, "", "", new ConcatSQl().result());
		}
		// 超期
		if (timeProgress == 5) {
			baseSQL = new ConcatSQl().base(1, 100, "", "", new ConcatSQl().result());
		}

		// 待办
		if (flag == 1) {
			// 表示窗口人员录入在待办中可以看到分流室录入的
			Integer windowId = tsWindowPersonRepository.getWindowByUserId(userId);
			List<FdCase> listFdCase2 = fdCaseRepository.getCaseByWindowNumber(windowId);
			if (listFdCase2 != null && listFdCase2.size() > 0) {
				baseSQL = new ConcatSQl().base(1, 1, " OR  window_number = " + listFdCase2.get(0).getWindowNumber(),
						"  OR (ifnull(case_handle_state, 0) <> 2003 and c.handle_userid = (" + userId
								+ ") and ifnull(c.is_flow, '') = '' and c.is_handle != 2) \n ",
						new ConcatSQl().result());
				// baseSQL += " or window_number = " +
				// listFdCase2.get(0).getWindowNumber();
			} else {
				baseSQL = new ConcatSQl().base(1, 1, "",
						"  OR (ifnull(case_handle_state, 0) <> 2003 and c.handle_userid = (" + userId
								+ ") and ifnull(c.is_flow, '') = '' and c.is_handle != 2)\n ",
						new ConcatSQl().result());
			}
			// 分流室人员待办看不到分流室录入的案件
			// List<FdCase> listFdCase =
			// fdCaseRepository.getByHandleUserid(userId);
			// if (listFdCase != null && listFdCase.size() > 0) {
			// baseSQL += " AND form != 6 ";
			// }

			baseSQL += " AND is_todo = 1 ";

		}
		// 正办-只有流程
		if (flag == 2) {
			// 分流室录入的未完结的在正办, 非分流室录入的在待办
//			baseSQL += " AND is_todo = 0 AND case_state != 2003 ";
			baseSQL += " AND ifnull(case_state,'')<> 2003 ";
		}
		// 已办
		if (flag == 3) {
			baseSQL += " AND case_state = 2003 ";
		}
		// 收文岗
		if (flag == 4) {
			baseSQL += " AND is_todo = 1 ";
		}
		// 已退回
		if (flag == 5) {
			baseSQL += " AND handle_type = 8 ";
		}
		// 汇总
		// if (flag == 6) {
		//
		// }

		// 转办(仅流程)
		if (handleState == 1) {
			baseSQL += " AND flow_type = 2 ";
		}
		// 交办(仅流程)
		if (handleState == 2) {
			baseSQL += " AND flow_type = 1 ";
		}
		// 待审
		if (handleState == 3) {
			baseSQL += " AND handle_type = 11 ";
		}
		// 待处理
		if (handleState == 4) {
			baseSQL += " AND handle_type = 12 ";
		}
		// 待办结
		if (handleState == 5) {
			baseSQL += " AND handle_type = 13 ";
		}
		// 不予受理
		if (handleState == 6) {
			baseSQL += " AND handle_type = 5 ";
		}
		// 不再受理
		if (handleState == 7) {
			baseSQL += " AND handle_type = 6 ";
		}
		// 直接回复
		if (handleState == 8) {
			baseSQL += " AND handle_type = 4 ";
		}
		// 退回件
		if (handleState == 9) {
			baseSQL += " AND handle_type = 8 ";
		}
		// 存件
		if (handleState == 10) {
			baseSQL += " AND handle_type = 7 ";
		}

		// baseSQL = new ConcatSQl().result(baseSQL);

		String sql = baseSQL + limitSQL;

		System.out.println(">>>>>>>>>>>>>>>     ");
		System.out.println(">>>>>>>>>>>>>>>     " + sql);
		System.out.println(">>>>>>>>>>>>>>>     ");
		List<Map<String, Object>> list = null;
		try {
			System.out.println("====================================sql:");
			System.out.println(">>>"+sql);
			list = jdbcTemplate.queryForList(sql);
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return list;
		}
		return list;
	}

	/**
	 * excel 下载
	 * 
	 * @param list
	 * @param response
	 * @param fileName
	 */
	private void excelDownload(List<Map<String, Object>> list, HttpServletResponse response, String fileName) {
		String columnNames[] = { "乡镇", "村", "姓名", "性别", "证件号码", "贫困户属性", "联系电话", "疾病诊断", "确诊时间", "治疗机构", "门诊或住院", "总费用",
				"医保结算", "医疗扶助资金", "自费" };// 列名
		String keys[] = { "region_xz_name", "region_name", "name", "sex_name", "incard", "type", "phone",
				"disease_name", "QZSJ", "ZLJG", "cure_type", "count_money", "health_care_money", "funds_money",
				"self_pay_money" };// map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			try {
				throw e;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Map<String, Object> dispatcher(Integer userId, int caseState, int handleState, int timeProgress,
			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage, int pageSize) {
		String limitSQL = "";
		if (startPage != -1 && pageSize != -1) {
			limitSQL = " ORDER BY dd.case_id DESC LIMIT " + (startPage - 1) * pageSize + ", " + pageSize;
		}

		// 模糊搜索
		String fluzzSQL = " AND (g.username LIKE '%" + fuzzy + "%' OR c.petition_code LIKE '%" + fuzzy
				+ "%' OR c.case_desc LIKE '%" + fuzzy + "%'\n"
				+ "      OR c.handle_userid IN (SELECT group_concat(RY_ID) FROM fc_ryb WHERE RY_MC LIKE '%" + fuzzy
				+ "%')) ";
		// 信访方式
		String typeSQL = " AND c.petition_way = " + petitionType;
		// 问题属地
		String questionBelongtoSQL = " AND c.question_belonging_to = " + caseBelongTo;
		// 参与部门
		// String deptSQL = " AND v.NAME_ = 'depid' AND v.LONG_ = " + dept;
		String deptSQL = " AND c.current_handle_unitid = " + dept;

		class ConcatSQl {
			private String result() {
				String vSQL = fluzzSQL;
				String wSQl = typeSQL;
				String xSQL = questionBelongtoSQL;
				String ySQl = deptSQL;
				String sql = "";

				if (!StringUtils.isEmpty(fuzzy)) {
					sql += vSQL;
				} else {
					vSQL = "";
				}
				if (petitionType != 0) {
					sql += wSQl;
				} else {
					wSQl = "";
				}
				if (caseBelongTo != 0) {
					sql += xSQL;
				} else {
					xSQL = "";
				}
				if (dept != 0) {
					sql += ySQl;
				}
				return sql;
			}

			private String base(double a, double b, String baseCondition) {

				String baseSQL = "SELECT *\n" + "FROM (\n" + "       SELECT\n" + "         c.petition_code,\n"
						+ "         g.username,\n" + "         g.head_pic,\n" + "         (\n"
						+ "           SELECT name\n" + "           FROM fd_code\n"
						+ "           WHERE code = c.petition_way\n"
						+ "         )                                                             AS petition_way,\n"
						+ "          c.petition_way  as petition_way_id,\n" + "         (\n"
						+ "           SELECT name\n" + "           FROM fd_code\n"
						+ "           WHERE code = c.question_belonging_to\n"
						+ "         )                                                             AS question_belong_to,\n"
						+ "         c.case_desc,\n" + "         c.petition_time,\n" + "         c.handle_period_end,\n"
						+ "         c.is_degree,\n"
						+ "         DATE_FORMAT(c.gmt_create, '%Y-%m-%d')                         AS gmt_create,\n"
						+ "         c.current_handle_state                                        AS current_state,\n"
						+ "         (\n" + "           SELECT DW_MC\n" + "           FROM fc_dwb\n"
						+ "           WHERE DW_ID = c.current_handle_unitid\n"
						+ "         )                                                             AS current_unit,\n"
						+ "         (\n" + "           SELECT RY_MC\n" + "           FROM fc_ryb\n"
						+ "           WHERE RY_ID = c.current_handle_personalid\n"
						+ "         )                                                             AS current_username,\n"
						+ "         (\n" + "           SELECT RY_SFZ\n" + "           FROM fc_ryb\n"
						+ "           WHERE RY_ID = c.current_handle_personalid\n"
						+ "         )                                                             AS current_userpic,\n"
						+ "         c.ID                                                          AS case_id,\n"
						+ "         c.guest_id,\n" + "\n" + "         (SELECT max(PROC_INST_ID_)\n"
						+ "          FROM act_hi_varinst\n" + "          WHERE NAME_ = 'caseid' AND LONG_ = c.id\n"
						+ "         )                                                             AS proc_inst_id,\n"
						+ "\n" + "         (SELECT max(t.ID_)\n" + "          FROM act_ru_task t, act_hi_varinst v\n"
						+ "          WHERE ASSIGNEE_ = " + userId + " AND t.PROC_INST_ID_ = v.PROC_INST_ID_\n"
						+ "                AND v.NAME_ = 'caseid' AND v.LONG_ = c.id)             AS task_id,\n" + "\n"
						+ "         CASE\n"
						+ "         WHEN ifnull(case_handle_state, 0) <> 2003 AND NOW() > handle_period_end\n"
						+ "           THEN\n" + "             1\n"
						+ "         WHEN ifnull(case_handle_state, 0) = 2003 AND c.handle_fact_end_time > handle_period_end\n"
						+ "           THEN\n" + "             1\n"
						+ "         ELSE 0 END                                                    AS overdue_is_end,\n"
						+ "\n" + "         CASE\n" + "         WHEN ifnull(case_handle_state, 0) <> 2003 AND\n"
						+ "              NOW() > date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY)\n"
						+ "           THEN\n" + "             1\n"
						+ "         WHEN ifnull(case_handle_state, 0) = 2003 AND\n"
						+ "              handle_fact_end_time > date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY)\n"
						+ "           THEN\n" + "             1\n"
						+ "         ELSE 0 END                                                    AS deadline_morethanhalf,\n"
						+ "\n" + "         CASE\n"
						+ "         WHEN NOW() >= date_add(c.gmt_create, INTERVAL floor(handle_days * " + a
						+ ") DAY) AND\n" + "              NOW() <= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ b + ") DAY)\n" + "              AND ifnull(case_handle_state, 0) <> 2003\n"
						+ "           THEN 1\n"
						+ "         WHEN handle_fact_end_time >= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ a + ") DAY) AND\n"
						+ "              handle_fact_end_time <= date_add(c.gmt_create, INTERVAL floor(handle_days * "
						+ b + ") DAY)\n" + "              AND ifnull(case_handle_state, 0) = 2003\n"
						+ "           THEN 1\n"
						+ "         ELSE 0 END                                                    AS time_progress,\n"
						+ "         c.handle_flow_type                                            AS flow_type,\n"
						+ "         date_add(c.gmt_create, INTERVAL floor(handle_days * 0.5) DAY) AS morethanhalf_time,\n"
						+ "         c.handle_fact_end_time                                        AS case_end_time,\n"
						+ "         c.case_handle_state                                           AS case_state,\n"
						+ "         c.handle_days                                                 AS expect_days,\n"
						+ "         c.handle_period_end                                           AS expect_end_time,\n"
						+ "CASE\n" + "\n"
						+ " when  ifnull(c.case_handle_state, 0) <> 2003 and ifnull(c.is_flow, '') = '' and c.is_handle != 2 \n"
						+ "  and ifnull(c.is_dispatcher_receive, 0)=0 and ifnull(c.dispatcher_to_userid, 0)=" + userId
						+ "\n" + "\n" + "  then 1\n" + "\n" + "\n"
						+ "when ifnull(c.case_handle_state, 0) <> 2003 and (select count(*) FROM act_hi_taskinst where \n"
						+ " ASSIGNEE_ IN (" + userId + ") and  NAME_ like ('%收文岗%') and\n" + "\n"
						+ "ifnull(END_TIME_,'')='' and PROC_INST_ID_ =\n"
						+ "(select max(ifnull(cast(PROC_INST_ID_  as SIGNED) ,0)) FROM act_hi_varinst where NAME_='caseid' and LONG_=c.id)\n"
						+ ")>0 then 1\n" + "else 0\n" + "\n" + " END is_todo,\n" + "         c.handle_type,\n"
						+ "         c.form,\n" + "         c.window_number,\n" + "         c.is_flow,\n"
						+ "         c.is_handle\n" + "       FROM fd_case c, fd_guest g\n"
						+ "       WHERE c.guest_id = g.ID\n" + "             AND ((SELECT count(*)\n"
						+ "                   FROM FD_CASE_FEEDBACK\n"
						+ "                   WHERE CASE_ID = c.id AND CREATER_ID = " + userId + ") <> 0\n"
						+ "                  OR  ifnull(dispatcher_to_userid,0) = " + userId
						+ "  OR c.current_handle_personalid = " + userId + ")) dd\n" + "WHERE 1 = 1 \n"
						+ " AND is_todo = 1\n";

				return baseSQL;
			}
		}

		String baseSQL = new ConcatSQl().base(1, 1, new ConcatSQl().result());
		// 正常
		if (timeProgress == 1) {
			baseSQL = new ConcatSQl().base(1, 0.5, new ConcatSQl().result());
		}
		// 预警
		if (timeProgress == 2) {
			baseSQL = new ConcatSQl().base(0.5, 0.75, new ConcatSQl().result());
		}
		// 警告
		if (timeProgress == 3) {
			baseSQL = new ConcatSQl().base(0.75, 0.875, new ConcatSQl().result());
		}
		// 严重警告
		if (timeProgress == 4) {
			baseSQL = new ConcatSQl().base(0.875, 1, new ConcatSQl().result());
		}
		// 超期
		if (timeProgress == 5) {
			baseSQL = new ConcatSQl().base(1, 100, new ConcatSQl().result());
		}

		// 转办(仅流程)
		if (handleState == 1) {
			baseSQL += " AND flow_type = 2 ";
		}
		// 交办(仅流程)
		if (handleState == 2) {
			baseSQL += " AND flow_type = 1 ";
		}
		// 待审
		if (handleState == 3) {
			baseSQL += " AND handle_type = 11 ";
		}
		// 待处理
		if (handleState == 4) {
			baseSQL += " AND handle_type = 12 ";
		}
		// 待办结
		if (handleState == 5) {
			baseSQL += " AND handle_type = 13 ";
		}
		// 不予受理
		if (handleState == 6) {
			baseSQL += " AND handle_type = 5 ";
		}
		// 不再受理
		if (handleState == 7) {
			baseSQL += " AND handle_type = 6 ";
		}
		// 直接回复
		if (handleState == 8) {
			baseSQL += " AND handle_type = 4 ";
		}
		// 退回件
		if (handleState == 9) {
			baseSQL += " AND handle_type = 8 ";
		}
		// 存件
		if (handleState == 10) {
			baseSQL += " AND handle_type = 7 ";
		}

		// baseSQL = new ConcatSQl().result(baseSQL);

		String sql = baseSQL + limitSQL;

		System.out.println(">>>>>>>>>>>>>>>     ");
		System.out.println(">>>>>>>>>>>>>>>     " + sql);
		System.out.println(">>>>>>>>>>>>>>>     ");
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> listAll = null;
		Map<String, Object> record = new HashMap<>();
		try {
			list = jdbcTemplate.queryForList(sql);
			listAll = jdbcTemplate.queryForList(baseSQL);
			record.put("countRecord", listAll.size());
			record.put("restult", list);
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return record;
		}
		return record;
	}

	@Override
	public List<Map<String, Object>> caseStateQuery(Integer userId, int caseState, int handleState, int timeProgress,
			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept) {
		// 模糊搜索
		String fluzzSQL = " AND (g.username LIKE '%" + fuzzy + "%' OR c.petition_code LIKE '%" + fuzzy
				+ "%' OR c.case_desc LIKE '%" + fuzzy + "%'\n"
				+ "      OR c.handle_userid IN (SELECT group_concat(RY_ID) FROM fc_ryb WHERE RY_MC LIKE '%" + fuzzy
				+ "%')) ";
		// 信访方式
		String typeSQL = " AND c.petition_way = " + petitionType;
		// 问题属地
		String questionBelongtoSQL = " AND c.question_belonging_to = " + caseBelongTo;
		// 参与部门
		// String deptSQL = " AND v.NAME_ = 'depid' AND v.LONG_ = " + dept;
		String deptSQL = " AND c.current_handle_unitid = " + dept;

		class ConcatSQl {
			private String result() {
				String vSQL = fluzzSQL;
				String wSQl = typeSQL;
				String xSQL = questionBelongtoSQL;
				String ySQl = deptSQL;
				String sql = "";

				if (!StringUtils.isEmpty(fuzzy)) {
					sql += vSQL;
				} else {
					vSQL = "";
				}
				if (petitionType != 0) {
					sql += wSQl;
				} else {
					wSQl = "";
				}
				if (caseBelongTo != 0) {
					sql += xSQL;
				} else {
					xSQL = "";
				}
				if (dept != 0) {
					sql += ySQl;
				}
				return sql;
			}

			private String base(double a, double b, String baseCondition) {

				String baseSQL = "SELECT \n" + "    c.petition_code,\n" + "    g.username,\n" + "    g.head_pic,\n"
						+ "    (SELECT \n" + "            name\n" + "        FROM\n" + "            fd_code\n"
						+ "        WHERE\n" + "            code = c.petition_way) AS petition_way,\n" + "    (SELECT \n"
						+ "            name\n" + "        FROM\n" + "            fd_code\n" + "        WHERE\n"
						+ "            code = c.question_belonging_to) AS question_belong_to,\n" + "    c.case_desc,\n"
						+ "    c.petition_time,\n" + "    c.handle_period_end,\n" + "    c.is_degree,\n"
						+ "    DATE_FORMAT(c.gmt_create, '%Y-%m-%d') AS gmt_create,\n"
						+ "    c.current_handle_state AS current_state,\n" + "    (SELECT \n" + "            DW_MC\n"
						+ "        FROM\n" + "            fc_dwb\n" + "        WHERE\n"
						+ "            DW_ID = c.current_handle_unitid) AS current_unit,\n" + "    (SELECT \n"
						+ "            RY_MC\n" + "        FROM\n" + "            fc_ryb\n" + "        WHERE\n"
						+ "            RY_ID = c.current_handle_personalid) AS current_username,\n" + "    (SELECT \n"
						+ "            RY_SFZ\n" + "        FROM\n" + "            fc_ryb\n" + "        WHERE\n"
						+ "            RY_ID = c.current_handle_personalid) AS current_userpic,\n"
						+ "    c.ID AS case_id,\n" + "    c.guest_id,\n" + "    (SELECT \n"
						+ "            MAX(PROC_INST_ID_)\n" + "        FROM\n" + "            act_hi_varinst\n"
						+ "        WHERE\n" + "            NAME_ = 'caseid' AND LONG_ = c.id) AS proc_inst_id,\n"
						+ "    (SELECT \n" + "            MAX(t.ID_)\n" + "        FROM\n"
						+ "            act_ru_task t,\n" + "            act_hi_varinst v\n" + "        WHERE\n"
						+ "            ASSIGNEE_ = " + userId + "\n"
						+ "                AND t.PROC_INST_ID_ = v.PROC_INST_ID_\n"
						+ "                AND v.NAME_ = 'caseid'\n"
						+ "                AND v.LONG_ = c.id) AS task_id,\n" + "    CASE\n" + "        WHEN\n"
						+ "            IFNULL(case_handle_state, 0) <> 2003\n"
						+ "                AND NOW() > handle_period_end\n" + "        THEN\n" + "            1\n"
						+ "        WHEN\n" + "            IFNULL(case_handle_state, 0) = 2003\n"
						+ "                AND c.handle_fact_end_time > handle_period_end\n" + "        THEN\n"
						+ "            1\n" + "        ELSE 0\n" + "    END AS overdue_is_end,\n" + "    CASE\n"
						+ "        WHEN\n" + "            IFNULL(case_handle_state, 0) <> 2003\n"
						+ "                AND NOW() > DATE_ADD(c.gmt_create,\n"
						+ "                INTERVAL FLOOR(handle_days * 0.5) DAY)\n" + "        THEN\n"
						+ "            1\n" + "        WHEN\n" + "            IFNULL(case_handle_state, 0) = 2003\n"
						+ "                AND handle_fact_end_time > DATE_ADD(c.gmt_create,\n"
						+ "                INTERVAL FLOOR(handle_days * 0.5) DAY)\n" + "        THEN\n"
						+ "            1\n" + "        ELSE 0\n" + "    END AS deadline_morethanhalf,\n" + "    CASE\n"
						+ "        WHEN\n" + "            NOW() >= DATE_ADD(c.gmt_create,\n"
						+ "                INTERVAL FLOOR(handle_days * " + a + ") DAY)\n"
						+ "                AND NOW() <= DATE_ADD(c.gmt_create,\n"
						+ "                INTERVAL FLOOR(handle_days * " + b + ") DAY)\n"
						+ "                AND IFNULL(case_handle_state, 0) <> 2003\n" + "        THEN\n"
						+ "            1\n" + "        WHEN\n"
						+ "            handle_fact_end_time >= DATE_ADD(c.gmt_create,\n"
						+ "                INTERVAL FLOOR(handle_days * " + a + ") DAY)\n"
						+ "                AND handle_fact_end_time <= DATE_ADD(c.gmt_create,\n"
						+ "                INTERVAL FLOOR(handle_days * " + b + ") DAY)\n"
						+ "                AND IFNULL(case_handle_state, 0) = 2003\n" + "        THEN\n"
						+ "            1\n" + "        ELSE 0\n" + "    END AS time_progress,\n"
						+ "    c.handle_flow_type AS flow_type,\n" + "    DATE_ADD(c.gmt_create,\n"
						+ "        INTERVAL FLOOR(handle_days * 0.5) DAY) AS morethanhalf_time,\n"
						+ "    c.handle_fact_end_time AS case_end_time,\n" + "    c.case_handle_state AS case_state,\n"
						+ "    c.handle_days AS expect_days,\n" + "    c.handle_period_end AS expect_end_time,\n"
						+ "    c.handle_type,\n" + "    c.form,\n" + "    c.window_number,\n" + "    c.is_flow,\n"
						+ "    c.is_handle,\n" + "    c.handle_userid\n" + "FROM\n" + "    fd_case c,\n"
						+ "    fd_guest g\n" + "WHERE\n" + "    c.guest_id = g.ID\n" + "        AND ((SELECT \n"
						+ "            COUNT(*)\n" + "        FROM\n" + "            FD_CASE_FEEDBACK\n"
						+ "        WHERE\n" + "            CASE_ID = c.id AND CREATER_ID = " + userId + ") <> 0\n"
						+ "        OR c.current_handle_personalid = " + userId + ")" + baseCondition;

				return baseSQL;
			}
		}

		String baseSQL = new ConcatSQl().base(1, 1, new ConcatSQl().result());
		// 正常
		if (timeProgress == 1) {
			baseSQL = new ConcatSQl().base(1, 0.5, new ConcatSQl().result());
		}
		// 预警
		if (timeProgress == 2) {
			baseSQL = new ConcatSQl().base(0.5, 0.75, new ConcatSQl().result());
		}
		// 警告
		if (timeProgress == 3) {
			baseSQL = new ConcatSQl().base(0.75, 0.875, new ConcatSQl().result());
		}
		// 严重警告
		if (timeProgress == 4) {
			baseSQL = new ConcatSQl().base(0.875, 1, new ConcatSQl().result());
		}
		// 超期
		if (timeProgress == 5) {
			baseSQL = new ConcatSQl().base(1, 100, new ConcatSQl().result());
		}

		// 逾期未结
		if (caseState == 1) {
			baseSQL += " AND IFNULL(case_handle_state, 0) <> 2003\n"
					+ "                        AND NOW() > handle_period_end";
		}
		// 期限过半
		if (caseState == 2) {
			baseSQL += " and IFNULL(case_handle_state, 0) <> 2003\n"
					+ "                        AND NOW() > DATE_ADD(gmt_create, INTERVAL FLOOR(handle_days * 24 * 0.5) HOUR)";
		}
		// 未办理
		if (caseState == 3) {
			baseSQL += " and (IFNULL(window_number, 0) = 0\n"
					+ "                        AND IFNULL(case_handle_state, 0) <> 2003\n"
					+ "                        AND IFNULL(is_flow, 0) = 0\n"
					+ "                        AND is_handle = 0) or (IFNULL(window_number, 0) <> 0\n"
					+ "                        AND IFNULL(handle_userid, 0) = 0\n"
					+ "                        AND IFNULL(case_handle_state, 0) <> 2003\n"
					+ "                        AND IFNULL(is_flow, 0) = 0\n"
					+ "                        AND is_handle <> 2)";
		}
		// 办理中
		if (caseState == 4) {
			baseSQL += " and (IFNULL(case_handle_state, 0) = 2001\n"
					+ "                        AND is_handle = 1) or (IFNULL(case_handle_state, 0) <> 2003\n"
					+ "                        AND is_handle = 2)";
		}
		// 逾期办结
		if (caseState == 5) {
			baseSQL += " and  IFNULL(case_handle_state, 0) = 2003\n"
					+ "                        AND handle_fact_end_time > handle_period_end";
		}
		// 按期办结
		if (caseState == 6) {
			baseSQL += " and IFNULL(case_handle_state, 0) = 2003\n"
					+ "                        AND handle_fact_end_time <= handle_period_end";
		}

		// 转办(仅流程)
		if (handleState == 1) {
			baseSQL += " AND flow_type = 2 ";
		}
		// 交办(仅流程)
		if (handleState == 2) {
			baseSQL += " AND flow_type = 1 ";
		}
		// 待审
		if (handleState == 3) {
			baseSQL += " AND handle_type = 11 ";
		}
		// 待处理
		if (handleState == 4) {
			baseSQL += " AND handle_type = 12 ";
		}
		// 待办结
		if (handleState == 5) {
			baseSQL += " AND handle_type = 13 ";
		}
		// 不予受理
		if (handleState == 6) {
			baseSQL += " AND handle_type = 5 ";
		}
		// 不再受理
		if (handleState == 7) {
			baseSQL += " AND handle_type = 6 ";
		}
		// 直接回复
		if (handleState == 8) {
			baseSQL += " AND handle_type = 4 ";
		}
		// 退回件
		if (handleState == 9) {
			baseSQL += " AND handle_type = 8 ";
		}
		// 存件
		if (handleState == 10) {
			baseSQL += " AND handle_type = 7 ";
		}

		System.out.println(">>>>>>>>>>>>>>>     ");
		System.out.println(">>>>>>>>>>>>>>>     " + baseSQL);
		System.out.println(">>>>>>>>>>>>>>>     ");
		List<Map<String, Object>> listAll = null;
		try {
			System.out.println("=================================================");
			System.out.println("正半sql："+baseSQL);
			listAll = jdbcTemplate.queryForList(baseSQL);
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return listAll;
		}
		return listAll;
	}

}
