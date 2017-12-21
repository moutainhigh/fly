package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentCommentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhengzb on 2017/7/21.
 * 删除单条评论
 */
@Component("contentCommentDelete1.0")
@NotNull(name = {"id","handleUserId"}, message = "缺少参数")
public class ContentCommentDelete extends AbstractManageService {

    @Reference(version = "1.0")
    ContentCommentService contentCommentService;

    @Override
    public Map service(Map request) {
        contentCommentService.delete(formatString(request.get("id")),formatString(request.get("handleUserId")));
        return ApiResponse.success();
    }
}
