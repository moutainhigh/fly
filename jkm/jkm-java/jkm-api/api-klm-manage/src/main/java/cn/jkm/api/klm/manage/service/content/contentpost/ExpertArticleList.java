package cn.jkm.api.klm.manage.service.content.contentpost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ExpertArticle;
import cn.jkm.service.content.ExpertArticleService;
import cn.jkm.service.content.ExpertService;
import cn.jkm.service.sys.SysUserService;
import cn.jkm.service.sys.UserService;

/**
 * Created by zhengzb on 2017/7/22.
 * 分页查询专家文章列表(条件可以全部为空)
 */
@Component("expertArticleList1.0")
public class ExpertArticleList extends AbstractManageService{

	@Reference(version = "1.0")
	ExpertArticleService expertArticleService;
	@Reference(version = "1.0")
	SysUserService sysUserService;
	@Reference(version = "1.0")
	ExpertService expertService;
	@Override
	public Map service(Map request) {
		List<ExpertArticle> expertAticles = expertArticleService.list(formatString(request.get("keys")),
									formatLong(request.get("startTime")),
									formatLong(request.get("endTime")),
									formatString(request.get("status")),
									formatString(request.get("orderBy")),
									formatInteger(request.get("limit")),
									formatInteger(request.get("page")));
		return ApiResponse.success().body(new ArrayList() {{
			expertAticles.forEach(v->{
                add(new HashMap(){{
                    put("id",v.getId());
                    put("title",v.getTitle());
                    put("browseNum",v.getBrowseNum());
                    put("pointNum",v.getPointNum());
                    put("collectionNum",v.getCollectionNum());
                    put("publicUserName",sysUserService.find(v.getId()).getAccountName());
                    put("expertName",expertService.findById(v.getExpertId()).getName());
                    put("status",v.getStatus());
                    put("createAt",v.getCreateAt());
                }});
            });
		}});
	}

}
