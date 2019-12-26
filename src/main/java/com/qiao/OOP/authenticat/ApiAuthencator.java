package com.qiao.OOP.authenticat;

/**
 * @author qiaojh
 */
public interface ApiAuthencator {

    void auth(String url);

    void auth(ApiRequest apiRequest);
}
