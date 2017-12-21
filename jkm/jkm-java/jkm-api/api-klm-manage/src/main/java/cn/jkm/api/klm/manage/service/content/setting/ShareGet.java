package cn.jkm.api.klm.manage.service.content.setting;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.service.content.RulePostService;
import cn.jkm.service.content.ShareSettingService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.setting
 * @description //发帖规则保存
 * @update 2017/7/26 16:47
 */
@Component("shareGet1.0")
public class ShareGet extends AbstractManageService {

    @Reference(version = "1.0")
    ShareSettingService shareSettingService;

    @Override
    public Map service(Map request) {

            Map<String,Object> data = shareSettingService.getShareInfo();
            return ApiResponse.success().body(data);
    }
}
