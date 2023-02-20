package io.maxi.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密解密
 *
 */
public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private static final String ENCODING = "UTF-8";

    // 加密秘钥
    private static final String PASSWORD = "Y24uam5sanMuYXBpLnV0aWxzLlNlY3VyaXR5VXRpbA==";


    public static String md5(String info) {
        try {
            // 获取 MessageDigest 对象，参数为 MD5 字符串，表示这是一个 MD5 算法（其他还有 SHA1 算法等）：
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // update(byte[])方法，输入原数据
            // 类似StringBuilder对象的append()方法，追加模式，属于一个累计更改的过程
            md5.update(info.getBytes("UTF-8"));
            // digest()被调用后,MessageDigest对象就被重置，即不能连续再次调用该方法计算原数据的MD5值。可以手动调用reset()方法重置输入源。
            // digest()返回值16位长度的哈希值，由byte[]承接
            byte[] md5Array = md5.digest();
            // byte[]通常我们会转化为十六进制的32位长度的字符串来使用,本文会介绍三种常用的转换方法
            return byte2Hex(md5Array);
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


    public static String sha256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            logger.error("sha256 error",e);
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String phone(String phone){
        String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";
        String PHONE_BLUR_REPLACE_REGEX = "$1****$2";
        return phone.replaceAll(PHONE_BLUR_REGEX, PHONE_BLUR_REPLACE_REGEX);
    }


    /**
     * AES加密
     *
     * @param content
     *            明文
     * @return 密文
     */
    public static String encryptAES(String content , String password) {
        if (StringUtils.isEmpty(content)) {
            throw new RuntimeException("content is null");
        }
        byte[] encryptResult = encrypt(content, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        // BASE64位加密
        encryptResultStr = ebotongEncrypto(encryptResultStr);
        return encryptResultStr;
    }

    /**
     * AES解密
     *
     * @param encryptResultStr
     *            密文
     * @return 明文
     */
    public static String decryptAES(String encryptResultStr,String password) {
        if (StringUtils.isEmpty(encryptResultStr)) {
            throw new RuntimeException("密文不能为空");
        }
        // BASE64位解密
        try {
            String decrpt = ebotongDecrypto(encryptResultStr);
            byte[] decryptFrom = parseHexStr2Byte(decrpt);
            byte[] decryptResult = decrypt(decryptFrom, password);
            return new String(decryptResult);
        } catch (Exception e) { // 当密文不规范时会报错，可忽略，但调用的地方需要考虑
            return null;
        }
    }

    /**
     * 加密字符串
     */
    private static String ebotongEncrypto(String str) {
        BASE64Encoder base64encoder = new BASE64Encoder();
        String result = str;
        if (str != null && str.length() > 0) {
            try {
                byte[] encodeByte = str.getBytes(ENCODING);
                result = base64encoder.encode(encodeByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // base64加密超过一定长度会自动换行 需要去除换行符
        return result.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 解密字符串
     */
    private static String ebotongDecrypto(String str) {
        BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            byte[] encodeByte = base64decoder.decodeBuffer(str);
            return new String(encodeByte);
        } catch (IOException e) {
            logger.error("IO 异常",e);
            return str;
        }
    }

    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    private static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 注意这句是关键，防止linux下 随机生成key。用其他方式在Windows上正常，但Linux上会有问题
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            // kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            logger.error("加密异常", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    private static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            // kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            logger.error("解密异常", e);
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        express();
    }

    /**
     * 测试
     */
    private static void express() {
        System.out.println("加密解密试试：");
        String content = "EXPRESS";
        System.out.println("原内容为：" + content);
        String encryContent = encryptAES(content,PASSWORD);
        System.out.println("加密后的内容为：" + encryContent);
        String decryContent = decryptAES(encryContent,PASSWORD);
        System.out.println("解密后的内容为：" + decryContent);
    }

}