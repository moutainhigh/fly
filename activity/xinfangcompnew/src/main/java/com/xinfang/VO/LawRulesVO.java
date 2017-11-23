package com.xinfang.VO;

/**
 * 法律法规VO
 * 
 * @author sunbjx Date: 2017年5月24日下午4:07:49
 */
public class LawRulesVO {
	// 法律名称
	private String name;
	// 法律类别
	private String type;
	// 法律内容
	private String content;
	// 参考案例
	private String referenceCase;
	// 法律法规ID
	private Integer lawId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReferenceCase() {
		return referenceCase;
	}

	public void setReferenceCase(String referenceCase) {
		this.referenceCase = referenceCase;
	}

	public Integer getLawId() {
		return lawId;
	}

	public void setLawId(Integer lawId) {
		this.lawId = lawId;
	}

}
