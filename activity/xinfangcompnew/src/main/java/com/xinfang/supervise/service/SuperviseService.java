package com.xinfang.supervise.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.xinfang.VO.LogInInfo;
import com.xinfang.context.PageFinder;
import com.xinfang.context.Respon;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdWarnRepository;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdGuest;
import com.xinfang.model.FdWarn;
import com.xinfang.service.IdentityService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.supervise.VO.FdCaseVO;
import com.xinfang.supervise.dao.SuperviseDetailDao;
import com.xinfang.supervise.dao.SuperviseRecordDao;
import com.xinfang.supervise.model.Supervise;
import com.xinfang.supervise.model.SuperviseDetail;
import com.xinfang.utils.DateUtils;

@Service
public class SuperviseService {
    
	@Autowired
	SuperviseRecordDao superviseRecordDao;
	@Autowired
	SuperviseDetailDao superviseDetailDao;
	@Autowired
	FdCaseRepository fdCaseRepository;
	@Autowired
	IdentityService identityService;
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	TzmPetitionService tzmPetitionService;
	@Autowired
	FdWarnRepository fdWarnRepository;
	
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
	
	
	public void create( Integer createDepId, String createDepName, Integer userId, Integer accidentDepId,
			String accidentDepName, String accidentUsers, String accidentReason, String accidentType,
			String enclosure) throws Exception{
		Date now = new Date();
		SuperviseDetail entity = new SuperviseDetail();
		entity.setAccidentDepId(accidentDepId);
		entity.setAccidentDepName(accidentDepName);
		entity.setAccidentReason(accidentReason);
		entity.setAccidentType(accidentType);
		entity.setType(1);
		entity.setAccidentUsers(accidentUsers);
		//entity.setAppointaddress(appointaddress);
		//entity.setAppointtime(appointtime);
		//entity.setAppointuser(appointuser);
		//entity.setCaseId(caseId);
		entity.setCreateDepId(createDepId);
		entity.setCreateDepName(createDepName);
		entity.setCreateTime(now);
		entity.setCreateUser(userId);
		entity.setEnclosure(enclosure);
		entity.setState(1);
		entity.setUpdateTime(now);
		
		System.out.println("2 : "+DateUtils.formatDateTime(now));
		superviseDetailDao.save(entity);
		
	}

/**
 * 
 * @param startdate 开始时间
 * @param endDate   结束时间
 * @param key       关键词
 * @param page      开始页数
 * @param pagesize   每页条数
 * @param state      1查询全部,0按照条件查询
 * @param type  1督责列表  ,2历史督责列表 ,3问责列表 ,4历史问责列表 ,5被退回问责列表,6问责过程列表
 * @param endstate 0查询全部 1已经问责 2未问责
 * @author ZhangHuaRong
 * @update 2017年5月10日 上午9:40:55
 */
	public PageFinder<Supervise> query(String startdate, String endDate, String key, Integer page, Integer pagesize,
			Integer state,Integer type, Integer endstate) throws Exception{
		Map<String,Supervise> map = new HashMap<String,Supervise>();
		
    	//List<SuperviseDetail> results =	superviseDetailDao.findAll();
		Specification<SuperviseDetail> specification =getspecification(startdate,endDate,key,page,pagesize,state,type,endstate,1,null,null,null,null);
		Page<SuperviseDetail>  pages = superviseDetailDao.findAll(specification, new PageRequest(0, 1000));
		List<SuperviseDetail> results=pages.getContent();
    	for(SuperviseDetail detail:results){
    	//	String idkey =detail.getCreateDepId()+ ":"+detail.getAccidentDepId();
    		String idkey ="duze"+ ":"+detail.getAccidentDepId();
    		if(map.containsKey(idkey)){
    			Supervise value =map.get(idkey);
    			value.setKey(idkey);
    			if(value.getStarttime().getTime()>detail.getCreateTime().getTime()){
    				value.setStarttime(detail.getCreateTime());
    			}
    			if(value.getEndtime().getTime()<detail.getCreateTime().getTime()){
    				value.setEndtime(detail.getCreateTime());
    			}
    			value.getSuperviseDetailIds().add(detail.getId());
    		//	value.getCaseIds().add(detail.getCaseId());
    			value.setSuperviseNum(value.getSuperviseNum()+1);
    			map.put(idkey, value);
    		}else{
    			Supervise value = new Supervise();
    			value.setKey(idkey);
    			value.setAccidentDepId(detail.getAccidentDepId());
    			value.setAccidentDepName(detail.getAccidentDepName());
    			/*Set<Integer> caseids = new HashSet<Integer>();
    			caseids.add(detail.getCaseId());
    			value.setCaseIds(caseids);*/  //TODO 需要统计
    			value.setCreateDepId(detail.getCreateDepId());
    			value.setCreateDepName(detail.getCreateDepName());
    			value.setCreateTime(detail.getCreateTime());
    			value.setFeiVisit(0);
    			value.setGroupVisit(0);
    			value.setOverdue(0);
    			value.setOverTimereturnNum(0);
    			value.setState(0);
    		    List<Integer> ids = 	new ArrayList<Integer>();
    		    ids.add(detail.getId());
    			value.setSuperviseDetailIds(ids);
    			value.setSuperviseNum(1);
    			if(startdate!=null){
    				value.setStarttime(DateUtils.parseDate(startdate));
    			}else{
    				value.setStarttime(detail.getCreateTime());
    			}
    			if(endDate!=null){
    				value.setEndtime(DateUtils.parseDate(endDate));
    			}else{
    				value.setEndtime(new Date());
    			}
    			PredicateBuilder<FdWarn> build =Specifications.<FdWarn>and();
    			build.between("createTime", new Range<>(value.getStarttime(), value.getEndtime()));
    			//build.eq("createDep", value.getCreateDepId());
    			build.eq("receiveDep", value.getAccidentDepId());
    			build.eq("level", 3);
    			
    			Specification<FdWarn> specifications  = build.build();
    			
    			List<FdWarn> result =fdWarnRepository.findAll(specifications);
    			if(!result.isEmpty()){
    				Set <Integer> caseids = new HashSet<Integer>();
    				for(FdWarn warn:result){
    					caseids.add(warn.getCaseId());
    				}
    				value.setCaseIds(caseids);
    			}
    			
    			map.put(idkey, value);
    		}
    		
    	}
    	
    	 PageFinder<Supervise> result = new PageFinder<>(page, pagesize, map.size(), mapTransitionList(map));
		
		return result;
	}
	/**
	 * 
	 * @param startdate 开始时间
	 * @param endDate   结束时间
	 * @param key       关键词
	 * @param page      开始页数
	 * @param pagesize   每页条数
	 * @param state      1查询全部,0按照条件查询
	 * @param type  1督责列表  ,2历史督责列表 ,3问责列表 ,4历史问责列表 ,5被退回问责列表,6问责过程列表
	 * @param endstate 0查询全部 1已经问责 2未问责
	 * @author ZhangHuaRong
	 * @param string3 
	 * @param string2 
	 * @param string 
	 * @throws ParseException 
	 * @update 2017年5月10日 上午9:40:55
	 */
	private Specification<SuperviseDetail> getspecification(String startdate, String endDate, String key, Integer page,
		Integer pagesize, Integer state, Integer type, Integer endstate,Integer istest,String name, String string, String string2, String string3) throws ParseException {
		
		PredicateBuilder<SuperviseDetail> build =null;
		String newkey = "%"+key+"%";
		if(istest.intValue()!=1){
			build=Specifications.<SuperviseDetail>or();
			build.like(name, newkey).like(string, newkey).like(string2, newkey).like(string3, newkey);
			return build.build();
		}else{
			build =Specifications.<SuperviseDetail>and();
		}
		Date start =null;
		Date end =null;
		if(startdate!=null){
			start=DateUtils.strToDate2(startdate+" 00:00:01");
		}else{
			start = DateUtils.strToDate2("2017-01-01 13:30:30");
		}
		
		if(endDate!=null){
			end=DateUtils.strToDate2(endDate+" 23:59:59");
		}else{
			end=new Date();
		}
		
		build.between("createTime", new Range<>(start, end));
		if(key!=null){
			build.predicate(getspecification(startdate, endDate, key, null, null, null, null, null, 2,"createDepName","accidentDepName","accidentReason","accidentType"));
			

		//	build.like("createDepName", key).like("accidentDepName", key).like("accidentReason", key).like("accidentType", key);
		}
		if(endstate==1){//查询已经问责
			build.ne("msg", null);
		}else if(endstate==2){
			build.eq("msg", null);
		}
		//1督责列表  ,2历史督责列表 ,3问责列表 ,4历史问责列表 ,5被退回问责列表,6问责过程列表
		if(type==1){//1督责列表 
			build.eq("type",1);	
		}else if(type==2){//2历史督责列表
			build.eq("type",1);	
		}else if(type==3){//3问责列表
			build.eq("type",2);	
		}else if(type==4){//4历史问责列表 
			build.eq("type",2);	
		}else if(type==5){//5被退回问责列表
			build.eq("state",3);	
		}else if(type==6){//6问责过程列表
			build.ne("msg", null);
		}
		
		
		
	return build.build();
}

