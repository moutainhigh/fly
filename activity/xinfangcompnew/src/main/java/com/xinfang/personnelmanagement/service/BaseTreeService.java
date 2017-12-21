package com.xinfang.personnelmanagement.service;

import com.xinfang.Exception.BizException;
import com.xinfang.VO.FcRybTreeVO;
import com.xinfang.VO.LogInInfo;
import com.xinfang.VO.PermissionPersonDetail;
import com.xinfang.dao.*;
import com.xinfang.log.ApiLog;
import com.xinfang.model.AuthUserGroupNew;
import com.xinfang.model.FdCode;
import com.xinfang.model.TsInputPersonCaseTypeNew;
import com.xinfang.model.TsTransferPerson;
import com.xinfang.service.TzmPetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-17 16:18
 **/
@Service
@Transactional
public class BaseTreeService {

    @Autowired
    TsInputPersonCaseTypeRepository tsInputPersonCaseTypeRepository;

    @Autowired
    TsPersonCaseTypeRepository tsPersonCaseTypeRepository;

    @Autowired
    FcRybAllFieldRepository fcRybAllFieldRepository;

    @Autowired
    TzmPetitionService tzmPetitionService;

    @Autowired
    FcQxsbRepository fcQxsbRepository;

    @Autowired
    FcDwbRepository fcDwbRepository;

    @Autowired
    FcKsbRepository fcKsbRepository;

    @Autowired
    FcKszwbRepository fcKszwbRepository;

    @Autowired
    TsWindowRepository tsWindowRepository;

    @Autowired
    FdCodeRepository fdCodeRepository;

    @Autowired
    TsTransferPersonRepository tsTransferPersonRepository;

    @Autowired
    AuthUserGroupNewRepository authUserGroupNewRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Map> getTreeByFcRybTreeVO(List<FcRybTreeVO> fcRybs) {
        Set<Map> qxsSet = new HashSet<>();
        Set<Map> dwSet = new HashSet<>();
        Set<Map> ksSet = new HashSet<>();
        Set<Map> rySet = new HashSet<>();
        for (FcRybTreeVO fcRybTreeVO : fcRybs) {
            Map qxsMap = new HashMap();
            qxsMap.put("id", fcRybTreeVO.getQxsId());
            qxsMap.put("name", fcRybTreeVO.getQxsMc());
            qxsSet.add(qxsMap);
            Map dwMap = new HashMap();
            dwMap.put("qxsId", fcRybTreeVO.getQxsId());
            dwMap.put("id", fcRybTreeVO.getDwId());
            dwMap.put("name", fcRybTreeVO.getDwMc());
            dwSet.add(dwMap);
            Map ksMap = new HashMap();
            ksMap.put("dwId", fcRybTreeVO.getDwId());
            ksMap.put("id", fcRybTreeVO.getKsId());
            ksMap.put("name", fcRybTreeVO.getKsMc());
            ksSet.add(ksMap);
            Map ryMap = new HashMap();
            ryMap.put("ksId", fcRybTreeVO.getKsId());
            ryMap.put("id", fcRybTreeVO.getRyId());
            ryMap.put("name", fcRybTreeVO.getRyMc());
            ryMap.put("img", fcRybTreeVO.getRyImg());
            rySet.add(ryMap);
        }
        List<Map> result = new ArrayList<>();
        for (Map qxs : qxsSet) {
            Map qxsMap = new HashMap();
            qxsMap.put("id", qxs.get("id"));
            qxsMap.put("name", qxs.get("name"));
            List<Map> qxsChildrenList = new ArrayList<>();
            qxsMap.put("children", qxsChildrenList);
            for (Map dw : dwSet) {
                if ((int) qxs.get("id") == (int) dw.get("qxsId")) {
                    Map dwMap = new HashMap();
                    dwMap.put("id", dw.get("id"));
                    dwMap.put("name", dw.get("name"));
                    List<Map> dwChildrenList = new ArrayList<>();
                    dwMap.put("children", dwChildrenList);
                    ((List<Map>) qxsMap.get("children")).add(dwMap); // 添加单位

                    for (Map ks : ksSet) {
                        if ((int) dw.get("id") == (int) ks.get("dwId")) {
                            Map ksMap = new HashMap();
                            ksMap.put("id", ks.get("id"));
                            ksMap.put("name", ks.get("name"));
                            List<Map> ksChildrenList = new ArrayList<>();
                            ksMap.put("children", ksChildrenList);
                            ((List<Map>) dwMap.get("children")).add(ksMap); // 添加科室

                            for (Map ry : rySet) {
                                if ((int) ks.get("id") == (int) ry.get("ksId")) {
                                    Map ryMap = new HashMap();
                                    ryMap.put("id", ry.get("id"));
                                    ryMap.put("name", ry.get("name"));
                                    ryMap.put("img", ry.get("img"));
                                    ((List<Map>) ksMap.get("children")).add(ryMap); // 添加人员
                                }
                            }
                        }
                    }
                }
            }
            result.add(qxsMap);
        }
        return result;
    }

