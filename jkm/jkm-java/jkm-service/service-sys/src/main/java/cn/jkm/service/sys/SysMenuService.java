package cn.jkm.service.sys;

import cn.jkm.core.domain.mongo.user.SysMenu;

import java.util.List;

/**
 * Created by Xia Guosong on 2017/7/19.
 */
public interface SysMenuService {

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    SysMenu findById(String id);

    /**
     * 根据一级菜单封装子菜单
     * @return
     */
    List<SysMenu> list();

    /**
     * 查询指定id下的子菜单
     * @param id
     * @return
     */
    List<SysMenu> findChildById(String id);
}
