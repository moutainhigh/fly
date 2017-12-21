package cn.jkm.core.domain.mongo.user;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

/**
 * Created by Administrator on 2017/7/17.
 */
@Document(name = "sys_log")
public class SysLog extends MongoBasic<SysLog> {

    public enum Operation {
        INSERT, UPDATE
    }

    private String sysUserId;
    private String realName;
    private String content;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
