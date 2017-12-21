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
 * Created by zhengzb on 2017/7/21.
 * 新增活动
 */
@Component("activityCreate1.0")
@NotNull(name = {"id","handleUserId"}, message = "缺少参数")
public class ActivityCreate extends AbstractManageService{
	@Reference(version = "1.0")
	ActivityService activityService;

	@Override
	public Map service(Map request) {
		ContentPost contentPost = new ContentPost();
		List<ActivityItems> activityItems = null;
		contentPost.setTitle(formatString(request.get("title")));
		contentPost.setActivityName(formatString(request.get("activityName")));
		contentPost.setAddress(formatString(request.get("address")));
		contentPost.setSponsorName(formatString(request.get("sponsorName")));
		contentPost.setBeginTime(formatLong(request.get("beginTime")));
		contentPost.setEndTime(formatLong(request.get("endTime")));
		contentPost.setMaxNum(formatInteger(request.get("maxNum")));
		
		Object items = request.get("items");
		if(items != null) {//活动定制表不为空 新增活动
			activityItems = new ArrayList<ActivityItems>();
			JSONArray array = JSON.parseArray(items.toString());
			for (Object object : array) {
				ActivityItems item = JSON.parseObject(object.toString(), ActivityItems.class);
				activityItems.add(item);
			}
		}
		activityService.createOrUpdate(contentPost, activityItems);
		return ApiResponse.success();
	}
	public static void main(String[] args) {
//		String data = "{\"title\":\"zhangsan\",\"address\":\"hsdfhsidf\",\"items\":[{\"field\":\"lishi\",\"itemsJson\":\"男\"},{\"field\":\"wangwu\",\"itemsJson\":\"女\"}]}";
		String data = "{\"title\":\"zhangsan\",\"address\":\"hsdfhsidf\"}";
		HashMap hashMap = JSON.parseObject(data, HashMap.class);
		System.out.println("--------"+hashMap.get("title").toString());
		System.out.println("--------"+hashMap.get("items"));
		String str = hashMap.get("items").toString();
		System.out.println("+++:"+str);
		JSONArray array = JSON.parseArray(str);
		for (Object object : array) {
//			String contentPostStr = JSON.toJSONString(object.toString(),true);
			ActivityItems contentPost = JSON.parseObject(object.toString(), ActivityItems.class);
			System.out.println(contentPost.getField());
		}
		
	}
	
}
