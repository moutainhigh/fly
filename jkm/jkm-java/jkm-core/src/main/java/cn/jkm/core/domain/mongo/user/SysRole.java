package cn.jkm.core.domain.mongo.user;


import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;
import java.util.List;

/**
 * Created by werewolf on 2017/7/17.
 */
@Document(name = "sys_role")
public class SysRole extends MongoBasic<SysRole> {

    private String name;
    private List<SysMenu> sysMenus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SysMenu> getSysMenus() {
        return sysMenus;
    }

    public void setSysMenus(List<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }
}

