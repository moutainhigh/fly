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
 * 帖子置顶或取消置顶
 */
@Component("contentPostStick1.0")
@NotNull(name = {"id","isTop"}, message = "缺少参数")
public class ContentPostStick  extends AbstractManageService {
    @Reference(version = "1.0")
    ContentPostService contentPostService;

    @Override
    public Map service(Map request) {
        contentPostService.upPost(formatString(request.get("id")),formatString(request.get("isTop")));
        return ApiResponse.success();
    }
}
