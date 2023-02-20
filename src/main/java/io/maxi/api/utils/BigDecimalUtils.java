package io.maxi.api.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class BigDecimalUtils {

    public static String multiply(String a ,String b){
        return new BigDecimal(a).multiply(new BigDecimal(b)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String subtract(String a ,String b){
        return new BigDecimal(a).subtract(new BigDecimal(b)).setScale(2,BigDecimal.ROUND_UP).toString();
    }
    public static String add(String a ,String b){
        return new BigDecimal(a).add(new BigDecimal(b)).setScale(3,BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String percentage(String a ){
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(new BigDecimal(a));
    }

    public static void main(String[] args) {
        System.out.println(percentage("0.1"));
        System.out.println(percentage("0.11"));
        System.out.println(percentage("0.111"));
        System.out.println(percentage("0.1111"));
        System.out.println(percentage("0.11111"));
    }
}
