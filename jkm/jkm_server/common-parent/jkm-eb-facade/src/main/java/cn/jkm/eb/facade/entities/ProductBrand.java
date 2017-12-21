package cn.jkm.eb.facade.entities;

public class ProductBrand {
    /**
     *
     * 品牌id
     */
    private String brandId;

    /**
     *
     * 品牌名称
     */
    private String brandName;

    /**
     *
     * 品牌图片
     */
    private String brandImage;

    /**
     * 
     * 品牌顺序.
     */
    private String brandOrder;

    /**
     *
     * 品牌状态1.删除
     */
    private String brandStatus;
    

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandImage() {
		return brandImage;
	}

	public void setBrandImage(String brandImage) {
		this.brandImage = brandImage;
	}

	public String getBrandOrder() {
		return brandOrder;
	}

	public void setBrandOrder(String brandOrder) {
		this.brandOrder = brandOrder;
	}

	public String getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(String brandStatus) {
		this.brandStatus = brandStatus;
	}

}