package com.xinfang.app.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.xinfang.Exception.BizException;
import com.xinfang.app.VO.ComplaintVO;
import com.xinfang.app.VO.UserVO;
import com.xinfang.app.dao.FdConsultDao;
import com.xinfang.app.dao.FdLawDao;
import com.xinfang.app.dao.FdSuggestDao;
import com.xinfang.app.model.FdConsult;
import com.xinfang.app.model.FdSuggest;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.dao.FdGuestRepository;
import com.xinfang.dao.LawRulesDao;
import com.xinfang.enums.HandleState;
import com.xinfang.evaluating.dao.FpAppraiseDao;
import com.xinfang.evaluating.model.FpAppraiseEntity;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.model.FdCode;
import com.xinfang.model.FdGuest;
import com.xinfang.model.LawRulesEntity;
import com.xinfang.service.CaseService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.utils.Base64Utils;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.HttpSender;
import com.xinfang.utils.MD5Utils;

@Service
public class AipService {
	
	 @Autowired
	 RedisTemplate redisTemplate;
	 @Autowired
	 FdGuestRepository fdGuestRepository;
	 @Autowired
	 FdLawDao fdLawDao;
	 @Autowired
	 FdSuggestDao fdSuggestDao;
	 @Autowired
	 FdConsultDao fdConsultDao;
	 @Autowired
	 CaseService caseService;
	 
	 @Autowired
	 FdCaseRepository fdCaseRepository;
	 @Autowired
	 FpAppraiseDao fpAppraiseDao;
	 @Autowired
	 FdCodeRepository fdCodeRepository;
	 @Autowired
	 LawRulesDao lawRulesDao;
	 @Autowired
	 FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;
	 @Autowired
	 TzmPetitionService tzmPetitionService;
	 
   /**
    * 
    * @param telephone
    * @description 发送验证码
    * @author ZhangHuaRong
    * @update 2017年5月16日 下午3:38:31
    */
	public String sendcode(String telephone) throws Exception {
		if(!Base64Utils.isMobileNO(telephone)){
			throw BizException.IS_NOT_PHONE;
		}
		String key="code:"+telephone;
		String code= Base64Utils.getRandomFourNum();
		ValueOperations<String, String> operations=redisTemplate.opsForValue();
		operations.set(key, code,5,TimeUnit.MINUTES);
		HttpSender.send(telephone,code);
		return code;
	}

	public Integer register(String username, String password, String telephone, String code) {
		veryfiy(telephone, code);
		int result= fdGuestRepository.countbymobile(telephone);
		if(result>0){
			throw BizException.PHONE_REPEAT;
		}
		
		FdGuest gues=new FdGuest();
		gues.setPhone(telephone);
		gues.setUsername(username);
		gues.setPassWd(MD5Utils.getPwd(password));
		FdGuest guest =fdGuestRepository.save(gues);
		return guest.getId();
	}

	private void veryfiy(String telephone, String code) {
		String key="code:"+telephone;
		ValueOperations<String, String> operations=redisTemplate.opsForValue();
		boolean exists=redisTemplate.hasKey(key);
		if(!exists){
			throw BizException.CODE_TIME_OUT;
		}else{
			String cacheCode= operations.get(key);
			if(!cacheCode.equals(code)){
				throw BizException.CODE_WRONG;
			}
		}
		
		if(!Base64Utils.isMobileNO(telephone)){
			throw BizException.IS_NOT_PHONE;
		}
	}

	public FdGuest login(String username, String password) {
		String pwd = MD5Utils.getPwd(password);
		FdGuest guest =	fdGuestRepository.getGuestByUsernameAndPasswd(username,pwd);
		if(guest==null){
			throw BizException.USER_OR_PW_WRONG;
		}
		
		return guest;
	}

	public List<FdConsult> searchConsult(Integer queryType, String queryValue, Integer page, Integer pageCount,
			Integer userId) {
		PageRequest pagerequest= new PageRequest(page-1, pageCount);
		Specification<FdConsult> specification = null;
		PredicateBuilder<FdConsult> build =Specifications.<FdConsult>and();
		/*String newkey = "%"+queryValue+"%";
		
		build.like("consult_text", newkey);
		build.eq("type", queryType);
		if(userId!=null){
			build.eq("sf_person_id", userId);
		}*/
		if(queryType.intValue()==1){//以用户查询;queryValue登录人ID
			build.eq("sf_person_id", Integer.parseInt(queryValue));
		}else if(queryType.intValue()==2){//以咨询ID查询;queryValue咨询ID
			build.eq("id", Integer.parseInt(queryValue));
		}else if(queryType.intValue()==4){
			build.eq("is_open", 1);
		}
		specification = build.build();
		Page<FdConsult>  result=fdConsultDao.findAll(specification,pagerequest);
		
		 List<FdConsult> lists = result.getContent();
			List<FdCode> fdCodeList = fdCodeRepository.findByTypeAndState(15, 1); // 1表示启用的字段
			Map<Integer,String> maps = casttomap(fdCodeList);
		   for(FdConsult fdConsult:lists){
			String name = maps.get(fdConsult.getCase_xf_des());
			 fdConsult.setCase_xf_des_name(name);
		   }
		
		return lists;
	}

