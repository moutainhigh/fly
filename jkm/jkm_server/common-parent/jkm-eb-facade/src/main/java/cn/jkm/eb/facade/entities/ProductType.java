package cn.jkm.eb.facade.entities;

public class ProductType {
    /**
     *
     * 商品分类id
     */
    private String proTypeId;

    /**
     *
     * 商品分类父id
     */
    private String typeParId;

    /**
     *
     * 分类名字
     */
    private String typeName;

    /**
     *
     * 分类状态1.删除
     */
    private String typeStatus;

	public String getProTypeId() {
		return proTypeId;
	}

	public void setProTypeId(String proTypeId) {
		this.proTypeId = proTypeId;
	}

	public String getTypeParId() {
		return typeParId;
	}

	public void setTypeParId(String typeParId) {
		this.typeParId = typeParId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

  
  }