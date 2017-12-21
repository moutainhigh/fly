package cn.jkm.api.klm.manage.service.content.sensitiveWords;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.service.content.BannerService;
import cn.jkm.service.content.WordsSettingService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2017/7/18.
 */
@Component("wordAdd1.0")
public class WordAdd extends AbstractManageService {

    @Reference(version = "1.0")
    WordsSettingService service;

    @Override
    public Map service(Map request) {
        service.add(formatString(request.get("word")));
        return ApiResponse.success().body("");
    }
}
