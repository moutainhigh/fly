搜索帖子列表接口（contentPostList1.0）
-----------------------------
>请求参数:

    {
       postType:内容类型（传POST）
       channelId:频道栏目id
       contentStatus:帖子状态（如果选择全部则不传该参数）
       keys:搜索关键字（目前只能根据标题和内容模糊搜索）
       startTime:开始时间
       endTime:结束时间
       orderBy:排序的字段（回复:commentNum,收藏:collectionNum,点赞:pointNum,查看:browseNum,发表时间:createAt)
       page:当前页数
       limit:每页显示条数
    }

>返回参数：

	{
       status:
       message:
       body:{
                count:100,
                list:[
                        {
                            id:5975bd922d24093bb258fb31
                            title:帖子1
                            channelName:美容/养生
                            commentNum：0
                            browseNum：0
                            pointNum：0
                            collectionNum：0
                            publicUserName:张三
                            createAt：1500889617
                            status:SHOW(PRE_COMMIT("待提交"),PRE_EXAMINE("待审核"),SHOW("显示"),TOP("置顶"),HIDDEN("隐藏"),DEL("删除"))
                    
                        }
                        ..........
                     ]
            }
        
	}
