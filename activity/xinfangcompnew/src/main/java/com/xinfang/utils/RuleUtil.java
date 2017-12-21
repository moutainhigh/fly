package com.xinfang.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.xinfang.app.VO.ComType;
import com.xinfang.model.FdRule;
/**
 * 
 * @title RuleUtil.java
 * @package com.xinfang.utils
 * @description 规则暂时定死 以后改为从数据库读写 并提供修改接口
 * @author ZhangHuaRong   
 * @update 2017年4月18日 下午3:38:17
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
public class RuleUtil {
	
	public static FdRule rule=null;
	
	public static FdRule getRule(){
		if(rule!=null){
			return rule;
		}else{
			rule = new FdRule();
			rule.setId(1);
			rule.setType(1);
			rule.setState(1);
			rule.setRegister(7.2f);
			rule.setForword(16.7f);
			rule.setReply(33.3f);
			rule.setReplyLetter(83.3f);
			rule.setAreaApproval(9.4f);
			rule.setCityApproval(7.2f);
			rule.setWarn1(50f);
			rule.setWarn2(75f);
			rule.setWarn3(100f);
			return rule;
		}
		
	}
	
	public static List<ComType> gettype(){
		List<ComType> result = new ArrayList<ComType>();
		 ComType type1 =	new ComType("1", "广告监管");
		 ComType type2 =	new ComType("2", "国土资源");
		 ComType type3 =	new ComType("3", "城乡建设");
		 ComType type4 =	new ComType("4", "劳动和社会保障");
		 ComType type5 =	new ComType("5", "卫生计生");
		 ComType type6 =	new ComType("6", "教育问题'");
		 ComType type7 =	new ComType("7", "民政");
		 ComType type8 =	new ComType("8", "政法");
		 ComType type9 =	new ComType("9", "经济管理");
		 ComType type10 =	new ComType("10", "交通运输");
		 ComType type11 =	new ComType("11", "商贸旅游");
		 ComType type12 =	new ComType("12", "科技与信息产业");
		 ComType type13 =	new ComType("13", "环境保护");
		 ComType type14 =	new ComType("14", "党务政务'");
		 ComType type15=	new ComType("15", "组织人事");
		 ComType type16=	new ComType("16", "纪检监察");
		 ComType type17=	new ComType("17", "其它");
		 result.add(type1);
		 result.add(type2);
		 result.add(type3);
		 result.add(type4);
		 result.add(type5);
		 result.add(type6);
		 result.add(type7);
		 result.add(type8);
		 result.add(type9);
		 result.add(type10);
		 result.add(type11);
		 result.add(type12);
		 result.add(type13);
		 result.add(type14);
		 result.add(type15);
		 result.add(type16);
		 result.add(type17);
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(90*RuleUtil.getRule().getReply()*0.01f);
		Date dd = DateUtils.add(new Date(), 90*RuleUtil.getRule().getReply()*0.01f);
		System.out.println(dd.toLocaleString());
	}

}
