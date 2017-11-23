package cn.jkm.core.domain.mysql.product;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.Column;

/**
 * @Author: Guo Fei
 * @Description: 经销商产品库存
 * @Date: 17:48 2017/7/19
 */
public class DealerStock extends MySqlBasic<DealerStock> {

    /**
     * 产品id
     */
    @Column(name = "product_id", columnDefinition = "bigint COMMENT '产品id'")
    private String productId;
    /**
     * 经销商id
     */
    @Column(name = "dealer_id", columnDefinition = "bigint COMMENT '经销商id'")
    private String dealerId;
    /**
     * 实际库存
     */
    @Column(name = "real_stock_num", columnDefinition = "bigint COMMENT '实际库存'")
    private int realStockNum;
    /**
     * 特殊库存
     */
    @Column(name = "spec_stock_num", columnDefinition = "bigint COMMENT '特殊库存'")
    private int specStockNum;
    /**
     * 动态库存
     */
    @Column(name = "dynamic_stockNum", columnDefinition = "bigint COMMENT '动态库存'")
    private int dynamicStockNum;
    /**
     * 总库存
     */
    @Column(name = "total_stock_num", columnDefinition = "bigint COMMENT '总库存'")
    private int totalStockNum;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public int getRealStockNum() {
        return realStockNum;
    }

    public void setRealStockNum(int realStockNum) {
        this.realStockNum = realStockNum;
    }

    public int getSpecStockNum() {
        return specStockNum;
    }

    public void setSpecStockNum(int specStockNum) {
        this.specStockNum = specStockNum;
    }

    public int getDynamicStockNum() {
        return dynamicStockNum;
    }

    public void setDynamicStockNum(int dynamicStockNum) {
        this.dynamicStockNum = dynamicStockNum;
    }

    public int getTotalStockNum() {
        return totalStockNum;
    }

    public void setTotalStockNum(int totalStockNum) {
        this.totalStockNum = totalStockNum;
    }
}
