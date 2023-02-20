package io.maxi.api.config.constant;

/**
 * 订单状态
 */
public enum OrderStatusEnum {

    unknown,

    order,
    pay,
    //对账确认
    pay_check,
    //超时
    expired,
    settle,
    cancel,


    ;


    public boolean canChangeTo(OrderStatusEnum toStatus){

        if(this == toStatus){
            return true;
        }

        //不能直接从下单到清算
        if(this == order){
            return toStatus!= settle;
        }

        //订单取消,只能重新下单
        if(this == cancel || this == expired){
            return false;
        }

        // 支付订单只能清算
        if(this == pay){
            return settle == toStatus || pay_check == pay_check;
        }
        if(this == pay_check){
            return settle == toStatus;
        }

        //清算订单不能变更
        if(this == settle){
            return false;
        }

        //其他的,都不能变更
        return false;
    }

    /**
     * 是否需要校验订单金额
     * @return
     */
    public boolean needCheckAmount(){
        return this == pay;
    }

    /**
     * 刷新库存
     * @return
     */
    public boolean needFlushStock(){
        return this == pay;
    }


    public static OrderStatusEnum get(String name){
        for (OrderStatusEnum value : OrderStatusEnum.values()) {
            if(value.name().equals(name)){
                return value;
            }
        }
        return unknown;
    }

}
