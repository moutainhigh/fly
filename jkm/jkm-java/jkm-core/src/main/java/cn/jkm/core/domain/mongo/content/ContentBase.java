package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;


/**
 * ContentBase entity. @author zhengzb 2017/7/15
 * 内容基本表
 */
@Document(name = "content_base")
@Deprecated
public class ContentBase extends MongoBasic<ContentBase> {

	// Fields

	private String title;					//内容标题
	private Boolean type;					//内容类型1：帖子 2：咨询 3：专家文章 4：配方
	private Long publishTime;				//内容发布时间
	private Long updateTime;				//内容修改时间
	private Long topTime;					//内容置顶时间
	private String channelId;				//内容所属频道栏目id
	private Integer commentNum;				//评论数
	private Integer browseNum;				//浏览数
	private Integer pointNum;				//点赞数
	private Integer collectionNum;			//收藏数
	private String publishUserId;			//内容发布人
	private Short contentStatus;			//内容状态 1.待提交；2.待审核；3.显示；4.置顶；5.隐藏；6.删除；
	private String picUrl;					//内容标题图片地址
	private Short isTop;					//是否置顶
	private Integer topOrder;				//置顶顺序
	private String detail;					//内容详情
	private String postFormulaInfo;			//内容中关联的配方id
	private String postProductionInfo;		//内容中关联的产品id
	private String handleUserId;			//内容审核人
	private Long handleTime;				//审核时间
	private String expertId;				//专家文章专家id
	private String formulaInfo;				//配方详情
	private String formulaSymptom;			//配方适应证
	private Integer formulaBuyNum;			//配方购买量
	private String formulaProductionInfo;	//配方包含的商品信息
	private String formulaExpertId;			//配方专家id
	private Integer complaintCount;			//投诉次数
	private Integer complaintHandleCount;	//已处理次数
	
	public ContentBase() {
		super();
	}

	public ContentBase(String title, Boolean type, Long publishTime,
					   Long updateTime, Long topTime,
					   String channelId, Integer commentNum, Integer browseNum,
					   Integer pointNum, Integer collectionNum, String publishUserId,
					   Short contentStatus, String picUrl, Short isTop, Integer topOrder,
					   String detail, String postFormulaInfo, String postProductionInfo,
					   String handleUserId, Long handleTime, String expertId,
					   String formulaInfo, String formulaSymptom, Integer formulaBuyNum,
					   String formulaProductionInfo, String formulaExpertId,
					   Integer complaintCount, Integer complaintHandleCount) {
		super();
		this.title = title;
		this.type = type;
		this.publishTime = publishTime;
		this.updateTime = updateTime;
		this.topTime = topTime;
		this.channelId = channelId;
		this.commentNum = commentNum;
		this.browseNum = browseNum;
		this.pointNum = pointNum;
		this.collectionNum = collectionNum;
		this.publishUserId = publishUserId;
		this.contentStatus = contentStatus;
		this.picUrl = picUrl;
		this.isTop = isTop;
		this.topOrder = topOrder;
		this.detail = detail;
		this.postFormulaInfo = postFormulaInfo;
		this.postProductionInfo = postProductionInfo;
		this.handleUserId = handleUserId;
		this.handleTime = handleTime;
		this.expertId = expertId;
		this.formulaInfo = formulaInfo;
		this.formulaSymptom = formulaSymptom;
		this.formulaBuyNum = formulaBuyNum;
		this.formulaProductionInfo = formulaProductionInfo;
		this.formulaExpertId = formulaExpertId;
		this.complaintCount = complaintCount;
		this.complaintHandleCount = complaintHandleCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
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

	public Long getTopTime() {
		return topTime;
	}

	public void setTopTime(Long topTime) {
		this.topTime = topTime;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getBrowseNum() {
		return browseNum;
	}

	public void setBrowseNum(Integer browseNum) {
		this.browseNum = browseNum;
	}

	public Integer getPointNum() {
		return pointNum;
	}

	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	public String getPublishUserId() {
		return publishUserId;
	}

	public void setPublishUserId(String publishUserId) {
		this.publishUserId = publishUserId;
	}

	

	public Short getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(Short contentStatus) {
		this.contentStatus = contentStatus;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Short getIsTop() {
		return isTop;
	}

	public void setIsTop(Short isTop) {
		this.isTop = isTop;
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

	public String getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}

	public Long getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Long handleTime) {
		this.handleTime = handleTime;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getFormulaInfo() {
		return formulaInfo;
	}

	public void setFormulaInfo(String formulaInfo) {
		this.formulaInfo = formulaInfo;
	}

	public String getFormulaSymptom() {
		return formulaSymptom;
	}

	public void setFormulaSymptom(String formulaSymptom) {
		this.formulaSymptom = formulaSymptom;
	}

	public Integer getFormulaBuyNum() {
		return formulaBuyNum;
	}

	public void setFormulaBuyNum(Integer formulaBuyNum) {
		this.formulaBuyNum = formulaBuyNum;
	}

	public String getFormulaProductionInfo() {
		return formulaProductionInfo;
	}

	public void setFormulaProductionInfo(String formulaProductionInfo) {
		this.formulaProductionInfo = formulaProductionInfo;
	}

	public String getFormulaExpertId() {
		return formulaExpertId;
	}

	public void setFormulaExpertId(String formulaExpertId) {
		this.formulaExpertId = formulaExpertId;
	}

	public Integer getComplaintCount() {
		return complaintCount;
	}

	public void setComplaintCount(Integer complaintCount) {
		this.complaintCount = complaintCount;
	}

	public Integer getComplaintHandleCount() {
		return complaintHandleCount;
	}

	public void setComplaintHandleCount(Integer complaintHandleCount) {
		this.complaintHandleCount = complaintHandleCount;
	}

	
}