角色列表接口（roleList1.0）
-----------------------------
>请求参数:
    
    {
        limit:每页显示的条数
        page:当前页码
    }

>返回参数：

	{
	    message:
	    body:{
	        sysRoles:[
                {
                    roleId:
                    roleName:角色名称
                }
                ......
            ],
	        count:总条数
        }
        status:
    }

