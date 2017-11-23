package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

/**
 * ContentActivity entity. @author zhengzb 2017/7/15
 * 活动表
 */
@Document(name = "content_activity")
@Deprecated
public class ContentActivity extends MongoBasic<ContentActivity> {

	// Fields

	private Long publishTime;			//发布时间
	private Long updateTime;			//修改时间
	private String title;				//活动标题
	private Integer enrollNum;			//活动当前报名人数
	private Integer browseNum;			//活动浏览数
	private String detail;				//活动详情
	private Long beginTime;				//活动开始时间
	private Long endTime;				//活动结束时间
	private String sponsorName;			//主办方名称
	private String address;				//活动地址
	private String loc;					//活动经纬度
	private Integer minMun;				//活动最少报名人数
	private Integer maxNum;				//活动最多报名人数
	private Long enrollCost;			//报名费用
	private Short enrollSex;			//性别限制
	private Integer enrollMinAge;		//最小年龄
	private Integer enrollMaxAge;		//最大年龄
	private String enrollContactName;	//报名联系人
	private String enrollContactTel;	//联系电话
	private String remark;				//备注
	private String publishUserId;		//活动发布人
	public ContentActivity() {
		super();
	}
	public ContentActivity(Long publishTime, Long updateTime, String title,
                           Integer enrollNum, Integer browseNum, String detail,
                           Long beginTime, Long endTime, String sponsorName, String address,
                           String loc, Integer minMun, Integer maxNum, Long enrollCost,
                           Short enrollSex, Integer enrollMinAge, Integer enrollMaxAge,
                           String enrollContactName, String enrollContactTel, String remark,
                           String publishUserId) {
		super();
		this.publishTime = publishTime;
		this.updateTime = updateTime;
		this.title = title;
		this.enrollNum = enrollNum;
		this.browseNum = browseNum;
		this.detail = detail;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.sponsorName = sponsorName;
		this.address = address;
		this.loc = loc;
		this.minMun = minMun;
		this.maxNum = maxNum;
		this.enrollCost = enrollCost;
		this.enrollSex = enrollSex;
		this.enrollMinAge = enrollMinAge;
		this.enrollMaxAge = enrollMaxAge;
		this.enrollContactName = enrollContactName;
		this.enrollContactTel = enrollContactTel;
		this.remark = remark;
		this.publishUserId = publishUserId;
	}
	public Long getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getEnrollNum() {
		return enrollNum;
	}
	public void setEnrollNum(Integer enrollNum) {
		this.enrollNum = enrollNum;
	}
	public Integer getBrowseNum() {
		return browseNum;
	}
	public void setBrowseNum(Integer browseNum) {
		this.browseNum = browseNum;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
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
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public Integer getMinMun() {
		return minMun;
	}
	public void setMinMun(Integer minMun) {
		this.minMun = minMun;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	public Long getEnrollCost() {
		return enrollCost;
	}
	public void setEnrollCost(Long enrollCost) {
		this.enrollCost = enrollCost;
	}
	public Short getEnrollSex() {
		return enrollSex;
	}
	public void setEnrollSex(Short enrollSex) {
		this.enrollSex = enrollSex;
	}
	public Integer getEnrollMinAge() {
		return enrollMinAge;
	}
	public void setEnrollMinAge(Integer enrollMinAge) {
		this.enrollMinAge = enrollMinAge;
	}
	public Integer getEnrollMaxAge() {
		return enrollMaxAge;
	}
	public void setEnrollMaxAge(Integer enrollMaxAge) {
		this.enrollMaxAge = enrollMaxAge;
	}
	public String getEnrollContactName() {
		return enrollContactName;
	}
	public void setEnrollContactName(String enrollContactName) {
		this.enrollContactName = enrollContactName;
	}
	public String getEnrollContactTel() {
		return enrollContactTel;
	}
	public void setEnrollContactTel(String enrollContactTel) {
		this.enrollContactTel = enrollContactTel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPublishUserId() {
		return publishUserId;
	}
	public void setPublishUserId(String publishUserId) {
		this.publishUserId = publishUserId;
	}

	
	}