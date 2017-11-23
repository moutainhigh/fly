系统用户列表接口（sysUserList1.0）
-----------------------------
>请求参数:

    {
        userStatus:系统用户状态（非必传，不传此参数查询所有状态的用户）
        roleId:角色id（非必传，不传此参数查询所有角色下的用户）
        accountName:账户名（非必传，不传此参数查询全部用户）
        limit:
        page:
    }

>返回参数：

	{
	    message:
	    body:{
            count:
            list:[
                {
                    accountName:
                    createAt:
                    headUrl:
                    id:
                    password:
                    realName:
                    roleId:
                    status:
                    updateAt:
                    userStatus:
                }   
                ......
            ]
        }
        status:
    }