	private  List<Supervise>  mapTransitionList(Map<String,Supervise> map) {
		List<Supervise> list = new ArrayList<Supervise>();
		Iterator<Entry<String, Supervise>> iter = map.entrySet().iterator();  //获得map的Iterator
		while(iter.hasNext()) {
			Entry entry = iter.next();
			list.add((Supervise) entry.getValue());
		}
		return list;
	}


	public PageFinder<FdCaseVO> getcases(Integer[] caseIds) throws Exception {
		List<FdCase> results = fdCaseRepository.findByIdIn(caseIds);
		List<FdCaseVO> lists = new ArrayList<FdCaseVO>();
		for(FdCase fdcase:results){
			List<SuperviseDetail> details = superviseDetailDao.findByCaseId(fdcase.getId());
			SuperviseDetail detail = null;
			FdCaseVO vo = new FdCaseVO();
			if(!details.isEmpty()){
				detail=details.get(0);
				vo.setAccindetReason(detail.getAccidentReason());
				vo.setAccinentDepName(detail.getAccidentDepName());
			}
			FdGuest guest=identityService.getGuestById(fdcase.getGuestId());
			
			
			vo.setArea(fdcase.getCaseAddress());
			vo.setCaseId(fdcase.getId());
			vo.setNote(fdcase.getCaseDesc());
			if(fdcase.getPetitionWhy()!=null){
				vo.setCompreason(fdcase.getPetitionWhy().toString());

			}
			vo.setCreateTime(fdcase.getGmtCreate());
			vo.setGuestIdCard(guest.getIdcard());
			vo.setGuestName(guest.getUsername());
			lists.add(vo);
		}
		 PageFinder<FdCaseVO> pages = new  PageFinder<FdCaseVO>();
		 pages.setData(lists);
		return pages;
	}
	
	
	
