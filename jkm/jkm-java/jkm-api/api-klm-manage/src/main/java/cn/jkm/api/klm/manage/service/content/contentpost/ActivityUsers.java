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
import cn.jkm.core.domain.mongo.content.ActivityEnrollUser;
import cn.jkm.core.domain.mongo.content.ActivityItems;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ActivityService;

/**
 * Created by zhengzb on 2017/7/21.
 * 获取报名活动用户信息
 */
@Component("activityUsers1.0")
@NotNull(name = {"id"}, message = "缺少参数")
public class ActivityUsers extends AbstractManageService{
	@Reference(version = "1.0")
	ActivityService activityService;

	@Override
	public Map service(Map request) {
		List<ActivityEnrollUser> enrollUsers = activityService.findEnrollUsers(formatString(request.get("id")));
		return ApiResponse.success().body(enrollUsers);
	}
}
