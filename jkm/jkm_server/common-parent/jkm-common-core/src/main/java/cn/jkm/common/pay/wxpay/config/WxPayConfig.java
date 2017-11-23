//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jkm.common.pay.wxpay.config;

import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

public class WxPayConfig {
    private String appId;
    private String subAppId;
    private String mchId;
    private String mchKey;
    private String subMchId;
    private String notifyUrl;
    private String tradeType;
    private SSLContext sslContext;
    private String keyPath;
    private boolean useSandboxEnv = false;

    public WxPayConfig() {
    }

    public String getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getMchId() {
        return this.mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return this.mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getNotifyUrl() {
        return this.notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public SSLContext getSslContext() {
        return this.sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public boolean useSandbox() {
        return this.useSandboxEnv;
    }

    public void setUseSandboxEnv(boolean useSandboxEnv) {
        this.useSandboxEnv = useSandboxEnv;
    }

    public SSLContext initSSLContext() {
        if(null == this.mchId) {
            throw new IllegalArgumentException("请确保商户号mch_id已设置");
        } else {
            File file = new File(this.keyPath);
            if(!file.exists()) {
                throw new RuntimeException("证书文件：【" + file.getPath() + "】不存在！");
            } else {
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    KeyStore keystore = KeyStore.getInstance("PKCS12");
                    char[] partnerId2charArray = this.mchId.toCharArray();
                    keystore.load(inputStream, partnerId2charArray);
                    this.sslContext = SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
                    return this.sslContext;
                } catch (Exception var5) {
                    throw new RuntimeException("证书文件有问题，请核实！", var5);
                }
            }
        }
    }
}
