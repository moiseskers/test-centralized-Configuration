package com.gingapay.splitcoreapplicationapi.resource;

import com.gingapay.splitcoreapplicationapi.configuration.FeignClientConfiguration;
import com.gingapay.splitcoreapplicationapi.dtos.OauthTokenRequest;
import com.gingapay.splitcoreapplicationapi.dtos.OauthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gingapay-connect-client", url = "${gingapay.connect.webservice-uri}", configuration = FeignClientConfiguration.class)
public interface GingapayConnectClient {

    @PostMapping(path = "/oauth/token", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OauthTokenResponse oauthToken(@RequestHeader("Authorization") String authorizationHeader, OauthTokenRequest request);

}
