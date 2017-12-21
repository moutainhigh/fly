package cn.jkm.core.domain.mongo.user;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

import java.util.List;

/**
 * Created by werewolf on 2017/7/17.
 */
@Document(name = "sys_menu")
public class SysMenu extends MongoBasic<SysMenu> {

    public enum Permission {
        READ_ONLY, READ_AND_WRITE; //查看/操作
    }

    private String name;
    private String url;
    private String parentId; //父级id
    private List<SysMenu> sysMenus;
    private Permission permission;

    public SysMenu() {

    }

    public SysMenu(String name) {
        this.name = name;
    }

    public SysMenu(String name, String parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public SysMenu(String name, String url, String parentId) {
        this.name = name;
        this.url = url;
        this.permission = permission;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<SysMenu> getSysMenus() {
        return sysMenus;
    }

    public void setSysMenus(List<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }
}
