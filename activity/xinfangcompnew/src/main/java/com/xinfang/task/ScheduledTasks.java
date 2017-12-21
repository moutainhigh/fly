package com.xinfang.task;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.xinfang.VO.LogInInfo;
import com.xinfang.VO.NodeVO;
import com.xinfang.dao.FcRybAllFieldRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.dao.UserRepository;
import com.xinfang.log.ApiLog;
import com.xinfang.log.SuperLog;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdDepCase;
import com.xinfang.model.FdWarn;
import com.xinfang.service.ActivitiService;
import com.xinfang.service.CaseService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.HttpSender;
import com.xinfang.utils.RuleUtil;


@Component
@EnableScheduling
public class ScheduledTasks{
/*	@Autowired
	ServerEventListener serverEventListener;*/
	
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	
	 @Autowired
	 RedisTemplate redisTemplate;
	 
		@Autowired
		 ActivitiService myService;
		
		@Autowired
		 CaseService caseService;
		
		@Autowired
		TzmPetitionService tzmPetitionService;
		

		@Autowired
		FcRybAllFieldRepository fcRybAllFieldRepository;
		

		@Autowired
		UserRepository userRepository;


    //每1分钟执行一次  晚上10点半以后 早上7点之前不扫描
//	@Scheduled(fixedRate = 1000 * 60)
	//@Scheduled(cron="0 0/1 * * * ?")
    public void reportCurrentByCron() {
    	System.out.println("----------定时更新缓存---------------"+new Date().toLocaleString());
    	/*CouponEvent couponEvent = new CouponEvent("admin",10);
    	serverEventListener.postCoupon(couponEvent);*/
    }
    
    //朝九晚五工作时间内每半小时
  // @Scheduled(cron="0 0/2 * * * ?")
     @Scheduled(cron="0 0/30 9-20 * * ?")
//    @Scheduled(cron="0 0/10 9-20 * * ?")
    public void warnByCron() {
    	//超期50% 75% 发给处理人 超期100%发给领导
    	//HttpSender.send(mobiles,msg);
    	System.out.println("----------定时更新缓存---------------"+new Date().toLocaleString());

    	Specification<FdDepCase> spec = getspecificationoth(1);
    	Specification<FdDepCase> spec2 = getspecificationoth(2);
    	Specification<FdDepCase> spec3 = getspecificationoth(3);
    	
    	List<FdDepCase> depcases=fdDepCaseRepository.findAll(spec);
    
    	Date now = new Date();
    	sendall(depcases, now,"flag1");//超过50% 小于75% 
    	List<FdDepCase> depcases2=fdDepCaseRepository.findAll(spec2);
    	sendall(depcases2, now,"flag2");//超过75% 小于100%
    	List<FdDepCase> depcases3=fdDepCaseRepository.findAll(spec3);
    	sendall(depcases3, now,"flag3");//超期100%是否发送短信
    	
    	
    	System.out.println("----------定时更新缓存---------------"+new Date().toLocaleString());
    	/*CouponEvent couponEvent = new CouponEvent("admin",10);
    	serverEventListener.postCoupon(couponEvent);*/
    }

