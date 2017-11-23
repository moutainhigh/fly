package cn.jkm.api.klm.manage.service.content.contentpost;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.Expert;
import cn.jkm.core.domain.mongo.content.ExpertArticle;
import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ExpertArticleService;
import cn.jkm.service.content.ExpertService;
import cn.jkm.service.sys.SysUserService;

/**
 * Created by zhengzb on 2017/7/22.
 * 修改专家文章状态
 */
@Component("expertArticleStatus1.0")
@NotNull(name = {"id","type"}, message = "缺少参数")
public class ExpertArticleStatus extends AbstractManageService{

	@Reference(version = "1.0")
	ExpertArticleService expertArticleService;
	@SuppressWarnings("unchecked")
	@Override
	public Map service(Map request) {
		expertArticleService.updateStatus(formatString(request.get("id")), formatString(request.get("type")));
		return ApiResponse.success();
	}

}
