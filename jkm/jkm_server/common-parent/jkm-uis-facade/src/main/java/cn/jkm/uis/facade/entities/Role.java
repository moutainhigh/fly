package cn.jkm.uis.facade.entities;
/*
 * 角色
 */

import java.util.List;

public class Role {
	private String id;
	private String name;
	private long createTime;
	
	private List<PermissionInfo> permissionInfos;
}
