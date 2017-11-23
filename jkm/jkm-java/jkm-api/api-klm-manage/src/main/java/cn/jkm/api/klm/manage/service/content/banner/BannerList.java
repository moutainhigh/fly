package cn.jkm.api.klm.manage.service.content.banner;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.BannerService;
import cn.jkm.service.core.domain.ListResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2017/7/18.
 */
@Component("bannerList1.0")
@NotNull(name = {"limit","page"}, message = "缺少参数")
public class BannerList extends AbstractManageService{

    @Reference(version = "1.0")
    BannerService bannerService;

    @Override
    public Map service(Map request) {
        ListResult<SettingBanner> result = bannerService.findBannersWithCount(limit(request),page(request));

        return ApiResponse.success().body(result);
    }
}
