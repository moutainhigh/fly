package cn.jkm.service.core.exception;

/**
 * Created by werewolf on 2017/7/13.
 */
public class ProviderException extends RuntimeException {
    private Integer code;

    public ProviderException() {
        super();
    }

    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProviderException(Integer code) {
        super(ProviderStatus.messages.get(code));
        this.code = code;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