    public List<Map> getDwKsRyTreeByFcRybTreeVO(List<FcRybTreeVO> fcRybs) {
        Set<Map> dwSet = new HashSet<>();
        Set<Map> ksSet = new HashSet<>();
        Set<Map> rySet = new HashSet<>();
        for (FcRybTreeVO fcRybTreeVO : fcRybs) {
            Map dwMap = new HashMap();
            dwMap.put("qxsId", fcRybTreeVO.getQxsId());
            dwMap.put("id", fcRybTreeVO.getDwId());
            dwMap.put("name", fcRybTreeVO.getDwMc());
            dwSet.add(dwMap);
            Map ksMap = new HashMap();
            ksMap.put("dwId", fcRybTreeVO.getDwId());
            ksMap.put("id", fcRybTreeVO.getKsId());
            ksMap.put("name", fcRybTreeVO.getKsMc());
            ksSet.add(ksMap);
            Map ryMap = new HashMap();
            ryMap.put("ksId", fcRybTreeVO.getKsId());
            ryMap.put("id", fcRybTreeVO.getRyId());
            ryMap.put("name", fcRybTreeVO.getRyMc());
            ryMap.put("img", fcRybTreeVO.getRyImg());
            rySet.add(ryMap);
        }
        List<Map> result = new ArrayList<>();
        for (Map dw : dwSet) {
            Map dwMap = new HashMap();
            dwMap.put("id", dw.get("id"));
            dwMap.put("name", dw.get("name"));
            List<Map> dwChildrenList = new ArrayList<>();
            dwMap.put("children", dwChildrenList);

            for (Map ks : ksSet) {
                if ((int) dw.get("id") == (int) ks.get("dwId")) {
                    Map ksMap = new HashMap();
                    ksMap.put("id", ks.get("id"));
                    ksMap.put("name", ks.get("name"));
                    List<Map> ksChildrenList = new ArrayList<>();
                    ksMap.put("children", ksChildrenList);
                    ((List<Map>) dwMap.get("children")).add(ksMap); // 添加科室

                    for (Map ry : rySet) {
                        if ((int) ks.get("id") == (int) ry.get("ksId")) {
                            Map ryMap = new HashMap();
                            ryMap.put("id", ry.get("id"));
                            ryMap.put("name", ry.get("name"));
                            ryMap.put("img", ry.get("img"));
                            ((List<Map>) ksMap.get("children")).add(ryMap); // 添加人员
                        }
                    }
                }
            }
            result.add(dwMap);
        }
        return result;
}

    public List<Map> getQxsDwTypeDwKsTreeByFcRybTreeVO(List<FcRybTreeVO> fcRybs) {
        Set<Map> qxsSet = new HashSet<>();
        Set<Map> dwTypeSet = new HashSet<>();
        Set<Map> dwSet = new HashSet<>();
        Set<Map> ksSet = new HashSet<>();
        for (FcRybTreeVO fcRybTreeVO : fcRybs) {
            Map qxsMap = new HashMap();
            qxsMap.put("id", fcRybTreeVO.getQxsId());
            qxsMap.put("name", fcRybTreeVO.getQxsMc());
            qxsSet.add(qxsMap);
            Map dwTypeMap = new HashMap();
            dwTypeMap.put("qxsId", fcRybTreeVO.getQxsId());
            dwTypeMap.put("id", fcRybTreeVO.getDwType());
            dwTypeMap.put("name", null);
            dwTypeSet.add(dwTypeMap);
            Map dwMap = new HashMap();
            dwMap.put("dwType", fcRybTreeVO.getDwType());
            dwMap.put("qxsId", fcRybTreeVO.getQxsId());  // 避免重复
            dwMap.put("id", fcRybTreeVO.getDwId());
            dwMap.put("name", fcRybTreeVO.getDwMc());
            dwSet.add(dwMap);
            Map ksMap = new HashMap();
            ksMap.put("dwId", fcRybTreeVO.getDwId());
            ksMap.put("id", fcRybTreeVO.getKsId());
            ksMap.put("name", fcRybTreeVO.getKsMc());
            ksSet.add(ksMap);
        }
        List<Map> result = new ArrayList<>();
        for (Map qxs : qxsSet) {
            Map qxsMap = new HashMap();
            qxsMap.put("id", qxs.get("id"));
            qxsMap.put("name", qxs.get("name"));
            qxsMap.put("tag", "区县市");
            List<Map> qxsChildrenList = new ArrayList<>();
            qxsMap.put("children", qxsChildrenList);
            for (Map dwType : dwTypeSet) {
                if ((int) qxs.get("id") == (int) dwType.get("qxsId")) {
                    Map dwTypeMap = new HashMap();
                    dwTypeMap.put("id", dwType.get("id"));
                    dwTypeMap.put("name", dwType.get("name"));
                    dwTypeMap.put("tag", "单位类型");
                    List<Map> dwChildrenList = new ArrayList<>();
                    dwTypeMap.put("children", dwChildrenList);
                    ((List<Map>) qxsMap.get("children")).add(dwTypeMap); // 添加单位类型

                    for (Map dw : dwSet) {
                        if ((int) dwType.get("id") == (int) dw.get("dwType") &&
                                (int) dwType.get("qxsId") == (int) dw.get("qxsId")) {  // 单独比较dwType会重复
                            Map dwMap = new HashMap();
                            dwMap.put("id", dw.get("id"));
                            dwMap.put("name", dw.get("name"));
                            dwMap.put("tag", "单位");
                            List<Map> ksChildrenList = new ArrayList<>();
                            dwMap.put("children", ksChildrenList);
                            ((List<Map>) dwTypeMap.get("children")).add(dwMap); // 添加单位

                            for (Map ks : ksSet) {
                                if ((int) dw.get("id") == (int) ks.get("dwId")) {
                                    Map ksMap = new HashMap();
                                    ksMap.put("id", ks.get("id"));
                                    ksMap.put("name", ks.get("name"));
                                    ksMap.put("tag", "科室");
                                    ((List<Map>) dwMap.get("children")).add(ksMap); // 添加科室
                                }
                            }
                        }
                    }
                }
            }
            result.add(qxsMap);
        }
        return result;
    }

