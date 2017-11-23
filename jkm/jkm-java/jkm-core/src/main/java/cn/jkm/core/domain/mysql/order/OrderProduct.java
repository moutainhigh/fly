package cn.jkm.core.domain.mysql.order;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.core.domain.mysql.order
 * @description 订单产品类
 * @create 2017-07-20 9:29
 */
@Entity
@Table(name = "tb_order_product")
public class OrderProduct  extends MySqlBasic<OrderProduct>{

    @Column(name = "order_item_id")
    private String orderItemId;// 子订单id.

    @Column(name = "order_product_sn")
    private String productSN; //单品码.

    @Column(name = "order_product_no")
    private String productNo;//商品码.

    @Column(name = "shelves_id")
    private String shelvesId;//货架id.

    @Column(name = "ord_product_name")
    private String ordProductName;//产品名称.

    @Column(name = "ord_product_url")
    private long ordProductUrl;//产品图片.

    @Column(name = "product_sn_suffix")
    private String productSnSuffix;//产品后8位.

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getProductSN() {
        return productSN;
    }

    public void setProductSN(String productSN) {
        this.productSN = productSN;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getShelvesId() {
        return shelvesId;
    }

    public void setShelvesId(String shelvesId) {
        this.shelvesId = shelvesId;
    }

    public String getOrdProductName() {
        return ordProductName;
    }

    public void setOrdProductName(String ordProductName) {
        this.ordProductName = ordProductName;
    }

    public long getOrdProductUrl() {
        return ordProductUrl;
    }

    public void setOrdProductUrl(long ordProductUrl) {
        this.ordProductUrl = ordProductUrl;
    }

    public String getProductSnSuffix() {
        return productSnSuffix;
    }

    public void setProductSnSuffix(String productSnSuffix) {
        this.productSnSuffix = productSnSuffix;
    }
}
