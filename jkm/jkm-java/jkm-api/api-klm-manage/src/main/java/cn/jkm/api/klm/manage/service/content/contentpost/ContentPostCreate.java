package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentPostService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhengzb on 2017/7/20.
 * 后台管理员发新闻
 */
@Component("contentPostCreate1.0")
@NotNull(name = {"title", "detail","channelId","publishUserId"}, message = "缺少参数")
public class ContentPostCreate extends AbstractManageService {

    @Reference(version = "1.0")
    ContentPostService contentPostService;

    @Override
    public Map service(Map request) {
        String contentPostStr = JSON.toJSONString(request,true);
        ContentPost contentPost = JSON.parseObject(contentPostStr, ContentPost.class);
        if(contentPost.getContentStatus() == null){
            contentPost.setContentStatus(ContentStatus.SHOW);//新闻默认发表状态为显示
        }
        contentPostService.create(contentPost);
        return ApiResponse.success();
    }

}
