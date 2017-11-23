package cn.jkm.api.klm.manage.service.content.contentpost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ActivityItems;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ActivityService;

/**
 * Created by zhengzb on 2017/7/22.
 * 查询活动列表
 */
@Component("activityList1.0")
public class ActivityList extends AbstractManageService{
	
	@Reference(version = "1.0")
	ActivityService activityService;

	@Override
	public Map service(Map request) {
		List<ContentPost> activitys = activityService.list(formatLong(request.get("publicStTime")), 
														   formatLong(request.get("publicEnTime")), 
														   formatString(request.get("status")), 
														   formatLong(request.get("beStTime")), 
														   formatLong(request.get("beEnTime")), 
														   formatLong(request.get("enStTime")), 
														   formatLong(request.get("enEnTime")), 
														   formatInteger(request.get("stNum")), 
														   formatInteger(request.get("enNum")), 
														   formatString(request.get("address")), 
														   formatString(request.get("keys")),
														   formatString(request.get("orderBy")),
														   formatInteger(request.get("limit")),
														   formatInteger(request.get("page")));
		return ApiResponse.success().body(new ArrayList() {{
			activitys.forEach(v->{{
				new HashMap() {{
					put("title",v.getTitle());
					put("sponsorName",v.getSponsorName());
					put("address",v.getAddress());
					put("enrollNum",v.getEnrollNum());
					put("beginTime",v.getBeginTime());
					put("endTime",v.getEndTime());
					put("status",v.getStatus());
				}};
			}});
		}});
	}
}
