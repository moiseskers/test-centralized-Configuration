package com.gingapay.splitcoreapplicationapi.service;


import com.gingapay.splitcoreapplicationapi.dtos.OauthTokenRequest;
import com.gingapay.splitcoreapplicationapi.resource.GingapayConnectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private GingapayConnectClient gingapayConnectClient;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    String clientId;

    @Override
    public String getAuthorizationHeaderValue() {
        var basicToken = "Basic " + this.getAuthBasicToken();
        var oauthTokenRequest = new OauthTokenRequest();
        oauthTokenRequest.setGrant_type("client_credentials");
        var response = gingapayConnectClient.oauthToken(basicToken, oauthTokenRequest);
        return "Bearer " + response.getAccessToken();
    }

    protected String getAuthBasicToken() {
        try {
            return this.encodeBase64(clientId + ":" + clientSecret);
        } catch (Exception ex) {
            return null;
        }
    }

    protected String encodeBase64(final String value) {
        try {
            final byte[] headerByteValue = Base64.getEncoder().encode(value.getBytes());
            return new String(headerByteValue);
        } catch (Exception ex) {
            return null;
        }
    }

}
