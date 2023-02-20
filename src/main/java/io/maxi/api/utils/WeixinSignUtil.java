/**
 * 
 */
package io.maxi.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author wangtao
 * @Date Apr 16, 2019 6:51:28 PM
 */
public class WeixinSignUtil {
	protected static Logger logger = LoggerFactory.getLogger(WeixinSignUtil.class);
	
	public static final String WEIXIN_TOKEN = "weixin_token";
	
	public static final String WEIXIN_APPID = "wxb96b8676ef14ab40";
	public static final String WEIXIN_SECRET = "b46ebcb0720b12df6ee002d10d00e6f2";
	
	/**
	 * 验证签名
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		
		if(StringUtils.isAnyEmpty(signature,timestamp,nonce)) {
			logger.error("checkSignature params is empty : {},{},{}",signature,timestamp,nonce);
			return false;
		}
		
		
		String[] arr = new String[] { WEIXIN_TOKEN, timestamp, nonce };

		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		String tmpStr = sha1(content.toString()); // 进行sha1数字签名
		logger.info("[SignUtil] [sha1 after tmpStr:{}]", tmpStr);

		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toLowerCase()) : false;
	}

	/**
	 * 对给定字符串内容进行sha1数字签名
	 * 
	 * @date 2015-8-28
	 */
	public static String sha1(String content) {
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return tmpStr.toLowerCase();
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @date 2015-7-21
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @date 2015-7-21
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
	public static String buildSignature(String timestamp, String nonce) {
		String[] arr = new String[] { WEIXIN_TOKEN, timestamp, nonce };

		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		String tmpStr = sha1(content.toString()); // 进行sha1数字签名
		logger.info("[SignUtil] [sha1 after tmpStr:{}]", tmpStr);
		
		return tmpStr;
	}
	
	public static String getJsSdkSign(String noncestr,String tsapiTicket,String timestamp,String url){
		String content="jsapi_ticket="+tsapiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		logger.info("[getJsSdkSign] [sha1 contentr:{}]", content);
		String ciphertext=sha1(content);
		logger.info("[getJsSdkSign] [sha1 after tmpStr:{}]", ciphertext);
		return ciphertext;
	}

	
	public static void main(String[] args) {
		
		String timestamp = "1556260665";
		String nonce = "1q2w3e4r";
		
		String signature = buildSignature(timestamp, nonce);
		
		System.out.println(checkSignature(signature, timestamp, nonce));
		
	}
	
}
