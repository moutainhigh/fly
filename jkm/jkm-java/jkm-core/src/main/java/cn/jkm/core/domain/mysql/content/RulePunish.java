package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.type.IntegralType;
import cn.jkm.core.domain.type.PunishType;
import cn.jkm.core.domain.type.RewardType;

import java.io.Serializable;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.mysql.content
 * @description //惩罚积分规则
 * @update 2017/7/26 9:56
 */
public class RulePunish implements Serializable{

    /**
     * 积分惩罚的类别
     */
    private PunishType punishType;
    /**
     * 发表人，扣除的积分
     */
    private int score;

    /**
     * 扣除积分的类型
     */
    private IntegralType type;

    /**
     * 上级扣除的积分
     */
    private int higherLevelScore;

    /**
     * 上级扣除的积分类型
     */
    private IntegralType higherLevelType;
    /**
     *  超时时长，单位天
     */
    private int timecount;

    public PunishType getPunishType() {
        return punishType;
    }

    public void setPunishType(PunishType punishType) {
        this.punishType = punishType;
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

    public int getHigherLevelScore() {
        return higherLevelScore;
    }

    public void setHigherLevelScore(int higherLevelScore) {
        this.higherLevelScore = higherLevelScore;
    }

    public IntegralType getHigherLevelType() {
        return higherLevelType;
    }

    public void setHigherLevelType(IntegralType higherLevelType) {
        this.higherLevelType = higherLevelType;
    }

    public int getTimecount() {
        return timecount;
    }

    public void setTimecount(int timecount) {
        this.timecount = timecount;
    }
}
