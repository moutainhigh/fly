package cn.jkm.core.domain.mysql.order;/**
 * Created by admin on 2017/7/17.
 */

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.LogisticsStatus;
import cn.jkm.core.domain.type.RefundStatus;

import javax.persistence.*;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.core.domain.mysql.order
 * @description 退货订单申请实体类
 * @create 2017-07-20 9:29
 */
@Entity
@Table(name = "tb_refund_order_applyment")
public class RefundOrderApplyment extends MySqlBasic<RefundOrderApplyment> {


    @Column(name = "order_id")
    private String orderId;// 订单id

    //@Column(name = "order_item_id")
    //private int orderItemId;// 退货子订单id

    // 1.通过 /售后 2.拒绝 /已完成 修改订单状态.
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    //@Enumerated(EnumType.STRING)
    private RefundStatus type;

    @Column(name = "reason")
    private String reason;//退货原因

    @Column(name = "reject")
    private String reject;//拒绝理由

    @Column(name = "order_url")
    private String orderUrl;// 订单退货凭证

    @Column(name = "desc")
    private String desc;//订单退货说明.

    @Column(name = "logistics_id")
    private String orderLogisticsId;// 订单物流id

    @Column(name = "total_amount")
    private long totalAmount; // 订单退货总金额

    @Column(name = "logistics_status")
    @Enumerated(EnumType.STRING)
    private LogisticsStatus logisticsStatus;// 物流状态.

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public RefundStatus getType() {
        return type;
    }

    public void setType(RefundStatus type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrderLogisticsId() {
        return orderLogisticsId;
    }

    public void setOrderLogisticsId(String orderLogisticsId) {
        this.orderLogisticsId = orderLogisticsId;
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
