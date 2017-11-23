package cn.jkm.api.klm.manage.service.content.sensitiveWords;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SensitiveWords;
import cn.jkm.service.content.WordsSettingService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.sensitiveWords.WordAddBatch
 * @description //批量添加敏感词
 * @update 2017/7/20 11:52
 */
@Component("wordAddBatch1.0")
public class WordAddBatch extends AbstractManageService {

    @Reference(version = "1.0")
    WordsSettingService service;

    @Override
    public Map service(Map request) {

        List<String> wordsList = new ArrayList<>();
        String words = formatString(request.get("words"));
        words = words.replace("[","");
        words = words.replace("]","");

        String[] wordArray = words.split(",");
            for (int i =0;i<wordArray.length;i++){
            wordsList.add(wordArray[i].trim());
        }
        service.batchAdd(wordsList);
        return ApiResponse.success().body("");
    }
}
