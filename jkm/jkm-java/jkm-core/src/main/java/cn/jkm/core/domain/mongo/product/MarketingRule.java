package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.DataType;
import cn.jkm.core.domain.type.IntegralType;
import cn.jkm.core.domain.type.SwitchStatus;
import cn.jkm.core.domain.type.UserType;
import cn.jkm.framework.core.annotation.Document;

/**
 * @Author: Guo Fei
 * @Description: 营销规则
 * @Date: 18:45 2017/7/18
 */
@Document(name = "marketing_rule")
public class MarketingRule extends MongoBasic<MarketingRule> {

    /**
     * 营销规则
     */
    private String name;
    /**
     * 用户类型开关
     */
    private SwitchStatus userTypeSwitch = SwitchStatus.TURN_OFF;
    /**
     * 用户类型
     */
    private UserType userType = UserType.ORDINARY_MEMBER;
    /**
     * 注册时间段开关
     */
    private SwitchStatus registerPeriodSwitch = SwitchStatus.TURN_OFF;
    /**
     * 注册开始时间
     */
    private long registerStartTime;
    /**
     * 注册结束时间
     */
    private long registerEndTime;
    /**
     * 注册时长开关
     */
    private SwitchStatus registerLengthSwitch = SwitchStatus.TURN_OFF;
    /**
     * 注册时长
     */
    private String registerLength;
    /**
     * 累计消费开关
     */
    private SwitchStatus consumeSwitch = SwitchStatus.TURN_OFF;
    /**
     * 累计消费总金额
     */
    private long consumeAmount;
    /**
     * 发表评论开关
     */
    private SwitchStatus commentSwitch = SwitchStatus.TURN_OFF;
    /**
     * 发表评论数
     */
    private long commentCount;
    /**
     * 发表帖子开关
     */
    private SwitchStatus postSwitch = SwitchStatus.TURN_OFF;
    /**
     * 发表帖子数
     */
    private long postCount;
    /**
     * 折扣开关
     */
    private SwitchStatus discountSwitch = SwitchStatus.TURN_ON;
    /**
     * 折扣值
     */
    private int discountValue;
    /**
     * 积分开关
     */
    private SwitchStatus integralSwitch = SwitchStatus.TURN_OFF;
    /**
     * 消费者奖励值
     */
    private int consumerValue;
    /**
     * 消费值类型
     */
    private DataType consumerValueType = DataType.FIXED_VALUE;
    /**
     * 消费者奖励类型
     */
    private IntegralType consumerType = IntegralType.CONSUMER_POINTS;
    /**
     * 上级奖励值
     */
    private int supLevelValue;
    /**
     * 上级奖励值类型
     */
    private DataType supLevelValueType = DataType.FIXED_VALUE;
    /**
     * 上级奖励类型
     */
    private IntegralType supLevelType = IntegralType.CONSUMER_POINTS;
    /**
     * 顶级奖励值
     */
    private int topLevelValue;
    /**
     * 顶级奖励值类型
     */
    private DataType topLevelValueType = DataType.FIXED_VALUE;
    /**
     * 顶级奖励类型
     */
    private IntegralType topLevelType = IntegralType.CONSUMER_POINTS;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SwitchStatus getUserTypeSwitch() {
        return userTypeSwitch;
    }

    public void setUserTypeSwitch(SwitchStatus userTypeSwitch) {
        this.userTypeSwitch = userTypeSwitch;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public SwitchStatus getRegisterPeriodSwitch() {
        return registerPeriodSwitch;
    }

    public void setRegisterPeriodSwitch(SwitchStatus registerPeriodSwitch) {
        this.registerPeriodSwitch = registerPeriodSwitch;
    }

    public long getRegisterStartTime() {
        return registerStartTime;
    }

    public void setRegisterStartTime(long registerStartTime) {
        this.registerStartTime = registerStartTime;
    }

    public long getRegisterEndTime() {
        return registerEndTime;
    }

    public void setRegisterEndTime(long registerEndTime) {
        this.registerEndTime = registerEndTime;
    }

    public SwitchStatus getRegisterLengthSwitch() {
        return registerLengthSwitch;
    }

    public void setRegisterLengthSwitch(SwitchStatus registerLengthSwitch) {
        this.registerLengthSwitch = registerLengthSwitch;
    }

    public String getRegisterLength() {
        return registerLength;
    }

    public void setRegisterLength(String registerLength) {
        this.registerLength = registerLength;
    }

    public SwitchStatus getConsumeSwitch() {
        return consumeSwitch;
    }

    public void setConsumeSwitch(SwitchStatus consumeSwitch) {
        this.consumeSwitch = consumeSwitch;
    }

    public long getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(long consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public SwitchStatus getCommentSwitch() {
        return commentSwitch;
    }

    public void setCommentSwitch(SwitchStatus commentSwitch) {
        this.commentSwitch = commentSwitch;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public SwitchStatus getPostSwitch() {
        return postSwitch;
    }

    public void setPostSwitch(SwitchStatus postSwitch) {
        this.postSwitch = postSwitch;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }

    public SwitchStatus getDiscountSwitch() {
        return discountSwitch;
    }

    public void setDiscountSwitch(SwitchStatus discountSwitch) {
        this.discountSwitch = discountSwitch;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public SwitchStatus getIntegralSwitch() {
        return integralSwitch;
    }

    public void setIntegralSwitch(SwitchStatus integralSwitch) {
        this.integralSwitch = integralSwitch;
    }

    public int getConsumerValue() {
        return consumerValue;
    }

    public void setConsumerValue(int consumerValue) {
        this.consumerValue = consumerValue;
    }

    public DataType getConsumerValueType() {
        return consumerValueType;
    }

    public void setConsumerValueType(DataType consumerValueType) {
        this.consumerValueType = consumerValueType;
    }

    public IntegralType getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(IntegralType consumerType) {
        this.consumerType = consumerType;
    }

    public int getSupLevelValue() {
        return supLevelValue;
    }

    public void setSupLevelValue(int supLevelValue) {
        this.supLevelValue = supLevelValue;
    }

    public DataType getSupLevelValueType() {
        return supLevelValueType;
    }

    public void setSupLevelValueType(DataType supLevelValueType) {
        this.supLevelValueType = supLevelValueType;
    }

    public IntegralType getSupLevelType() {
        return supLevelType;
    }

    public void setSupLevelType(IntegralType supLevelType) {
        this.supLevelType = supLevelType;
    }

    public int getTopLevelValue() {
        return topLevelValue;
    }

    public void setTopLevelValue(int topLevelValue) {
        this.topLevelValue = topLevelValue;
    }

    public DataType getTopLevelValueType() {
        return topLevelValueType;
    }

    public void setTopLevelValueType(DataType topLevelValueType) {
        this.topLevelValueType = topLevelValueType;
    }

    public IntegralType getTopLevelType() {
        return topLevelType;
    }

    public void setTopLevelType(IntegralType topLevelType) {
        this.topLevelType = topLevelType;
    }
}
