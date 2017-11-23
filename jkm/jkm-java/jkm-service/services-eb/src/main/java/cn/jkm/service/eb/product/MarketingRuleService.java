package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mongo.product.MarketingRule;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 营销规则
 * @Date: 10:01 2017/7/20
 */
public interface MarketingRuleService {

    /**
     * 新增
     * @param marketingRule
     * @return
     */
    MarketingRule addMarketingRule(MarketingRule marketingRule);

    /**
     * 修改
     * @param marketingRule
     * @return
     */
    void updateMarketingRule(MarketingRule marketingRule);
    /**
     * 删除营销规则
     * @param ruleId
     */
    void delMarketingRule(String ruleId);

    /**
     * 根据id查询
     * @param ruleId
     */
    MarketingRule findMarketingRule(String ruleId);

    /**
     * 分页查询
     * @param keyword 关键字
     * @param limit
     * @param page
     * @return
     */
    List<MarketingRule> pageMarketingRule(String keyword, int limit, int page);
}
