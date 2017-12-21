package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentPostService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/20.
 * 查询帖子是否有待处理的投诉
 */
@Component("contentPostUntreated1.0")
@NotNull(name = {"id"}, message = "缺少参数")
public class ContentPostUntreated extends AbstractManageService {
    @Reference(version = "1.0")
    ContentPostService contentPostService;
    @Override
    public Map service(Map request) {
        boolean flag = true;
        try {
            flag = contentPostService.isHandle(formatString(request.get("id")));
        } catch (ParseException e) {
            return ApiResponse.fail("帖子不存在");
        }
        return ApiResponse.success().body(flag);
    }
}
