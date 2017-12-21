package cn.jkm.uis.facade.entities;
/*
 * 后台用户
 */
import java.util.List;

public class SysUser {
	private String id;
	private String name;
	private String password;
	private long createTime;
	private int status;

	private List<Role> roles;
}