    public List<Map> dwKsRyTree(Integer dwId) {
        List<FcRybTreeVO> fcRybs = fcRybAllFieldRepository.findRybByDwId(dwId);
        List<Map> result = getDwKsRyTreeByFcRybTreeVO(fcRybs);
        return result;
    }


    public List<Map> getInputPersonCaseTypeTree(Integer dwId) {
        List<Integer> userIds = tsInputPersonCaseTypeRepository.findUserIdGroupByUserId();
        List<FcRybTreeVO> fcRybs = null;
        if (dwId == null || dwId == -1)
            fcRybs = fcRybAllFieldRepository.findRybByIds(userIds);
        else
            fcRybs = fcRybAllFieldRepository.findRybByIdsAndDwId(userIds, dwId);
        List<Map> result = new ArrayList<>();
        result = getTreeByFcRybTreeVO(fcRybs);
        return result;
    }

    public List<Map> getPersonCaseTypeTree(Integer dwId, List<Integer> typeIds) {
        List<Integer> userIds;
        if (typeIds == null || typeIds.size() == 0)
            userIds = tsPersonCaseTypeRepository.findUserIdGroupByUserId();
        else
            userIds = tsPersonCaseTypeRepository.findUserIdByTypeIds(typeIds);
        List<FcRybTreeVO> fcRybs = null;
        if (dwId == null || dwId == -1)
            fcRybs = fcRybAllFieldRepository.findRybByIds(userIds);
        else
            fcRybs = fcRybAllFieldRepository.findRybByIdsAndDwId(userIds, dwId);
        List<Map> result = new ArrayList<>();
        result = getTreeByFcRybTreeVO(fcRybs);
        return result;
    }

    public List<Map> getPersonCaseType(Integer dwId) {
        List<Integer> typeIds = null;
        List<Map> ryInfoList = null;
        List<Map> result = new ArrayList<>();
        if (dwId == null || dwId == -1) {
            typeIds = null;
            ryInfoList = tsTransferPersonRepository.findDwRyInfo();
        } else {
            typeIds = tsPersonCaseTypeRepository.findTypeIdByDwId(dwId);  // 人员数目大于1的接受类型
            ryInfoList = tsTransferPersonRepository.findRyInfoByDwId(dwId);   // 单位下接受 某案件类型的人员id
            List<Map> ryInfoMap = tsTransferPersonRepository.findDwRyInfoByDwId(dwId); // 单位接收人
            if (ryInfoMap == null || ryInfoMap.size() > 1) {
                System.err.print("单位收文岗为空或者大于1 ！！！（单位id:" + dwId + "）");
            } else if (ryInfoMap.size() == 1) {
                Map item = new HashMap();
                Map map = ryInfoMap.get(0);
                item.put("caseTypeId", map.get("caseTypeId"));
                item.put("caseTypeName", "单位收文岗");
                item.put("ryId", map.get("ryId"));
                item.put("ryName", map.get("ryName"));
                item.put("ryImg", map.get("ryImg"));
                result.add(item);
            }
        }

        List<Integer> types = new ArrayList<>();
        types.add(12);
//        types.add(13);
        List<Map> codeList = fdCodeRepository.getCodeAndNameByTypeFor1213(types);

        if (typeIds == null || typeIds.size() == 0) {
            if (ryInfoList != null && ryInfoList.size() > 0) {
                Map item = new HashMap();
                for (Map map : ryInfoList) {
                    item.put("ryId", map.get("ryId"));
                    item.put("ryName", map.get("ryName"));
                    item.put("ryImg", map.get("ryImg"));
                    item.put("dwId", map.get("dwId"));
                    item.put("dwName", map.get("dwName"));
                }
                result.add(item);
            }
            return result;
        }
        for (Integer caseTypeId : typeIds) {
            Map item = new HashMap();
            item.put("caseTypeName", null);
            item.put("caseTypeId", caseTypeId);
            for (Map codeMap : codeList) {
                if ((int) codeMap.get("code") == caseTypeId) {
                    item.put("caseTypeName", codeMap.get("name"));
                }
            }
            item.put("ryId", null);
            item.put("ryName", null);
            item.put("ryImg", null);
            for (Map map : ryInfoList) {
                if (caseTypeId == (int) map.get("caseTypeId")) {
                    item.put("ryId", map.get("ryId"));
                    item.put("ryName", map.get("ryName"));
                    item.put("ryImg", map.get("ryImg"));
                }
            }
            result.add(item);
        }
        return result;
    }

