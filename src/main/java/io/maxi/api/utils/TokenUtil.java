package io.maxi.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 生成各种token
 */
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    // 加密秘钥
    private static final String PASSWORD = "Y24uam5sanMuYXBpLnV0aWxzLlRva2VuVXRpbA==";

    private static final char[] CHARS = new char[] {'F', 'L', 'G', 'W', '5', 'X', 'C', '3',
            '9', 'Z', 'M', '6', '7', 'Y', 'R', 'T', '2', 'H', 'S', '8', 'D', 'V', 'E', 'J', '4', 'K',
            'Q', 'P', 'U', 'A', 'N', 'B'};
    static final int CODE_LENGTH = 6;
    static final int CHARS_LENGTH = CHARS.length;

    public static String randCode(){
        return SecurityUtil.md5(String.valueOf(System.nanoTime()));
    }


    public static String  getLoginToken(String code){
        return SecurityUtil.sha256(code + System.nanoTime());
    };


    public static String genPromotionCode(String uid) {
        long id = Long.parseLong(uid);

        //将 id 转换成32进制的值
        long[] b = new long[CODE_LENGTH];
        //32进制数
        b[0] = id;
        for (int i = 0; i < CODE_LENGTH - 1; i++) {
            b[i + 1] = b[i] / CHARS_LENGTH;
            //扩大每一位的差异
            b[i] = (b[i] + i * b[0]) % CHARS_LENGTH;
        }
        b[5] = (b[0] + b[1] + b[2] + b[3] + b[4])  % CHARS_LENGTH;

        //进行混淆
        long[] codeIndexArray = new long[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeIndexArray[i] = b[i  % CODE_LENGTH];
        }

        StringBuilder buffer = new StringBuilder();
        Arrays.stream(codeIndexArray).boxed().map(Long::intValue).map(t -> CHARS[t]).forEach(buffer::append);
        return buffer.toString();
    }


    public static String genUid(String seed){

        if(!StringUtils.isNumeric(seed)){
            //seed = seed.substring(0,seed.length()/2);
            seed = char2num(seed);
        }

        String ms = String.format("%04d",System.nanoTime()%10000);
        if(StringUtils.isEmpty(seed) || seed.length() < 2){
            throw new RuntimeException("seed illegal");
        }
        if(seed.length() < 7){
            seed = seed+seed+seed+seed;
        }
        if(seed.length() > 7){
            seed = seed.substring(0,7);
        }
        return "20888"+ms+seed;
    }

    public static String getOrderNo(String seed) {
        //14 位时间-->12位(去掉秒)
        String date = DateUtil.getNoPrefix();
        //6位商品code --> 3位
        String code = TokenUtil.char2num(seed);
        code = String.format("%1$-3s", code).replaceAll(" ","0").substring(0,3);
        //4位uid
        String uidEnd = ContextUtil.getUid().substring(12,16);
        //6位时间-->3位
        String nano = String.format("%03d",System.nanoTime()%1000);
        return date+code+uidEnd+nano;
    }

    public static String char2num(String a){
        byte[] b = a.getBytes();
        int sum = 0;
        for (int i = 0; i < b.length; i++) {
            sum += b[i]*13;
        }
        if(sum < 1000){
            sum = sum*103;
        }
        if(sum < 10000){
            sum = sum*13;
        }
        return String.valueOf(sum);
    }



    public static void main(String[] args) {

//        System.out.println(String.valueOf(System.nanoTime() % 10000));
//        System.out.println(String.format("%05d",System.nanoTime()%10000));
//
//
//        long s = System.currentTimeMillis();
//        System.out.println(getLoginToken("oL28G5vN6zqQo7SkSe-ro5rHB9qU"));
//        System.out.println(System.currentTimeMillis() - s);
////        System.out.println(getLoginToken("138000000"));
////        System.out.println(System.currentTimeMillis() - s);
////        System.out.println(getLoginToken("138000000"));
////        System.out.println(System.currentTimeMillis() - s);
//
//        System.out.println(genUid("a123"));
//        System.out.println(genUid("a123").substring(12,16));
//
//        ContextUtil.setUid(genUid("a123"));
//
//        System.out.println("订单号 : "+getOrderNo("123"));
//
//        System.out.println(System.currentTimeMillis() - s);

        System.out.println(char2num("1999999999"));
        System.out.println(genPromotionCode("2088853842544125"));
    }

}