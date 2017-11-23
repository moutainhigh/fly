系统用户登录接口（getUserAccount1.0）
-----------------------------
请求地址如：
http://118.89.201.196:9092/api-klm-manage/manage/getUserAccount?salt=130862&data=%7B%22userId%22%3A%22f44cfafd-86f2-47c0-8555-56f9485ce4d4%22%7D&service=getUserAccount&sign=a793384c5060a4e267258d0c289fdf9c&version=1.0&timestamp=1501134369017
-------------------------
>请求参数(get/post):


    {
     service:业务接口名称
      version:接口版本号
      timestamp：系统时间
      salt:客户端端生成效验签名随机数(6位)
      sign:签名
      data:{
       userId:用户id
       }
    }

**返回参数**

	{
       status:
       message:
       body:{
           caseAmount:现金余额
           consumeIntegral:消费积分
           coupon:优惠券
           createAt:创建时间
           id:账户id
           rewardAmount:奖励佣金
           rewardIntegral:奖励积分
           status:状态（ACTIVE，DELETE）
           updateAt:更新时间
           userId:用户id
           vipCardAmount:会员卡
        }
	}
