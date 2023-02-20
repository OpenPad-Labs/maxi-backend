package io.maxi.api.aop;

import io.maxi.api.utils.ContextUtil;
import io.maxi.api.utils.IpUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求耗时记录
 */
@Aspect
@Component
@Order(AopOrder.cost)
public class RequestCostAop {

    private static Logger logger = LoggerFactory.getLogger(RequestCostAop.class);


    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object limitRequestCount(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = null;
        try {

            Object[] args = joinPoint.getArgs();
            Object[] arguments  = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                    //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                    //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                    continue;
                }
                arguments[i] = args[i];
            }
            String argStr = "";
            if(args!=null && args.length != 0){
                argStr = JSON.toJSONString(arguments);
            }

            String ip = IpUtil.getIpAddr(getRequest());
            ContextUtil.setOriginIp(ip);
            logger.info("| {}  |  request in | ip : {} |args : {} ", getRequest().getRequestURL().toString(),ip, argStr);
            result = joinPoint.proceed();
            return result;
        }finally {
            long costTime = System.currentTimeMillis() - start;
            logger.info("| {}  |  costTime : {} ms   result: {}", getRequest().getRequestURI(), costTime, JSON.toJSONString(result));
        }


    }

    private HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

}