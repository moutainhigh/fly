增加或者修改banner（bannerUpsert1.0）
-----------------------------
>请求参数:

    {
        bannerId:         banner的id，非必填，如为空，表示新增，如不空，表示修改
       name:        banner或者闪屏的主题
       bannerImages:[
                        {
                            "picUrl":"http://www.baidu.com/..png",
                            "actionUrl":"http://www.baidu.com/..png"
                        },
                        {
                            "picUrl":"http://www.baidu.com/..png",
                            "actionUrl":"http://www.baidu.com/..png"
                        },
                        {
                            "picUrl":"http://www.baidu.com/..png",
                            "actionUrl":"http://www.baidu.com/..png"
                        }
                    ]
        bannerType: 区分banner和闪屏的，banner：BANNER，闪屏：SPLASH
        endTime:时间戳(13位)
        beginTime:时间戳（13位）
    }

>返回参数：

	{
       status:
       message:
	}
