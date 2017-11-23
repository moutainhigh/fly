package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.BatchType;
import cn.jkm.framework.core.annotation.Document;

import java.util.Set;

/**
 * @Author: Guo Fei
 * @Description: 产品批次
 * @Date: 10:01 2017/7/18
 */
@Document(name = "batch")
public class Batch extends MongoBasic<Batch> {

    /**
     * 货架id
     */
    private String shelvesId;
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
     * 产品批次号
     */
    private String productBatchNo;
    /**
     * 成本价格
     */
    private long costPrice;
    /**
     * 产品数量
     */
    private long productNum;
    /**
     * 批次类型
     */
    private BatchType batchType = BatchType.BATCH_STORAGE;
    /**
     * 操作人员(名称)
     */
    private String operateUserName;
    /**
     * 备注
     */
    private String remark;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getShelvesId() {
        return shelvesId;
    }

    public void setShelvesId(String shelvesId) {
        this.shelvesId = shelvesId;
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

    public String getProductBatchNo() {
        return productBatchNo;
    }

    public void setProductBatchNo(String productBatchNo) {
        this.productBatchNo = productBatchNo;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public long getProductNum() {
        return productNum;
    }

    public void setProductNum(long productNum) {
        this.productNum = productNum;
    }

    public BatchType getBatchType() {
        return batchType;
    }

    public void setBatchType(BatchType batchType) {
        this.batchType = batchType;
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
