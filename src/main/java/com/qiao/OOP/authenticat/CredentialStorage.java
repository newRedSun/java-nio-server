package com.qiao.OOP.authenticat;

/**
 * @author qiaojh
 */
public interface CredentialStorage {

    /**
     * 获取密码
     * @param appId id
     * @return 密码
     */
    String getPasswordByAppId(String appId);
}
