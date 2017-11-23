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
 * 修改活动状态
 */
@Component("activityStatus1.0")
@NotNull(name = {"id","status"}, message = "缺少参数")
public class ActivityStatus extends AbstractManageService{
	@Reference(version = "1.0")
	ActivityService activityService;

	@Override
	public Map service(Map request) {
		activityService.updateStatus(formatString(request.get("id")), formatString(request.get("status")));
		return ApiResponse.success();
	}
}
