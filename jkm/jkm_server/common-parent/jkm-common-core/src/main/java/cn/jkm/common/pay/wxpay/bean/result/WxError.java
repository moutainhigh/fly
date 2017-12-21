package cn.jkm.common.pay.wxpay.bean.result;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 15:03 2017/6/19
 */
public class WxError implements Serializable {
    private static final long serialVersionUID = 7869786563361406291L;
    private int errorCode;
    private String errorMsg;
    private String json;

    public WxError() {
    }

    public static WxError fromJson(String json) {
        try {
            return (WxError) GsonBuilder.class.newInstance().create().fromJson(json, WxError.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static WxError.Builder newBuilder() {
        return new WxError.Builder();
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String toString() {
        return this.json != null?this.json:"错误: Code=" + this.errorCode + ", Msg=" + this.errorMsg;
    }

    public static class Builder {
        private int errorCode;
        private String errorMsg;

        public Builder() {
        }

        public WxError.Builder setErrorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public WxError.Builder setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public WxError build() {
            WxError wxError = new WxError();
            wxError.setErrorCode(this.errorCode);
            wxError.setErrorMsg(this.errorMsg);
            return wxError;
        }
    }
}
