删除banner（bannerGet1.0）
-----------------------------
>请求参数:

    {
       bannerId:        banner或者闪屏的id,必填
    }

>返回参数：

	{
       status:
       message:
       data:{
            name: "banner或者闪屏的主题",
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
                        ],
            bannerType: "区分banner和闪屏的，banner：BANNER，闪屏：SPLASH",
            endTime:"时间戳(13位)",
            beginTime:"时间戳（13位）"
       }
	}
