APP用户列表接口（userList1.0）
-----------------------------
>请求参数:

    {
        userStatus:APP用户状态（非必传，不传此参数查询所有状态的APP用户）
        type:用户类型（VIP/普通用户，非必传，不传此参数查询所有类型的APP用户）
        gender:性别（非必传，不传此参数查询所有）
        keyword:关键字（非必传，不传此参数查询全部用户）
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
