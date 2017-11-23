获取发帖规则信息（rulePostInfo1.0）
-----------------------------
>请求参数:

    无
>返回参数：

	{
       status:
       message:
       data:{
               SUBJECT_ACCEPT:{                 主题贴被审核通过
                   "score": 2,                  获得分数
                   "type": "CONSUMER_POINTS",  积分类别:
                   "dayLimit": 200,             每日上限
                   "totalLimit": 2000           总上限
               }
               ACCEPT_SUBORDINATW:{ 审核并通过下级主题贴
                    "score": 2,
                    "type": "CONSUMER_POINTS",
                    "dayLimit": 200,
                    "totalLimit": 2000
               }
               COMMENT:{  发表评论
                    "score": 2,
                    "type": "CONSUMER_POINTS",
                    "dayLimit": 200,
                    "totalLimit": 2000
               }
               HIGHER_REJECT:{  主题贴被上级打回
                       "score": 2,                          扣除积分
                       "type": "BONUS_POINTS",              扣除积分的类别
                       "higherLevelScore": 2,               扣除上级积分
                       "higherLevelType": "CONSUMER_POINTS",    扣除上级积分的类别
                       "timeout": 3}                        设置的超时时间(单位为天数)
               COMPLAINT_REJECT:{  主题贴被投诉打回
                   "score": 2,
                   "type": "CONSUMER_POINTS",
                   "higherLevelScore": 2,
                   "higherLevelType": "BONUS_POINTS",
                   "timeout": 3}
               TIME_OUT:{  主题贴超时未审核
                    "score": 2,
                    "type": "CONSUMER_POINTS",
                    "higherLevelScore": 2,
                    "higherLevelType": "CONSUMER_POINTS",
                    "timeout": 3
               }
               DEL:{  主题贴被删除
                    "score": 2,
                   "type": "CONSUMER_POINTS",
                   "higherLevelScore": 2,
                   "higherLevelType": "BONUS_POINTS",
                   "timeout": 3
               }
               HIDDEN:{  主题贴被隐藏
                  "score": 2,
                  "type": "CONSUMER_POINTS",
                  "higherLevelScore": 2,
                  "higherLevelType": "CONSUMER_POINTS",
                  "timeout": 3
              }
               COMMENT_DEL:{  评论被删除
                   "score": 2,
                   "type": "CONSUMER_POINTS",
                   "higherLevelScore": 2,
                   "higherLevelType": "BONUS_POINTS",
                   "timeout": 3
               }
               DEFAULT_AVATAR: 默认头像
            }
	}

1. 积分类别说明（type，higherLevelType）

    CONSUMER_POINTS,//消费积分
    BONUS_POINTS,//奖励积分
    BONUS_CASH//奖励现金