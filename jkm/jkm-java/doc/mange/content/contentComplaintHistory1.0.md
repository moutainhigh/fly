搜索帖子投诉历史接口（contentComplaintHistory1.0）
-----------------------------
>请求参数:

    {
       id:帖子id
       type:POST(主题类型)
       page:当前页数
       limit:每页显示条数
    }

>返回参数：

	{
       status:
       message:
       body:{
                "count": 4,
                "list": [
                            {
                                "complaintStatus": "IGNORE",(投诉的状态：NOHANDLE 待处理,IGNORE 忽略,DELMASTER 删除主题,DELSALVE 删除评论;)
                                "complaintType": "评论",（投诉类型：主题、评论）
                                "create": 1500973144,
                                "id": "597708582d245a05ba614204",
                                "commentContent": "",(如果投诉的是主题则为空)
                                "content": "这是帖子0的投诉内容"，
                                "userName":投诉用户名
                                "headUrl":投诉用户头像
                            }
                            .........
                        ]
             }
        
	}
