package cn.jkm.provider.sys.v1_0.user;

import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.core.domain.type.Status;
import cn.jkm.core.domain.type.UserStatus;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.SysUserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Xia Guosong on 2017/7/17.
 */
@Service(version = "1.0")
public class SysUserServiceImpl implements SysUserService {

    @Override
    public SysUser login(String accountName, String password) {
        return Mongo.buildMongo().eq("accountName", accountName).eq("password", password).findOne(SysUser.class);
    }

    @Override
    public ListResult<SysUser> list(UserStatus userStatus, String roleId, String accountName, int limit, int page) {
        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        if (!StringUtils.isEmpty(userStatus)) {
            mongo.eq("userStatus", userStatus);
        }
        if (!StringUtils.isEmpty(roleId)) {
            mongo.eq("roleId", roleId);
        }
        if (!StringUtils.isEmpty(accountName)) {
            mongo.fuzzy("accountName", accountName);
        }
        List<SysUser> list = mongo.limit(limit, page).find(SysUser.class);
        return new ListResult<SysUser>().setCount(mongo.count("sys_user")).setList(list);
    }


    @Override
    public void create(String accountName, String password, String headerUrl, String roleId) {
        SysUser sysUser = new SysUser();
        sysUser.setAccountName(accountName);
        sysUser.setPassword(password);
        sysUser.setUserStatus(UserStatus.ACTIVE);
        sysUser.setHeadUrl(headerUrl);
        sysUser.setRoleId(roleId);
        Mongo.buildMongo().insert(sysUser);
    }

    @Override
    public void update(String userId, String accountName, String headerUrl, String roleId) {
        Mongo mongo = Mongo.buildMongo();
        mongo.eq("_id", userId).updateFirst(update -> {
            update.set("accountName", accountName);
            if (!StringUtils.isEmpty(headerUrl)) {
                update.set("headerUrl", headerUrl);
            }
            update.set("roleId", roleId);
        }, SysUser.class);
    }

    @Override
    public void updatePwd(String userId, String newPwd) {
        Mongo.buildMongo().eq("_id", userId).updateFirst(update -> update.set("password", newPwd), SysUser.class);
    }

    @Override
    public void lock(String userId) {
        Mongo.buildMongo().eq("_id", userId).updateFirst(update -> update.set("userStatus", UserStatus.LOCKED), SysUser.class);
    }

    @Override
    public void unlock(String userId) {
        Mongo.buildMongo().eq("_id", userId).updateFirst(update -> update.set("userStatus", UserStatus.ACTIVE), SysUser.class);
    }

    @Override
    public void delete(String userId) {
        Mongo.buildMongo().eq("_id", userId).updateFirst(update -> update.set("status", Status.DELETE), SysUser.class);
    }

    @Override
    public SysUser find(String userId) {
        return Mongo.buildMongo().eq("_id", userId).findOne(SysUser.class);
    }

}
