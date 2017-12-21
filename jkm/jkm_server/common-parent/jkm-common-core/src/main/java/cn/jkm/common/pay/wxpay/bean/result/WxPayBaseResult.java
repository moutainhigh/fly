package cn.jkm.common.pay.wxpay.bean.result;

import cn.jkm.common.pay.wxpay.exception.WxErrorException;
import cn.jkm.common.pay.wxpay.service.WxPayService;
import cn.jkm.common.pay.wxpay.util.SignUtils;
import cn.jkm.common.pay.wxpay.xml.ToStringUtils;
import cn.jkm.common.pay.wxpay.xml.XStreamInitializer;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 15:32 2017/6/19
 */
public class WxPayBaseResult {
    @XStreamAlias("return_code")
    protected String returnCode;
    @XStreamAlias("return_msg")
    protected String returnMsg;
    @XStreamAlias("result_code")
    private String resultCode;
    @XStreamAlias("err_code")
    private String errCode;
    @XStreamAlias("err_code_des")
    private String errCodeDes;
    @XStreamAlias("appid")
    private String appid;
    @XStreamAlias("mch_id")
    private String mchId;
    @XStreamAlias("sub_appid")
    private String subAppId;
    @XStreamAlias("sub_mch_id")
    private String subMchId;
    @XStreamAlias("nonce_str")
    private String nonceStr;
    @XStreamAlias("sign")
    private String sign;
    private String xmlString;
    private Document xmlDoc;

    public WxPayBaseResult() {
    }

    public static String feeToYuan(Integer fee) {
        return (new BigDecimal(Double.valueOf((double)fee.intValue()).doubleValue() / 100.0D)).setScale(2, 4).toPlainString();
    }
    @SuppressWarnings("unchecked")
    public static <T extends WxPayBaseResult> T fromXML(String xmlString, Class<T> clz) {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(clz);
		T result = (T) xstream.fromXML(xmlString);
        result.setXmlString(xmlString);
        return result;
    }

    public String getXmlString() {
        return this.xmlString;
    }

    public void setXmlString(String xmlString) {
        this.xmlString = xmlString;
    }

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public String getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return this.returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return this.errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

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

    public Map<String, String> toMap() {
        if(StringUtils.isBlank(this.xmlString)) {
            throw new RuntimeException("xml数据有问题，请核实！");
        } else {
            Map<String, String> result = Maps.newHashMap();
            Document doc = this.getXmlDoc();

            try {
                NodeList list = (NodeList) XPathFactory.newInstance().newXPath().compile("/xml/*").evaluate(doc, XPathConstants.NODESET);
                int len = list.getLength();

                for(int i = 0; i < len; ++i) {
                    result.put(list.item(i).getNodeName(), list.item(i).getTextContent());
                }

                return result;
            } catch (XPathExpressionException var6) {
                throw new RuntimeException("非法的xml文本内容：" + this.xmlString);
            }
        }
    }

    protected Document getXmlDoc() {
        if(this.xmlDoc != null) {
            return this.xmlDoc;
        } else {
            try {
                this.xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(this.xmlString.getBytes("UTF-8")));
                return this.xmlDoc;
            } catch (IOException | ParserConfigurationException | SAXException var2) {
                throw new RuntimeException("非法的xml文本内容：" + this.xmlString);
            }
        }
    }

    protected String getXmlValue(String... path) {
        Document doc = this.getXmlDoc();
        String expression = String.format("/%s//text()", new Object[]{Joiner.on("/").join(path)});

        try {
            return (String)XPathFactory.newInstance().newXPath().compile(expression).evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException var5) {
            throw new RuntimeException("未找到相应路径的文本：" + expression);
        }
    }

    protected Integer getXmlValueAsInt(String... path) {
        String result = this.getXmlValue(path);
        return StringUtils.isBlank(result)?null:Integer.valueOf(result);
    }

    public void checkResult(WxPayService wxPayService) throws WxErrorException {
        Map<String, String> map = this.toMap();
        if(this.getSign() != null && !SignUtils.checkSign(map, wxPayService.getConfig().getMchKey())) {
            this.getLogger().debug("校验结果签名失败，参数：{}", map);
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg("参数格式校验错误！").build());
        } else if(!"SUCCESS".equalsIgnoreCase(this.getReturnCode()) || !"SUCCESS".equalsIgnoreCase(this.getResultCode())) {
            StringBuilder errorMsg = new StringBuilder();
            if(this.getReturnCode() != null) {
                errorMsg.append("返回代码：").append(this.getReturnCode());
            }

            if(this.getReturnMsg() != null) {
                errorMsg.append("，返回信息：").append(this.getReturnMsg());
            }

            if(this.getResultCode() != null) {
                errorMsg.append("，结果代码：").append(this.getResultCode());
            }

            if(this.getErrCode() != null) {
                errorMsg.append("，错误代码：").append(this.getErrCode());
            }

            if(this.getErrCodeDes() != null) {
                errorMsg.append("，错误详情：").append(this.getErrCodeDes());
            }

            WxError error = WxError.newBuilder().setErrorCode(-1).setErrorMsg(errorMsg.toString()).build();
            this.getLogger().error("\n结果业务代码异常，返回結果：{},\n{}", map, error);
            throw new WxErrorException(error);
        }
    }
}
