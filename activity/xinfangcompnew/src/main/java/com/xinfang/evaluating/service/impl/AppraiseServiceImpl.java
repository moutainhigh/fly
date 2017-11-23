package com.xinfang.evaluating.service.impl;

import com.xinfang.context.PageFinder;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.evaluating.dao.FpAppraiseDao;
import com.xinfang.evaluating.model.FpAppraiseEntity;
import com.xinfang.evaluating.service.AppraiseService;
import com.xinfang.evaluating.vo.AppraiseDetailVO;
import com.xinfang.evaluating.vo.AppraiseVO;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdCase;
import com.xinfang.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 投诉办理-评价业务层逻辑实现类
 * Created by sunbjx on 2017/5/8.
 */
@Service("appraiseService")
public class AppraiseServiceImpl implements AppraiseService {


    @Autowired
    private FpAppraiseDao fpAppraiseDao;
    @Autowired
    private FdCaseRepository fdCaseRepository;

    @Override
    public AppraiseDetailVO getByCaseId(Integer caseId) {

        FpAppraiseEntity appraise = null;
        try {
            List<FpAppraiseEntity> appraiseList = fpAppraiseDao.getByCaseId(caseId);
            if (appraiseList != null && appraiseList.size() > 0) {
                appraise = appraiseList.get(0);
            }
            if (null == appraise) return null;
        } catch (Exception e) {
            ApiLog.chargeLog1(e.getMessage());
            return null;
        }
        FdCase fdCase = fdCaseRepository.getOne(caseId);
        AppraiseDetailVO detailVO = new AppraiseDetailVO();
        detailVO.setUsername(appraise.getAppraiseName());
        detailVO.setUserHeadSrc(appraise.getAppraisePicture());
        detailVO.setPetitionTime(appraise.getCaseXfDate());
        detailVO.setPetitionWay(appraise.getCaseWay());
        detailVO.setPetitionCode(appraise.getCaseBh());
        detailVO.setGuestId(fdCase.getGuestId());
        detailVO.setPetitionNumbers(fdCase.getPetitionNumbers());
        detailVO.setCaseId(caseId);
        detailVO.setPetitionAppraiseUnit(appraise.getCaseDepartment());
        detailVO.setPetitionAppraiseSatisfied(appraise.getDepartment());
        detailVO.setPetitionAppraiseTime(appraise.getDepartmentDate());
        detailVO.setPetitionAppraiseContent(appraise.getDepartmentContent());
        detailVO.setDutyAppraiseUnit(appraise.getCaseUnit());
        detailVO.setDutyAppraiseSatisfied(appraise.getUnit());
        detailVO.setDutyAppraiseTime(appraise.getUnitDate());
        detailVO.setDutyAppraiseContent(appraise.getUnitContent());
        detailVO.setCaseAppraiseSatisfied(appraise.getCaseSatisfied());
        detailVO.setCaseAppraiseTime(appraise.getCaseDate());
        detailVO.setCaseAppraiseContent(appraise.getAppraiseContent());

        return detailVO;
    }

