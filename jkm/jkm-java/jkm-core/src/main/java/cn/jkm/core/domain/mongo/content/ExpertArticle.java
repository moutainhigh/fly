package cn.jkm.core.domain.mongo.content;

/**
 * Created by zhong on 2017/7/18.
 * 专家文章表
 */

public class ExpertArticle extends ContentBasic<ExpertArticle>{

    private String content;                 //专家文章详细内容
    private String expertId;				//专家文章专家id
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
}
