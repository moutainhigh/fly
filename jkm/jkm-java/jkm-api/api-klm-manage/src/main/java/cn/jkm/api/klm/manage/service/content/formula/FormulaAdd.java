package cn.jkm.api.klm.manage.service.content.formula;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingChannel;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ChannelService;
import cn.jkm.service.content.FormulaService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.formula
 * @description //添加配方
 * @update 2017/7/25 16:13
 */
@Component("formulaAdd1.0")
@NotNull(name = {"title","info","symptom","expertId","productInfos"}, message = "缺少参数")
public class FormulaAdd extends AbstractManageService {

    @Reference(version = "1.0")
    FormulaService formulaService;

    @Override
    public Map service(Map request) {

        String title = formatString(request.get("title"));
        String info = formatString(request.get("info"));
        String symptom = formatString(request.get("symptom"));
        String expertId = formatString(request.get("expertId"));
        String productInfos = formatString(request.get("productInfos"));
        formulaService.formulaAdd(title,info,symptom,expertId,productInfos);
        return ApiResponse.success().body("");
    }
}