    /**
     * 
     * @param superviseDetailIds
     * @param type 1督责详情列表  2回复确认列表 3被退回问责列表
     * @description TODO
     * @author ZhangHuaRong
     * @update 2017年5月10日 上午10:20:32
     */
	public PageFinder<SuperviseDetail> queryDetails(Integer[] superviseDetailIds, int type) {
		List<SuperviseDetail> results =superviseDetailDao.findByIdIn(superviseDetailIds);
		for(SuperviseDetail detail:results){
			addUserInfos(detail);
		}
		 PageFinder<SuperviseDetail> page = new  PageFinder<SuperviseDetail>();
		 page.setData(results);
		return page;
	}

	private void addUserInfos(SuperviseDetail detail) {
		   List<LogInInfo> all = new ArrayList<LogInInfo>();
		   List<LogInInfo> all1 = new ArrayList<LogInInfo>();
           String users = detail.getAccidentUsers();
           String user1 = detail.getAppointuser();
           if(users!=null){
        	   String[] user= users.split(",");
        	   for(String id:user){
        		   if(StringUtils.isNotBlank(id)){
        			   LogInInfo info = getUserInfo(Long.parseLong(id));
            		   if(info!=null){
            			   all.add(info) ;
            		   } 
        		   }
        		   
        		   
        	   }
           }
           
           if(user1!=null){
        	   String[] uu= user1.split(",");
        	   for(String id:uu){
        		   LogInInfo info = getUserInfo(Long.parseLong(id));
        		   if(info!=null){
        			   all1.add(info) ;
        		   }
        		   
        	   }
           }
           detail.setAppointuserinfo(all1);
           detail.setUserinfo(all);
		
	}


	public void create(Integer createDepId, String createDepName, Integer userId, Integer accidentDepId,
			String accidentDepName, String accidentUsers, String accidentReason, String accidentType, String enclosure,
			String appointtime, String appointaddress, String appointuser) throws Exception {
		
		
		Date now = new Date();
		SuperviseDetail entity = new SuperviseDetail();
		entity.setAccidentDepId(accidentDepId);
		entity.setAccidentDepName(accidentDepName);
		entity.setAccidentReason(accidentReason);
		entity.setAccidentType(accidentType);
		entity.setType(2);
		entity.setAccidentUsers(accidentUsers);
		entity.setAppointaddress(appointaddress);
		entity.setAppointtime(DateUtils.parseDateTime(appointtime));
		entity.setAppointuser(appointuser);
		//entity.setCaseId(caseId);
		entity.setCreateDepId(createDepId);
		entity.setCreateDepName(createDepName);
		entity.setCreateTime(now);
		entity.setCreateUser(userId);
		entity.setEnclosure(enclosure);
		entity.setState(1);
		entity.setUpdateTime(now);
		
		superviseDetailDao.save(entity);
		
		
	}

