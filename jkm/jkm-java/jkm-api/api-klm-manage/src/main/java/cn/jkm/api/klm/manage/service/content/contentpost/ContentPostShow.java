package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentPostService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhengzb on 2017/7/20.
 * 显示隐藏帖子或新闻
 */
@Component("contentPostShow1.0")
@NotNull(name = {"id","status"}, message = "缺少参数")
public class ContentPostShow extends AbstractManageService {
    @Reference(version = "1.0")
    ContentPostService contentPostService;
    @Override
    public Map service(Map request) {
        contentPostService.handleStatus(formatString(request.get("id")),formatString(request.get("status")));
        return ApiResponse.success();
    }
}
