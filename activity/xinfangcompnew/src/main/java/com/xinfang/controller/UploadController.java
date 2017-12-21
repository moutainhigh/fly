package com.xinfang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.ftp.FtpClientHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/upload")
@Api(description = "上传")
public class UploadController {

	@RequestMapping(value = "/ftp", method = RequestMethod.POST)
	@ApiOperation(value = "FTP上传", notes = "FTP上传")
	public Responses ftpUpload(@ApiParam(name = "localFilePath", value = "文件") @RequestParam String localFilePath,
			@ApiParam(name = "fileName", value = "文件名") @RequestParam String fileName) {

		FtpClientHelper Ftp = new FtpClientHelper();
		Ftp.connect();
		Ftp.login();
		// Ftp.download();
		String returnSrc = "";
		try {
			returnSrc = Ftp.uploadHeadSrc(localFilePath, fileName);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, returnSrc);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_OK,
				ResponseConstants.CODE_SUCCESS_VALUE, returnSrc);
	}
}