    public List<Map> getPersonCaseType2(Integer dwId) {
        return tsPersonCaseTypeRepository.findTypeIdByDwId2(dwId);
    }

    public Map<String, Object> getPersonCaseType3(Integer dwId, Integer typeId) {
        String sql = "SELECT\n" +
                " FH_RY_ID,\n" +
                " FH_RY_FPLX,\n" +
                " (\n" +
                "  SELECT\n" +
                "   ifnull(max(RY_MC), '')\n" +
                "  FROM\n" +
                "   FC_RYB\n" +
                "  WHERE\n" +
                "   RY_ID = CC.FH_RY_ID\n" +
                " ) AS RY_MC,\n" +
                " CASE\n" +
                "WHEN FH_RY_FJID <>- 1 THEN\n" +
                " (\n" +
                "  SELECT\n" +
                "   ifnull(max(check_person_id), 0)\n" +
                "  FROM\n" +
                "   FC_RYB\n" +
                "  WHERE\n" +
                "   RY_ID = CC.FH_RY_FJID\n" +
                " )\n" +
                "ELSE\n" +
                " FH_RY_FJID\n" +
                "END FH_RY_FJID,\n" +
                " CASE\n" +
                "WHEN FH_RY_FJID <>- 1 THEN\n" +
                " ''\n" +
                "ELSE\n" +
                " '无上级'\n" +
                "END FH_RY_FJMC,\n" +
                " CODE1,\n" +
                " CODE_MC,\n" +
                " DW_ID,\n" +
                " DW_MC,\n" +
                " 0 TE\n" +
                "FROM\n" +
                " (\n" +
                "  SELECT\n" +
                "   CASE\n" +
                "  WHEN JSRY_CT = 1 THEN\n" +
                "   (\n" +
                "    SELECT\n" +
                "     ifnull(max(user_id), 0)\n" +
                "    FROM\n" +
                "     ts_person_case_type_new\n" +
                "    WHERE\n" +
                "     type_id = DD.CODE1\n" +
                "    AND user_id IN (\n" +
                "     SELECT\n" +
                "      RY_ID\n" +
                "     FROM\n" +
                "      FC_RYB\n" +
                "     WHERE\n" +
                "      DW_ID = DD.DW_ID\n" +
                "    )\n" +
                "   )\n" +
                "  WHEN JSRY_CT > 1\n" +
                "  AND TYPE_SWGRY_ID <> 0 THEN\n" +
                "   TYPE_SWGRY_ID\n" +
                "  WHEN JSRY_CT > 1\n" +
                "  AND TYPE_SWGRY_ID = 0 THEN\n" +
                "   DW_SWGRY_ID\n" +
                "  ELSE\n" +
                "   DW_SWGRY_ID\n" +
                "  END FH_RY_ID,\n" +
                "  CASE\n" +
                " WHEN JSRY_CT = 1 THEN\n" +
                "  1\n" +
                " WHEN JSRY_CT > 1\n" +
                " AND TYPE_SWGRY_ID <> 0 THEN\n" +
                "  2\n" +
                " WHEN JSRY_CT > 1\n" +
                " AND TYPE_SWGRY_ID = 0 THEN\n" +
                "  3\n" +
                " ELSE\n" +
                "  3\n" +
                " END FH_RY_FPLX,\n" +
                " CASE\n" +
                "WHEN JSRY_CT = 1 THEN\n" +
                " CASE\n" +
                "WHEN (\n" +
                " SELECT\n" +
                "  ifnull(max(user_id), 0)\n" +
                " FROM\n" +
                "  ts_person_case_type_new\n" +
                " WHERE\n" +
                "  type_id = DD.CODE1\n" +
                " AND user_id IN (\n" +
                "  SELECT\n" +
                "   RY_ID\n" +
                "  FROM\n" +
                "   FC_RYB\n" +
                "  WHERE\n" +
                "   DW_ID = DD.DW_ID\n" +
                "  AND IFNULL(check_person_id, 0) > 0\n" +
                " )\n" +
                ") <> 0 THEN\n" +
                " (\n" +
                "  SELECT\n" +
                "   ifnull(max(user_id), 0)\n" +
                "  FROM\n" +
                "   ts_person_case_type_new\n" +
                "  WHERE\n" +
                "   type_id = DD.CODE1\n" +
                "  AND user_id IN (\n" +
                "   SELECT\n" +
                "    RY_ID\n" +
                "   FROM\n" +
                "    FC_RYB\n" +
                "   WHERE\n" +
                "    DW_ID = DD.DW_ID\n" +
                "   AND IFNULL(check_person_id, 0) > 0\n" +
                "  )\n" +
                " )\n" +
                "ELSE\n" +
                " - 1\n" +
                "END\n" +
                "WHEN JSRY_CT > 1\n" +
                "AND TYPE_SWGRY_ID <> 0 THEN\n" +
                " TYPE_SWGRY_ID\n" +
                "WHEN JSRY_CT > 1\n" +
                "AND TYPE_SWGRY_ID = 0 THEN\n" +
                " DW_SWGRY_ID\n" +
                "ELSE\n" +
                " DW_SWGRY_ID\n" +
                "END FH_RY_FJID,\n" +
                " DD.*\n" +
                "FROM\n" +
                " (\n" +
                "  SELECT\n" +
                "   A. CODE AS CODE1,\n" +
                "   A. NAME AS CODE_MC,\n" +
                "   (\n" +
                "    SELECT\n" +
                "     ifnull(max(user_id), 0)\n" +
                "    FROM\n" +
                "     ts_transfer_person\n" +
                "    WHERE\n" +
                "     org_id = B.DW_ID\n" +
                "    AND ifnull(case_type_id, 0) = 0\n" +
                "   ) AS DW_SWGRY_ID,\n" +
                "   (\n" +
                "    SELECT\n" +
                "     ifnull(max(user_id), 0)\n" +
                "    FROM\n" +
                "     ts_transfer_person\n" +
                "    WHERE\n" +
                "     org_id = B.DW_ID\n" +
                "    AND case_type_id = A. CODE\n" +
                "   ) AS TYPE_SWGRY_ID,\n" +
                "   (\n" +
                "    SELECT\n" +
                "     COUNT(*)\n" +
                "    FROM\n" +
                "     ts_person_case_type_new\n" +
                "    WHERE\n" +
                "     type_id = A. CODE\n" +
                "    AND user_id IN (\n" +
                "     SELECT\n" +
                "      RY_ID\n" +
                "     FROM\n" +
                "      FC_RYB\n" +
                "     WHERE\n" +
                "      DW_ID = B.DW_ID\n" +
                "    )\n" +
                "   ) AS JSRY_CT,\n" +
                "   B.DW_ID AS DW_ID,\n" +
                "   B.DW_MC AS DW_MC,\n" +
                "   0 AS ce\n" +
                "  FROM\n" +
                "   FD_CODE A,\n" +
                "   fc_dwb B\n" +
                "  WHERE\n" +
                "   A.type = 12\n" +
                "  AND ifnull(A.parent_code ,- 1) <>- 1\n" +
                "  AND B.DW_ID ="+dwId;
        sql  += ("\n" +
                " ) dd\n" +
                "WHERE\n" +
                " CODE1 = "+typeId);
        sql  += ("\n" +
                " ) CC");

        Map<String, Object> result = null;
        try {
            result = jdbcTemplate.queryForMap(sql);
        } catch (DataAccessException e) {
            ApiLog.chargeLog1(e.getMessage());
            return null;
        }
        return result;
    }

