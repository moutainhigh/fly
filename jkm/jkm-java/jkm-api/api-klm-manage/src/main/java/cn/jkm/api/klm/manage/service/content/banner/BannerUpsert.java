package cn.jkm.api.klm.manage.service.content.banner;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.core.domain.type.BannerType;
import cn.jkm.service.content.BannerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.banner
 * @description //添加banner类
 * @update 2017/7/21 13:55
 */
@Component("bannerUpsert1.0")
public class BannerUpsert extends AbstractManageService {

    @Reference(version = "1.0")
    BannerService bannerService;

    @Override
    public Map service(Map request) {
        boolean b = false;
        Object id = request.get("bannerId");
        if(id==null||"".equals(formatString(id))){
            b = bannerService.addBanner(request);
        }else{
            SettingBanner banner = new SettingBanner();
            banner.setId(formatString(id));
            banner.setName(formatString(request.get("name")));
            banner.setBannerImages(formatString(request.get("bannerImages")));
            banner.setBeginTime(formatLong(request.get("beginTime")));
            banner.setEndTime(formatLong(request.get("endTime")));
            banner.setType(BannerType.valueOf(formatString(request.get("type"))));
            b = bannerService.updateBanner(banner);
        }
        return ApiResponse.success().body(b);
    }
}
