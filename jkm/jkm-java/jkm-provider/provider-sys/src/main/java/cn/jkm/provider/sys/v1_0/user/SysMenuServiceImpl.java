package cn.jkm.provider.sys.v1_0.user;

import cn.jkm.core.domain.mongo.user.SysMenu;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.sys.SysMenuService;

import java.util.List;

/**
 * Created by Xia Guosong on 2017/7/17.
 */
public class SysMenuServiceImpl implements SysMenuService {

    @Override
    public SysMenu findById(String menuId) {
        return Mongo.buildMongo().eq("_id", menuId).findOne(SysMenu.class);
    }

    @Override
    public List<SysMenu> list() {
        Mongo mongo = Mongo.buildMongo();
        List<SysMenu> sysMenus = mongo.eq("parentId", null).find(SysMenu.class);
        for (SysMenu sysMenu : sysMenus) {
            List<SysMenu> list = this.findChildById(sysMenu.getId());
            sysMenu.setSysMenus(list); //二级
            for (SysMenu sysMenu1 : list) {
                List<SysMenu> list1 = this.findChildById(sysMenu.getId());
                if (list1 != null) {
                    sysMenu1.setSysMenus(list1); //三级
                }
            }
        }
        return sysMenus;
    }

    @Override
    public List<SysMenu> findChildById(String id) {
        return Mongo.buildMongo().eq("parentId", id).find(SysMenu.class);
    }

    /**
     * 递归查询算法（导航固定三级，递归查询影响效率）
     * @param list
     * @param id
     */
    private void getSysMenus(List<SysMenu> list, String id) {
        List<SysMenu> sysMenus = findChildById(id);
        if (null != list && list.size() > 0) {
            for (SysMenu sysMenu : sysMenus) {
                list.add(sysMenu);
                getSysMenus(list, sysMenu.getId());
            }
        }
    }
}
