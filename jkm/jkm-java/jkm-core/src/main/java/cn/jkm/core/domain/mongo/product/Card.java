package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.ShelfStatus;
import cn.jkm.core.domain.type.SwitchStatus;
import cn.jkm.framework.core.annotation.Document;

import java.util.Set;

/**
 * @Author: Guo Fei
 * @Description: 促销规则（卡）
 * @Date: 10:01 2017/7/18
 */
@Document(name = "card")
public class Card extends MongoBasic<Card> {
    /**
     * 卡名称
     */
    private String name;
    /**
     * 卡编号
     */
    private String cardNo;
    /**
     * 卡缩略图
     */
    private String cardThumb;
    /**
     * 图片
     */
    private Set<String> cardImages;
    /**
     * 出售价
     */
    private long price;
    /**
     * 是否开启储值
     */
    private SwitchStatus cardSwitch = SwitchStatus.TURN_ON;
    /**
     * 卡储值
     */
    private long cardValue;
    /**
     * 卡储值用尽后是否成为vip
     */
    private SwitchStatus convertVip = SwitchStatus.TURN_OFF;
    /**
     * 生效时间
     */
    private Long effectTime;
    /**
     * 失效时间
     */
    private Long endTime;
    /**
     * 简介
     */
    private String cardBrief;
    /**
     * 详情内容信息
     */
    private String cardDesc;
    /**
     * 卡券状态：是否上下架
     */
    private ShelfStatus cardStatus = ShelfStatus.UP;
    /**
     * 备注
     */
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardThumb() {
        return cardThumb;
    }

    public void setCardThumb(String cardThumb) {
        this.cardThumb = cardThumb;
    }

    public Set<String> getCardImages() {
        return cardImages;
    }

    public void setCardImages(Set<String> cardImages) {
        this.cardImages = cardImages;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public SwitchStatus getCardSwitch() {
        return cardSwitch;
    }

    public void setCardSwitch(SwitchStatus cardSwitch) {
        this.cardSwitch = cardSwitch;
    }

    public long getCardValue() {
        return cardValue;
    }

    public void setCardValue(long cardValue) {
        this.cardValue = cardValue;
    }

    public SwitchStatus getConvertVip() {
        return convertVip;
    }

    public void setConvertVip(SwitchStatus convertVip) {
        this.convertVip = convertVip;
    }

    public Long getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Long effectTime) {
        this.effectTime = effectTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getCardBrief() {
        return cardBrief;
    }

    public void setCardBrief(String cardBrief) {
        this.cardBrief = cardBrief;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public String getRemark() {
        return remark;
    }

    public ShelfStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(ShelfStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