    @Override
    public PageFinder<AppraiseVO> listBySignInUser(Integer signInUserUnitId,
                                                   int flag,
                                                   Integer petitionPurpose,
                                                   Integer caseType,
                                                   Integer caseBelongTo,
                                                   Integer unitId,
                                                   Byte petitionAppraiseOk,
                                                   Byte dutyAppraiseOk,
                                                   Byte caseAppraiseOk,
                                                   String startTime,
                                                   String endTime,
                                                   int startPage,
                                                   int pageSize) {


        Page<FpAppraiseEntity> list = null;
        try {
            list = listBySignInUserCurl(signInUserUnitId, flag, petitionPurpose,
                    caseType,
                    caseBelongTo,
                    unitId,
                    petitionAppraiseOk,
                    dutyAppraiseOk,
                    caseAppraiseOk,
                    startTime,
                    endTime,
                    startPage,
                    pageSize);
        } catch (Exception e) {
            ApiLog.chargeLog1(e.getMessage());
            return null;
        }
        List<AppraiseVO> appraiseVOList = new ArrayList<AppraiseVO>();
        if (list != null && list.getSize() > 0) {
            for (FpAppraiseEntity appraise : list) {
                AppraiseVO appraiseVO = new AppraiseVO();
                appraiseVO.setUsername(appraise.getAppraiseName());
                appraiseVO.setUserHeadSrc(appraise.getAppraisePicture());
                appraiseVO.setPetitionAppraiseOk(appraise.getDepartment());
                appraiseVO.setPetitionAppraiseTime(appraise.getDepartmentDate());
                appraiseVO.setDutyAppraiseOk(appraise.getUnit());
                appraiseVO.setDutyAppraiseTime(appraise.getUnitDate());
                appraiseVO.setCaseAppraiseOk(appraise.getCaseSatisfied());
                appraiseVO.setCaseAppraiseTime(appraise.getCaseDate());
                appraiseVO.setAppraiseWay(appraise.getAppraiseWay());
                appraiseVO.setCaseBelongTo(appraise.getAppraiseArea());
                appraiseVO.setPetitionPurpose(appraise.getAppraisePurpose());
                appraiseVO.setCaseType(appraise.getAppraiseGenre());
                appraiseVO.setCaseAppraiseContent(appraise.getAppraiseContent());
                appraiseVO.setPetitionUnit(appraise.getCaseDepartment());
                appraiseVO.setDutyUnit(appraise.getCaseUnit());
                appraiseVO.setCaseId(appraise.getCaseId());
                appraiseVOList.add(appraiseVO);
            }
        }

        int recordCount = (int)countRecord(signInUserUnitId, flag, petitionPurpose,
                caseType,
                caseBelongTo,
                unitId,
                petitionAppraiseOk,
                dutyAppraiseOk,
                caseAppraiseOk,
                startTime,
                endTime);
        return new PageFinder<AppraiseVO>(startPage, pageSize, recordCount, appraiseVOList);

    }

    @Override
    public Map<String, Object> okRateStatistical(Integer petitionPurpose, Integer caseType, Integer caseBelongTo, Integer unitId, String startTime, String endTime) {
        return okRateStatisticalCurl(petitionPurpose, caseType, caseBelongTo, unitId, startTime, endTime);
    }

    /**
     * 评价列表分页请求数据
      */
    private Page<FpAppraiseEntity> listBySignInUserCurl(final Integer signInUserUnitId,
                                                        final int flag,
                                                        final Integer petitionPurpose,
                                                        final Integer caseType,
                                                        final Integer caseBelongTo,
                                                        final Integer unitId,
                                                        final Byte petitionAppraiseOk,
                                                        final Byte dutyAppraiseOk,
                                                        final Byte caseAppraiseOk,
                                                        final String startTime,
                                                        final String endTime,
                                                        int startPage,
                                                        int pageSize) {

        return fpAppraiseDao.findAll(new Specification<FpAppraiseEntity>() {
            @Override
            public Predicate toPredicate(Root<FpAppraiseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                Date st = null;
                Date et = null;
                if (!StringUtils.isEmpty(startTime) || !StringUtils.isEmpty(endTime)) {
                    try {
                        st = DateUtils.parseDate(startTime);
                        et = DateUtils.parseDate(endTime);
                    } catch (Exception e) {
                        ApiLog.chargeLog1(e.getMessage());
                    }
                }
                // 信访目的
                if (0 != petitionPurpose) {
                    predicate.add(criteriaBuilder.equal(root.get("appraisePurposeId"), petitionPurpose));
                }
                // 案件类型
                if (0 != caseType) {
                    predicate.add(criteriaBuilder.equal(root.get("appraiseGenreId"), caseType));
                }
                // 问题属地
                if (0 != caseBelongTo) {
                    predicate.add(criteriaBuilder.equal(root.get("appraiseAreaId"), caseBelongTo));
                }
                // 单位ID
                if (0 != unitId) {
                    predicate.add(criteriaBuilder.equal(root.get("caseUnitId"), unitId));
                }
                // 是否满意信访单位
                if (-1 != petitionAppraiseOk) {
                    predicate.add(criteriaBuilder.equal(root.get("isDepartment"), petitionAppraiseOk));
                }
                // 是否满意责任单位
                if (-1 != dutyAppraiseOk) {
                    predicate.add(criteriaBuilder.equal(root.get("isUnit"), dutyAppraiseOk));
                }
                // 是否满意案件处理
                if (-1 != caseAppraiseOk) {
                    predicate.add(criteriaBuilder.equal(root.get("isCase"), caseAppraiseOk));
                }

                // 开始时间
                if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
                    predicate.add(criteriaBuilder.greaterThan(root.get("gmtModified").as(Date.class), st));
                }
                // 结束时间
                if (!StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
                    predicate.add(criteriaBuilder.lessThan(root.get("gmtModified").as(Date.class), et));
                }
                if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
                    predicate.add(criteriaBuilder.between(root.get("gmtModified").as(Date.class), st, et));
                }
                // 单位过滤
                if (flag == 1) {
                    predicate.add(criteriaBuilder.equal(root.get("caseDepartmentId"), signInUserUnitId));
                }

                if (flag == 2) {
                    predicate.add(criteriaBuilder.equal(root.get("caseUnitId"), signInUserUnitId));
                }
                if (flag == 0) {
                    Predicate currentDepartmentIdPre = criteriaBuilder.or(criteriaBuilder.equal(root.get("caseDepartmentId"), signInUserUnitId), criteriaBuilder.equal(root.get("caseUnitId"), signInUserUnitId));
                    predicate.add(currentDepartmentIdPre);
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        }, new PageRequest(startPage - 1, pageSize));
    }

