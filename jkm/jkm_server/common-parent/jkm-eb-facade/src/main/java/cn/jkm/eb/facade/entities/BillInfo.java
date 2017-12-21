package cn.jkm.eb.facade.entities;

public class BillInfo {
    /**
     *  发票主键.
     * 
     */
    private String billId;

    /**
     *
     *  发票类型. 1个人 2 公司
     */
    private String billType;

    /**
     * 发票描述.
     * 
     */
    private String billDesc;

    /**
     *
     * 单位名称.
     */
    private String companyName;

    
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillDesc() {
		return billDesc;
	}

	public void setBillDesc(String billDesc) {
		this.billDesc = billDesc;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}