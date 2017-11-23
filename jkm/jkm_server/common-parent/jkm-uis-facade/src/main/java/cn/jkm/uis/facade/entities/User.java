package cn.jkm.uis.facade.entities;

/**
 * 用户
 */
public class User {
	
	private String id;
	private int type;//0 正常用户  1 后台客户
	private String name;//姓名
	private String nickname;//昵称
	private int sex;//性别,1：男；2：女
	private int age;//年龄
	private long address;//住址
	private long birthday;//生日
	private String tel;//手机号
	private String headUrl;//头像
	private long createTime;//注册时间
	private int status;//1:正常；2:锁定；3:删除
	private String password;//密码
	private String idCard;//身份证
	private String valitationCode;//邀请码
	private String valitationedCode;//被邀请码
	private String supId;//上级用户id
	private String thirdId;//第三方平台id
	private String deviceNum;//设备号
	
}
