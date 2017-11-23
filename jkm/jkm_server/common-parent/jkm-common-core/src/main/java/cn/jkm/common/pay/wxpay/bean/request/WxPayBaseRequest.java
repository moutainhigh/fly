package cn.jkm.common.pay.wxpay.bean.request;

import cn.jkm.common.pay.wxpay.config.WxPayConfig;
import cn.jkm.common.pay.wxpay.exception.WxErrorException;
import cn.jkm.common.pay.wxpay.util.BeanUtils;
import cn.jkm.common.pay.wxpay.util.SignUtils;
import cn.jkm.common.pay.wxpay.xml.ToStringUtils;
import cn.jkm.common.pay.wxpay.xml.XStreamInitializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 14:57 2017/6/19
 */
public abstract class WxPayBaseRequest {
    @XStreamAlias("appid")
    protected String appid;
    @XStreamAlias("mch_id")
    protected String mchId;
    @XStreamAlias("sub_appid")
    protected String subAppId;
    @XStreamAlias("sub_mch_id")
    protected String subMchId;
    @XStreamAlias("nonce_str")
    protected String nonceStr;
    @XStreamAlias("sign")
    protected String sign;

    public WxPayBaseRequest() {
    }

    public static Integer yuanToFee(String yuan) {
        return Integer.valueOf((new BigDecimal(yuan)).setScale(2, 4).multiply(new BigDecimal(100)).intValue());
    }

    protected void checkFields() throws WxErrorException {
        BeanUtils.checkRequiredFields(this);
        this.checkConstraints();
    }

    protected abstract void checkConstraints();

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return this.mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return this.nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSubAppId() {
        return this.subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubMchId() {
        return this.subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public String toXML() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(this.getClass());
        return xstream.toXML(this);
    }

    public void checkAndSign(WxPayConfig config) throws WxErrorException {
        this.checkFields();
        if(StringUtils.isBlank(this.getAppid())) {
            this.setAppid(config.getAppId());
        }

        if(StringUtils.isBlank(this.getMchId())) {
            this.setMchId(config.getMchId());
        }

        if(StringUtils.isBlank(this.getSubAppId())) {
            this.setSubAppId(config.getSubAppId());
        }

        if(StringUtils.isBlank(this.getSubMchId())) {
            this.setSubMchId(config.getSubMchId());
        }

        if(StringUtils.isBlank(this.getNonceStr())) {
            this.setNonceStr(String.valueOf(System.currentTimeMillis()));
        }

        this.setSign(SignUtils.createSign(this, config.getMchKey()));
    }
}
