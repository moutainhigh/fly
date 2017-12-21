package com.xinfang.enums;
/**
 * 
 * @title RoleEnum.java
 * @package com.xinfang.enums
 * @description 敏感调度角色
 * @author ZhangHuaRong   
 * @update 2017年2月9日 下午2:47:04
 */
public enum RoleEnum {
	SHI_JI_ZHIHUI(1,"调度指挥","市级"),SHI_JI_RENYUAN(2,"调度人员","市级");
	private Integer id;
	private String name;
	private String level;
	private RoleEnum(Integer id, String name, String level) {
		this.id = id;
		this.name = name;
		this.level = level;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	


}
