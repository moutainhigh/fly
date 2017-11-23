package com.xinfang.personnelmanagement.controller;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FcDwb;
import com.xinfang.model.FcKsb;
import com.xinfang.model.FcKszwb;
import com.xinfang.model.FcQxsb;
import com.xinfang.model.TsWindow;
import com.xinfang.personnelmanagement.service.OrganizationService;
import com.xinfang.personnelmanagement.service.StaffPandectService;

@RestController
@RequestMapping("/V1/api/organization")
@Api(description = "机构管理右侧列表(张波)")
public class OrganizationListController {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private StaffPandectService staffPandectService;
    @RequestMapping(value = "/deptlist", method = RequestMethod.GET)
    @ApiOperation(value = "通过区县市ID获取单位列表", notes = "通过区县市ID获取单位列表")
    public Responses getDeptlistByQxsId(
            @ApiParam(name = "qxsId", value = "区县市ID") @RequestParam Integer qxsId,
            @ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false) String fuzzy,
            @ApiParam(name = "startPage", value = "起始页数") @RequestParam Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam Integer pageCount) {
        Map<String, Object> fcDwb = null;
        try {
            fcDwb = organizationService.getdeptByQXSId(qxsId, fuzzy, startPage,
                    pageCount);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, fcDwb);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, fcDwb);
    }

    @RequestMapping(value = "/qxslist", method = RequestMethod.GET)
    @ApiOperation(value = "获取区县市列表", notes = "获取区县市列表")
    public Responses getQxsList(
            @ApiParam(name = "startPage", value = "起始页数") @RequestParam Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam Integer pageCount,
            @ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false) String fuzzy) {
        Map<String, Object> qxsList = null;
        try {
            qxsList = organizationService.getQxsList(startPage, pageCount,
                    fuzzy);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, qxsList);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, qxsList);
    }

    @RequestMapping(value = "/saveqxs", method = RequestMethod.POST)
    @ApiOperation(value = "添加修改区县市", notes = "添加修改区县市")
    public Responses updateOrAddQxs(
            @ModelAttribute FcQxsb fcQxs,
            @ApiParam(name = "strCJSJ", value = "创建时间") @RequestParam(required = false) String strCJSJ,
            @ApiParam(name = "strXGSJ", value = "更新时间") @RequestParam(required = false) String strXGSJ) {
        Map<String, Object> qxs = null;
        try {
            qxs = organizationService.updateOrAddQxs(fcQxs, strCJSJ, strXGSJ);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, qxs);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, qxs);
    }

    @RequestMapping(value = "/savedept", method = RequestMethod.POST)
    @ApiOperation(value = "添加修改单位", notes = "添加修改单位")
    public Responses updateOrAddDept(
            @ModelAttribute FcDwb fcDwb,
            @ApiParam(name = "strCJSJ", value = "创建时间") @RequestParam(required = false) String strCJSJ,
            @ApiParam(name = "strXGSJ", value = "更新时间") @RequestParam(required = false) String strXGSJ) {
        Map<String, Object> dept = null;
        try {
            dept = organizationService.updateOrAddDept(fcDwb, strCJSJ, strXGSJ);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, dept);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, dept);
    }

    @RequestMapping(value = "/saveKS", method = RequestMethod.POST)
    @ApiOperation(value = "添加修改科室", notes = "添加修改科室")
    public Responses updateOrAddKs(
            @ModelAttribute FcKsb fcKsb,
            @ApiParam(name = "strCJSJ", value = "创建时间") @RequestParam(required = false) String strCJSJ,
            @ApiParam(name = "strXGSJ", value = "更新时间") @RequestParam(required = false) String strXGSJ) {
        Map<String, Object> ks = null;
        try {
            ks = organizationService.updateOrAddKs(fcKsb, strCJSJ, strXGSJ);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, ks);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, ks);
    }

    @RequestMapping(value = "/savezw", method = RequestMethod.POST)
    @ApiOperation(value = "添加修改职务", notes = "添加修改职务")
    public Responses updateOrAddKszw(
            @ModelAttribute FcKszwb fcKszwb,
            @ApiParam(name = "strCJSJ", value = "创建时间") @RequestParam(required = false) String strCJSJ,
            @ApiParam(name = "strXGSJ", value = "更新时间") @RequestParam(required = false) String strXGSJ) {
        Map<String, Object> zw = null;
        try {
            zw = organizationService.updateOrAddZw(fcKszwb, strCJSJ, strXGSJ);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, zw);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, zw);
    }

    @RequestMapping(value = "/windowlist", method = RequestMethod.GET)
    @ApiOperation(value = "通过单位ID获取窗口列表", notes = "通过单位ID获取窗口列表")
    public Responses getwingdowListBydeptId(
            @ApiParam(name = "deptId", value = "单位ID") @RequestParam Integer deptId,
            @ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false) String fuzzy,
            @ApiParam(name = "startPage", value = "起始页数") @RequestParam Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam Integer pageCount

    ) {
        Map<String, Object> windowList = null;
        try {
            windowList = organizationService.getTsWindowByDeptId(deptId,
                    startPage, pageCount, fuzzy);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, windowList);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, windowList);
    }

    @RequestMapping(value = "/ksList", method = RequestMethod.GET)
    @ApiOperation(value = "通过单位ID获取科室列表", notes = "通过单位ID获取科室列表")
    public Responses getKsListByDeptId(
            @ApiParam(name = "deptId", value = "单位ID") @RequestParam Integer deptId,
            @ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false) String fuzzy,
            @ApiParam(name = "startPage", value = "起始页数") @RequestParam Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam Integer pageCount

    ) {
        Map<String, Object> ksList = null;
        try {
            ksList = organizationService.getKsListByDeptId(deptId, startPage,
                    pageCount, fuzzy);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, ksList);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, ksList);
    }

    @RequestMapping(value = "/zwList", method = RequestMethod.GET)
    @ApiOperation(value = "通过科室ID获取职务列表", notes = "通过科室ID获取职务列表")
    public Responses getZwListByKstId(
            @ApiParam(name = "ksId", value = "科室ID") @RequestParam Integer ksId,
            @ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false) String fuzzy,
            @ApiParam(name = "startPage", value = "起始页数") @RequestParam Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam Integer pageCount

    ) {
        Map<String, Object> zwList = null;
        try {
            zwList = organizationService.getZwListByKsId(ksId, startPage,
                    pageCount, fuzzy);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, zwList);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, zwList);
    }


    @RequestMapping(value = "/staff/pandect", method = RequestMethod.GET)
    @ApiOperation(value = "机构人员总览", notes = "机构人员总览")
    public Responses staffPandect(
            @ApiParam(name = "countyId", value = "区县市ID") @RequestParam(defaultValue = "0") Integer countyId,
            @ApiParam(name = "unitId", value = "单位ID") @RequestParam(defaultValue = "0") Integer unitId
           ) {

        Map<String, Object> map = null;
        try {
            map = staffPandectService.staffPandect(countyId, unitId);
        } catch (Exception e) {
        	ApiLog.chargeLog1(e.getMessage());
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, map);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, map);
    }

    @RequestMapping(value = "/savewindow", method = RequestMethod.POST)
    @ApiOperation(value = "添加修改窗口", notes = "添加修改窗口")
    public Responses updateOrAddWindow(
            @ModelAttribute TsWindow tsWindow,
            @ApiParam(name = "rIds", value = "人员id数组") @RequestParam(name = "rIds", required = false) List<Integer> rIds,
            @ApiParam(name = "strCJSJ", value = "创建时间") @RequestParam(required = false) String strCJSJ,
            @ApiParam(name = "strXGSJ", value = "更新时间") @RequestParam(required = false) String strXGSJ) {
        Map<String, Object> window = null;
        try {
            window = organizationService.updateOrAddWindow(tsWindow, rIds,
                    strCJSJ, strXGSJ);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, window);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, window);
    }

    @RequestMapping(value = "/getDeptListBytypeId", method = RequestMethod.GET)
    @ApiOperation(value = "通过单位类型ID获取单位", notes = "通过单位类型ID获取单位")
    public Responses getDeptListBytypeId(
            @ApiParam(name = "typeId", value = "单位类型ID") @RequestParam Integer typeId,
            @ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false) String fuzzy,
            @ApiParam(name = "startPage", value = "起始页数") @RequestParam Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam Integer pageCount

    ) {
        Map<String, Object> deptList = null;
        try {
            deptList = organizationService.getDeptListByDeptType(typeId,
                    startPage, pageCount, fuzzy);

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, deptList);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, deptList);
    }
	@RequestMapping(value = "/getObjectBytypeId", method = RequestMethod.GET)
	@ApiOperation(value = "获取区县市、单位、科室等的详细信息", notes = "ID类型，1为区县、2为单位、3为科室、4为窗口、5为职务")
	public Responses getObjectBytypeId(
			@ApiParam(name = "typeId", value = "单位类型ID") @RequestParam Integer typeId,
			@ApiParam(name = "id", value = "ID") @RequestParam Integer id
	
	) {
		Map<String, Object> object = null;
		try {
			object = organizationService.getObjectByTypeIdAndId(typeId, id);

		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, object);
		}

		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, object);
	}
	@RequestMapping(value = "/getKsListByUnitId", method = RequestMethod.GET)
	@ApiOperation(value = "通过单位ID获取科室列表（科室包含职务）", notes = "通过单位ID获取科室列表（科室包含职务）")
	public Responses getKsListByUnitId(
			@ApiParam(name = "unitId", value = "单位ID") @RequestParam Integer unitId
	
	) {
		List<Map> object = null;
		try {
			object = organizationService.getKsListByUnitId(unitId);

		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, object);
		}

		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, object);
	}
	@RequestMapping(value = "/getstaffTreeByUnitId", method = RequestMethod.GET)
	@ApiOperation(value = "通过单位ID获取职务层级树", notes = "通过单位ID获取职务层级树")
	public Responses getstaffTreeByUnitId(
			@ApiParam(name = "unitId", value = "单位ID") @RequestParam Integer unitId
	
	) {
		Map<String, Object> object = null;
		try {
			object = staffPandectService.staffTree(unitId);

		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, object);
		}

		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, object);
	}
	@RequestMapping(value = "/deleteObjectById", method = RequestMethod.GET)
	@ApiOperation(value = "根据种类删除对应的信息", notes = "根据种类删除对应的信息")
	public Responses deleteObjectById(
			@ApiParam(name = "id", value = "ID") @RequestParam Integer id,
			@ApiParam(name = "type", value = "ID种类（1.区县市 2.单位 3.科室 4.窗口 5.职务 ）") @RequestParam Integer type
	
	) {
		Map<String, Object> object = null;
		try {
			object = organizationService.deleteObject(id, type);

		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, object);
		}

		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, object);
	}
	@RequestMapping(value = "/getParentByUnitId", method = RequestMethod.GET)
	@ApiOperation(value = "查找单位父id为空的职务", notes = "查找单位父id为空的职务")
	public Responses getParentByUnitId(
			@ApiParam(name = "unitId", value = "单位ID") @RequestParam Integer unitId
	
	) {
		Map<String, Object> object = null;
		try {
			object = organizationService.getParentByUnitId(unitId);

		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, object);
		}

		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, object);
	}
	
	@RequestMapping(value = "/getRyIdListByWindowId", method = RequestMethod.GET)
	@ApiOperation(value = "获取除该窗口ID下 该单位窗口关联所有人员", notes = "获取除该窗口ID下 该单位窗口关联所有人员")
	public Responses getRyIdListByWindowId(
			@ApiParam(name = "unitId", value = "单位ID") @RequestParam Integer unitId,
			@ApiParam(name = "windowId", value = "窗口ID") @RequestParam(required=false,defaultValue="0") Integer windowId
			
	) {
		Map<String, Object> object = null;
		try {
			object = organizationService.getRyIdListByWindowId(windowId, unitId);

		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, object);
		}

		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, object);
	}
    @RequestMapping(value = "/getWindowIdBysignUserId", method = RequestMethod.GET)
    @ApiOperation(value = "根据登录人编号获取窗口编号", notes = "根据登录人编号获取窗口编号")
    public Responses getWindowIdBysignUserId(
            @ApiParam(name = "signUserId", value = "登录人ID") @RequestParam Integer signUserId
        ){
            Map<String, Object> map = null;
        try {
            map = organizationService.getWindowIdBysignUserId(signUserId);
        } catch (Exception e) {     	
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, map);
        }
        return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, map);
}
}