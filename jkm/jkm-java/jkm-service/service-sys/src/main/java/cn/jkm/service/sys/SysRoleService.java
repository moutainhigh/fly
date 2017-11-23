package cn.jkm.service.sys;

import cn.jkm.core.domain.mongo.user.SysRole;
import cn.jkm.service.core.domain.ListResult;

import java.util.List;

/**
 * Created by Xia Guosong on 2017/7/17.
 */
public interface SysRoleService {

    List<SysRole> list();

    ListResult<SysRole> list(int limit, int page);

    void create(SysRole sysRole);

    void update(SysRole sysRole);

    void delete(String roleId);

    /**
     * 查询角色*
     * @param roleId
     * @return
     */
    SysRole find(String roleId);
}
