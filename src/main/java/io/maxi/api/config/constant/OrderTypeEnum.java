package io.maxi.api.config.constant;

/**
 * 订单类型
 */
public enum OrderTypeEnum {

    unknown,

    market,
    vip,

    ;


    public static OrderTypeEnum get(String name){
        for (OrderTypeEnum value : OrderTypeEnum.values()) {
            if(value.name().equals(name)){
                return value;
            }
        }
        return unknown;
    }

}