    public Map getXfByQxsId(Integer qxsId){
        return fcDwbRepository.findXfByQxsId(qxsId);
    }

    public Map getXfByFdCodeId(Integer areaId){
        List<FdCode> fdCodeList = fdCodeRepository.findByTypeAndState(15,1);
        for (FdCode fdCode: fdCodeList){
            //if (areaId == fdCode.getCode())
            if (areaId == fdCode.getCode().intValue())
                return fcDwbRepository.findXfByQxsId(Integer.parseInt(fdCode.getMappingField()));
        }
        return null;
    }

    @Transactional
    public List<Map> getQxsDwTypeDwKsTree(Integer ryId) {
        LogInInfo ryInfo = tzmPetitionService.selectLogInInfoByRyId(ryId);
//        List<FcRybTreeVO> fcRybs = null;
//        if(ryInfo.getQxsId()==8){  // 登录人员的区县市为‘贵阳市’，显示所有
//            fcRybs = fcRybAllFieldRepository.findRybAll();  // 只显示有人员的树
//            fcRybs = fcKsbRepository.findTreeAll();  // 显示所有的树，拥有可以的区县市、单位
//        }else {
//            fcRybs = fcRybAllFieldRepository.findRybByQxsId(ryInfo.getQxsId());  // 只显示有人员的树
//            fcRybs = fcKsbRepository.findTreeByQxsId(ryInfo.getQxsId());  // 只显示有人员的树，拥有可以的区县市、单位
//        }
//        List<Map> result = new ArrayList<>();
//        result = getQxsDwTypeDwKsTreeByFcRybTreeVO(fcRybs);
        return getFcRybTreeVOList(ryInfo.getQxsId());  // 返回所有
    }

