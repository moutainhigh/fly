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
 * 查询专家文章详情
 */
@Component("expertArticleDetail1.0")
@NotNull(name = {"id"}, message = "缺少参数")
public class ExpertArticleDetail extends AbstractManageService{

	@Reference(version = "1.0")
	ExpertArticleService expertArticleService;
	@Reference(version = "1.0")
	SysUserService sysUserService;
	@Reference(version = "1.0")
	ExpertService expertService;
	@SuppressWarnings("unchecked")
	@Override
	public Map service(Map request) {
		ExpertArticle expertArticle = expertArticleService.find(formatString(request.get("id")));
		Expert expert = expertService.findById(expertArticle.getExpertId());
		SysUser sysUser = sysUserService.find(expertArticle.getPublishUserId());
		return ApiResponse.success().body(new HashMap() {{
			put("id", expertArticle.getId());
			put("title", expertArticle.getTitle());
			put("status", expertArticle.getStatus());
			put("browseNum",expertArticle.getBrowseNum());
            put("pointNum",expertArticle.getPointNum());
            put("collectionNum",expertArticle.getCollectionNum());
            put("content",expertArticle.getContent());
            put("expertId",expertArticle.getExpertId());
            put("expertAvatar",expert.getAvatar());
            put("expertStatus",expert.getStatus());
            put("createAt",expertArticle.getCreateAt());
            put("publicUserName",sysUser.getAccountName());
		}});
	}

}
