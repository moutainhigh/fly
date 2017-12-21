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
 * @package cn.jkm.api.klm.manage.service.content.sensitiveWords
 * @description //清空所有的敏感词
 * @update 2017/7/21 15:13
 */
@Component("wordAllDel1.0")
public class WordAllDel extends AbstractManageService {

    @Reference(version = "1.0",retries = 0)
    WordsSettingService service;

    @Override
    public Map service(Map request) {
        service.delAllWords();
        return ApiResponse.success().body("");
    }
}