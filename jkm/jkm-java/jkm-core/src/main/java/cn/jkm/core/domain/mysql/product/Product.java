package cn.jkm.core.domain.mysql.product;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.SwitchStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: Guo Fei
 * @Description: 产品信息表
 * @Date: 14:12 2017/7/27
 */
@Entity
@Table(name = "tb_product")
public class Product extends MySqlBasic<Product> {

    /**
     * 产品编码
     */
    @Column(name = "goods_no", columnDefinition = "varchar(64) COMMENT '产品编码'")
    private String goodsNo;
    /**
     * 产品名称
     */
    @Column(name = "goods_name", columnDefinition = "varchar COMMENT '产品名称'")
    private String goodsName;
    /**
     * 产品分类id，使用分隔符“|”
     */
    @Column(name = "category_id", columnDefinition = "varchar COMMENT '产品分类id'")
    private String categoryId;
    /**
     * 品牌id
     */
    @Column(name = "branch_id", columnDefinition = "varchar(64) COMMENT '产品品牌'")
    private String branchId;
    /**
     * 模板id
     */
    @Column(name = "template_id", columnDefinition = "varchar(64) COMMENT '属性模板id'")
    private String templateId;
    /**
     * 营销规则id
     */
    @Column(name = "marketing_rule_id", columnDefinition = "varchar(64) COMMENT '营销规则id'")
    private String marketingRuleId;
    /**
     * 产品缩略图
     */
    @Column(name = "product_thumb", columnDefinition = "varchar(64) COMMENT '产品缩略图'")
    private String productThumb;
    /**
     * 产品图片
     */
    @Column(name = "product_images", columnDefinition = "varchar COMMENT '产品图片'")
    private String productImages;
    /**
     * 成本价
     */
    @Column(name = "cost_price", columnDefinition = "bigint COMMENT '成本价'")
    private long costPrice;
    /**
     * 出售价
     */
    @Column(name = "price", columnDefinition = "bigint COMMENT '出售价'")
    private long price;
    /**
     * 是否允许退货
     */
    @Column(name = "allow_return", columnDefinition = "varchar COMMENT '是否允许退货'")
    private SwitchStatus allowReturn = SwitchStatus.TURN_OFF;
    /**
     * 用量
     */
    @Column(name = "dosage", columnDefinition = "varchar COMMENT '用量'")
    private String dosage;
    /**
     * 成分
     */
    @Column(name = "component", columnDefinition = "varchar COMMENT '成分'")
    private String component;
    /**
     * 功效
     */
    @Column(name = "effect", columnDefinition = "varchar COMMENT '功效'")
    private String effect;
    /**
     * 产品简介
     */
    @Column(name = "product_brief", columnDefinition = "varchar COMMENT '产品简介'")
    private String productBrief;
    /**
     * 产品详情内容
     */
    @Column(name = "detail", columnDefinition = "text COMMENT '产品详情内容'")
    private String detail;
    /**
     * 产品保质期
     */
    @Column(name = "shelf_life", columnDefinition = "int COMMENT '产品保质期'")
    private int shelfLife;
    /**
     * 实际库存
     */
    @Column(name = "real_num", columnDefinition = "bigint COMMENT '实际库存'")
    private int realNum;
    /**
     * 特殊库存数
     */
    @Column(name = "spec_num", columnDefinition = "bigint COMMENT '特殊库存数'")
    private int specNum;
    /**
     * 动态库存
     */
    @Column(name = "dynamic_num", columnDefinition = "bigint COMMENT '动态库存'")
    private int dynamicNum;
    /**
     * 总库存数
     */
    @Column(name = "total_num", columnDefinition = "varchar(64) COMMENT '总库存数'")
    private int totalStockNum;

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getMarketingRuleId() {
        return marketingRuleId;
    }

    public void setMarketingRuleId(String marketingRuleId) {
        this.marketingRuleId = marketingRuleId;
    }

    public String getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(String productThumb) {
        this.productThumb = productThumb;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public SwitchStatus getAllowReturn() {
        return allowReturn;
    }

    public void setAllowReturn(SwitchStatus allowReturn) {
        this.allowReturn = allowReturn;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getRealNum() {
        return realNum;
    }

    public void setRealNum(int realNum) {
        this.realNum = realNum;
    }

    public int getSpecNum() {
        return specNum;
    }

    public void setSpecNum(int specNum) {
        this.specNum = specNum;
    }

    public int getDynamicNum() {
        return dynamicNum;
    }

    public void setDynamicNum(int dynamicNum) {
        this.dynamicNum = dynamicNum;
    }

    public int getTotalStockNum() {
        return totalStockNum;
    }

    public void setTotalStockNum(int totalStockNum) {
        this.totalStockNum = totalStockNum;
    }
}
