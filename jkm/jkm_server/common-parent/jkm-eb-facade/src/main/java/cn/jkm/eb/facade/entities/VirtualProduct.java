package cn.jkm.eb.facade.entities;

public class VirtualProduct {
    /**
     *
     * 虚拟产品主键
     */
    private String producerId;

    /**
     *
     * 虚拟产品名称.
     */
    private String productName;

    /**
     *
     * 产品价格.
     */
    private String Price;

    /**
     *
     * 产品规格
     */
    private String productFormat;

    /**
     *
     * 产品描述
     */
    private String productDesc;

    /**
     *
     * 产品图片.
     */
    private String productUrl;

    /**
     *
     * 缩略图
     */
    private String thumbnail;

    /**
     *
     * 高清图
     */
    private String hdimage;
    
    
    
	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getProductFormat() {
		return productFormat;
	}

	public void setProductFormat(String productFormat) {
		this.productFormat = productFormat;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getHdimage() {
		return hdimage;
	}

	public void setHdimage(String hdimage) {
		this.hdimage = hdimage;
	}

}