package cn.jkm.core.domain.mysql.order;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.BillType;

import javax.persistence.*;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.core.domain.mysql.order
 * @description 订单发票类
 * @create 2017-07-20 9:29
 */
@Entity
@Table(name ="tb_order_bill")
public class OrderBill extends MySqlBasic<OrderBill>{

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BillType type;// 发票类型.

    @Column(name = "desc")
    private String desc; // 发票描述

    @Column(name = "company_name")
    private String companyName;//单位名称.

    @Column(name = "order_id")
    private String orderId;// 订单id

    @Column(name = "org_code")
    private String orgCode;//组织机构代码

    public BillType getType() {
        return type;
    }

    public void setType(BillType type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
