package cn.jkm.api.klm.manage.service.content.sensitiveWords;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.service.content.WordsSettingService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.sensitiveWords
 * @description //todo
 * @update 2017/7/20 11:52
 */
@Component("wordDelBatch1.0")
public class WordDelBatch extends AbstractManageService {

    @Reference(version = "1.0")
    WordsSettingService service;

    @Override
    public Map service(Map request) {


        List<String> wordIdList = new ArrayList<>();
        String wordIds = formatString(request.get("wordIds"));
        wordIds = wordIds.replace("[","");
        wordIds = wordIds.replace("]","");

        String[] wordIdArray = wordIds.split(",");
        for (int i =0;i<wordIdArray.length;i++){
            wordIdList.add(wordIdArray[i].trim());
        }
        service.batchDel(wordIdList);
        return ApiResponse.success().body("");
    }
}
