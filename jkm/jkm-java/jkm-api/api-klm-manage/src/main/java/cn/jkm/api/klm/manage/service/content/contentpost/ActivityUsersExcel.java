package cn.jkm.api.klm.manage.service.content.contentpost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ActivityEnrollUser;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ActivityService;

/**
 * Created by zhengzb on 2017/7/21.
 * 导出活动报名用户信息
 */
@Component("activityUsersExcel1.0")
@NotNull(name = {"id"}, message = "缺少参数")
public class ActivityUsersExcel extends AbstractManageService{

	@Reference(version = "1.0")
	ActivityService activityService;
	
	@Override
	public Map service(Map request) {
		List<ActivityEnrollUser> enrollUsers = activityService.findEnrollUsers(formatString(request.get("id")));
		List<Map<String, Object>> list = createExcelRecord(enrollUsers);
		
		ActivityEnrollUser enrollUser = enrollUsers.get(0);
		HashMap hashMap = JSON.parseObject(enrollUser.getItemData(), HashMap.class);
		String[] strs = new String[hashMap.size()];
		int i = 0;
		for (Object  key : hashMap.keySet()) {
			strs[i++] = key.toString();
		}
		// TODO 
		return null;
	}
	private List<Map<String, Object>> createExcelRecord(
			List<ActivityEnrollUser> enrollUsers) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		ActivityEnrollUser enrollUser = null;
		for (int j = 0; j < enrollUsers.size(); j++) {
			enrollUser = enrollUsers.get(j);
			HashMap hashMap = JSON.parseObject(enrollUser.getItemData(), HashMap.class);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			for (Object key : hashMap.keySet()) {
				mapValue.put(key.toString(), hashMap.get(key));
			}
			listmap.add(mapValue);
		}
		return listmap;
	}

}