	private Map<Integer, String> casttomap(List<FdCode> fdCodeList) {
		Map<Integer,String> result = new HashMap<Integer,String>();
		for(FdCode code:fdCodeList){
			result.put(code.getCode(), code.getName());
		}
		return result;
	}

	public List<FdSuggest> searchFdSuggest(Integer queryType, String queryValue, Integer page, Integer pageCount,
			Integer userId) {
		PageRequest pagerequest= new PageRequest(page-1, pageCount);
		Specification<FdSuggest> specification = null;
		PredicateBuilder<FdSuggest> build =Specifications.<FdSuggest>and();
		
		//String newkey = "%"+queryValue+"%";
		
		if(queryType.intValue()==1){//以用户查询;queryValue登录人ID
			build.eq("sf_person_id", Integer.parseInt(queryValue));
		}else if(queryType.intValue()==2){//以建议ID查询;queryValue建议ID
			build.eq("id", Integer.parseInt(queryValue));
		}else if(queryType.intValue()==4){
			build.eq("is_open", 1);
		}
		
		//build.like("suggest_text", newkey);
		//build.eq("type", queryType);
		if(userId!=null){
			build.eq("sf_person_id", userId);
		}
		
		
		specification = build.build();
		Page<FdSuggest>  result=fdSuggestDao.findAll(specification,pagerequest);
		List<FdSuggest> lists =result.getContent();
		List<FdCode> fdCodeList = fdCodeRepository.findByTypeAndState(15, 1); // 1表示启用的字段
		Map<Integer,String> maps = casttomap(fdCodeList);
	   for(FdSuggest fdConsult:lists){
		String name = maps.get(fdConsult.getCase_xf_des());
		 fdConsult.setCase_xf_des_name(name);
	   }
		
		return lists;
	}

	public List<LawRulesEntity> law_query(Integer queryType, String queryValue, Integer page, Integer pageCount) {
		PageRequest pagerequest= new PageRequest(page-1, pageCount);
		Specification<LawRulesEntity> specification = null;
		PredicateBuilder<LawRulesEntity> build =Specifications.<LawRulesEntity>and();
        //queryType=0查全部；queryType=3以类别和关键字查询；queryValue关键字|选中的类别	
		if(queryType.intValue()==3){
			build.like("content", "%"+queryValue.substring(0, queryValue.indexOf("|"))+"%");
			build.eq("category", queryValue.substring(queryValue.indexOf("|")+1));
		}else if(queryType.intValue()==1){
			build.like("content", "%"+queryValue+"%");
		}
		
		
		specification = build.build();
		Page<LawRulesEntity>  result=lawRulesDao.findAll(specification,pagerequest);
		
		
		return result.getContent();
	}

	public LawRulesEntity get_law_content(Integer law_id) {
		LawRulesEntity result = lawRulesDao.findOne(law_id);
		return result;
	}

	public FdSuggest suggest_submit(Integer sf_person_id, String sc_text, Integer send_county, Integer sc_type,
			String title) {
		FdSuggest suggest = new FdSuggest();
		suggest.setInput_time(new Date());
		suggest.setSf_person_id(sf_person_id);
		suggest.setSuggest_text(sc_text);
		suggest.setType(sc_type);
		suggest.setTitle(title);
		suggest.setSuggest_status(0);
		suggest.setCase_xf_des(send_county);
		
		FdSuggest result=	fdSuggestDao.save(suggest);
		result.setCase_id(result.getId().toString());
		fdSuggestDao.save(result);
		return suggest;
	}

	public FdConsult consult_submit(Integer sf_person_id, String sc_text, Integer send_county, Integer sc_type,
			String title) {
		
		FdConsult suggest = new FdConsult();
		suggest.setInput_time(new Date());
		suggest.setSf_person_id(sf_person_id);
		suggest.setConsult_text(sc_text);
		suggest.setType(sc_type);
		suggest.setTitle(title);
		suggest.setCase_xf_des(send_county);
		suggest.setConsult_status(0);
		FdConsult result = fdConsultDao.save(suggest);
		result.setCase_id(result.getId().toString());
		fdConsultDao.save(result);
		return suggest;
	}

