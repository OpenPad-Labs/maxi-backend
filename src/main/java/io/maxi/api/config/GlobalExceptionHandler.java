package io.maxi.api.config;

import io.maxi.api.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 统一异常处理类
 * @author wangtao
 * @date 2018年4月16日 下午1:53:48
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		
		log.error("request error url:"+req.getRequestURL(),e);

		return Result.error("net error","The system is busy, please try again later...");
	}

}