package io.maxi.api.request;

public class CommonRequest extends BaseRequest{

    //需要清除的key
    String cleanKey;

    public String getCleanKey() {
        return cleanKey;
    }

    public void setCleanKey(String cleanKey) {
        this.cleanKey = cleanKey;
    }
}
