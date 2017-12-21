package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.SwitchStatus;
import cn.jkm.framework.core.annotation.Document;

/**
 * @Author: Guo Fei
 * @Description: 产品属性
 * @Date: 10:02 2017/7/18
 */
@Document(name = "attr")
public class Attr extends MongoBasic<Attr> {
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 属性key
     */
    private String attrKey;
    /**
     * 属性值
     */
    private String attrValue;
    /**
     * 是否必填
     */
    private SwitchStatus attrRequired = SwitchStatus.TURN_ON;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public SwitchStatus getAttrRequired() {
        return attrRequired;
    }

    public void setAttrRequired(SwitchStatus attrRequired) {
        this.attrRequired = attrRequired;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
