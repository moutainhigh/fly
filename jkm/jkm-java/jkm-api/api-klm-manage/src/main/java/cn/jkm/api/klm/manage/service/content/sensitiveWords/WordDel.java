package cn.jkm.api.klm.manage.service.content.sensitiveWords;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.service.content.WordsSettingService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.sensitiveWords.WordDel
 * @description //todo
 * @update 2017/7/20 11:52
 */
@Component("wordDel1.0")
public class WordDel extends AbstractManageService {

    @Reference(version = "1.0")
    WordsSettingService service;

    @Override
    public Map service(Map request) {
        service.del(formatString(request.get("wordId")));
        return ApiResponse.success().body("");
    }
}