package cn.jkm.core.domain.mysql.order;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.LogisticsStatus;

import javax.persistence.*;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.core.domain.mysql.order
 * @description 退货子订单类
 * @create 2017-07-20 9:29
 */
@Entity
@Table(name = "tb_refund_order_item")
public class RefundOrderItem extends MySqlBasic<RefundOrderItem>{

    @Column(name = "order_id")
    private String orderId;// 订单id

    @Column(name = "product_name")
    private String productName;//产品名称.

    @Column(name = "product_unit")
    private long  productUnit;// 产品单价

    @Column(name = "product_deduction")
    private long  productDeduction;// 产品折后价

    @Column(name = "product_num")
    private int productNum;// 产品数量.

    @Column(name = "total_amount")
    private long totalAmount;// 总金额

    @Column(name = "logistics_status")
    @Enumerated(EnumType.STRING)
    private LogisticsStatus logisticsStatus;// 物流状态.

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

    public LogisticsStatus getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(LogisticsStatus logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }
}
