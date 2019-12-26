package com.qiao.OOP.authenticat;

import java.util.HashMap;

/**
 * @author qiaojh
 */
public class DefaultApiAuthencatorImpl implements ApiAuthencator {

    private CredentialStorage credentialStorage;


    public DefaultApiAuthencatorImpl() {
        this.credentialStorage = new MysqlCredentialStorage();
    }

    public DefaultApiAuthencatorImpl(CredentialStorage credentialStorage) {
        this.credentialStorage = credentialStorage;
    }

    @Override
    public void auth(String url) {
        ApiRequest request = ApiRequest.createFromFullUrl(url);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        String token = apiRequest.getToken();
        String appId = apiRequest.getAppId();
        String baseUrl = apiRequest.getBaseUrl();
        long timestamp = apiRequest.getTimestamp();

        Authtoken clientAuthtoken = new Authtoken(token, timestamp);
        if (clientAuthtoken.isExpired()) {
            throw new RuntimeException("Token is expired!");
        }

        String password = credentialStorage.getPasswordByAppId(appId);
        HashMap<String, String> map = new HashMap<>();
        map.put("AppID", appId);
        map.put("password", password);
        Authtoken serverAuthtoken = Authtoken.create(baseUrl, timestamp, map);
        if (!serverAuthtoken.match(clientAuthtoken)) {
            throw new RuntimeException("Token verification failed.");
        }

    }
}