	/**
	 * 
	 * @param sf_person_id 投诉人id  guestId
	 * @param send_county  接收单位        petitionUnit
	 * @param title        标题                 title
	 * @param complaint_time  投诉时间   caseTime  --gmtCreate
	 * @param complaint_location  事件地点 caseAddress
	 * @param complaint_reason    内容及诉求  caseDesc
	 * @param complaint_requirement  内容及诉求 
	 * @param attachment   附件                    fileSrc
	 * @param tf_type     投诉类型，0表示个访，1表示群访 type
	 * @param tf_num      同访人数  associatedNumbers
	 * @param tf_idcards   同访人身份证号
	 * @param tf_names     同访人姓名
	 * @param tf_phones    同访人电话
	 * @param tf_addresses   同访人地址
	 * @param petition_channel 信访渠道   petitionWay  1 现场 2 网络 3 信件
	 * @param complaint_system  问题涉及部门
	 * @description TODO
	 * @author ZhangHuaRong
	 * @param xffs 
	 * @param files 
	 * @param casetime 
	 * @param dwid 
	 * @param dispatcher_to_userid 
	 * @throws ParseException 
	 * @update 2017年5月17日 下午3:49:17
	 */
	public FdCase complaint_submit(Integer sf_person_id, String send_county, String title, String complaint_time,
			String complaint_location, String complaint_reason, String complaint_requirement, String attachment,
			Integer tf_type, Integer tf_num, String tf_idcards, String tf_names, String tf_phones, String tf_addresses,
			Integer petition_channel, Integer complaint_system, Integer xffs, String files, String casetime, Integer dwid, Integer dispatcher_to_userid) throws Exception {
		/*FdGuest guest =	fdGuestRepository.getGuestById(sf_person_id);
		guest.setPetitionTime(petitionTime);*/
		if(tf_names!=null){
			//TODO  同访人信息
		}
		List<Integer> ids = tzmPetitionService.selectSwgInfoByDwId(dwid);//获取收文岗id
		Date now = new Date();
		FdCase fdcase =  new FdCase();
		fdcase.setPetitionNumbers(1);
		fdcase.setGuestId(sf_person_id);
		fdcase.setPetitionUnit(send_county);
		fdcase.setTitle(title);
		fdcase.setGmtCreate(new Date());
		fdcase.setPetitionTime(now);
		fdcase.setCaseAddress(complaint_location);
		fdcase.setCaseDesc(complaint_reason);
		fdcase.setFileSrc(attachment);
		fdcase.setType(tf_type);
		fdcase.setCreatorId(0);
		if(!ids.isEmpty()){
			fdcase.setDispatcherToUserid(ids.get(0));
		}
		
		if(tf_num!=null){
			fdcase.setAssociatedNumbers(tf_num);
		}
		fdcase.setFileSrc(files);
		
	//	fdcase.setPetitionWay(petition_channel);
		fdcase.setPetitionWay(xffs);
		fdcase.setState(0);
	//	fdcase.setDispatcherToUserid(1);
		fdcase.setIsHandle((byte)0);
		if(dwid!=null){
			fdcase.setCreateUnitid(dwid);
		}
		fdcase.setIsDispatcherReceive((byte)0);
		
		fdcase.setComefrom("web");
		 // 生成案件编码
         String prefix = "M520100";
		 String strPetitionTimenew = DateUtils.formatDateTime(new Date());
	        String time = strPetitionTimenew.substring(0, 4) + strPetitionTimenew.substring(5, 7) + strPetitionTimenew.substring(8, 10);

	        String suffix = "";
	        long count = fdCaseRepository.countCurrentRecord(prefix + time + '%');
	        int countLength = Long.toString(count).length();
	        if (count < 1) {
	            suffix = "00001";
	        }
	        if (count > 0 && countLength < 5) {
	            int fill = 5 - countLength;
	            for (int i = 0; i < fill; i++) {
	                suffix += "0";
	            }
	            suffix += (count + 1);
	        }

	        fdcase.setPetitionCode(prefix + time + suffix);
		
		
		
	    FdCase result = fdCaseRepository.save(fdcase);
	    if(complaint_location!=null){
	    	result.setCaseTime(DateUtils.parseDate(complaint_time));
		}
		caseService.save(result,complaint_time+" 00:00:00");
		
		return fdcase;
	}

