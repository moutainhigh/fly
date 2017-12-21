package cn.jkm.eb.facade.entities;

public class ProductStore {
    /**
     * 库存id
     */
    private String storeId;

    /**
     * 库存数量	
     */
    private String storeNum;

    /**
     * 入库时间.
     */
    private String createTime;


   

	/**
     * 操作人.
     */
    private String storeUserid;

    /**
     * 库存类型.
     */
    private String storeType;

    /**
     * 库存商品.
     */
    private String storeProductid;



	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreUserid() {
		return storeUserid;
	}

	public void setStoreUserid(String storeUserid) {
		this.storeUserid = storeUserid;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getStoreProductid() {
		return storeProductid;
	}

	public void setStoreProductid(String storeProductid) {
		this.storeProductid = storeProductid;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}