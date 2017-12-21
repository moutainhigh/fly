package cn.jkm.eb.facade.entities;

public class Product extends ProductKey {
	
    /**
     *
     * 产品名字
     */
    private String productName;

    /**
     * 
     * 价格
     */
    private String price;

    /**
     * 
     * 产品格式
     */
    private String productFormat;

    /**
     * 产品描述
     *
     */
    private String productDesc;

    /**
     *
     * 产品url
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

    /**
     *
     * 生产时间.
     */
    private String productTime;

    /**
     *
     * 产品用量
     */
    private String productUsage;

    /**
     *
     * 产品成分. 
     */
    private String productCollection;

    /**
     *
     * 产品功效
     */
    private String productEffect;

    /**
     *
     * 是否可以退货.
     */
    private String isExchange;

    /**
     *
     * 产品状态
     */
    private String productStatus;
    
    
    /**
     * 过期时间.
     */
    private String expireTime;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getProductTime() {
		return productTime;
	}

	public void setProductTime(String productTime) {
		this.productTime = productTime;
	}

	public String getProductUsage() {
		return productUsage;
	}

	public void setProductUsage(String productUsage) {
		this.productUsage = productUsage;
	}

	public String getProductCollection() {
		return productCollection;
	}

	public void setProductCollection(String productCollection) {
		this.productCollection = productCollection;
	}

	public String getProductEffect() {
		return productEffect;
	}

	public void setProductEffect(String productEffect) {
		this.productEffect = productEffect;
	}

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
}