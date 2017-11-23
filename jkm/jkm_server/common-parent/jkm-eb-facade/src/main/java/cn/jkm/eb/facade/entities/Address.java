package cn.jkm.eb.facade.entities;

public class Address {
	
	/**
     * 收货地址id
     */
    private String addressId;

    /**
     * 收貨人.
     */
    private String addressUserName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 所属地区 
     */
    private String area;

    /**
     * 街道
     */
    private String road;

    /**
     * 收货详细地址
     */
    private String addDetail;

    /**
     *
     * 是否是默认地址.
     */
    private String isDefault;

    /**
     * 
     * 收货地址状态.
     */
    private String addrStauts;
    
    /**
     * 关联用户id
     */
    private String userId;

    

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddressUserName() {
		return addressUserName;
	}

	public void setAddressUserName(String addressUserName) {
		this.addressUserName = addressUserName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getAddDetail() {
		return addDetail;
	}

	public void setAddDetail(String addDetail) {
		this.addDetail = addDetail;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getAddrStauts() {
		return addrStauts;
	}

	public void setAddrStauts(String addrStauts) {
		this.addrStauts = addrStauts;
	}
}