	public List<ComplaintVO> complaint_query(Integer queryType, String queryValue, Integer page, Integer pageCount,
			Integer userId)  throws Exception{
		List<ComplaintVO> result = new ArrayList<ComplaintVO>();
		PageRequest pagerequest= new PageRequest(page-1, pageCount);
		Specification<FdCase> specification = null;
		PredicateBuilder<FdCase> build =Specifications.<FdCase>and();
		if(queryType.intValue()==1){//查询个人列表
			build.eq("guestId", Integer.parseInt(queryValue));
		}else if(queryType.intValue()==2){//案件编号查询
			build.eq("id",queryValue);
		}else if(queryType.intValue()==5){//关键字查询
			String userid = queryValue.substring(0, queryValue.indexOf("|"));
			String key = queryValue.substring(queryValue.indexOf("|")+1);
			build.eq("guestId", userid);
			build.predicate(getspecificationoth(key));
		}
		
		specification = build.build();
		Page<FdCase>  pages=fdCaseRepository.findAll(specification,pagerequest);
		
	   List<FdCase> lists=	pages.getContent();
	   List<FdCode> fdCodeList = fdCodeRepository.findByTypeAndState(12, 1); // 1表示启用的字段
		Map<Integer,String> maps = casttomap(fdCodeList);
	   for(FdCase fdcase:lists){
		   ComplaintVO vo = new ComplaintVO();
		   vo.setCase_id(fdcase.getId().toString());
		   vo.setTitle(fdcase.getTitle());
		   vo.setComplaint_reason(fdcase.getCaseDesc());
		   vo.setComplaint_time(fdcase.getCaseTime());
		   vo.setCreated_time(fdcase.getGmtCreate());
		   vo.setComplaint_location(fdcase.getCaseAddress());
		   vo.setFiles(fdcase.getFileSrc());
		   vo.setCase_method(maps.get(fdcase.getPetitionWay()));
		   if(fdcase.getHandleType()==null){
			   vo.setStatus("未处理");
		   }else{
			   vo.setStatus(HandleState.getName(fdcase.getHandleType()));
		   }
		   vo.setCaseCode(fdcase.getPetitionCode());
		   //答复意见书  办结报告
		   List<FdCaseFeedbackAll> list1=  fdCaseFeedbackAllRepository.findbycaseidandtype(fdcase.getId(), 9);
		   //受理告知书 
		   List<FdCaseFeedbackAll> list2=  fdCaseFeedbackAllRepository.findbycaseidandtype(fdcase.getId(), 3);
		   //不予受理告知书 
		   List<FdCaseFeedbackAll> list3=  fdCaseFeedbackAllRepository.findbycaseidandtype(fdcase.getId(), 5);

		   FdCaseFeedbackAll detail= null;
		   if(!list1.isEmpty()){
			   detail= list1.get(0);
			   if(detail.getEnclosure2()!=null){
				   String[] dfyjs=new String[]{detail.getEnclosure2()};
				   vo.setDfyjs(dfyjs);
			   }
			   
			   if(detail.getEnclosure1()!=null){
				   String[] bjbg=new String[]{detail.getEnclosure1()};
				   vo.setBjbg(bjbg);
			   }
			  
			  
		   }
		   if(!list2.isEmpty()){
			   detail= list2.get(0);
			   if(detail.getEnclosure1()!=null){
				   String[] slgzs=new String[]{detail.getEnclosure1()};
				   vo.setSlgzs(slgzs);
			   }
		   }
		   
		   if(!list3.isEmpty()){
			   detail= list3.get(0);
			   if(detail.getEnclosure1()!=null){
				   String[] dismiss=new String[]{detail.getEnclosure1()};
				   vo.setDismiss(dismiss);
			   }
		   }
		   
		   result.add(vo); 
	   }
		
		
		return result;
	}
	
	private Specification getspecificationoth(String key) {
		PredicateBuilder<FdCase> build=Specifications.<FdCase>or();
		String filterkey="%"+ key+"%";
		build.like("caseAddress",filterkey).like("caseDesc", filterkey);
		return build.build();
	}
	
