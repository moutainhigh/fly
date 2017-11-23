package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentPostService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhengzb on 2017/7/21.
 * 删除主题帖或新闻
 */
@Component("contentPostDelete1.0")
@NotNull(name = {"id","type","handleUserId"}, message = "缺少参数")
public class ContentPostDelete extends AbstractManageService {

    @Reference(version = "1.0")
    ContentPostService contentPostService;

    @Override
    public Map service(Map request) {
        contentPostService.delete(formatString(request.get("id")),formatString(request.get("type")),formatString(request.get("handleUserId")));
        return ApiResponse.success();
    }
}
