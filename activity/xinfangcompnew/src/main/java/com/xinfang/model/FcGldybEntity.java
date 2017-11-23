package com.xinfang.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-08 18:29
 **/
@Entity
@Table(name = "fc_gldyb")
@IdClass(FcGldybEntityPK.class)
public class FcGldybEntity {
	private Integer codeId;
	private int ruleId;
	private String des;
	private Byte isFlag;
	private Integer px;

	@Id
	@Column(name = "code_id", nullable = false)
	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	@Id
	@Column(name = "rule_id", nullable = false)
	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	@Basic
	@Column(name = "des", nullable = true, length = 100)
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Basic
	@Column(name = "is_flag", nullable = true)
	public Byte getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Byte isFlag) {
		this.isFlag = isFlag;
	}

	@Basic
	@Column(name = "px", nullable = true)
	public Integer getPx() {
		return px;
	}

	public void setPx(Integer px) {
		this.px = px;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		FcGldybEntity that = (FcGldybEntity) o;

		if (codeId != that.codeId)
			return false;
		if (ruleId != that.ruleId)
			return false;
		if (des != null ? !des.equals(that.des) : that.des != null)
			return false;
		if (isFlag != null ? !isFlag.equals(that.isFlag) : that.isFlag != null)
			return false;
		if (px != null ? !px.equals(that.px) : that.px != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = codeId;
		result = 31 * result + ruleId;
		result = 31 * result + (des != null ? des.hashCode() : 0);
		result = 31 * result + (isFlag != null ? isFlag.hashCode() : 0);
		result = 31 * result + (px != null ? px.hashCode() : 0);
		return result;
	}
}
