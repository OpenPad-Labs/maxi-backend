//package io.maxi.api.aop;
//
//import io.maxi.api.config.constant.SecretKeyConstant;
//import io.maxi.api.config.constant.WebApiResultType;
//import io.maxi.api.response.Result;
//import io.maxi.api.utils.ContextUtil;
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//
///**
// * 请求耗时记录
// */
//@Aspect
//@Component
//@Order(AopOrder.user)
//public class ValidUserAop {
//
//    private static Logger logger = LoggerFactory.getLogger(ValidUserAop.class);
//
//    @Autowired
//    protected LoginSessionService loginSessionService;
//
//
//    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//    public Object limitRequestCount(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        try{
//            logger.info("| {}  |  ValidUserAop" , getRequest().getRequestURL().toString());
//
//            //排查不需要校验工具的公共接口
//            Class<?> cls =joinPoint.getTarget().getClass();
//
//
//            //类名注释不做检查
//            if(cls.isAnnotationPresent(NoCheckLOgin.class)){
//                return joinPoint.proceed();
//            }
//            MethodSignature sign =(MethodSignature)joinPoint.getSignature();
//            Method method= cls.getDeclaredMethod(sign.getName(), sign.getParameterTypes());
//            //方法注释不做检查
//            if(method.isAnnotationPresent(NoCheckLOgin.class)){
//                return joinPoint.proceed();
//            }
//            logger.info("[ValidUserAop] cls :{}  method :{}  cls ann : {}   method ann :{}" ,
//                    cls.getName(),method.getName(),cls.getDeclaredAnnotations(),method.getDeclaredAnnotations());
//
//            //从http 头中获取token 解析用户信息,如果没有,则直接返回未登陆
//            HttpServletRequest request = getRequest();
//            String token = request.getHeader(SecretKeyConstant.USER_TOKEN);
//            if(StringUtils.isEmpty(token)){
//                logger.info("[ValidUserAop] token is null :{} ", token);
//                return Result.error(WebApiResultType.NotLogin);
//            }
//
//            long start =System.currentTimeMillis();
//
//            User user = loginSessionService.getUserByToken(token);
//            if (user == null){
//                logger.info("[ValidUserAop] user is null , token :{} ", token);
//                return Result.error(WebApiResultType.NotLogin);
//            }
//            long userEnd =System.currentTimeMillis();
//            //就算为null,也只走一次redis, 内存中是一个option对象
//            UserThirdOauth oauth = loginSessionService.getOauthByToken(token);
//            ContextUtil.setOauth(oauth);
//            long oauthEnd =System.currentTimeMillis();
//
//            UserExt userExt = loginSessionService.getUserExtByToken(token);
//            ContextUtil.setUserExt(userExt);
//
//            logger.info("[ValidUserAop] token  :{}  user: {}  oauth : {} userExt:{} ", token, JSON.toJSONString(user), JSON.toJSONString(oauth),JSON.toJSONString(userExt));
//            logger.info("[ValidUserAop] usercost : {} ms oauthcost : {}", userEnd-start,oauthEnd-userEnd);
//
//            // 校验用户权限, 默认所有的接口校验用户权限, 特殊接口不需要校验的,需要加特殊标记;  特殊权限接口,需要增加权限标识,需要用户有对应的权限才能访问
//
//            ContextUtil.setUser(user);
//            ContextUtil.setUid(user.getUid());
//            ContextUtil.setToken(token);
//
//            return joinPoint.proceed();
//        }finally {
//            ContextUtil.clearAll();
//        }
//
//
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