    /**
     * 返回列表查询综述
     * @param signInUserUnitId
     * @param flag
     * @param petitionPurpose
     * @param caseType
     * @param caseBelongTo
     * @param unitId
     * @param petitionAppraiseOk
     * @param dutyAppraiseOk
     * @param caseAppraiseOk
     * @param startTime
     * @param endTime
     * @return
     */
    private long countRecord(final Integer signInUserUnitId,
                             final int flag,
                             final Integer petitionPurpose,
                             final Integer caseType,
                             final Integer caseBelongTo,
                             final Integer unitId,
                             final Byte petitionAppraiseOk,
                             final Byte dutyAppraiseOk,
                             final Byte caseAppraiseOk,
                             final String startTime,
                             final String endTime) {
        return fpAppraiseDao.count(new Specification<FpAppraiseEntity>() {
            @Override
            public Predicate toPredicate(Root<FpAppraiseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                Date st = null;
                Date et = null;
                if (!StringUtils.isEmpty(startTime) || !StringUtils.isEmpty(endTime)) {
                    try {
                        st = DateUtils.parseDate(startTime);
                        et = DateUtils.parseDate(endTime);
                    } catch (Exception e) {
                        ApiLog.chargeLog1(e.getMessage());
                    }
                }
                // 信访目的
                if (0 != petitionPurpose) {
                    predicate.add(criteriaBuilder.equal(root.get("appraisePurposeId"), petitionPurpose));
                }
                // 案件类型
                if (0 != caseType) {
                    predicate.add(criteriaBuilder.equal(root.get("appraiseGenreId"), caseType));
                }
                // 问题属地
                if (0 != caseBelongTo) {
                    predicate.add(criteriaBuilder.equal(root.get("appraiseAreaId"), caseBelongTo));
                }
                // 单位ID
                if (0 != unitId) {
                    predicate.add(criteriaBuilder.equal(root.get("caseUnitId"), unitId));
                }
                // 是否满意信访单位
                if (-1 != petitionAppraiseOk) {
                    predicate.add(criteriaBuilder.equal(root.get("isDepartment"), petitionAppraiseOk));
                }
                // 是否满意责任单位
                if (-1 != dutyAppraiseOk) {
                    predicate.add(criteriaBuilder.equal(root.get("isUnit"), dutyAppraiseOk));
                }
                // 是否满意案件处理
                if (-1 != caseAppraiseOk) {
                    predicate.add(criteriaBuilder.equal(root.get("isCase"), caseAppraiseOk));
                }

                // 开始时间
                if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
                    predicate.add(criteriaBuilder.greaterThan(root.get("gmtModified").as(Date.class), st));
                }
                // 结束时间
                if (!StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
                    predicate.add(criteriaBuilder.lessThan(root.get("gmtModified").as(Date.class), et));
                }
                if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
                    predicate.add(criteriaBuilder.between(root.get("gmtModified").as(Date.class), st, et));
                }
                // 单位过滤
                if (flag == 1) {
                    predicate.add(criteriaBuilder.equal(root.get("caseDepartmentId"), signInUserUnitId));
                }

                if (flag == 2) {
                    predicate.add(criteriaBuilder.equal(root.get("caseUnitId"), signInUserUnitId));
                }
                if (flag == 0) {
                    Predicate currentDepartmentIdPre = criteriaBuilder.or(criteriaBuilder.equal(root.get("caseDepartmentId"), signInUserUnitId), criteriaBuilder.equal(root.get("caseUnitId"), signInUserUnitId));
                    predicate.add(currentDepartmentIdPre);
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        });
    }


