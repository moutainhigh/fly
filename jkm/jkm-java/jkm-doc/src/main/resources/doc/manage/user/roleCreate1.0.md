新建角色接口（roleCreate1.0）
-----------------------------
>请求参数:
    
    {
        json:{
            name:角色名
            sysMenus:[
                {
                    id:一级菜单id
                    name:一级菜单名称
                    sysMenus:[
                        {
                            id:二级菜单id
                            name:二级菜单名称
                            sysMenus:[
                                {
                                    id:三级菜单id
                                    name:三级菜单名称
                                    permission:权限类型
                                }
                                ......
                            ]
                        }
                        ......
                    ]
                }
                ......
            ]
        }
    }

>返回参数：

	{
	    message:
        status:
	}