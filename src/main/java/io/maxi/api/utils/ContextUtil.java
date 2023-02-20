package io.maxi.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程上下文统一管理
 */
public class ContextUtil {
    private static Logger logger = LoggerFactory.getLogger(ContextUtil.class);
    /**
     * uid
     */
    private static final ThreadLocal<String> uid = new ThreadLocal<>();
    private static final ThreadLocal<String> token = new ThreadLocal<>();
    private static final ThreadLocal<String> originIp = new java.lang.ThreadLocal<java.lang.String>();



    public static void setUid(String uid){
        if (StringUtils.isBlank(uid)) {
            logger.warn("setUid error, uid={}", uid);
            return;
        }
        ContextUtil.uid.set(uid);
    }

    public static String getUid(){
    	return uid.get();
    }

    public static void clearUid(){
        uid.remove();
    }



    public static void setOriginIp(String originIp){
        if (StringUtils.isBlank(originIp)) {
            logger.warn("setOriginIp error, originIp={}", originIp);
            return;
        }
        ContextUtil.originIp.set(originIp);
    }

    public static String getOriginIp(){
        String ip = originIp.get();
        if(ip == null){
            return "127.0.0.1";
        }
        return originIp.get();
    }

    public static void clearOriginIp(){
        originIp.remove();
    }



    /**
     */
    public static void setToken(String token) {
        if (StringUtils.isBlank(token)) {
            logger.warn("setToken error, token={}", token);
            return;
        }
        ContextUtil.token.set(token);
    }
    /**
     * @return
     */
    public static String getToken(){
        return token.get();
    }
    /**
     * 移除线程变量
     */
    public static void clearToken(){
        token.remove();
    }


    public static void clearAll(){
        clearUid();
        clearToken();
    }

}
