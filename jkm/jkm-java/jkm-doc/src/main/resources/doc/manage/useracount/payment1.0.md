支付宝充值接口（payment1.0）
----------------------------------
    请求地址：http://ip/{service}?version=1.0&timestamp&...&data={}
    http://118.89.201.196:9092/api-klm-manage/manage/payment?salt=237586&data=%7B%22totalAmount%22%3A%221000%22%2C%22subject%22%3A%22%E5%85%85%E5%80%BC%E6%B5%8B%E8%AF%95%22%2C%22userId%22%3A%221%22%7D&service=payment&sign=d41be7f90c5a3813aa6b3a79106bdc9a&version=1.0&timestamp=1501134167417

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
	      subject:备注
	      totalAmount：金额
      }
     
	}


**返回参数**

	{
       status:
       message:
       body:{
           body:支付宝返回的信息
        }
	}

