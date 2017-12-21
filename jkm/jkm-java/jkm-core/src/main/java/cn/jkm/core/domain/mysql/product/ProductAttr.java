package cn.jkm.core.domain.mysql.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.SwitchStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: Guo Fei
 * @Description: 产品属性
 * @Date: 10:41 2017/7/25
 */
@Entity
@Table(name = "tb_Product_attr")
public class ProductAttr extends MongoBasic<ProductAttr> {

    /**
     * 产品id
     */
    @Column(name = "product_id", columnDefinition = "varchar(64) COMMENT '产品id'")
    private String productId;
    /**
     * 模板id
     */
    @Column(name = "template_id", columnDefinition = "varchar(64) COMMENT '模板id'")
    private String templateId;
    /**
     * 属性id
     */
    @Column(name = "attr_id", columnDefinition = "varchar(64) COMMENT '属性id'")
    private String attrId;
    /**
     * 属性key
     */
    @Column(name = "attr_key", columnDefinition = "varchar(64) COMMENT '属性key'")
    private String attrKey;
    /**
     * 属性value
     */
    @Column(name = "attr_value", columnDefinition = "varchar(64) COMMENT '属性value'")
    private String attrValue;
    /**
     * 是否必填
     */
    @Column(name = "required", columnDefinition = "varchar(64) COMMENT '是否必填'")
    private SwitchStatus required = SwitchStatus.TURN_ON;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public SwitchStatus getRequired() {
        return required;
    }

    public void setRequired(SwitchStatus required) {
        this.required = required;
    }
}