    @Transactional
    public List<Map> getQxsDwTypeDwTree(Integer qxsId, Integer dwId) {

        return getFcRybTreeVOListByQxsIdAndDwId(qxsId, dwId);  // 返回所有
    }


    @Transactional
    List<Map> getFcRybTreeVOList(Integer qxsId) {
        List<Map> result = new ArrayList<>();
        List<Map> qxsMapList = null;
        List<Map> dwTypeMapList = null;
        List<Map> dwMapList = null;
        List<Map> ksMapList = null;
        if (qxsId == 8) {
            qxsMapList = fcQxsbRepository.findQxsIdAndName();
            dwTypeMapList = fcDwbRepository.findDwTypeAndQxsId();
            dwMapList = fcDwbRepository.findDwIdMcType(); // 包含 单位id、名称、类型
            ksMapList = fcKsbRepository.findKsIdAndName();
        } else {
            qxsMapList = fcQxsbRepository.findQxsIdAndNameByQxsId(qxsId);
            dwTypeMapList = fcDwbRepository.findDwTypeAndQxsIdByQxsId(qxsId);
            dwMapList = fcDwbRepository.findDwIdMcTypeByQxsId(qxsId); // 包含 区县市id、单位id、名称、类型
            ksMapList = fcKsbRepository.findKsIdAndNameByQxsId(qxsId);
        }

        for (Map qxs : qxsMapList) {
            Map qxsMap = new HashMap();
            qxsMap.put("id", qxs.get("qxsId"));
            qxsMap.put("name", qxs.get("qxsMc"));
            qxsMap.put("tag", "区县市");
            List<Map> qxsChildrenList = new ArrayList<>();
            qxsMap.put("children", qxsChildrenList);
            for (Map dwType : dwTypeMapList) {
                if ((int) qxs.get("qxsId") == (int) dwType.get("qxsId")) {
                    Map dwTypeMap = new HashMap();
                    dwTypeMap.put("id", dwType.get("dwType"));
                    dwTypeMap.put("name", null);
                    dwTypeMap.put("tag", "单位类型");
                    List<Map> dwChildrenList = new ArrayList<>();
                    dwTypeMap.put("children", dwChildrenList);
                    ((List<Map>) qxsMap.get("children")).add(dwTypeMap); // 添加单位类型

                    for (Map dw : dwMapList) {
                        if (dwType.get("dwType") != null && dw.get("dwType") != null && dwType.get("qxsId") != null && dw.get("qxsId") != null &&
                                (int) dwType.get("dwType") == (int) dw.get("dwType") &&
                                (int) dwType.get("qxsId") == (int) dw.get("qxsId")) {  // 单独比较dwType会重复
                            Map dwMap = new HashMap();
                            dwMap.put("id", dw.get("dwId"));
                            dwMap.put("name", dw.get("dwMc"));
                            dwMap.put("tag", "单位");
                            List<Map> ksChildrenList = new ArrayList<>();
                            dwMap.put("children", ksChildrenList);
                            ((List<Map>) dwTypeMap.get("children")).add(dwMap); // 添加单位

                            for (Map ks : ksMapList) {
                                if ((int) dw.get("dwId") == (int) ks.get("dwId")) {
                                    Map ksMap = new HashMap();
                                    ksMap.put("id", ks.get("ksId"));
                                    ksMap.put("name", ks.get("ksMc"));
                                    ksMap.put("tag", "科室");
                                    ((List<Map>) dwMap.get("children")).add(ksMap); // 添加科室
                                }
                            }
                        }
                    }
                }
            }
            result.add(qxsMap);
        }
        return result;
    }

