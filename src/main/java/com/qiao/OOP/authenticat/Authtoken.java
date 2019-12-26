package com.qiao.OOP.authenticat;

import java.util.Map;

/**
 * 1.把URL、AppID、密码、时间戳拼接成一个字符串
 * 2.对字符串通过加密算法加密生成token
 * 3.根据时间戳判断token是否失效
 * 4.验证两个token是否匹配
 *
 * @author qiaojh
 */
public class Authtoken {

    /**
     * token有效期时间
     */
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL = 60000;

    /**
     * token
     */
    private String token;

    private long createTime;

    private long expiredTimeInterval = DEFAULT_EXPIRED_TIME_INTERVAL;

    public Authtoken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public Authtoken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }


    public static Authtoken create(String baseUrl, long createTime, Map<String, String> params) {
        String token = baseUrl + params.get("AppID") + params.get("password") + createTime;
        return new Authtoken(token, createTime);
    }

    public String getToken() {
        return this.token;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createTime > expiredTimeInterval;
    }

    public boolean match(Authtoken authtoken) {
        return authtoken.getToken().equals(this.token);
    }

}