	private void sendall(List<FdDepCase> depcases, Date now, String flag) {
		for(FdDepCase depcase:depcases){
    		NodeVO user = null;
    		String mobiles="18275009232";
    		
    		if(depcase.getCaseId().intValue()==317){
    			System.out.println("-------------debug---------------");
    		}
    		if(depcase.getLimitTime()==null){
    			continue;
    		}
    		Float warn = depcase.getLimitTime()*RuleUtil.getRule().getWarn1()*0.01F;//预警50%
			Float warn1 =  depcase.getLimitTime()*RuleUtil.getRule().getWarn2()*0.01F;//预警75%
			Float warn2 =  depcase.getLimitTime()*RuleUtil.getRule().getWarn3()*0.01F;//预警100%
			FdCase fdCase = caseService.getFdCaseById(depcase.getCaseId().intValue());
			Date showdTime1 = DateUtils.add(depcase.getCreateTime(), warn);
			Date showdTime2 = DateUtils.add(depcase.getCreateTime(), warn1);
			Date showdTime3 = DateUtils.add(depcase.getCreateTime(), warn2);
			
			String key = null;
			
				try {
					if(now.getTime()<showdTime1.getTime()){//没有超期
						
					}else if(now.getTime()<showdTime2.getTime()){//超过50% 小于75% 发出短信通知
						if(depcase.getProcessid()!=null){
							user = myService.getcurrentnode(depcase.getProcessid());
						}
						if(depcase.getFlag1()==null){
							updatedep(depcase,flag);
							sendmsg(depcase, user, mobiles, fdCase,RuleUtil.getRule().getWarn1()*0.01f,1);
						}
						
					}else if(now.getTime()<showdTime3.getTime()){ //超过75% 小于100% 发出短信通知
						if(depcase.getProcessid()!=null){
							user = myService.getcurrentnode(depcase.getProcessid());
						}
						if(depcase.getFlag2()==null){
							updatedep(depcase,flag);
							sendmsg(depcase, user, mobiles, fdCase,RuleUtil.getRule().getWarn2()*0.01f,2);
						}
						
					}else{//超期
						if(depcase.getProcessid()!=null){
							user = myService.getcurrentnode(depcase.getProcessid());
						}
						if(depcase.getFlag3()==null){
							updatedep(depcase,flag);
							sendmsg(depcase, user, mobiles, fdCase,RuleUtil.getRule().getWarn3()*0.01f,3);//此处发给领导
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
					ApiLog.log(e.getMessage());
					continue;
				}
				
    		
    		
    	}
	}

	private void updatedep(FdDepCase depcase, String flag) {
		if(flag.equals("flag1")){
			depcase.setFlag1(1);
		}else if(flag.equals("flag2")){
			depcase.setFlag2(1);
		}else if(flag.equals("flag3")){
			depcase.setFlag3(1);
		}
		FdDepCase result = myService.saveFdDepCase(depcase);
		System.out.println("----");
	}

	@SuppressWarnings("unused")
	private void sendmsg(FdDepCase depcase, NodeVO user, String mobiles, FdCase fdCase,float warn,int level) throws Exception {
		String key;
		key = "msg:"+depcase.getCaseId()+":"+depcase.getCaseId()+":"+warn;
		/*boolean exists=redisTemplate.hasKey(key);*/
		
		if(user!=null&&user.getCurrentUserInfo()!=null)
			
		{
			 FdWarn log = new FdWarn();
			 
			 LogInInfo leader = null;
			 StringBuffer msg = new StringBuffer();
			 StringBuffer msgleader = null;
			 if(level==1){//  50%~75% 
				 if(depcase.getType().intValue()==4){//责任单位出具意见答复书阶段
					 String last = DateUtils.getDatePoor(new Date(),depcase.getEndTime() );
					 msg.append(user.getCurrentUserInfo().getUserName()).append("你好!");
					 msg.append("编号为").append(fdCase.getPetitionCode()).append("的案件节点处理时间已经超过50%，剩余时间").append(last).append("，请及时处理。"); 
				 }else{
					 String last = DateUtils.getDatePoor(new Date(),depcase.getEndTime() );
					 msg.append(user.getCurrentUserInfo().getUserName()).append("你好!");
					 msg.append("编号为").append(fdCase.getPetitionCode()).append("的案件节点处理时间已经超过50%，剩余时间").append(last).append("，请及时处理。"); 
				 }
				 
				 
			 }else if(level==2){// 75%~100%
				 if(depcase.getType().intValue()==4){//责任单位出具意见答复书阶段
					 String last = DateUtils.getDatePoor(new Date(),depcase.getEndTime() );
					 msg.append(user.getCurrentUserInfo().getUserName()).append("你好!");
					 msg.append("编号为").append(fdCase.getPetitionCode()).append("的案件节点处理时间已经超过75%,剩余时间").append(last).append(",请及时处理"); 
				 }else{
					 String last = DateUtils.getDatePoor(new Date(),depcase.getEndTime() );
					 msg.append(user.getCurrentUserInfo().getUserName()).append("你好!");
					 msg.append("编号为").append(fdCase.getPetitionCode()).append("的案件节点处理时间已经超过75%,剩余时间").append(last).append(",请及时处理"); 
				 }
				 
			 }else{// 超100%
				 
					Integer checkPersonId = fcRybAllFieldRepository.getCheckPersonIdByRyId(user.getCurrentUserInfo().getRyId());
					if(null!=checkPersonId){
//						leader = userRepository.findRyInfoByRyid(checkPersonId);
						leader = tzmPetitionService.selectLogInInfoByRyId(checkPersonId);
						msgleader = new StringBuffer();
					}
					
					 msg.append(user.getCurrentUserInfo().getUserName()).append("你好!");
					 msg.append("编号为").append(fdCase.getPetitionCode()).append("的案件节点处理时间已经超过100%").append("，请尽快处理，超期情况将作为贵单位信访工作考核依据。"); 
				     if(msgleader!=null){
				    	 msgleader.append(leader.getUserName()).append("你好!");
				    	 msgleader.append("您的下属").append(user.getCurrentUserInfo().getUserName()).append("在案件编号为").append(fdCase.getPetitionCode()).append("的案件节点处理时间已经超过100%，")
						 .append("请督促办理人员 ").append(user.getCurrentUserInfo().getUserName())
						 .append("尽快处理，超期情况将作为贵单位信访工作考核依据。 "); 
				     }
					
				 /*if(depcase.getType().intValue()==4){//责任单位出具意见答复书阶段
					 msg.append(user.getCurrentUserInfo().getUserName()).append("你好!");
					 msg.append("编号为").append(fdCase.getPetitionCode()).append("的案件节点处理时间已经超过100%").append("，请尽快处理，超期情况将作为贵单位信访工作考核依据。"); 
				     if(msgleader!=null){
				    	 msgleader.append(leader.getUserName()).append("你好!");
				    	 msgleader.append("我局交办贵单位编号为").append(fdCase.getPetitionCode()).append("信访件已到办理期限")
						 .append("请督促办理人员 ").append(user.getCurrentUserInfo().getUserName())
						 .append("，尽快提交结案报告和相关材料。超期情况将作为贵单位信访工作考核依据。"); 
				     }
					 
				 }else{
					 msg.append(user.getCurrentUserInfo().getUserName()).append("你好!");
					 msg.append("编号为").append(fdCase.getPetitionCode()).append("信访件已到办理期限").append("，请及时处理。"); 
					 if(msgleader!=null){
				    	 msgleader.append(leader.getUserName()).append("你好!");
				    	 msgleader.append("我局交办贵单位编号为").append(fdCase.getPetitionCode()).append("信访件已到办理期限")
						 .append("请督促办理人员 ").append(user.getCurrentUserInfo().getUserName())
						 .append("，尽快处理。超期情况将作为贵单位信访工作考核依据。"); 
				     }
				 }*/
				 
			 }
			
			 
			 LogInInfo sender= getUserInfo(fdCase.getCreatorId());
			 log.setLevel(level);
			 log.setCaseId(fdCase.getId());
			// log.setSender(fdCase.getPetitionUnit());
			 log.setSender(fdCase.getCreatorId().toString());
			 log.setReceiver(user.getCurrentUserInfo().getRyId());
			 log.setMsg(msg.toString());
			 log.setCreateTime(new Date());
			 log.setCreateDep(sender.getDwId());
			 log.setReceiveDep(user.getCurrentUserInfo().getDwId());
			 System.out.println("caseid===="+log.getCaseId());
			 System.out.println("caseid===="+depcase.getCaseId());
			 FdWarn result = myService.save(log);
			 System.out.println(result);
			
			 HttpSender.send("18275009232","[预警]"+msg.toString());// TODO 暂时注释
			 HttpSender.send("17628296197","[预警】"+msg.toString());// TODO 暂时注释
			 if(msgleader!=null){
			     HttpSender.send("18275009232","[预警]"+msgleader.toString());// TODO 暂时注释
				 HttpSender.send("17628296197","[预警】"+msgleader.toString());// TODO 暂时注释
			 }
			 SuperLog.chargeLog1(DateUtils.formatDateTimehm(new Date())+"send msg [预警] "+msg.toString()+"  to 18275009232,17628296197");

			/* ValueOperations<String, String> operations=redisTemplate.opsForValue();
			 operations.set(key, "true",20,TimeUnit.DAYS);*/
			SuperLog.chargeLog1(DateUtils.formatDateTimehm(new Date())+" :[key] "+key+" ; msg="+msg.toString());
			 
		}
	}
	
	private Specification getspecificationoth(Integer depId) {
		PredicateBuilder<FdDepCase> build=Specifications.<FdDepCase>and();
		if(depId.intValue()==1){
			build.eq("state", 1).eq("flag1", null);
		}else if(depId.intValue()==2){
			build.eq("state", 1).eq("flag2", null);
		}else if(depId.intValue()==3){
			build.eq("state", 1).eq("flag3", null);
		}
		
		return build.build();
	}

	private LogInInfo getUserInfo(long userid) {
		LogInInfo info;
		Object obj = redisTemplate.opsForValue().get("user:" + userid);
		if (obj != null) {
			info = (LogInInfo) obj;
		} else {
			info = tzmPetitionService.selectLogInInfoByRyId((int) userid);
		}
		return info;
	}
  
}
