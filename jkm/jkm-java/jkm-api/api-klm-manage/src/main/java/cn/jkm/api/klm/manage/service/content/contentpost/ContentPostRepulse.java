package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentPostService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhengzb on 2017/7/20.
 * 打回帖子
 */
@Component("contentPostRepulse1.0")
@NotNull(name = {"id"}, message = "缺少参数")
public class ContentPostRepulse extends AbstractManageService {
    @Reference(version = "1.0")
    ContentPostService contentPostService;
    @Override
    public Map service(Map request) {
        contentPostService.repulsePost(formatString(request.get("id")), ContentType.POST.toString());
        return ApiResponse.success();
    }
}
