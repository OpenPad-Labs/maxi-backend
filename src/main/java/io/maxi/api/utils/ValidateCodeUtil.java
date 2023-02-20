package io.maxi.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 短信、邮箱验证码
 * 
 * @author zouyj
 *
 */
public class ValidateCodeUtil {
	private static final Logger logger = LoggerFactory.getLogger(ValidateCodeUtil.class);


	public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	public static final String NUMBER_VERIFY_CODES = "0123456789";
	static int deflength = 4;

	public static String get(){
		return generateVerifyCode(deflength);
	}

	public static String generateVerifyCode(int verifySize){
		return generateVerifyCode(verifySize, NUMBER_VERIFY_CODES);
	}
	/**
	 * 使用指定源生成验证码
	 * @param verifySize    验证码长度
	 * @param sources   验证码字符源
	 * @return
	 */
	public static String generateVerifyCode(int verifySize, String sources){
		if(sources == null || sources.length() == 0){
			sources = VERIFY_CODES;
		}
		int codesLen = sources.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for(int i = 0; i < verifySize; i++){
			verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
		}
		return verifyCode.toString();
	}

	public static void main(String[] args) {
		System.out.println(get());
	}

}
