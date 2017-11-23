package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mongo.product.MarketingRule;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.eb.product.MarketingRuleService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 营销规则
 * @Date: 9:55 2017/7/21
 */
public class MarketingRuleServiceImpl implements MarketingRuleService {

    /**
     * 新增或修改
     * @param marketingRule
     * @return
     */
    @Override
    public MarketingRule addMarketingRule(MarketingRule marketingRule) {
        if(this.nameExistCount("", marketingRule.getName()) > 0) {
            throw new ProviderException("规则名称已经存在");
        }

        Mongo.buildMongo().insert(marketingRule);

        return marketingRule;
    }

    /**
     * 修改营销规则
     * @param marketingRule
     * @return
     */
    @Override
    public void updateMarketingRule(MarketingRule marketingRule) {
        if(this.nameExistCount(marketingRule.getId(), marketingRule.getName()) > 0) {
            throw new ProviderException("规则名称已经存在");
        }

        Mongo.buildMongo().eq("_id", marketingRule.getId()).updateFirst(update -> {
            update.set("name", marketingRule.getName());
            update.set("userTypeSwitch", marketingRule.getUserTypeSwitch());
            update.set("userType", marketingRule.getUserType());
            update.set("registerPeriodSwitch", marketingRule.getRegisterPeriodSwitch());
            update.set("registerStartTime", marketingRule.getRegisterStartTime());
            update.set("registerEndTime", marketingRule.getRegisterEndTime());
            update.set("registerLengthSwitch", marketingRule.getRegisterLengthSwitch());
            update.set("registerLength", marketingRule.getRegisterLength());
            update.set("consumeSwitch", marketingRule.getCommentSwitch());
            update.set("consumeAmount", marketingRule.getConsumeAmount());
            update.set("commentSwitch", marketingRule.getCommentSwitch());
            update.set("commentCount", marketingRule.getCommentCount());
            update.set("postSwitch", marketingRule.getPostSwitch());
            update.set("postCount", marketingRule.getPostCount());
            update.set("discountSwitch", marketingRule.getDiscountSwitch());
            update.set("discountValue", marketingRule.getDiscountValue());
            update.set("integralSwitch", marketingRule.getIntegralSwitch());
            update.set("consumerValue", marketingRule.getConsumerValue());
            update.set("consumerValueType", marketingRule.getConsumerValueType());
            update.set("consumerType", marketingRule.getConsumerType());
            update.set("supLevelValue", marketingRule.getSupLevelValue());
            update.set("supLevelValueType", marketingRule.getSupLevelValueType());
            update.set("supLevelType", marketingRule.getSupLevelType());
            update.set("topLevelValue", marketingRule.getTopLevelValue());
            update.set("topLevelValueType", marketingRule.getTopLevelValueType());
            update.set("topLevelType", marketingRule.getTopLevelType());
        }, MarketingRule.class);
    }

    /**
     * 删除营销
     * @param ruleId
     */
    @Override
    public void delMarketingRule(String ruleId) {
        Mongo.buildMongo().eq("_id", "id").updateFirst(update -> {
            update.set("status", Status.DELETE);
        }, MarketingRule.class);
    }

    /**
     * 根据id查询
     * @param ruleId
     */
    @Override
    public MarketingRule findMarketingRule(String ruleId) {
        return Mongo.buildMongo().eq("_id", ruleId).findOne(MarketingRule.class);
    }

    /**
     * 分页查询
     * @param keyword 关键字
     * @param limit
     * @param page
     * @return
     */
    @Override
    public List<MarketingRule> pageMarketingRule(String keyword, int limit, int page) {

        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        if(!StringUtils.isEmpty(keyword)) {
            mongo.or(new String[]{"name"},
                    new String[]{keyword},
                    Mongo.OP.F);
        }

        if(limit > 0) {
            return mongo.limit(limit, page).find(MarketingRule.class);
        }

        return mongo.find(MarketingRule.class);
    }

    private long nameExistCount(String id, String name) {
        Mongo mongo = Mongo.buildMongo().eq("name", name);
        if(!StringUtils.isEmpty(id)) {
            mongo.ne("_id", id);
        }
        return mongo.count("marketing_rule");
    }
}
