package cn.jkm.api.klm.manage.service.content.banner;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.BannerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhong on 2017/7/18.
 */
@Component("bannerGet1.0")
@NotNull(name = {"bannerId"}, message = "缺少参数")
public class BannerGet extends AbstractManageService{

    @Reference(version = "1.0")
    BannerService bannerService;

    @Override
    public Map service(Map request) {
        SettingBanner services = bannerService.findBannerById(formatString(request.get("bannerId")));
        return ApiResponse.success().body(services);
    }
}
