package cn.jkm.common.pay.wxpay.exception;

import cn.jkm.common.pay.wxpay.bean.result.WxError;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 15:02 2017/6/19
 */
public class WxErrorException extends Exception {
    private static final long serialVersionUID = -6357149550353160810L;
    private WxError error;

    public WxErrorException(WxError error) {
        super(error.toString());
        this.error = error;
    }

    public WxErrorException(WxError error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public WxError getError() {
        return this.error;
    }
}
