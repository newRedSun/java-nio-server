package com.qiao.OOP.authenticat;

/**
 *
 * 1.将token、AppID、时间戳拼接到URL中，生成新的URL
 * 2.解析URL，得到token、AppID、时间戳等信息。
 * @author qiaojh
 */
public class ApiRequest {

    private String baseUrl;

    private String token;

    private String appId;

    private long timestamp;

    public ApiRequest(String baseUrl, String token, String appId, long timestamp) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public static ApiRequest createFromFullUrl(String url) {
        // FullUrl解析
        //return new ApiRequest();
        return null;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
