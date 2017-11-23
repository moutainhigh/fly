接口定义
----------------------------------
    请求地址：http://ip/{service}?version=1.0&timestamp&...&data={}

-------------------------
>请求参数(get/post):

	{
	  service:业务接口名称
      version:接口版本号
      timestamp：系统时间
      salt:客户端端生成效验签名随机数(6位)
      sign:签名
      data:{
          os: android/iphone
          clientVersion:客户端版本号
          mobileType:真实手机型号
          guid:手机唯一标识
          channel:渠道号
          token:用户有效标识，登录后传
          userId:登陆后传

          ......:其他业务参数

      }
	}


接口验证与数据加密
-----------------------
1. 数据签名

        md5(service+timestamp+data+version+salt+key_01)
        key_01:系统定义
        timestamp:可通过(systemTime来效正服务器与本地时间)

2. 请求加密

	    加密方式：base64.encode(3desc(json明文参数data,密码))
	    密码:md5(salt+key_02)
	    key_02:系统定义

3. 返回解密

        解密方式：base64.decode(3desc.decode(body密文，密码))
        密码：md5(key+key_02)
        key_02:系统定义

接口响应数据定义
--------------------------------
>返回参数

	{
        status:状态码
        message:提示信息
        key:解密key【接口返回加密数据，有此参数】
        body：{
           .....返回业务数据，
        }

	}
