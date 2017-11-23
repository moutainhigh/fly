package com.xinfang.evaluating.service;

import java.util.Map;

import com.xinfang.evaluating.model.TemplateMaintenanceEntity;


public interface TemplateMaintenanceService {
	/**
	 * 
	 * @param type
	 * @return
	 * 模板列表显示
	 */
	Map<String, Object> getTemplateMaintenanceList(Integer type);
	/**
	 * 添加修改模板
	 * @param tem
	 * @return
	 */
	Map<String, Object> saveAndupdateTemlate(TemplateMaintenanceEntity tem);
	/**
	 * 删除模板
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteTemlate(Integer id);
}
