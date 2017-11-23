package cn.jkm.service.core.exception;

/**
 * Created by werewolf on 2017/7/13.
 */
public class ProviderException extends RuntimeException {
	
	/**
	 * 数据库操作,insert返回0
	 */
	public static final ProviderException DB_INSERT_RESULT_0 = new ProviderException(9000001, "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final ProviderException DB_UPDATE_RESULT_0 = new ProviderException(9000002, "数据库操作,update返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final ProviderException DB_SELECTONE_IS_NULL = new ProviderException(9000003, "数据库操作,select返回null");
	
	/**
	 * 数据库操作,list返回null
	 */
	public static final ProviderException DB_LIST_IS_NULL = new ProviderException(9000004, "数据库操作,list返回null");
	
	
	/**
	 * 支付异常
	 */
	public static final ProviderException ALIPAY_ERROR = new ProviderException(5000010, "支付异常");
	
	
	
	
	
	
    private Integer code;

    public ProviderException() {
        super();
    }

    public ProviderException(String message) {
        super(message);
        this.code = ProviderStatus.SHOW_ERROR_MESSAGE;
    }

    public ProviderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProviderException(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
