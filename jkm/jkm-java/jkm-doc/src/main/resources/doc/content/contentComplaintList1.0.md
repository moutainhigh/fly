搜索用户投诉列表（contentComplaintList1.0）
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
                "count": 98,
                "list": [
                            {
                                "id":"5975c2112d2436ba64b7e6ac",
                                "title": "帖子1",
                                "channelName": "美容",
                                "columnName":"养生",
                                "complaintNoHandleCount":5(待处理投诉次数),
                                "complaintCount":10(投诉次数),
                                "complaintTime": 1500973144(最近投诉事件),
                                "handleName":"张三",
                                "handleTime":1500973144
                            }
                            .........
                        ]
             }
        
	}
