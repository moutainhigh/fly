package cn.jkm.provider.sys.v1_0.user;

import cn.jkm.core.domain.mongo.user.SysRole;
import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.SysRoleService;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * Created by Xia Guosong on 2017/7/17.
 */
@Service(version = "1.0")
public class SysRoleServiceImpl implements SysRoleService {

    @Override
    public List<SysRole> list() {
        return Mongo.buildMongo().eq("status", Status.ACTIVE).find(SysRole.class);
    }

    @Override
    public ListResult<SysRole> list(int limit, int page) {
        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        List<SysRole> list = mongo.limit(limit, page).find(SysRole.class);
        return new ListResult<SysRole>().setCount(mongo.count("sys_role")).setList(list);
    }

    @Override
    public void create(SysRole sysRole) {
        Mongo.buildMongo().insert(sysRole);
    }

    @Override
    public void update(SysRole sysRole) {
        Mongo.buildMongo().eq("_id", sysRole.getId()).updateFirst(update -> update.set("name", sysRole.getName()).set("sysMenus", sysRole.getSysMenus()), SysRole.class);
    }

    @Override
    public void delete(String roleId) {
        Mongo mongo = Mongo.buildMongo();
        mongo.eq("_id", roleId).updateFirst(update -> update.set("status", Status.DELETE), SysRole.class);
        mongo.eq("roleId", roleId).updateMulti(update -> update.set("roleId",""),SysUser.class); //清除当前角色下的用户
    }

    @Override
    public SysRole find(String roleId) {
        return Mongo.buildMongo().eq("_id", roleId).findOne(SysRole.class);
    }

}
