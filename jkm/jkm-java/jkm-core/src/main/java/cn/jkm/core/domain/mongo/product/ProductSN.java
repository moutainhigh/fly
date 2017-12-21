package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.BatchType;
import cn.jkm.core.domain.type.ProductStatus;
import cn.jkm.framework.core.annotation.Document;

/**
 * @Author: Guo Fei
 * @Description: 产品（单个产品信息）
 * @Date: 15:38 2017/7/18
 */
@Document(name = "product_sn")
public class ProductSN extends MongoBasic<ProductSN> {

    /**
     * 货架id
     */
    private String shelvesId;
    /**
     * 货架名称
     */
    private String shelvesName;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 入库批次号
     */
    private String batchNo;
    /**
     * 产品货号sn
     */
    private String productSN;
    /**
     * 产品货号后8位
     */
    private String productSNSuffix;
    /**
     * 成本价格
     */
    private long costPrice;
    /**
     * 产品生产时间
     */
    private Long productCreateTime;
    /**
     * 产品过期时间
     */
    private Long productExpireTime;
    /**
     * 保质期
     */
    private Long productShelfLife;
    /**
     * 批次类型
     */
    private BatchType totalType = BatchType.BATCH_STORAGE;
    /**
     * 操作类型
     */
    private BatchType operateType = BatchType.BATCH_ADD_STORAGE;
    /**
     * 操作人员(名称)
     */
    private String operateUserName;
    /**
     * 备注
     */
    private String remark;

    public String getShelvesId() {
        return shelvesId;
    }

    public String getShelvesName() {
        return shelvesName;
    }

    public void setShelvesName(String shelvesName) {
        this.shelvesName = shelvesName;
    }

    public void setShelvesId(String shelvesId) {
        this.shelvesId = shelvesId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProductSN() {
        return productSN;
    }

    public void setProductSN(String productSN) {
        this.productSN = productSN;
    }

    public String getProductSNSuffix() {
        return productSNSuffix;
    }

    public void setProductSNSuffix(String productSNSuffix) {
        this.productSNSuffix = productSNSuffix;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getProductCreateTime() {
        return productCreateTime;
    }

    public void setProductCreateTime(Long productCreateTime) {
        this.productCreateTime = productCreateTime;
    }

    public Long getProductExpireTime() {
        return productExpireTime;
    }

    public void setProductExpireTime(Long productExpireTime) {
        this.productExpireTime = productExpireTime;
    }

    public Long getProductShelfLife() {
        return productShelfLife;
    }

    public void setProductShelfLife(Long productShelfLife) {
        this.productShelfLife = productShelfLife;
    }

    public BatchType getTotalType() {
        return totalType;
    }

    public void setTotalType(BatchType totalType) {
        this.totalType = totalType;
    }

    public BatchType getOperateType() {
        return operateType;
    }

    public void setOperateType(BatchType operateType) {
        this.operateType = operateType;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
