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
import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ActivityService;
import cn.jkm.service.sys.SysUserService;

/**
 * Created by zhengzb on 2017/7/22.
 * 活动详情
 */
@Component("activityDetail1.0")
@NotNull(name = {"id"}, message = "缺少参数")
public class ActivityDetail extends AbstractManageService{
	
	@Reference(version = "1.0")
	ActivityService activityService;
	@Reference(version = "1.0")
	SysUserService sysUserService;

	@Override
	public Map service(Map request) {
		ContentPost contentPost = activityService.find(formatString(request.get("id")));
		SysUser user = sysUserService.find(contentPost.getPublishUserId());
		return ApiResponse.success().body(new HashMap() {{
			put("title",contentPost.getTitle());
			put("activityName",contentPost.getActivityName());
			put("sponsorName",contentPost.getSponsorName());
			put("address",contentPost.getAddress());
			put("maxNum",contentPost.getMaxNum());
			put("beginTime",contentPost.getBeginTime());
			put("endTime",contentPost.getEndTime());
			put("status",contentPost.getStatus());
			put("detail",contentPost.getDetail());
			put("enrollNum",contentPost.getEnrollNum());
			put("browseNum",contentPost.getBrowseNum());
			put("publicUserName",user.getAccountName());
			put("createAt",contentPost.getCreateAt());
		}});
	}
}