    @Transactional
    List<Map> getFcRybTreeVOListByQxsIdAndDwId(Integer qxsId, Integer dwId) {
        List<Map> result = new ArrayList<>();
        List<Map> qxsMapList = null;
        List<Map> dwTypeMapList = null;
        List<Map> dwMapList = null;
        if (qxsId == 8) {
            if (fcDwbRepository.isXFJByDwId(dwId) == null || fcDwbRepository.isXFJByDwId(dwId).size() == 0) { // 非信访局
                qxsMapList = fcQxsbRepository.findQxsIdAndNameByQxsId(qxsId);
                dwTypeMapList = fcDwbRepository.findDwTypeAndQxsIdByDwId(dwId);
                dwMapList = fcDwbRepository.findDwIdMcTypeByDwId(dwId); // 包含 单位id、名称、类型
            } else {
                qxsMapList = fcQxsbRepository.findQxsIdAndName();
                dwTypeMapList = fcDwbRepository.findDwTypeAndQxsId();
                dwMapList = fcDwbRepository.findDwIdMcType(); // 包含 单位id、名称、类型
            }
        } else {
            qxsMapList = fcQxsbRepository.findQxsIdAndNameByQxsId(qxsId);
            if (fcDwbRepository.isXFJByDwId(dwId) == null || fcDwbRepository.isXFJByDwId(dwId).size() == 0) { // 非信访局
                dwTypeMapList = fcDwbRepository.findDwTypeAndQxsIdByQxsIdAndDwId(qxsId, dwId);
                dwMapList = fcDwbRepository.findDwIdMcTypeByQxsIdAndDwId(qxsId, dwId); // 包含 单位id、名称、类型
            } else {
                dwTypeMapList = fcDwbRepository.findDwTypeAndQxsIdByQxsId(qxsId);
                dwMapList = fcDwbRepository.findDwIdMcTypeByQxsId(qxsId); // 包含 区县市id、单位id、名称、类型
            }
        }

        for (Map qxs : qxsMapList) {
            Map qxsMap = new HashMap();
            qxsMap.put("id", qxs.get("qxsId"));
            qxsMap.put("name", qxs.get("qxsMc"));
            qxsMap.put("tag", "区县市");
            List<Map> qxsChildrenList = new ArrayList<>();
            qxsMap.put("children", qxsChildrenList);
            for (Map dwType : dwTypeMapList) {
                if ((int) qxs.get("qxsId") == (int) dwType.get("qxsId")) {
                    Map dwTypeMap = new HashMap();
                    dwTypeMap.put("id", dwType.get("dwType"));
                    dwTypeMap.put("name", null);
                    dwTypeMap.put("tag", "单位类型");
                    List<Map> dwChildrenList = new ArrayList<>();
                    dwTypeMap.put("children", dwChildrenList);
                    ((List<Map>) qxsMap.get("children")).add(dwTypeMap); // 添加单位类型
                    for (Map dw : dwMapList) {
                        if (dwType.get("dwType") != null && dw.get("dwType") != null && dwType.get("qxsId") != null && dw.get("qxsId") != null &&
                                (int) dwType.get("dwType") == (int) dw.get("dwType") &&
                                (int) dwType.get("qxsId") == (int) dw.get("qxsId")) {  // 单独比较dwType会重复
                            Map dwMap = new HashMap();
                            dwMap.put("id", dw.get("dwId"));
                            dwMap.put("name", dw.get("dwMc"));
                            dwMap.put("tag", "单位");
                            List<Map> ksChildrenList = new ArrayList<>();
                            dwMap.put("children", ksChildrenList);
                            ((List<Map>) dwTypeMap.get("children")).add(dwMap); // 添加单位
                        }
                    }
                }
            }
            result.add(qxsMap);
        }
        return result;
    }

