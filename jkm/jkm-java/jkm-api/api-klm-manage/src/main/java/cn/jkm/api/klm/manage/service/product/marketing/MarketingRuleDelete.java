package cn.jkm.api.klm.manage.service.product.marketing;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.product.MarketingRule;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.MarketingRuleService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 删除营销规则
 * @Date: 16:27 2017/7/25
 */
@Component("marketingDelete1.0")
@NotNull(name = {"ruleId"}, message = "缺少参数")
public class MarketingRuleDelete extends AbstractManageService {

    @Reference(version = "1.0")
    private MarketingRuleService ruleService;

    @Override
    public Map service(Map request) {
        String ruleId = formatString(request.get("ruleId"));
        ruleService.delMarketingRule(ruleId);
        return ApiResponse.success();
    }
}
