package io.maxi.api.response;

import io.maxi.api.config.constant.WebApiResultType;

import java.io.Serializable;

/**
 * 统一返回结果包装类, 无论api,还是rpc,均使用此接口
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 7917345507074842804L;

    private boolean success;
    /**
     * 0成功 非0具体错误原因
     */
    private String code;

    /**
     * 请求token原样返回
     */
    String token;

    /**
     * 具体错误描述
     */
    private String msg;

    /**
     * 存放业务数据
     */
    private T data;

    

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    public static Result error(WebApiResultType type) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(type.getConfigKey());
        result.setMsg(type.getMessage());
        return result;
    }

    public static Result error(WebApiResultType type, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(type.getConfigKey());
        result.setMsg(msg);
        return result;
    }




    public static Result success(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
