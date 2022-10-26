package com.gingapay.templateapplication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Slf4j
public abstract class BaseController {

    @Autowired protected HttpServletRequest request;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    String clientId;

    protected String decodeBase64(final String value) {
        try {
            final byte[] headerByteValue = Base64.getDecoder().decode(value.getBytes());
            return new String(headerByteValue);
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
