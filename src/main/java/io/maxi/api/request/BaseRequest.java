package io.maxi.api.request;


import org.apache.commons.lang3.StringUtils;

/**
 * 基础的请求入参
 */
public class BaseRequest {

    /**
     * ms 时间戳
     * 超时30s则会直接拒绝发送
     */
    String _unix;

    /**
     * md5(unix+key)  避免短信被人刷
     */
    String _token;

    /**
     * 请求来源
     * app,web,h5,applets(小程序),official(公众号)
     */
    String _origin;



    public String get_unix() {
        return _unix;
    }

    public void set_unix(String _unix) {
        this._unix = _unix;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }

    public String get_origin() {
        return _origin;
    }

    public void set_origin(String _origin) {
        this._origin = _origin;
    }

    /**
     * 参数校验,如果为null,则通过,否则返回错误提示
     * @return
     */
    public String verify(){
        if(StringUtils.isEmpty(_unix)  || StringUtils.isEmpty(_token) || StringUtils.isEmpty(_origin)){
            return "_unix _token _origin is null ";
        }
        return null;
    }
}
