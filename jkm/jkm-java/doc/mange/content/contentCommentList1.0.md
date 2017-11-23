查询帖子的评论列表（contentCommentList1.0）
-----------------------------
>请求参数:

    {
       id:帖子id（必传）
       type:POST（主题的类型，这里传POST就行了）
       limit:10
       page:1
    }

>返回参数：

	{
       status:
       message:
       body:{
                count:10
                list:[
                           {
                              id:5975bd922d24093bb258fb31
                              content:这是帖子的评论内容
                              userName:张三
                              headUrl：评论用户的头像url
                              userStatus：ACTIVE(评论用户的状态:ACTIVE 正常, LOCKED 锁定)
                              createAt：1500889617         
                           }
                           ..........
                      ]
            }
        
	}
