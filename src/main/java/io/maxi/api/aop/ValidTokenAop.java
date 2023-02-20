//package io.maxi.api.aop;
//
//import io.maxi.api.config.constant.SecretKeyConstant;
//import io.maxi.api.config.constant.WebApiResultType;
//import io.maxi.api.request.BaseRequest;
//import io.maxi.api.response.Result;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 请求参数加密,是否是合法请求
// */
//@Aspect
//@Component
//@Order(AopOrder.token)
//public class ValidTokenAop {
//
//    private static Logger logger = LoggerFactory.getLogger(ValidTokenAop.class);
//
//
//    @Autowired
//    SecretKeyService secretKeyService;
//
//
//    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//    public Object limitRequestCount(ProceedingJoinPoint joinPoint) throws Throwable {
//
//
//        logger.info("| {}  |  ValidTokenAop" , getRequest().getRequestURL().toString());
//        Object[] paramValues = joinPoint.getArgs();
//        if(paramValues!=null && paramValues.length != 0){
//            for (Object paramValue : paramValues) {
//                if(paramValue instanceof BaseRequest){
//
//                    //TODO 校验token,确认请求合法
//                    String key = secretKeyService.getSecretKeyByCode(SecretKeyConstant.WX_APPLETS_SECRETKEY);
//
//
//                    String verify = ((BaseRequest)paramValue).verify();
//                    if(verify != null){
//                        return Result.error(WebApiResultType.ParameterError,verify);
//                    }
//                }
//            }
//        }
//
//
//        return joinPoint.proceed();
//
//
//    }
//
//    private HttpServletRequest getRequest(){
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        return attributes.getRequest();
//    }
//
//}