package cn.jkm.core.domain.mysql.order;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.*;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.core.domain.mysql.order
 * @description 子订单实体类
 * @create 2017-07-20 9:29
 */
@Entity
@Table(name = "tb_order_item")
public class OrderItem  extends MySqlBasic<OrderItem>{

    @Column(name = "order_id")
    private String orderId;// 订单id

    @Column(name = "product_name")
    private String productName;// 产品名称.

    @Column(name = "product_unit")
    private long  productUnit;// 产品单价

    @Column(name = "product_num")
    private int productNum;// 产品数量.

    @Column(name = "total_amount")
    private long totalAmount;// 总金额

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(long productUnit) {
        this.productUnit = productUnit;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
