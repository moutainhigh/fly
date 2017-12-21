package cn.jkm.core.domain.mongo.user;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.UserStatus;
import cn.jkm.framework.core.annotation.Document;

/**
 * Created by werewolf on 2017/7/17.
 */
@Document(name = "sys_user")
public class SysUser extends MongoBasic<SysUser> {

    private String accountName; //登录名
    private String realName; //真实姓名
    private String password;
    private UserStatus userStatus;
    private String headUrl;//员工头像
    private String roleId;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
