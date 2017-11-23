帖子详情列表（contentPostDetail1.0）
-----------------------------
>请求参数:

    {
       id:帖子id（必传）
    }

>返回参数：

	{
       status:
       message:
       body:{
                id:5975bd922d24093bb258fb31
                title:帖子1
                channelName:美容/养生
                detail:这是新闻的详细内容
                postFormulaInfo:ssssssss(内容中关联的配方id)
                postProductionInfo:ssssssss(内容中关联的产品id)
                commentNum：0
                browseNum：0
                pointNum：0
                collectionNum：0
                publicUserName:张三
                headUrl:http://localhost:8080/xxx（用户头像url）
                userStatus:ACTIVE(发布帖子用户状态:ACTIVE 正常, LOCKED 锁定)
                createAt：1500889617
                handleTime：1500889617
                handleUserName：李四（审核人）
                contentStatus:SHOW(PRE_COMMIT("待提交"),PRE_EXAMINE("待审核"),SHOW("显示"),TOP("置顶"),HIDDEN("隐藏"),DEL("删除"))       
            }
        
	}