	public void confirm(Integer superviseDetailId, Integer state, String reason, String user) {
		SuperviseDetail dateil = superviseDetailDao.findOne(superviseDetailId);
		dateil.setState(state);
		dateil.setReturnreason(reason);
		if(user!=null){
			dateil.setAccidentUsers(user);
		}
		superviseDetailDao.saveAndFlush(dateil);
	}

	public void recordcreate(Integer superviseDetailId, String msg, String enclosure1) {
		SuperviseDetail dateil = superviseDetailDao.findOne(superviseDetailId);
		dateil.setMsg(msg);
		dateil.setEnclosure1(enclosure1);
		superviseDetailDao.saveAndFlush(dateil);
		
	}

	public Respon<SuperviseDetail> getSuperviseDetail(Integer superviseDetailId) {
		SuperviseDetail result = superviseDetailDao.findOne(superviseDetailId);
		addUserInfos(result);
		Respon<SuperviseDetail> obj = new Respon<SuperviseDetail>();
		obj.setResults(result);
		return obj;
	}

	public PageFinder<SuperviseDetail> queryoth(String startdate, String endDate, String key, Integer page, Integer pagesize,
			Integer type, Integer depId,Integer endstate) throws ParseException {
		 Specification<SuperviseDetail> specification =getspecificationoth(startdate,endDate,key,page,pagesize,type,1,"createDepName","accidentDepName","accidentReason","accidentType",endstate,depId);
		 Page<SuperviseDetail>  pages = superviseDetailDao.findAll(specification, new PageRequest(page, pagesize));
		 List<SuperviseDetail> lists= pages.getContent();
		 for(SuperviseDetail detail:lists){
			 addUserInfos(detail);
		 }
		 PageFinder<SuperviseDetail> result = new PageFinder<>(page, pagesize, lists.size(), lists);
		
		return result;
	}

	private Specification<SuperviseDetail> getspecificationoth(String startdate, String endDate, String key,
			Integer page, Integer pagesize, Integer type, Integer istest, String name, String string, String string2,
			String string3,Integer endstate, Integer depId) throws ParseException {
		PredicateBuilder<SuperviseDetail> build =null;
		String newkey = "%"+key+"%";
		if(istest.intValue()!=1){
			build=Specifications.<SuperviseDetail>or();
			build.like(name, newkey).like(string, newkey).like(string2, newkey).like(string3, newkey);
			return build.build();
		}else{
			build =Specifications.<SuperviseDetail>and();
		}
		
		Date start =null;
		Date end =null;
		if(startdate!=null){
			start=DateUtils.strToDate2(startdate+" 00:00:01");
		}else{
			start = DateUtils.strToDate2("2017-01-01 13:30:30");
		}
		if(endDate!=null){
			end=DateUtils.strToDate2(endDate+" 23:59:59");
		}else{
			end=new Date();
		}
		build.between("createTime", new Range<>(start, end));
		if(key!=null){
			build.predicate(getspecification(startdate, endDate, key, null, null, null, null, null, 2,"createDepName","accidentDepName","accidentReason","accidentType"));
		}
		if(endstate.intValue()==1){//查询已经问责
			build.ne("msg", null);
		}else if(endstate==2){
			build.eq("msg", null);
		}
		//1回复确认列表 ,2被退回问责列表,3问责过程记录列表
		if(type==1){//1回复待确认列表
			build.eq("type",2);	
			build.eq("createDepId",depId).eq("state", 1);	
		}else if(type==2){//2被退回问责列表
			build.eq("type",2);	
			build.predicate(getspecificationoth(depId));
			build.eq("state", 3);
		}else if(type==3){//3问责过程记录列表(待记录)
			build.eq("type",2);	
			build.predicate(getspecificationoth(depId));
			build.eq("state", 2);
			build.eq("msg", null);
		}
		
		return build.build();
	}

	private Specification getspecificationoth(Integer depId) {
		PredicateBuilder<SuperviseDetail> build=Specifications.<SuperviseDetail>or();
		build.eq("createDepId", depId.intValue()).eq("accidentDepId", depId.intValue());
		return build.build();
	}
	
}
