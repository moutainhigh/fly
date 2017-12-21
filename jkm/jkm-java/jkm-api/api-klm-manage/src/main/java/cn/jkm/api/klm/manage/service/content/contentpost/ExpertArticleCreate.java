package cn.jkm.api.klm.manage.service.content.contentpost;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ExpertArticle;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ExpertArticleService;

/**
 * Created by zhengzb on 2017/7/22.
 * 添加或修改专家文章
 */
@Component("expertArticleCreate1.0")
@NotNull(name = {"title","content","expertId"}, message = "缺少参数")
public class ExpertArticleCreate extends AbstractManageService{

	@Reference(version = "1.0")
	ExpertArticleService expertArticleService;
	@Override
	public Map service(Map request) {
		try {
			String expertArticleStr = JSON.toJSONString(request,true);
			ExpertArticle expertArticle = JSON.parseObject(expertArticleStr, ExpertArticle.class);
			if(formatString(request.get("id")) != null)
				expertArticleService.update(expertArticle);
			else
				expertArticleService.create(expertArticle);
			return ApiResponse.success();
		}catch (Exception e) {
			return ApiResponse.fail("新增或修改专家文章数据失败！");
		}
	}

}
