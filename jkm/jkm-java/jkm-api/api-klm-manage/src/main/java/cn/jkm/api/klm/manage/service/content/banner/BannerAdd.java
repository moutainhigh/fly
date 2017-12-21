package cn.jkm.api.klm.manage.service.content.banner;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SensitiveWords;
import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.service.content.BannerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.banner
 * @description //添加banner类
 * @update 2017/7/21 13:55
 */
@Component("bannerAdd1.0")
public class BannerAdd extends AbstractManageService {

    @Reference(version = "1.0")
    BannerService bannerService;

    @Override
    public Map service(Map request) {

        boolean b = bannerService.addBanner(request);
        return ApiResponse.success().body(b);
    }
}
