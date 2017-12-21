package com.xinfang.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ly.file.FastDFSClient;
import com.xinfang.VO.LogInInfo;
import com.xinfang.context.StateConstants;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.dao.FdGuestRepository;
import com.xinfang.dao.SfbDao;
import com.xinfang.dao.SqbDao;
import com.xinfang.dao.XjbDao;
import com.xinfang.enums.HandleLimitState;
import com.xinfang.enums.HandleState;
import com.xinfang.enums.PetitionType;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.model.FdDepCase;
import com.xinfang.model.FdGuest;
import com.xinfang.service.IdentityService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.utils.Base64Utils;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;

/**
 * Created by sunbjx on 2017/3/22.
 */
@Repository("identityService")
@Transactional
public class IdentityServiceImpl implements IdentityService {

	@Autowired
	private FdGuestRepository fdGuestRepository;
	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Autowired
	private FdCodeRepository fdCodeRepository;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	TzmPetitionService tzmPetitionService;
	@Autowired
	FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;// 案件反馈表（日志表）
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	@Autowired
	private SfbDao sfbDao;
	@Autowired
	private SqbDao sqbDao;
	@Autowired
	private XjbDao xjbDao;

	@Override
	public Map<String, Integer> save(FdGuest fdGuest, String strPetitionTime, Integer petitionNumbers,
			Integer associatedNumbers, String strFollowIds, Integer form) {

		// 重点人员复制到敏感时期重点人员信息里面
		/*
		 * if (fdGuest.isFocus()) {
		 * 
		 * }
		 */
		Map<String, Integer> map = new HashMap<>();
		FdGuest result = null;
		List<Integer> isRepeat = new ArrayList<Integer>();

		try {
			// Guest 去重判断
			if (StringUtils.isEmpty(fdGuest.getIdcard())) {
				// 有一条则执行更新操作
				isRepeat = fdGuestRepository.getRepeat(fdGuest.getUsername(), fdGuest.getPhone());
				if (isRepeat.size() == 0) {
					fdGuest.setIsPetition((byte) 1);
					fdGuest.setGmtCreate(new Date());
					fdGuest.setPetitionTime(DateUtils.parseDateTime(strPetitionTime));
					result = isSaveGuest(fdGuest);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					fdGuest.setId(result.getId());
					result = isSaveGuest(fdGuest);
				}
			} else {
				isRepeat = fdGuestRepository.getRepeatByIdcard(fdGuest.getIdcard());
				if (isRepeat.size() == 0) {
					fdGuest.setIsPetition((byte) 1);
					fdGuest.setGmtCreate(new Date());
					fdGuest.setPetitionTime(DateUtils.parseDateTime(strPetitionTime));
					result = isSaveGuest(fdGuest);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					fdGuest.setId(result.getId());
					result = isSaveGuest(fdGuest);
				}
			}

			// 生成案件带入基础关联信息
			FdCase fdCase = new FdCase();
			fdCase.setGuestId(result.getId());
			fdCase.setPetitionTime(DateUtils.parseDateTime(strPetitionTime));
			fdCase.setFollowGuestIds(strFollowIds);
			fdCase.setPetitionNumbers(petitionNumbers);
			fdCase.setAssociatedNumbers(associatedNumbers);
			fdCase.setPetitionWay(result.getPetitionWayChild());
			fdCase.setPetitionNames(result.getUsername());
			fdCase.setState(StateConstants.STATE_DEFAULT);
			fdCase.setCreatorId(result.getCreatorId());
			Map dw = tzmPetitionService.getDwInfoRyId(result.getCreatorId());
			fdCase.setCreateUnitid((dw == null) ? 0 : Integer.valueOf(dw.get("dwId").toString()));
			fdCase.setHandleDuration(0);
			fdCase.setIsHandle((byte) 0);

			fdCase.setGmtCreate(new Date());
			// 窗口编号 , form 窗口还是分流室
			// 分流室录入
			if (form == 0) {
				fdCase.setForm(StateConstants.FORM_SHUNT);
			}
			// 窗口直接录入
			if (form > 0) {
				fdCase.setForm(StateConstants.FORM_WINDOW);
				fdCase.setWindowNumber(form);
				fdCase.setHandleUserid(result.getCreatorId());
			}
			if (form == -1) {
				fdCase.setForm(StateConstants.FORM_NOT);
				fdCase.setHandleUserid(result.getCreatorId());
			}
			fdCase.setCurrentHandleState("待录入");

			FdCase caseResult = updateTypeAndPetitionCode(fdCase, strFollowIds, strPetitionTime);
			LogInInfo info = null;
			if (form == 0) {
				info = addFeedBack(result.getCreatorId().longValue(), caseResult.getId().intValue(),
						HandleState.SHUNT.getValue(), "分流室添加", fdGuest.getFileSrc());
			} else {
				info = addFeedBack(result.getCreatorId().longValue(), caseResult.getId().intValue(),
						HandleState.WINDOW.getValue(), "窗口添加", fdGuest.getFileSrc());
			}
			/****
			 * ******************zhanghr 2017-04-19 ************************
			 ***/
			int count = fdDepCaseRepository.countByCaseIdAndDepId(fdCase.getId().intValue(), info.getDwId());
			if (count < 1) {
				Integer total = fdCase.getHandleDuration();
				if (total == null || total.intValue() == 0) {
					total = 90;
				}
				Date now = new Date();
				FdDepCase depcase = new FdDepCase();
				depcase.setCaseId(fdCase.getId().longValue());
				depcase.setCreaterId(result.getCreatorId().longValue());
				depcase.setCreateTime(now);
				depcase.setStartTime(now);
				depcase.setDepId(info.getDwId());
				depcase.setEndTime(DateUtils.add(now, total * RuleUtil.getRule().getRegister() * 0.01f));
				depcase.setState(1);
				depcase.setNeedReceipt(1);
				depcase.setLimitTime(total * RuleUtil.getRule().getRegister() * 0.01f);
				depcase.setType(HandleLimitState.register.getValue());
				depcase.setNote(HandleLimitState.register.getName());
				// fdDepCaseRepository.save(depcase);
			}

			// 返回信访ID 和 案件ID
			map.put("guestId", result.getId());
			map.put("caseId", caseResult.getId());
			map.put("no", 1);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("no", 0);
			return map;
		}
		return map;
	}