    public Map updateStatus(Integer type, Integer id, Integer status) {
        Map map = new HashMap();
        try {
            if (status != 0 && status != 1) {
                throw new BizException("状态码status只能为0或1");
            }
            Integer flag = 0;
            switch (type) {
                case 1:     // 区县市
                    flag = fcQxsbRepository.updateQxsStatus(id, status);
                    break;
                case 2:     // 单位
                    flag = fcDwbRepository.updateDwStatus(id, status);
                    break;
                case 3:     // 科室
                    flag = fcKsbRepository.updateKsbStatus(id, status);
                    break;
                case 4:     // 职位
                    flag = fcKszwbRepository.updateKszwbStatus(id, status);
                    break;
                case 5:     // 人员
                    flag = fcRybAllFieldRepository.updateRybStatus(id, status);
                    break;
                case 6:     // 窗口
                    flag = tsWindowRepository.updateWindowStatus(id, status);
                    break;
                default:
                    throw new BizException("类型type值错误，只能为1-5中的数字");
            }
            if (flag == 0) {
                throw new BizException("更新失败，id可能不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isUpdate", false);
            map.put("message", e.getMessage());
            return map;
        }
        map.put("isUpdate", true);
        map.put("message", "成功更新为type=" + type + ", id=" + id + ",status=" + status);
        return map;
    }

    @Transactional
    public void updateCheckPersonId() {
        Set<PermissionPersonDetail> rySetA = fcRybAllFieldRepository.personList();
        Set<PermissionPersonDetail> rySetB = fcRybAllFieldRepository.personList();
        for (PermissionPersonDetail ryInfoA : rySetA) {
            Integer checkPersonId = -1;
            Integer ryId = ryInfoA.getRyId();
            for (PermissionPersonDetail ryInfoB : rySetB) {
                if (ryInfoA.getCheckPersonId() != null && ryInfoB.getDutyId() != null &&
                        ryInfoA.getCheckPersonId() == ryInfoB.getDutyId()) {
                    checkPersonId = ryInfoB.getRyId();
                    break;
                }
            }
            fcRybAllFieldRepository.updateCheckPersonIdByRyId(ryId, checkPersonId);
        }
    }

    @Transactional
    public void updateInputPersonCaseTypeNew() {
        List<TsInputPersonCaseTypeNew> oldlist = tsInputPersonCaseTypeRepository.findAllOldType();
        for (TsInputPersonCaseTypeNew item : oldlist) {
            switch (item.getType()) {
                case 1: // 来访登记
                    Integer[] newTypes1 = new Integer[]{120000, 120001, 120104, 120102, 120200, 120201, 130300, 130301};
                    handleInputPersonCaseTypeNew(item, newTypes1);
                    break;
                case 2: // 来信登记
                    Integer[] newTypes2 = new Integer[]{120202, 130302};
                    handleInputPersonCaseTypeNew(item, newTypes2);
                    break;
                case 3:   // 电话信访，无
                    break;
                case 4:  // 电子邮件，无
                    break;
                case 5:  // 领导信箱，无
                    break;
                case 6: // 传真登记，无
                    break;
                case 7: // 短信信访登记，无
                    break;
                case 8: // 网上信访
                    Integer[] newTypes8 = new Integer[]{120204, 120205, 120203, 130304, 130305, 130306};
                    handleInputPersonCaseTypeNew(item, newTypes8);
                    break;
                case 9: // 督察督办登记
                    Integer[] newTypes9 = new Integer[]{120208};
                    handleInputPersonCaseTypeNew(item, newTypes9);
                    break;
                case 10:  // 复查复核登记，无
                    break;
                default:
                    break;
            }
        }
    }

    @Transactional
    public void updateUserRolePermission(Integer[] personIds, Integer permission) {
        List<AuthUserGroupNew> ryList = new ArrayList<>();
        for (int i = 0; i < personIds.length; i++) {
            AuthUserGroupNew item = new AuthUserGroupNew();
            item.setGroupId(permission);
            item.setUserId(personIds[i]);
            ryList.add(item);
        }
        authUserGroupNewRepository.save(ryList);
    }

    @Transactional
    void handleInputPersonCaseTypeNew(TsInputPersonCaseTypeNew tsInputPersonCaseTypeNew, Integer[] newTypes) {
        List<TsInputPersonCaseTypeNew> tsInputPersonCaseTypeNewList = new ArrayList<>();
        for (int i = 0; i < newTypes.length; i++) {
            TsInputPersonCaseTypeNew newItem = new TsInputPersonCaseTypeNew();
            newItem.setUserId(tsInputPersonCaseTypeNew.getUserId());
            newItem.setType(newTypes[i]);
            newItem.setCreatedAt(tsInputPersonCaseTypeNew.getCreatedAt());
            newItem.setUpdatedAt(tsInputPersonCaseTypeNew.getUpdatedAt());
            tsInputPersonCaseTypeNewList.add(newItem);
        }
        tsInputPersonCaseTypeRepository.delete(tsInputPersonCaseTypeNew.getId());
        tsInputPersonCaseTypeRepository.save(tsInputPersonCaseTypeNewList);
    }

    public List<TsTransferPerson> useSuperAdminToSetDwSwg(Set<Integer> dwIdSet){

        List<TsTransferPerson> tsTransferPersonList = new ArrayList<>();
        for (Integer dwId: dwIdSet){
            List<Integer> swgRyId = tsTransferPersonRepository.finSwgInfoByDwId(dwId);
            if ( swgRyId != null && swgRyId.size()>0){
                continue;
            }

            List<Map> ryIdList =  authUserGroupNewRepository.findUserIdByGroupId(1);// 具有‘超级管理员’权限的人员

            for (Map map: ryIdList){
                if ((int)map.get("dwId")==dwId){
                    tsTransferPersonList.add(setTransferPerson((int)map.get("userId"),dwId));
                    break;
                }
            }
        }
        return tsTransferPersonRepository.save(tsTransferPersonList);
    }

    public TsTransferPerson setTransferPerson(Integer userId, Integer dwId){
        TsTransferPerson tsTransferPerson = new TsTransferPerson();
        tsTransferPerson.setUserId(userId);
        tsTransferPerson.setOrgId(dwId);
        tsTransferPerson.setCreatedAt(new Timestamp(new Date().getTime()));
        tsTransferPerson.setUpdatedAt(new Timestamp(new Date().getTime()));
        return tsTransferPerson;
    }
}
