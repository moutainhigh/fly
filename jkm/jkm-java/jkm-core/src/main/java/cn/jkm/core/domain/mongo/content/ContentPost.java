package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.core.domain.type.TopType;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zhong on 2017/7/18.
 * 帖子，新闻吗，活动
 */
@Document(name = "content_post")
public class ContentPost extends ContentBasic<ContentPost>{

    private Long topTime;					//内容置顶时间

    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus;			//内容状态 1.待提交；2.待审核；3.显示；4.置顶，5.隐藏；6.删除————参考ContentStatus
    @Enumerated(EnumType.STRING)
    private ContentType postType;               //查看 ContentType
    private Integer topOrder;				//置顶顺序
    private String detail;					//资讯详情，内容详情，配方标签<pf></pf> 产品标签：<pd></pd>，
    private String postFormulaInfo;		//内容中关联的配方id
    private String postProductionInfo;	//内容中关联的产品id
    private String superId;                //用户的上级id，如果无上级，则为0
    private String handleUserId;			//内容审核人
    private String handleType;            //审核类别 1：上级审核，2：管理员审核（超时，无上级），手机端查询自己下级的待审核帖子。管理员查询无上级且超时的待审核
    private Long handleTime;				//审核时间
    private Long complaintTime;             //最近投诉时间
    //如果为活动



    private String activityName;            //活动名称
    private Integer enrollNum;			     //活动当前报名人数
    private Long beginTime;				//活动开始时间
    private Long endTime;				     //活动结束时间
    private String sponsorName;			//主办方名称
    private String address;				//活动地址
    private Integer maxNum;				//活动最大报名数

    public Long getTopTime() {
        return topTime;
    }

    public void setTopTime(Long topTime) {
        this.topTime = topTime;
    }


    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }

    public ContentType getPostType() {
        return postType;
    }

    public void setPostType(ContentType postType) {
        this.postType = postType;
    }

    public Integer getTopOrder() {
        return topOrder;
    }

    public void setTopOrder(Integer topOrder) {
        this.topOrder = topOrder;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPostFormulaInfo() {
        return postFormulaInfo;
    }

    public void setPostFormulaInfo(String postFormulaInfo) {
        this.postFormulaInfo = postFormulaInfo;
    }

    public String getPostProductionInfo() {
        return postProductionInfo;
    }

    public void setPostProductionInfo(String postProductionInfo) {
        this.postProductionInfo = postProductionInfo;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public Long getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Long handleTime) {
        this.handleTime = handleTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getEnrollNum() {
        return enrollNum;
    }

    public void setEnrollNum(Integer enrollNum) {
        this.enrollNum = enrollNum;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }
    public Long getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(Long complaintTime) {
        this.complaintTime = complaintTime;
    }

    @Override
    public String toString() {
        return "ContentPost{" +
                "title=" +getTitle()+
                "topTime=" + topTime +
                ", contentStatus=" + contentStatus +
                ", postType=" + postType +
                ", topOrder=" + topOrder +
                ", detail='" + detail + '\'' +
                ", postFormulaInfo='" + postFormulaInfo + '\'' +
                ", postProductionInfo='" + postProductionInfo + '\'' +
                ", superId='" + superId + '\'' +
                ", handleUserId='" + handleUserId + '\'' +
                ", handleType='" + handleType + '\'' +
                ", handleTime=" + handleTime +
                ", activityName='" + activityName + '\'' +
                ", enrollNum=" + enrollNum +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", sponsorName='" + sponsorName + '\'' +
                ", address='" + address + '\'' +
                ", maxNum=" + maxNum +
                '}';
    }
}
