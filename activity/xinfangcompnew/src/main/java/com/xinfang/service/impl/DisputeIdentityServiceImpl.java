package com.xinfang.service.impl;

import com.ly.file.FastDFSClient;
import com.xinfang.VO.LogInInfo;
import com.xinfang.context.StateConstants;
import com.xinfang.dao.*;
import com.xinfang.enums.HandleState;
import com.xinfang.enums.PetitionType;
import com.xinfang.log.ApiLog;
import com.xinfang.model.DisputeCaseEntity;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.model.FdGuest;
import com.xinfang.service.IdentityService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.utils.Base64Utils;
import com.xinfang.utils.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

/**
 * 矛盾纠纷案件 Created by sunbjx on 2017/5/17.
 */
@Service
@Transactional
public class DisputeIdentityServiceImpl implements IdentityService {

	@Autowired
	private FdGuestRepository fdGuestRepository;
	@Autowired
	private DisputeCaseDao disputeCaseDao;
	@Autowired
	private FdCodeRepository fdCodeRepository;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	TzmPetitionService tzmPetitionService;
	@Autowired
	FdCaseFeedbackAllRepository feedbackAllRepository;// 案件反馈表（日志表）

	@Autowired
	FdDepCaseRepository fdDepCaseRepository;

	@Override
	public Map<String, Integer> save(FdGuest fdGuest, String strPetitionTime, Integer petitionNumbers,
			Integer associatedNumbers, String strFollowIds, Integer form) {
		Map<String, Integer> map = new HashMap<>();
		FdGuest result = null;
		List<Integer> isRepeat = new ArrayList<Integer>();

		try {
			// Guest 去重判断
			if (StringUtils.isEmpty(fdGuest.getIdcard())) {
				// 如果没有或者两条以上 执行新增，有一条则执行更新操作
				isRepeat = fdGuestRepository.getRepeat(fdGuest.getUsername(), fdGuest.getPhone());
				if (isRepeat.size() != 1) {
//					fdGuest.setIsDispute((byte) 1);
					fdGuest.setGmtCreate(new Date());
					fdGuest.setPetitionTime(DateUtils.parseDateTime(strPetitionTime));
					result = isSaveGuest(fdGuest);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					result.setIsDispute((byte) 1);
					fdGuestRepository.saveAndFlush(result);
				}
			} else {
				isRepeat = fdGuestRepository.getRepeatByIdcard(fdGuest.getIdcard());
				if (isRepeat.size() != 1) {
//					fdGuest.setIsDispute((byte) 1);
					fdGuest.setGmtCreate(new Date());
					fdGuest.setPetitionTime(DateUtils.parseDateTime(strPetitionTime));
					result = isSaveGuest(fdGuest);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					result.setIsDispute((byte) 1);
					fdGuestRepository.saveAndFlush(result);
				}
			}

			// 生成案件带入基础关联信息
			DisputeCaseEntity disputeCase = new DisputeCaseEntity();
			disputeCase.setGuestId(result.getId());
			disputeCase.setPetitionTime(result.getPetitionTime());
			disputeCase.setFollowGuestIds(strFollowIds);
			disputeCase.setPetitionNumbers(petitionNumbers);
			disputeCase.setAssociatedNumbers(associatedNumbers);
			disputeCase.setPetitionWay(result.getPetitionWayChild());
			disputeCase.setPetitionNames(result.getUsername());
			disputeCase.setState(StateConstants.STATE_DEFAULT);
			disputeCase.setCreatorId(result.getCreatorId());
			Map dw = tzmPetitionService.getDwInfoRyId(result.getCreatorId());
			disputeCase.setCreateUnitid((dw == null) ? 0 : Integer.valueOf(dw.get("dwId").toString()));
			disputeCase.setHandleDuration(0);
			disputeCase.setIsHandle((byte) 0);

			disputeCase.setGmtCreate(new Date());
			// 窗口编号 , form 窗口还是分流室
			disputeCase.setWindowNumber(form);
			if (form == 0) {
				disputeCase.setForm(StateConstants.FORM_SHUNT);
			}
			if (form == 1) {
				disputeCase.setForm(StateConstants.FORM_WINDOW);
				disputeCase.setHandleUserid(result.getCreatorId());
			}
			if (form == -1) {
				disputeCase.setForm(StateConstants.FORM_NOT);
				disputeCase.setHandleUserid(result.getCreatorId());
			}
			disputeCase.setCurrentHandleState("待录入");

			DisputeCaseEntity caseResult = updateTypeAndPetitionCode(disputeCase, strFollowIds, strPetitionTime);

			addFeedBack(result.getCreatorId().longValue(), caseResult.getDisputeCaseId().intValue(),
					HandleState.SHUNT.getValue(), "添加", fdGuest.getFileSrc());
			// 返回信访ID 和 案件ID
			map.put("guestId", result.getId());
			map.put("caseId", caseResult.getDisputeCaseId());
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
			DisputeCaseEntity DisputeCaseEntity = disputeCaseDao.findOne(caseId);
			DisputeCaseEntity.setPetitionNumbers(petitionNumbers);
			DisputeCaseEntity.setAssociatedNumbers(associatedNumbers);

			updateTypeAndFollowIds(DisputeCaseEntity, strFollowIds);

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
				if (isRepeat.size() != 1) {
					result = fdGuestRepository.save(fdGuest);
					map.put("isRepeat", 0);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					map.put("isRepeat", 1);
				}
			} else {
				isRepeat = fdGuestRepository.getRepeatByIdcard(idcard);
				if (isRepeat.size() != 1) {
					result = fdGuestRepository.save(fdGuest);
					map.put("isRepeat", 0);
				} else {
					result = fdGuestRepository.findOne(isRepeat.get(0));
					map.put("isRepeat", 1);
				}
			}

			if (!StringUtils.isEmpty(result))
				;
			map.put("followId", result.getId());
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return map;
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
		DisputeCaseEntity DisputeCaseEntity = null;
		try {
			DisputeCaseEntity = disputeCaseDao.findOne(caseId);
			if (StringUtils.isEmpty(DisputeCaseEntity))
				return null;
			if (StringUtils.isEmpty(DisputeCaseEntity.getFollowGuestIds()))
				return null;
			String[] strFollowIds = DisputeCaseEntity.getFollowGuestIds().split(",");
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
	public FdGuest getGuestById(Integer guestId) {
		FdGuest fdGuest = null;
		try {
			fdGuest = fdGuestRepository.findOne(guestId);
			fdGuest.setStrPetitionWayParent(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayParent()));
			fdGuest.setStrPetitionWayChild(fdCodeRepository.getNameByCode(fdGuest.getPetitionWayChild()));
			fdGuest.setStrCardType(fdCodeRepository.getNameByCode(fdGuest.getCardType()));
			fdGuest.setStrType(fdGuest.getType() == PetitionType.ALONE.getValue() ? PetitionType.ALONE.getName()
					: PetitionType.GROUP.getName());
			fdGuest.setStrEmployedInfo(fdCodeRepository.getNameByCode(Integer.valueOf(fdGuest.getEmployedInfo())));
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

			DisputeCaseEntity DisputeCaseEntity = disputeCaseDao.findOne(caseId);
			if (StringUtils.isEmpty(DisputeCaseEntity))
				return null;
			if (StringUtils.isEmpty(DisputeCaseEntity.getFollowGuestIds()))
				return null;
			String[] strFollowIds = DisputeCaseEntity.getFollowGuestIds().split(",");
			Integer[] followIds = new Integer[strFollowIds.length];
			for (int i = 0; i < strFollowIds.length; i++) {
				followIds[i] = Integer.valueOf(strFollowIds[i]);
			}
			List<FdGuest> guestList = new ArrayList<FdGuest>();

			guestList = fdGuestRepository.listFollowById(followIds);
			map.put("guest", fdGuest);
			map.put("petitionNumbers", DisputeCaseEntity.getPetitionNumbers());
			map.put("associatedNumbers", DisputeCaseEntity.getAssociatedNumbers());
			map.put("guetFollows", guestList);
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
	 * @param DisputeCaseEntity
	 * @param strFollowIds
	 * @return
	 */
	private DisputeCaseEntity updateTypeAndFollowIds(DisputeCaseEntity DisputeCaseEntity, String strFollowIds) {
		// 设置访问类型 个访 OR 群访
		if (StringUtils.isEmpty(strFollowIds)) {
			DisputeCaseEntity.setType(PetitionType.ALONE.getValue());
		} else {
			String[] followIds = strFollowIds.split(",");
			if (followIds.length > 4) {
				DisputeCaseEntity.setType(PetitionType.GROUP.getValue());
			} else {
				DisputeCaseEntity.setType(PetitionType.ALONE.getValue());
			}
		}
		DisputeCaseEntity.setFollowGuestIds(strFollowIds);
		return disputeCaseDao.save(DisputeCaseEntity);
	}

	/**
	 * 上访人类型 和 案件编码
	 *
	 * @param DisputeCaseEntity
	 * @param strFollowIds
	 * @param strPetitionTime
	 * @return
	 */
	private DisputeCaseEntity updateTypeAndPetitionCode(DisputeCaseEntity DisputeCaseEntity, String strFollowIds,
			String strPetitionTime) {
		// 设置访问类型 个访 OR 群访
		if (StringUtils.isEmpty(strFollowIds)) {
			DisputeCaseEntity.setType(PetitionType.ALONE.getValue());
		} else {
			String[] followIds = strFollowIds.split(",");
			if (followIds.length > 4) {
				DisputeCaseEntity.setType(PetitionType.GROUP.getValue());
			} else {
				DisputeCaseEntity.setType(PetitionType.ALONE.getValue());
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
		long count = disputeCaseDao.countCurrentRecord(prefix + time + '%');
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

		DisputeCaseEntity.setPetitionCode(prefix + time + suffix);

		return disputeCaseDao.save(DisputeCaseEntity);
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
		FdCaseFeedbackAll feedback = new FdCaseFeedbackAll();
		feedback.setType(type);
		feedback.setUserurl(info.getUserImg());
		feedback.setCaseId(caseid);
		feedback.setCreaterId((int) userid);
		feedback.setCreateTime(now);
		feedback.setUpdateTime(now);
		feedback.setNote(note);
		feedback.setEnclosure3(files);
		feedback.setCreaterCompany(info.getKsMc());
		feedback.setCreaterRole(info.getZwMc());
		feedback.setCreaterDep(info.getDwMc());
		feedback.setDepId(info.getDwId());
		feedback.setCreater(info.getUserName());

		feedbackAllRepository.saveAndFlush(feedback);
		return info;
	}

	@Override
	public FdGuest getGuestByIdForUrl(Integer guestId) {
		// TODO Auto-generated method stub
		return null;
	}

}
