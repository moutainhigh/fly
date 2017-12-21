package com.xinfang.service;

import java.util.List;
import java.util.Map;

/**
 * 政法外部接口
 * @author sunbjx
 * Date: 2017年6月1日下午7:40:05
 */
public interface ExternalService {

	List<Map<String, Object>> listZhengFa(String startTime, String endTime);
}
