package io.maxi.api.config.constant;



public enum WebApiResultType {
    Success("0", "success", "成功"),
    SystemError("10001", "SystemError", "网络异常,请稍后再试"),
    NotLogin("10002", "NotLogin", "用户未登录"),
    ValidateCodeError("10003", "ValidateCodeError", "验证码错误，请您重新输入"),
    ValidateCodeErrorFrequently("100031", "ValidateCodeErrorFrequently", "验证错误次数过多，请重新获取验证码。"),
    SmsSendFail("10004", "SmsSendFail", "短信发送失败"),
    ParameterError("10005", "ParameterError", "参数错误"),
    SmsSendFrequently("10006", "SmsSendFrequently", "短信发送过于频繁,请稍后再试"),
    bindPhoneError("10007", "bindPhoneError", "手机号绑定失败"),
    bindWxError("10008", "bindWxError", "微信绑定失败"),


    WxappletLoginError("10007", "WxappletLoginError", "微信信息已过期获取失败,请退出重新登入"),
    WxappletNoBind("10008", "WxappletNoBind", "小程序支付需要先完成微信用户授权"),
    WxappletPayOrderError("10009", "WxappletPayOrderError", "微信小程序支付签名出错"),




    productNotExist("11001", "productNotExist", "商品不存在或已过期"),
    productSpecifNotExist("11002", "productSpecifNotExist", "商品规格不存在或已售罄"),
    orderStatusUpdateError("11003", "orderStatusUpdateError", "订单状态更新失败"),
    orderNotExist("11004", "orderNotExist", "订单不存在"),



    VipBuyError("12004", "VipBuyError", "会员购买下单失败,不能重复购买或降级,如有需要,请联系客服处理"),



    VipIsNull("13004", "VipIsNull", "无会员"),
    ;

    public String code;
    public String configKey;
    public String message;

    WebApiResultType(String code, String configKey, String message) {
        this.code = code;
        this.configKey = configKey;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}