    private Map<String, Object> okRateStatisticalCurl(final Integer petitionPurpose,
                                                      final Integer caseType,
                                                      final Integer caseBelongTo,
                                                      final Integer unitId,
                                                      final String startTime,
                                                      final String endTime) {

        Map<String, Object> result = new LinkedHashMap<String, Object>();

        try {
            long countApprais = fpAppraiseDao.count();

            long petitionOk = fpAppraiseDao.count(new Specification<FpAppraiseEntity>() {
                @Override
                public Predicate toPredicate(Root<FpAppraiseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicate = new ArrayList<>();
                    Date st = null;
                    Date et = null;
                    if (!StringUtils.isEmpty(startTime) || !StringUtils.isEmpty(endTime)) {
                        try {
                            st = DateUtils.parseDate(startTime);
                            et = DateUtils.parseDate(endTime);
                        } catch (Exception e) {
                            ApiLog.chargeLog1(e.getMessage());
                        }
                    }

                    // 信访目的
                    if (0 != petitionPurpose) {
                        predicate.add(criteriaBuilder.equal(root.get("appraisePurposeId").as(Integer.class), petitionPurpose));
                    }
                    // 案件类型
                    if (0 != caseType) {
                        predicate.add(criteriaBuilder.equal(root.get("appraiseGenreId").as(Integer.class), caseType));
                    }
                    // 问题属地
                    if (0 != caseBelongTo) {
                        predicate.add(criteriaBuilder.equal(root.get("appraiseAreaId").as(Integer.class), caseBelongTo));
                    }
                    // 单位ID
                    if (0 != unitId) {
                        Predicate currentDepartmentIdPre = criteriaBuilder.or(criteriaBuilder.equal(root.get("caseDepartmentId").as(Integer.class), unitId), criteriaBuilder.equal(root.get("caseUnitId").as(Integer.class), unitId));
                        predicate.add(currentDepartmentIdPre);
                    }
                    // 开始时间
                    if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
                        predicate.add(criteriaBuilder.greaterThan(root.get("gmtModified").as(Date.class), st));
                    }
                    // 结束时间
                    if (!StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
                        predicate.add(criteriaBuilder.lessThan(root.get("gmtModified").as(Date.class), et));
                    }
                    if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
                        predicate.add(criteriaBuilder.between(root.get("gmtModified").as(Date.class), st, et));
                    }
                    // 信访单位满意数量
                    predicate.add(criteriaBuilder.notEqual(root.get("departmentId"), 0));

                    Predicate[] pre = new Predicate[predicate.size()];
                    return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
                }
            });

