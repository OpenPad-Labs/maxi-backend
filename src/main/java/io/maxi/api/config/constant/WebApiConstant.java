package io.maxi.api.config.constant;

/**
 * web常量
 */
public interface WebApiConstant {
	String CACHE_PREFIX = "api.user.validcode.";
	String CACHE_TTL_SUFFIX = "ttl";
	String VERIFY_CODE_ERROR = "api.user.validcode.error";


	String LOGIN_TOKEN_PREFIX = "api.user.login.";

	String LOGIN_TOKEN_USER_SUFFIX = ".user";
	String LOGIN_TOKEN_OAUTH_SUFFIX = ".oauth";
	String LOGIN_TOKEN_USER_EXT_SUFFIX = ".user.ext";


	//记录用户token,可能用户本地已经删除了,但是服务器还在,不必重复生成,直接获取即可
	String UID_CACHE_KEY = "user.uid.cache.token.";
	String OAUTH_CACHE_KEY = "oauth.openid.cache.token.";
	String USER_EXT_CACHE_KEY = "user.ext.uid.cache.token.";

	int SMS_CACHE_TIME_SECOND = 60*5;
	int SMS_CACHE_TIME_TTL_SECOND = 60;


	String WX_OPEN_ID_SUFFIX = "_wxapplet";


}