package com.xinfang.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-08 18:29
 **/
public class FcGldybEntityPK implements Serializable {
    private Integer codeId;
    private Integer ruleId;

    @Column(name = "code_id", nullable = false)
    @Id
    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    @Column(name = "rule_id", nullable = false)
    @Id
    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FcGldybEntityPK that = (FcGldybEntityPK) o;

        if (codeId != that.codeId) return false;
        if (ruleId != that.ruleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codeId;
        result = 31 * result + ruleId;
        return result;
    }
}
