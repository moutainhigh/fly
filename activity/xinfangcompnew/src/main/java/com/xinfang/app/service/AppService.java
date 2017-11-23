package com.xinfang.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.xinfang.app.VO.Address;
import com.xinfang.app.VO.Qsx;
import com.xinfang.app.dao.FdConsultDao;
import com.xinfang.app.dao.FdLawDao;
import com.xinfang.app.dao.FdSuggestDao;
import com.xinfang.app.model.Area;
import com.xinfang.app.model.City;
import com.xinfang.app.model.FdConsult;
import com.xinfang.app.model.FdSuggest;
import com.xinfang.app.model.Province;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.dao.FdGuestRepository;
import com.xinfang.dao.LawRulesDao;
import com.xinfang.evaluating.dao.FpAppraiseDao;
import com.xinfang.evaluating.model.FpAppraiseEntity;
import com.xinfang.model.FdCode;
import com.xinfang.model.FdGuest;
import com.xinfang.model.LawRulesEntity;
import com.xinfang.utils.Base64Utils;
import com.xinfang.utils.MD5Utils;

@Service
public class AppService {
	@Autowired
	FdGuestRepository fdGuestRepository;
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	FdSuggestDao fdSuggestDao;
	@Autowired
	FdConsultDao fdConsultDao;
	@Autowired
	FdLawDao fdLawDao;
	@Autowired
	LawRulesDao lawRulesDao;
	@Autowired
	FdCodeRepository fdCodeRepository;
	@Autowired
	CityService cityService;
	
	 @Autowired
	 FpAppraiseDao fpAppraiseDao;

	public FdGuest appService(String sf_person_id, String old_password, String new_password) {
		FdGuest guest = fdGuestRepository.getGuestById(Integer.parseInt(sf_person_id));
		if(!guest.getPassWd().equals(MD5Utils.getPwd(old_password))){
			throw BizException.OLD_PW_WRONG;
		}
		guest.setPassWd(MD5Utils.getPwd(new_password));
		fdGuestRepository.saveAndFlush(guest);
		return guest;
	}

	public boolean compare_code(String telephone, String sms_code) {
		veryfiy(telephone,sms_code);
		return true;
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

	public Integer register(String username, String password, String telephone, String name) {
		FdGuest gues= fdGuestRepository.getGuestByPhone(telephone);
		if(gues==null){
			gues=new FdGuest();
		}
		if(gues.getName()!=null){
			throw  new BizException(10011, "已注册，请登录或者找回密码");
		}
		
		gues.setName(name);
		gues.setPhone(telephone);
		//gues.setUsername(username);
		gues.setPassWd(MD5Utils.getPwd(password));
		FdGuest guest =fdGuestRepository.save(gues);
		return guest.getId();
	}

	public FdSuggest suggest_submit(Integer sf_person_id, String sc_text, Integer send_county, Integer sc_type,
			String title) {
		FdSuggest suggest = new FdSuggest();
		suggest.setInput_time(new Date());
		suggest.setSf_person_id(sf_person_id);
		suggest.setSuggest_text(sc_text);
		suggest.setType(sc_type);
		suggest.setTitle(title);
		suggest.setSuggest_status(1);
		suggest.setCase_xf_des(send_county);
		
		FdSuggest result = fdSuggestDao.save(suggest);
		result.setCase_id(result.getId().toString());
		fdSuggestDao.save(suggest);
		
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
		suggest.setConsult_status(1);
		FdConsult result = fdConsultDao.save(suggest);
		result.setCase_id(result.getId().toString());
		fdConsultDao.save(result);
		return suggest;
	}

	public List<LawRulesEntity> law_query(Integer queryType, String queryValue, Integer page, Integer pageCount) {
		PageRequest pagerequest= new PageRequest(page-1, pageCount);
		Specification<LawRulesEntity> specification = null;
		PredicateBuilder<LawRulesEntity> build =Specifications.<LawRulesEntity>and();
        //queryType=0查全部；queryType=3以类别和关键字查询；queryValue关键字|选中的类别	
		if(queryType.intValue()==3){
			String key = queryValue.substring(0, queryValue.indexOf("|"));
			String type = queryValue.substring(queryValue.indexOf("|")+1);
			if(StringUtils.isNotEmpty(key)){
				build.like("content", "%"+key+"%");
			}
			if(StringUtils.isNotEmpty(type)){
				build.eq("category_id", type);
			}
			
			
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

	public List<FdSuggest> searchFdSuggest(Integer queryType, String queryValue, Integer page, Integer pageCount) {
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

	
	private Map<Integer, String> casttomap(List<FdCode> fdCodeList) {
		Map<Integer,String> result = new HashMap<Integer,String>();
		for(FdCode code:fdCodeList){
			result.put(code.getCode(), code.getName());
		}
		return result;
	}

	public List<Qsx> show_qsx_name(int i) {
		List<Qsx> result = new ArrayList<Qsx>();
		List<FdCode> fdCodeList = fdCodeRepository.findByTypeAndState(i, 1); // 1表示启用的字段
		for(FdCode code:fdCodeList){
			if(1506==code.getCode().intValue()){
				continue;
			}
			Qsx qsx = new Qsx();
			qsx.setQsx_code(code.getCode().toString());
			qsx.setQsx_name(code.getName());
			result.add(qsx);
		}
		
		return result;
	}
/**
 * 
 * @param c_id
 * @param type 地区类型， 1、2、3  省 市 县、区
 * @return
 * @description TODO
 * @version 1.0
 * @author ZhangHuaRong
 * @update 2017年5月31日 上午10:13:23
 */
	public List<Address> get_nation(String c_id, String type) {
		List<Address> result = new ArrayList<Address>();
		if("1".equals(type)){
			List<Province> provinces = cityService.getAllProvince();
			for(Province pro:provinces){
				Address vo = new Address();
				vo.setC_id(pro.getProvinceid());
				vo.setName(pro.getProvince());
				result.add(vo);
			}
		}else if("2".equals(type)){
			List<City> citys = cityService.getCityByProvinceId(c_id);
			for(City city:citys){
				Address vo = new Address();
				vo.setC_id(city.getCityid());
				vo.setName(city.getCity());
				result.add(vo);
			}
		}else {
			List<Area> areas= cityService.getAreaByCityId(c_id);
			for(Area area:areas){
				Address vo = new Address();
				vo.setC_id(area.getAreaid());
				vo.setName(area.getArea());
				result.add(vo);
			}
		}
         
		return result;
	}

public List<FpAppraiseEntity> getComments(Integer user_id, Integer case_id) {
	List<FpAppraiseEntity> result = fpAppraiseDao.getByCaseIdAndUserId(case_id, user_id);
	return result;
}

}
