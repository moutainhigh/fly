package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.type.IntegralType;
import cn.jkm.core.domain.type.RewardType;

import java.io.Serializable;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.mysql.content
 * @description //发帖奖励规则
 * @update 2017/7/26 9:40
 */
public class RuleReward implements Serializable {

    private RewardType rewardType;
    /**
     * 发表人，或者审核人添加的积分
     */
    private int score;

    /**
     * 奖励积分的类型
     */
    private IntegralType type;

    /**
     * 每日上限
     */
    private int dayLimit;

    /**
     * 累计上限
     */
    private int totalLimit;

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public IntegralType getType() {
        return type;
    }

    public void setType(IntegralType type) {
        this.type = type;
    }

    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    public int getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(int totalLimit) {
        this.totalLimit = totalLimit;
    }

    @Override
    public String toString() {
        return "RuleReward{" +
                "rewardType=" + rewardType +
                ", score=" + score +
                ", type=" + type +
                ", dayLimit=" + dayLimit +
                ", totalLimit=" + totalLimit +
                '}';
    }
}