      /**
       * 
       * @param sf_person_id 上访人员id  appraiseName
       * @param complaint_id 案件编号
       * @param beEstimate_tag  评价人tag 通过tag来确定人员所在部门，如：XF信访 SF上访 BA办案人员  departmentId unitId
       * @param attitude    态度
       * @param business    业务熟练度
       * @param satisfaction  办案满意度
       * @param advice          评价意见内容 
       * @author ZhangHuaRong
       * @update 2017年5月18日 下午2:10:39
       */
	public FpAppraiseEntity evaluation_submit(Integer sf_person_id, String complaint_id, String beEstimate_tag,
			String attitude, String business, String satisfaction, String advice) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserVO show_sf_people_info(Integer id) {
		//FdGuest guest = fdGuestRepository.getGuestByIdcard(idcard);
		FdGuest guest = fdGuestRepository.getGuestById(id);
		UserVO result = new UserVO();
		result.castfromguest(guest);
		
		return result;
	}

	public FpAppraiseEntity evaluation_submit(FpAppraiseEntity entity) throws Exception{
		Timestamp now = new Timestamp(System.currentTimeMillis());
		entity.setGmtCreate(now);
		entity.setGmtModified(now);
		fpAppraiseDao.save(entity);
		return entity;
	}
 
	public UserVO modify_personInfo(String person_id, String username, String name,String phone_num, String idcard, String sex,
			String nation, String provincedomain, String citydomain, String towndomain, String address, String work,
			String email, String attachment, String birth_date, String postal_address, String files) throws Exception{
		FdGuest guest = fdGuestRepository.getGuestById(Integer.parseInt(person_id));
		if(name!=null){
			guest.setName(name);
		}

		if(username!=null){
			guest.setUsername(username);
		}

		if(phone_num!=null){
			guest.setPhone(phone_num);
		}
		if(idcard!=null){
			guest.setIdcard(idcard);
		}
		if(email!=null){
			guest.setEmail(email);
		}
		if(work!=null){
			guest.setWork(work);
		}
		if(nation!=null){
			guest.setEthnic(nation);
		}
		if(sex!=null){
			if(sex.equals("女")||sex.equals("0")){
				guest.setSex(0);
			}else if(sex.equals("男")||sex.equals("1")){
				guest.setSex(1);
			}
		}
		
		if(files!=null){
			guest.setFileSrc(files);
		}
		if(provincedomain!=null){
			guest.setNaProvince(provincedomain);
		}
        if(citydomain!=null){
        	guest.setNaCity(citydomain);
		}
        if(towndomain!=null){
        	guest.setNaCounty(towndomain);
		}
        if(address!=null){
        	guest.setContactAddress(address);
		}
        if(birth_date!=null){
        	guest.setBirthday(birth_date);
		}
        if(postal_address!=null){
			guest.setNowAddress(postal_address);
		}
        
		
		
		fdGuestRepository.save(guest);
		return null;
	}

	public FdGuest update_password(Integer id, String old_password, String new_password) {
		FdGuest guest = fdGuestRepository.getGuestById(id);
		if(!guest.getPassWd().equals(MD5Utils.getPwd(old_password))){
			throw BizException.OLD_PW_WRONG;
		}
		guest.setPassWd(MD5Utils.getPwd(new_password));
		fdGuestRepository.saveAndFlush(guest);
		return guest;
	}

	

	public FdGuest forget_modify_password(String phone, String sms_code, String password) {
		veryfiy(phone, sms_code);
		FdGuest user = fdGuestRepository.getGuestByPhone(phone);
		user.setPassWd(MD5Utils.getPwd(password));
		fdGuestRepository.saveAndFlush(user);
		return user;
	}

	public FpAppraiseEntity evaluation_submit1(Integer case_id, Integer user_id, Integer type, String context, Integer star,
			String reason) {
		String nowtime = DateUtils.formatDateTime(new Date());
		List<FpAppraiseEntity> lists= fpAppraiseDao.getByCaseId(case_id);
		FpAppraiseEntity entity = null;
		
		if(lists.isEmpty()){
			entity = new FpAppraiseEntity();
		}else{
			entity = lists.get(0);
		}
		
		entity.setCaseId(case_id);
		entity.setUserid(user_id);
		if(type.intValue()==1){//对信访部门的评价
			entity.setDepartmentContent(context);
			entity.setDepartmentDate(nowtime);
			entity.setDepartmentId(star);
			
		}else if(type.intValue()==2){//对责任单位的评价
			entity.setUnitContent(context);
			entity.setUnitDate(nowtime);
			entity.setUnitId(star);
		}else{
			entity.setAppraiseContent(context);
			entity.setCaseSatisfiedId(star);
		}
		entity.setGmtCreate(new Timestamp(new Date().getTime()));
	//	entity.setIsCase(Byte.valueOf(star));
		entity.setAppraiseContent(reason);
		
		FpAppraiseEntity result = fpAppraiseDao.save(entity);
		return result;
	}

	
	 
	 

}
