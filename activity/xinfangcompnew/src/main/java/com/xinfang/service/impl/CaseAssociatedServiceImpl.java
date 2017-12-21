package com.xinfang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.VO.HistoryCaseVO;
import com.xinfang.VO.LawRulesVO;
import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FcGldybDao;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.dao.FdGuestRepository;
import com.xinfang.dao.LawRulesDao;
import com.xinfang.enums.CaseHandleState;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FcGldybEntity;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdGuest;
import com.xinfang.model.LawRulesEntity;
import com.xinfang.service.CaseAssociatedService;
import com.xinfang.utils.DateUtils;

/**
 * Created by sunbjx on 2017/3/27.
 */
@Service("caseAssociatedService")
@Transactional
public class CaseAssociatedServiceImpl implements CaseAssociatedService {

	@Autowired
	private FdGuestRepository fdGuestRepository;
	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Autowired
	private FdCodeRepository fdCodeRepository;
	@Autowired
	private LawRulesDao lawRulesDao;
	@Autowired
	private FcGldybDao fcGldybDao;
	@Autowired
	private FcDwbRepository dwbRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public List<HistoryCaseVO> listHistoryCaseByIdcard2(String idcard) {

		List<HistoryCaseVO> historyCaseVOList = new ArrayList<HistoryCaseVO>();
		try {
			List<FdGuest> result = fdGuestRepository.listHistoryCaseByIdcard(idcard);
			// List<FdGuest> result = fdGuestRepository.findAll(new
			// Specification<FdGuest>() {
			//
			// @Override
			// public Predicate toPredicate(Root<FdGuest> root, CriteriaQuery<?>
			// query, CriteriaBuilder cb) {
			// List<Predicate> predicates = new ArrayList<Predicate>();
			// predicates.add(cb.equal(root.get("idcard").as(String.class),
			// idcard));
			//
			// Predicate[] pre = new Predicate[predicates.size()];
			// return query.where(predicates.toArray(pre)).getRestriction();
			// }
			// });
			if (!result.isEmpty()) {
				String wp = fdCodeRepository.getNameByCode(result.get(0).getPetitionWayParent());
				String wc = fdCodeRepository.getNameByCode(result.get(0).getPetitionWayChild());
				List<FdCase> fdCaseList = result.get(0).getFdCaseList();
				if (!fdCaseList.isEmpty()) {
					for (FdCase fdCase : fdCaseList) {
						HistoryCaseVO historyCaseVO = new HistoryCaseVO();
						historyCaseVO.setCaseCode(fdCase.getPetitionCode());
						historyCaseVO.setPetitionName(result.get(0).getUsername());
						historyCaseVO.setPetitionType(wp == null || wc == null ? "" : wp + wc);
						historyCaseVO.setPetitionTime(
								fdCase.getPetitionTime() == null ? "" : DateUtils.formatDate(fdCase.getPetitionTime()));
						historyCaseVO.setQuestionBelongTo(fdCase.getQuestionBelongingTo() == null ? ""
								: fdCodeRepository.getNameByCode(fdCase.getQuestionBelongingTo()));
						historyCaseVO.setCaseType(fdCase.getCaseType() == null ? ""
								: fdCodeRepository.getNameByCode(fdCase.getCaseType()));

						// String pc =
						// fdCodeRepository.getNameByCode(fdCase.getPetitionCounty());
						// String pu = fdCase.getPetitionUnit();
						// historyCaseVO.setCurrentHandleUnit(pc == null || pu
						// == null ? "" : pc + pu);
						historyCaseVO.setCurrentHandleUnit(fdCase.getCurrentHandleUnitid() == null ? ""
								: dwbRepository.findDwMcAndDwIdByDwId(fdCase.getCurrentHandleUnitid()));
						historyCaseVO.setCaseState(fdCase.getCurrentHandleState());
						historyCaseVO.setHandlePeriodEnd(fdCase.getHandlePeriodEnd() == null ? ""
								: DateUtils.formatDate(fdCase.getHandlePeriodEnd()));
						historyCaseVO.setCaseDesc(fdCase.getCaseDesc());
						historyCaseVO.setGuestId(fdCase.getGuestId());
						historyCaseVO.setCaseId(fdCase.getId());

						historyCaseVOList.add(historyCaseVO);
					}
				}
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return historyCaseVOList;
	}

	@Override
	public List<HistoryCaseVO> listHistoryCaseByUsername2(String username) {
		List<HistoryCaseVO> historyCaseVOList = new ArrayList<HistoryCaseVO>();
		try {
			List<FdGuest> result = fdGuestRepository.listHistoryCaseByUsername(username);
			if (!result.isEmpty()) {
				String wp = fdCodeRepository.getNameByCode(result.get(0).getPetitionWayParent());
				String wc = fdCodeRepository.getNameByCode(result.get(0).getPetitionWayChild());
				List<FdCase> fdCaseList = result.get(0).getFdCaseList();
				if (!fdCaseList.isEmpty()) {
					for (FdCase fdCase : fdCaseList) {
						HistoryCaseVO historyCaseVO = new HistoryCaseVO();
						historyCaseVO.setCaseCode(fdCase.getPetitionCode());
						historyCaseVO.setPetitionName(result.get(0).getUsername());
						historyCaseVO.setPetitionType(wp == null || wc == null ? null : wp + wc);
						historyCaseVO.setPetitionTime(DateUtils.formatDate(fdCase.getPetitionTime()));
						historyCaseVO
								.setQuestionBelongTo(fdCodeRepository.getNameByCode(fdCase.getQuestionBelongingTo()));
						historyCaseVO.setCaseType(fdCodeRepository.getNameByCode(fdCase.getCaseType()));

						String pc = fdCodeRepository.getNameByCode(fdCase.getPetitionCounty());
						String pu = fdCase.getPetitionUnit();
						historyCaseVO.setCurrentHandleUnit(pc == null || pu == null ? null : pc + pu);
						historyCaseVO.setCaseState(CaseHandleState.getName(fdCase.getCaseHandleState()));
						historyCaseVO.setHandlePeriodEnd(DateUtils.formatDate(fdCase.getHandlePeriodEnd()));
						historyCaseVO.setCaseDesc(fdCase.getCaseDesc());
						historyCaseVO.setGuestId(fdCase.getGuestId());
						historyCaseVO.setCaseId(fdCase.getId());

						historyCaseVOList.add(historyCaseVO);
					}
				}
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return historyCaseVOList;
	}

	@Override
	public List<LawRulesVO> referenceLawRules(Integer[] categoryIds) {

		List<LawRulesVO> lawRulesVOs = new ArrayList<LawRulesVO>();
		List<Integer> lawIds = new ArrayList<Integer>();

		try {
			// 通过码表对应的code获取 法律法规码表关系表中的 法律法规ID
			List<FcGldybEntity> lawRulesRelation = fcGldybDao.findAll(new Specification<FcGldybEntity>() {

				@Override
				public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
					List<Predicate> predicates = new ArrayList<>();

					predicates.add(cb.equal(root.get("isFlag").as(Byte.class), 1));
					predicates.add(root.get("codeId").in(categoryIds));

					Predicate[] pre = new Predicate[predicates.size()];
					return query.where(predicates.toArray(pre)).getRestriction();
				}
			});

			if (lawRulesRelation != null && lawRulesRelation.size() > 0) {
				for (FcGldybEntity fcGldybEntity : lawRulesRelation) {
					lawIds.add(fcGldybEntity.getRuleId());
				}
			}

			// 关联法律法规信息
			List<LawRulesEntity> lawRules = lawRulesDao.findAll(new Specification<LawRulesEntity>() {

				@Override
				public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
					Predicate predicate = root.get("law_id").in(lawIds.toArray());
					return query.where(predicate).getRestriction();
				}
			});

			if (lawRules != null && lawRules.size() > 0) {

				for (LawRulesEntity lawRulesEntity : lawRules) {
					LawRulesVO referenceLawRule = new LawRulesVO();
					referenceLawRule.setLawId(lawRulesEntity.getId());
					referenceLawRule.setName(lawRulesEntity.getName());
					referenceLawRule.setType(lawRulesEntity.getCategory());
					referenceLawRule.setReferenceCase("");
					lawRulesVOs.add(referenceLawRule);
				}
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return lawRulesVOs;
		}

		return lawRulesVOs;
	}

	@Override
	public String getByLawId(Integer lawId) {
		String result = "";
		try {
			result = lawRulesDao.getByLawId(lawId);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> listHistoryCaseByIdcard(String idcard) {
		String sql = "select \n" + "	c.petition_code as caseCode,\n" + "    g.username as petitionName,\n"
				+ "    (select name from fd_code where code = g.petition_way_child) as petitionType,\n"
				+ "    date_format(g.petition_time, '%Y-%m-%d') as petitionTime,\n"
				+ "    (select name from fd_code where code = c.question_belonging_to) as questionBelongTo,\n"
				+ "    (select name from fd_code where code = c.question_type) as caseType,\n"
				+ "    (select dw_mc from fc_dwb where dw_id = c.current_handle_unitid) as currentHandleUnit,\n"
				+ "    c.current_handle_state as caseState,\n"
				+ "    date_format(c.handle_period_end, '%Y-%m-%d') as handlePeriodEnd,\n"
				+ "    c.case_desc as caseDesc,\n" + "    g.ID as guestId,\n"
				+ "    c.ID as caseId, c.case_handle_state as caseHandleState\n" + "from fd_guest g\n"
				+ "left join fd_case c on c.guest_id = g.ID\n" + "where ifnull(g.idcard, '') <> '' and g.idcard = '"
				+ idcard + "'";

		try {
			List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
			return results;
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}

	}

	@Override
	public List<Map<String, Object>> listHistoryCaseByUsername(String username) {
		String sql = "select \n" + "	c.petition_code as caseCode,\n" + "    g.username as petitionName,\n"
				+ "    (select name from fd_code where code = g.petition_way_child) as petitionType,\n"
				+ "    date_format(g.petition_time, '%Y-%m-%d') as petitionTime,\n"
				+ "    (select name from fd_code where code = c.question_belonging_to) as questionBelongTo,\n"
				+ "    (select name from fd_code where code = c.question_type) as caseType,\n"
				+ "    (select dw_mc from fc_dwb where dw_id = c.current_handle_unitid) as currentHandleUnit,\n"
				+ "    c.current_handle_state as caseState,\n"
				+ "    date_format(c.handle_period_end, '%Y-%m-%d') as handlePeriodEnd,\n"
				+ "    c.case_desc as caseDesc,\n" + "    g.ID as guestId,\n"
				+ "    c.ID as caseId,  c.case_handle_state as caseHandleState\n" + "from fd_guest g\n"
				+ "left join fd_case c on c.guest_id = g.ID\n" + "where ifnull(g.username, '') <> '' and g.username = '"
				+ username + "'";

		try {
			List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
			return results;
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
	}

	@Override
	public void relationCase(Integer caseId, String relationId) {
		try {
			FdCase fdCase = fdCaseRepository.findOne(caseId);
			fdCase.setRelationCaseid(relationId);
			fdCaseRepository.save(fdCase);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
	}

	@Override
	public boolean isRelation(Integer caseId) {
		FdCase fdCase;
		try {
			fdCase = fdCaseRepository.findOne(caseId);
			if (!StringUtils.isEmpty(fdCase.getRelationCaseid())) {
				return true;
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return false;
	}
}