            long dutyOk = fpAppraiseDao.count(new Specification<FpAppraiseEntity>() {
                @Override
                public Predicate toPredicate(Root<FpAppraiseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicate = new ArrayList<>();
                    Date st = null;
                    Date et = null;
                    if (!StringUtils.isEmpty(startTime) || !StringUtils.isEmpty(endTime)) {
                        try {
                            st = DateUtils.parseDate(startTime);
                            et = DateUtils.parseDate(endTime);
                        } catch (Exception e) {
                            ApiLog.chargeLog1(e.getMessage());
                        }
                    }
                    // 信访目的
                    if (0 != petitionPurpose) {
                        predicate.add(criteriaBuilder.equal(root.get("appraisePurposeId").as(Integer.class), petitionPurpose));
                    }
                    // 案件类型
                    if (0 != caseType) {
                        predicate.add(criteriaBuilder.equal(root.get("appraiseGenreId").as(Integer.class), caseType));
                    }
                    // 问题属地
                    if (0 != caseBelongTo) {
                        predicate.add(criteriaBuilder.equal(root.get("appraiseAreaId").as(Integer.class), caseBelongTo));
                    }
                    // 单位ID
                    if (0 != unitId) {
                        Predicate currentDepartmentIdPre = criteriaBuilder.or(criteriaBuilder.equal(root.get("caseDepartmentId").as(Integer.class), unitId), criteriaBuilder.equal(root.get("caseUnitId").as(Integer.class), unitId));
                        predicate.add(currentDepartmentIdPre);
                    }
                    // 开始时间
                    if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
                        predicate.add(criteriaBuilder.greaterThan(root.get("gmtModified").as(Date.class), st));
                    }
                    // 结束时间
                    if (!StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
                        predicate.add(criteriaBuilder.lessThan(root.get("gmtModified").as(Date.class), et));
                    }
                    if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
                        predicate.add(criteriaBuilder.between(root.get("gmtModified").as(Date.class), st, et));
                    }
                    // 责任单位满意数量
                    predicate.add(criteriaBuilder.notEqual(root.get("unitId"), 0));

                    Predicate[] pre = new Predicate[predicate.size()];
                    return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
                }
            });

            long caseOk = fpAppraiseDao.count(new Specification<FpAppraiseEntity>() {
                @Override
                public Predicate toPredicate(Root<FpAppraiseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicate = new ArrayList<>();
                    Date st = null;
                    Date et = null;
                    if (!StringUtils.isEmpty(startTime) || !StringUtils.isEmpty(endTime)) {
                        try {
                            st = DateUtils.parseDate(startTime);
                            et = DateUtils.parseDate(endTime);
                        } catch (Exception e) {
                            ApiLog.chargeLog1(e.getMessage());
                        }
                    }
                    // 信访目的
                    if (0 != petitionPurpose) {
                        predicate.add(criteriaBuilder.equal(root.get("appraisePurposeId").as(Integer.class), petitionPurpose));
                    }
                    // 案件类型
                    if (0 != caseType) {
                        predicate.add(criteriaBuilder.equal(root.get("appraiseGenreId").as(Integer.class), caseType));
                    }
                    // 问题属地
                    if (0 != caseBelongTo) {
                        predicate.add(criteriaBuilder.equal(root.get("appraiseAreaId").as(Integer.class), caseBelongTo));
                    }
                    // 单位ID
                    if (0 != unitId) {
                        Predicate currentDepartmentIdPre = criteriaBuilder.or(criteriaBuilder.equal(root.get("caseDepartmentId").as(Integer.class), unitId), criteriaBuilder.equal(root.get("caseUnitId").as(Integer.class), unitId));
                        predicate.add(currentDepartmentIdPre);
                    }
                    // 开始时间
                    if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
                        predicate.add(criteriaBuilder.greaterThan(root.get("gmtModified").as(Date.class), st));
                    }
                    // 结束时间
                    if (!StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
                        predicate.add(criteriaBuilder.lessThan(root.get("gmtModified").as(Date.class), et));
                    }
                    if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
                        predicate.add(criteriaBuilder.between(root.get("gmtModified").as(Date.class), st, et));
                    }

                    // 案件满意数量
                    predicate.add(criteriaBuilder.notEqual(root.get("caseSatisfiedId"), 0));

                    Predicate[] pre = new Predicate[predicate.size()];
                    return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
                }
            });

            result.put("petitionOk", petitionOk);
            result.put("petitionNo", countApprais - petitionOk);

            result.put("dutyOk", dutyOk);
            result.put("dutyNo", countApprais - dutyOk);

            result.put("caseOk", caseOk);
            result.put("caseNo", countApprais - caseOk);
        } catch (Exception e) {
            ApiLog.chargeLog1(e.getMessage());
            return null;
        }
        return result;
    }
}