	@Override
	public int update(FdGuest fdGuest, Integer guestId, String strPetitionTime, Integer caseId, Integer petitionNumbers,
			Integer associatedNumbers, String strFollowIds) {

		FdGuest result = fdGuestRepository.findOne(guestId);
		try {
			result.setPetitionWayParent(fdGuest.getPetitionWayParent());
			result.setPetitionWayChild(fdGuest.getPetitionWayChild());
			result.setPetitionTime(DateUtils.parseDateTime(strPetitionTime));
			result.setCardType(fdGuest.getCardType());
			result.setUsername(fdGuest.getUsername());
			result.setSex(fdGuest.getSex());
			result.setEthnic(fdGuest.getEthnic());
			result.setBirthday(fdGuest.getBirthday());
			result.setNationality(fdGuest.getNationality());
			result.setCensusRegister(fdGuest.getCensusRegister());
			result.setIdcard(fdGuest.getIdcard());
			result.setPhone(fdGuest.getPhone());
			result.setContactAddress(fdGuest.getContactAddress());
			result.setFinalTel(fdGuest.getFinalTel());
			result.setNowAddress(fdGuest.getNowAddress());
			result.setZipCode(fdGuest.getZipCode());
			result.setIsFocus(fdGuest.getIsFocus());
			result.setEmployedInfo(fdGuest.getEmployedInfo());
			result.setCaProvince(fdGuest.getCaProvince());
			result.setCaCity(fdGuest.getCaCity());
			result.setCaCounty(fdGuest.getCaCounty());
			result.setNaProvince(fdGuest.getNaProvince());
			result.setNaCity(fdGuest.getNaCity());
			result.setNaCounty(fdGuest.getNaCounty());
			result.setFileSrc(fdGuest.getFileSrc());
			result.setGmtModified(new Date());
			result.setIsAnonymity(fdGuest.getIsAnonymity());
			result.setHeadPic(fdGuest.getHeadPic());

			// 更新 Guest
			FdGuest upGuest = isSaveGuest(result);

			// 更新案件的随访人ID 和 上访人类型
			FdCase fdCase = fdCaseRepository.findOne(caseId);
			fdCase.setPetitionNumbers(petitionNumbers);
			fdCase.setAssociatedNumbers(associatedNumbers);

			// fdCase.setPetitionNames(upGuest.getUsername());
			// fdCase.setPetitionTime(upGuest.getPetitionTime());
			// fdCase.setPetitionWay(upGuest.getPetitionWayChild());

			updateTypeAndFollowIds(fdCase, strFollowIds);

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public Map<String, Object> saveFollowReturnId(String strPetitionTime, String username, String idcard,
			String censusRegister, String phone) {

		Map<String, Object> map = new HashedMap();
		FdGuest result = null;
		FdGuest fdGuest = new FdGuest();
		List<Integer> isRepeat = new ArrayList<Integer>();
		try {
			fdGuest.setPetitionTime(DateUtils.parseDateTime(strPetitionTime));
			fdGuest.setUsername(username);
			fdGuest.setIdcard(idcard);
			fdGuest.setCensusRegister(censusRegister);
			fdGuest.setPhone(phone);
			fdGuest.setGmtCreate(new Date());

			// Guest 去重判断
			if (StringUtils.isEmpty(idcard)) {
				// 如果没有或者两条以上 执行新增，有一条则执行更新操作
				isRepeat = fdGuestRepository.getRepeat(username, phone);
				if (isRepeat.size() == 0) {
					result = fdGuestRepository.save(fdGuest);
					// map.put("isRepeat", 0);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					fdGuest.setId(result.getId());
					result = fdGuestRepository.save(fdGuest);
					// map.put("isRepeat", 1);
				}
			} else {
				isRepeat = fdGuestRepository.getRepeatByIdcard(idcard);
				if (isRepeat.size() == 0) {
					result = fdGuestRepository.save(fdGuest);
					// map.put("isRepeat", 0);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					fdGuest.setId(result.getId());
					result = fdGuestRepository.save(fdGuest);
					// map.put("isRepeat", 1);
				}
			}

			if (!StringUtils.isEmpty(result)) {
				map.put("followId", result.getId());
			}
			return map;
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
	}

	@Override
	public int removeFollowById(String followIds) {
		int result = 0;
		try {
			result = fdGuestRepository.removeFollowById(StateConstants.STATE_REMOVW, Integer.valueOf(followIds));
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return result;
	}

	@Override
	public List<FdGuest> listFollowById(Integer caseId) {
		FdCase fdCase = null;
		try {
			fdCase = fdCaseRepository.findOne(caseId);
			if (StringUtils.isEmpty(fdCase))
				return null;
			if (StringUtils.isEmpty(fdCase.getFollowGuestIds()))
				return null;
			String[] strFollowIds = fdCase.getFollowGuestIds().split(",");
			Integer[] followIds = new Integer[strFollowIds.length];
			for (int i = 0; i < strFollowIds.length; i++) {
				followIds[i] = Integer.valueOf(strFollowIds[i]);
			}
			List<FdGuest> guestList = new ArrayList<FdGuest>();

			guestList = fdGuestRepository.listFollowById(followIds);
			return guestList;
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
	}

	@Override
	public FdGuest getGuestByIdForUrl(Integer guestId) {
		FdGuest fdGuest = fdGuestRepository.findOne(guestId);
		return fdGuest;
	}

	@Override
	public FdGuest getGuestById(Integer guestId) {
		FdGuest fdGuest = null;
		try {
			fdGuest = fdGuestRepository.findOne(guestId);
			fdGuest.setStrPetitionWayParent(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayParent()));
			fdGuest.setStrPetitionWayChild(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayChild()));
			fdGuest.setStrCardType(fdCodeRepository.getNameByCode(fdGuest.getCardType()));
			fdGuest.setStrType(fdGuest.getType() == PetitionType.ALONE.getValue() ? PetitionType.ALONE.getName()
					: PetitionType.GROUP.getName());
			fdGuest.setStrEmployedInfo(StringUtils.isEmpty(fdGuest.getEmployedInfo()) ? null
					: fdCodeRepository.getNameByCode(Integer.valueOf(fdGuest.getEmployedInfo())));
			fdGuest.setStrCaProvince(StringUtils.isEmpty(fdGuest.getCaProvince()) ? null
					: sfbDao.getProvinceNameById(Integer.valueOf(fdGuest.getCaProvince())));
			fdGuest.setStrCaCity(StringUtils.isEmpty(fdGuest.getCaCity()) ? null
					: sqbDao.getCityNameById(Integer.valueOf(fdGuest.getCaCity())));
			fdGuest.setStrCaCounty(StringUtils.isEmpty(fdGuest.getCaCounty()) ? null
					: xjbDao.getCountyNameById(Integer.valueOf(fdGuest.getCaCounty())));
			fdGuest.setStrNaProvince(StringUtils.isEmpty(fdGuest.getNaProvince()) ? null
					: sfbDao.getProvinceNameById(Integer.valueOf(fdGuest.getNaProvince())));
			fdGuest.setStrNaCity(StringUtils.isEmpty(fdGuest.getNaCity()) ? null
					: sqbDao.getCityNameById(Integer.valueOf(fdGuest.getNaCity())));
			fdGuest.setStrNaCounty(StringUtils.isEmpty(fdGuest.getNaCounty()) ? null
					: xjbDao.getCountyNameById(Integer.valueOf(fdGuest.getNaCounty())));
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdGuest;
		}
		return fdGuest;
	}

	@Override
	public Map<String, Object> getBaseByGuestIdAndCaseId(Integer guestId, Integer caseId) {
		Map<String, Object> map = new HashedMap();
		try {
			FdGuest fdGuest = fdGuestRepository.findOne(guestId);
			fdGuest.setStrPetitionWayParent(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayParent()));
			fdGuest.setStrPetitionWayChild(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayChild()));
			fdGuest.setStrCardType(fdCodeRepository.getNameByCode(fdGuest.getCardType()));
			fdGuest.setStrType(fdGuest.getType() == PetitionType.ALONE.getValue() ? PetitionType.ALONE.getName()
					: PetitionType.GROUP.getName());
			fdGuest.setStrCaProvince(StringUtils.isEmpty(fdGuest.getCaProvince()) ? null
					: sfbDao.getProvinceNameById(Integer.valueOf(fdGuest.getCaProvince())));
			fdGuest.setStrCaCity(StringUtils.isEmpty(fdGuest.getCaCity()) ? null
					: sqbDao.getCityNameById(Integer.valueOf(fdGuest.getCaCity())));
			fdGuest.setStrCaCounty(StringUtils.isEmpty(fdGuest.getCaCounty()) ? null
					: xjbDao.getCountyNameById(Integer.valueOf(fdGuest.getCaCounty())));
			fdGuest.setStrNaProvince(StringUtils.isEmpty(fdGuest.getNaProvince()) ? null
					: sfbDao.getProvinceNameById(Integer.valueOf(fdGuest.getNaProvince())));
			fdGuest.setStrNaCity(StringUtils.isEmpty(fdGuest.getNaCity()) ? null
					: sqbDao.getCityNameById(Integer.valueOf(fdGuest.getNaCity())));
			fdGuest.setStrNaCounty(StringUtils.isEmpty(fdGuest.getNaCounty()) ? null
					: xjbDao.getCountyNameById(Integer.valueOf(fdGuest.getNaCounty())));

			FdCase fdCase = fdCaseRepository.findOne(caseId);
			if (StringUtils.isEmpty(fdCase))
				return null;
			if (StringUtils.isEmpty(fdCase.getFollowGuestIds()))
				return null;
			String[] strFollowIds = fdCase.getFollowGuestIds().split(",");
			Integer[] followIds = new Integer[strFollowIds.length];
			for (int i = 0; i < strFollowIds.length; i++) {
				followIds[i] = Integer.valueOf(strFollowIds[i]);
			}
			List<FdGuest> guestList = new ArrayList<FdGuest>();

			guestList = fdGuestRepository.listFollowById(followIds);
			map.put("guest", fdGuest);
			map.put("petitionNumbers", fdCase.getPetitionNumbers());
			map.put("associatedNumbers", fdCase.getAssociatedNumbers());
			map.put("guetFollows", guestList);
			map.put("casePetitionTime", fdCase.getPetitionTime());

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return map;
		}
		return map;
	}

	@Override
	public FdGuest getGuestByIdCard(String idcard) {
		FdGuest fdGuest = null;
		try {
			fdGuest = fdGuestRepository.getGuestByIdcard(idcard);
			fdGuest.setStrPetitionWayParent(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayParent()));
			fdGuest.setStrPetitionWayChild(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayChild()));
			fdGuest.setStrCardType(fdCodeRepository.getNameByCode(fdGuest.getCardType()));
			fdGuest.setStrType(fdGuest.getType() == PetitionType.ALONE.getValue() ? PetitionType.ALONE.getName()
					: PetitionType.GROUP.getName());
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdGuest;
		}
		return fdGuest;
	}

	/**
	 * 头像设置
	 *
	 * @param fdGuest
	 * @return
	 */
	private FdGuest isSaveGuest(FdGuest fdGuest) {

		// 设置头像地址
		String strRemovePrefix = "";
		if (!StringUtils.isEmpty(fdGuest.getHeadPic())) {
			String strHeadPic = fdGuest.getHeadPic();
			if (strHeadPic.contains("data:image/jpg;base64,")) {
				strRemovePrefix = strHeadPic.substring(22, strHeadPic.length());
				// 将base64编码字符串转换为图片并返回图片路径-具体到文件
				String filePath = Base64Utils.GenerateImage(strRemovePrefix);

				File file = new File(filePath);
				String fileId = null;
				try {
					// 分布式上传到图片服务器并返回ID
					fileId = FastDFSClient.uploadFile(file, filePath);
					// 数据库字段存返回的ID
					fdGuest.setHeadPic(fileId);
				} catch (Exception e) {
					ApiLog.chargeLog1(e.getMessage());
				} finally {
					// 保存后删除本地上传的图片
					File headfile = new File(filePath);
					// 路径为文件且不为空则进行删除
					if (headfile.isFile() && file.exists()) {
						headfile.delete();
					}
				}
			}
		}
		return fdGuestRepository.save(fdGuest);
	}

	/**
	 * 上访人类型 和 随访人ID
	 *
	 * @param fdCase
	 * @param strFollowIds
	 * @return
	 */
	private FdCase updateTypeAndFollowIds(FdCase fdCase, String strFollowIds) {
		// 设置访问类型 个访 OR 群访
		if (StringUtils.isEmpty(strFollowIds)) {
			fdCase.setType(PetitionType.ALONE.getValue());
		} else {
			String[] followIds = strFollowIds.split(",");
			if (followIds.length > 4) {
				fdCase.setType(PetitionType.GROUP.getValue());
			} else {
				fdCase.setType(PetitionType.ALONE.getValue());
			}
		}
		fdCase.setFollowGuestIds(strFollowIds);
		return fdCaseRepository.save(fdCase);
	}

	/**
	 * 上访人类型 和 案件编码
	 *
	 * @param fdCase
	 * @param strFollowIds
	 * @param strPetitionTime
	 * @return
	 */
	private FdCase updateTypeAndPetitionCode(FdCase fdCase, String strFollowIds, String strPetitionTime) {
		// 设置访问类型 个访 OR 群访
		if (StringUtils.isEmpty(strFollowIds)) {
			fdCase.setType(PetitionType.ALONE.getValue());
		} else {
			String[] followIds = strFollowIds.split(",");
			if (followIds.length > 4) {
				fdCase.setType(PetitionType.GROUP.getValue());
			} else {
				fdCase.setType(PetitionType.ALONE.getValue());
			}
		}

		// 生成案件编码
		String prefix = "520100";
		// String time = strPetitionTime.substring(0, 4) +
		// strPetitionTime.substring(5, 7) + strPetitionTime.substring(8, 10);
		/******************
		 * update by zhanghr 2017-4-14 18:00
		 ********************************************/
		String strPetitionTimenew = DateUtils.formatDateTime(new Date());
		String time = strPetitionTimenew.substring(0, 4) + strPetitionTimenew.substring(5, 7)
				+ strPetitionTimenew.substring(8, 10);

		String suffix = "";
		long count = 0;
		try {
			count = fdCaseRepository.countCurrentRecord(prefix + time + '%');
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
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

		fdCase.setPetitionCode(prefix + time + suffix);

		return fdCaseRepository.save(fdCase);
	}

	/**
	 * @param userid
	 * @param caseid
	 * @param type
	 * @param note
	 * @param files
	 * @return
	 */
	private LogInInfo addFeedBack(long userid, int caseid, int type, String note, String files) {
		Date now = new Date();
		LogInInfo info = null;
		Object obj = redisTemplate.opsForValue().get("user:" + userid);
		if (obj != null) {
			info = (LogInInfo) obj;
		} else {
			info = tzmPetitionService.selectLogInInfoByRyId((int) userid);
		}
		FdCaseFeedbackAll fdCaseFeedback = new FdCaseFeedbackAll();
		fdCaseFeedback.setType(type);
		fdCaseFeedback.setUserurl(info.getUserImg());
		fdCaseFeedback.setCaseId(caseid);
		fdCaseFeedback.setCreaterId((int) userid);
		fdCaseFeedback.setCreateTime(now);
		fdCaseFeedback.setUpdateTime(now);
		fdCaseFeedback.setNote(note);
		fdCaseFeedback.setEnclosure3(files);
		fdCaseFeedback.setCreaterCompany(info.getKsMc());
		fdCaseFeedback.setCreaterRole(info.getZwMc());
		fdCaseFeedback.setCreaterDep(info.getDwMc());
		fdCaseFeedback.setDepId(info.getDwId());
		fdCaseFeedback.setCreater(info.getUserName());

		fdCaseFeedbackAllRepository.saveAndFlush(fdCaseFeedback);
		return info;
	}
}