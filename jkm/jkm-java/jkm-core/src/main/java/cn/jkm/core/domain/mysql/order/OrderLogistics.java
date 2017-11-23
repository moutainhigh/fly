package cn.jkm.core.domain.mysql.order;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.LogisticsStatus;

import javax.persistence.*;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.core.domain.mysql.order
 * @description 订单物流类
 * @create 2017-07-20 9:29
 */
@Entity
@Table(name = "tb_order_logistics")
public class OrderLogistics extends MySqlBasic<OrderLogistics>{

    @Column(name = "order_id")
    private String orderId;//订单id

    @Column(name = "order_iteam_id")
    private String orderIteamId;//子订单id


    @Column(name = "company_name")
    private String companyName;// 公司名称

    @Column(name = "code")
    private String code;// 编码

    @Column(name = "logistics_no")
    private String logisticsNo;// 物流运单号

    @Column(name = "logistics_status")
    @Enumerated(EnumType.STRING)
    private LogisticsStatus logisticsStatus;// 物流状态.

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderIteamId() {
        return orderIteamId;
    }

    public void setOrderIteamId(String orderIteamId) {
        this.orderIteamId = orderIteamId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public LogisticsStatus getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(LogisticsStatus logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }
}
