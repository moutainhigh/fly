package cn.jkm.service.sys;

import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.core.domain.type.UserStatus;
import cn.jkm.service.core.domain.ListResult;

/**
 * Created by Xia Guosong on 2017/7/17.
 */
public interface SysUserService {

    SysUser login(String accountName, String password);

    ListResult<SysUser> list(UserStatus userStatus, String roleId, String accountName, int limit, int page);

    void create(String accountName, String password, String headerUrl, String roleId);

    void update(String userId, String accountName, String headerUrl, String roleId);

    void updatePwd(String userId, String newPwd);

    void lock(String userId);

    void unlock(String userId);

    void delete(String userId);

    /**
     * 根据id查询后台用户
     * @param userId
     * @return
     */
    SysUser find(String userId);
}
