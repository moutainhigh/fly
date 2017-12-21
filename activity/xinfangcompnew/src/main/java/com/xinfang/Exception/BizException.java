package com.xinfang.Exception;

/**
 * 
 * @title BizException.java
 * @package com.xinfang.Exception
 * @description 业务异常基类，所有业务异常都必须继承于此异常 编码可以扩展
 * @author ZhangHuaRong   
 * @update 2016年12月20日 下午8:55:10
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 数据库操作,insert返回0
	 */
	public static final BizException DB_INSERT_RESULT_0 = new BizException(90040001, "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final BizException DB_UPDATE_RESULT_0 = new BizException(90040002, "数据库操作,update返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final BizException DB_SELECTONE_IS_NULL = new BizException(90040003, "数据库操作,select返回null");

	public static final BizException CODE_EXCEPTION = new BizException(5000400, "网络异常");

	/**
	 * 数据库操作,list返回null
	 */
	public static final BizException DB_LIST_IS_NULL = new BizException(90040004, "数据库操作,list返回null");

	/**
	 * Token 验证不通过
	 */
	public static final BizException TOKEN_IS_ILLICIT = new BizException(90040005, "Token 验证非法");
	/**
	 * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
	 */
	public static final BizException SESSION_IS_OUT_TIME = new BizException(90040006, "会话超时");
	
	
	public static final BizException WEIZHI_YICHANG = new BizException(90040008, "未知错误");
	
	public static final BizException DB_delete_IS_0 = new BizException(90040009, "数据库操作,delete返回0");
    
	public static final BizException ACCOUNT_IS_NOT_AVAILABLE = new BizException(10001, "账户不可用");
	
	public static final BizException REPEAT_ADD = new BizException(10002, "重复添加");
	
	public static final BizException NO_LEADER = new BizException(10003, "没有分管领导");

	public static final BizException RECEIVE_DATE_EXCEPTION = new BizException(10004, "接收数据异常，前台传入数据格式有误");
	public static final BizException NO_SHOUWG_RECEVIER = new BizException(10007, "没有设置收文岗和案件接收人");
	public static final BizException IS_NOT_PHONE = new BizException(10008, "手机号格式不正确!");
	public static final BizException CODE_TIME_OUT = new BizException(10009, "验证码已经过期!");
	public static final BizException CODE_WRONG = new BizException(10010, "验证码不正确!");
	public static final BizException PHONE_REPEAT = new BizException(10011, "该号码已经被注册!");
	public static final BizException USER_NO_EXIT = new BizException(10012, "	请输入正确的用户名和密码!");
	
	public static final BizException OLD_PW_WRONG = new BizException(10013, "	旧密码不正确!");
	
	public static final BizException USER_OR_PW_WRONG = new BizException(10014, "用户名或密码不正确!");
	
	public static final BizException NO_RECEIVE = new BizException(10015, "没有单位收文岗");

	
	
	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BizException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BizException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BizException newInstance(String msgFormat, Object... args) {
		return new BizException(this.code, msgFormat, args);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message) {
		super(message);
	}
}
