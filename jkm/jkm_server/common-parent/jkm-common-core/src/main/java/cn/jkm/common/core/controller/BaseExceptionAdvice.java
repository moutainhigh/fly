package cn.jkm.common.core.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.jkm.common.exception.ServiceException;

/**
 * 统一异常处理
 * 
 * @author zhengzb
 *
 */
@ControllerAdvice
public class BaseExceptionAdvice {
	private final Logger logger = LoggerFactory.getLogger(BaseExceptionAdvice.class);

	// @ExceptionHandler(NullPointerException.class)
	// @ResponseBody
	// public ReturnMessage handleNullPointerException(HttpServletRequest
	// request,
	// NullPointerException ex) {
	// handleLog(request, ex);
	// return new ReturnMessage(ReturnMessage.error1,"空指针异常！");
	// }

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ReturnMessage handleServiceException(HttpServletRequest request, ServiceException ex) {
		handleLog(request, ex);
		return new ReturnMessage(ReturnMessage.error1, getExceptionAllinformation(ex));
	}

	@ExceptionHandler(Exception.class)  
	@ResponseBody
	public ReturnMessage handleException(HttpServletRequest request,  
			Exception ex) {  
        handleLog(request, ex);  
        return new ReturnMessage(ReturnMessage.error1,getExceptionAllinformation(ex));   
    }

	private void handleLog(HttpServletRequest request, Exception ex) {
		logger.error("  request method=" + request.getMethod());
		logger.error("  url=" + request.getRequestURL());
		logger.error("comm_error:", ex);
	}

	private String getExceptionAllinformation(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		ex.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		pout.close();
		try {
			out.close();
		} catch (Exception e) {
		}
		return ret;
	}
}
