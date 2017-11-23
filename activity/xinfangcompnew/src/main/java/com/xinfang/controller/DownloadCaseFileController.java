package com.xinfang.controller;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.service.CaseDocDownloadService;
import com.xinfang.service.StatisticalService;
import com.xinfang.service.impl.DisputeIdentityServiceImpl;
import com.xinfang.utils.DownloadFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;



/**
 * 下载信访事项交办单
 * Created by Lx on 7/6/17.
 */
@RestController
@RequestMapping("/api/v1/downloadcasefile")
@Api(description = "下载信访事项交办单（李墨石）")
public class DownloadCaseFileController {
    @Autowired
    private DisputeIdentityServiceImpl CaseDocDownloadService;
    @Autowired
    private StatisticalService statisticalService;

    @RequestMapping(value = "/downloadDoc", method = RequestMethod.GET)
    @ApiOperation(value = "来信信访事项交办单下载", notes = "来信信访事项交办单下载")

    public Responses downRloadcaseDoc(@ApiParam(name = "bianhao", value = "编号") @RequestParam String bianhao, HttpServletResponse response) {

        List<Map<String, Object>> result = null;
        result = statisticalService.zhtj("GC_CX_JBCX","","","","","","","",bianhao,"","");
        //检测result是否为空；
        if (result == null || result.size() == 0) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, "案件未找到");
        }
        //生成文件生成时间字符串
        String current_time = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分").format(Calendar.getInstance().getTime());
        //给map填充数据
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("PETITION_CODE", result.get(0).get("PETITION_CODE"));
        dataMap.put("curr_time", current_time);
        dataMap.put("XFR_MC", result.get(0).get("XFR_MC"));
        dataMap.put("XFR_XJZD", result.get(0).get("XFR_XJZD"));
        dataMap.put("XFR_DH", result.get(0).get("XFR_DH"));
        dataMap.put("petition_time", result.get(0).get("petition_time"));
        dataMap.put("sd", result.get(0).get("sd"));
        dataMap.put("handle_days", result.get(0).get("handle_days"));
        dataMap.put("flmc", result.get(0).get("flmc"));
        dataMap.put("XFFS", result.get(0).get("XFFS"));
        dataMap.put("case_desc", result.get(0).get("case_desc"));
        dataMap.put("case_demand", result.get(0).get("case_demand"));
        dataMap.put("Blyj", result.get(0).get("blyj"));
        dataMap.put("cbdwyj",result.get(0).get("cbdwyj"));
        //给map填充数据

        //生成word文档
        File file;
        try {
            CaseDocDownloadService test = new CaseDocDownloadService();
            file =test.createWord(dataMap);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, "Fail_creation");
        }
        //下载
        DownloadFileUtils download = new DownloadFileUtils();
        try{
        boolean check = download.downLoadFile(file.getAbsolutePath(),response);
        }catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, "FAIL_DownLoad");
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, "success");
    }
}