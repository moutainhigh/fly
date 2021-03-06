package cn.jkm.api.core.helper;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by werewolf on 2017/7/15.
 */
public class RequestWarper {

    private String service;//接口名称
    private String version;//接口版本号
    private String data;//请求数据(json)

    private String sign;//数据签名
    private String salt;
    private Long timestamp;
    @JSONField(serialize = false)
    private HttpServletRequest request;

    @JSONField(serialize = false)
    private HttpServletResponse response;
    private Boolean checkSign = false;
    private Boolean decodeRequest = false;
    private Boolean encodeResponse = false;

    private String signKey;
    private String dataKey;

    public RequestWarper setService(String service) {
        this.service = service;
        return this;
    }

    public RequestWarper setVersion(String version) {
        this.version = version;
        return this;
    }

    public RequestWarper setData(String data) {
        this.data = data;
        return this;
    }


    public RequestWarper setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public RequestWarper setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public RequestWarper setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public RequestWarper setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public RequestWarper setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    public RequestWarper setCheckSign(Boolean checkSign) {
        this.checkSign = checkSign;
        return this;
    }

    public RequestWarper setDecodeRequest(Boolean decodeRequest) {
        this.decodeRequest = decodeRequest;
        return this;
    }

    public RequestWarper setEncodeResponse(Boolean encodeResponse) {
        this.encodeResponse = encodeResponse;
        return this;
    }

    public RequestWarper setSignKey(String signKey) {
        this.signKey = signKey;
        return this;
    }

    public RequestWarper setDataKey(String dataKey) {
        this.dataKey = dataKey;
        return this;
    }

    public String getService() {
        return service;
    }

    public String getVersion() {
        return version;
    }

    public String getData() {
        return data;
    }


    public String getSign() {
        return sign;
    }

    public String getSalt() {
        return salt;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Boolean getCheckSign() {
        return checkSign;
    }

    public Boolean getDecodeRequest() {
        return decodeRequest;
    }

    public Boolean getEncodeResponse() {
        return encodeResponse;
    }

    public String getSignKey() {
        return signKey;
    }

    public String getDataKey() {
        return dataKey;
    }
}
