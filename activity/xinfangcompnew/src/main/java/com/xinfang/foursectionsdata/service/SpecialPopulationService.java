package com.xinfang.foursectionsdata.service;

import java.util.Map;

public interface SpecialPopulationService {
	

	
	/**
	 * 统计人员分析
	 */
	public Map<String, Object> countPopulation(Integer QxsId,Integer Populationtype,Integer sex);

}

