package cn.jkm.api.klm.manage.service.content.banner;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.BannerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.banner
 * @description //删除banner
 * @update 2017/7/25 14:49
 */
@Component("bannerDel1.0")
@NotNull(name = {"bannerId"}, message = "缺少参数")
public class BannerDel extends AbstractManageService {

    @Reference(version = "1.0")
    BannerService bannerService;

    @Override
    public Map service(Map request) {

        boolean b = bannerService.delBanner(formatString(request.get("bannerId")));
        return ApiResponse.success();
    }
}
