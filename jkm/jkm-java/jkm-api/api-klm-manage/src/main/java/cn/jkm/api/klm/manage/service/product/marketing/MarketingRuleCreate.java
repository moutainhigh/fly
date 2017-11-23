package cn.jkm.api.klm.manage.service.product.marketing;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.product.Branch;
import cn.jkm.core.domain.mongo.product.MarketingRule;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.BranchService;
import cn.jkm.service.eb.product.MarketingRuleService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 新增营销规则
 * @Date: 16:23 2017/7/25
 */
@Component("marketingRuleCreate1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class MarketingRuleCreate extends AbstractManageService {

    @Reference(version = "1.0")
    private MarketingRuleService ruleService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        MarketingRule rule = JSON.parseObject(json, MarketingRule.class);
        return ApiResponse.success().body(ruleService.addMarketingRule(rule));
    }
}
