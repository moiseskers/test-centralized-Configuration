package com.gingapay.splitcoreapplicationapi.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        var message = getMessageOrGetNullHelper(response);

        if (responseStatus.is5xxServerError()) {
            if (Boolean.TRUE.equals(this.urlEndsWithOauthToken(response.request().url()))) {
                return retryOnOauthToken500Request(response);
            }

            return new ResponseStatusException(responseStatus, message);
        } else if (responseStatus.is4xxClientError()) {
            return new ResponseStatusException(responseStatus, message);
        }

        return errorDecoder.decode(methodKey, response);
    }

    Boolean urlEndsWithOauthToken(String url) {
        return url.matches(".*/oauth/token$");
    }

    RetryableException retryOnOauthToken500Request(Response response) {
        return new RetryableException(response.status(), "retry_on_oauth_token_500_request", response.request().httpMethod(), null, null, response.request());
    }

    String getMessageOrGetNullHelper(Response response) {
        return Optional.ofNullable(response.body()).map(value1 -> {
            try {
                return new BufferedReader(value1.asReader(StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                return null;
            }
        }).map(value2 -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            try {
                var message = mapper.readValue(value2, ExceptionMessage.class).message;
                return Objects.requireNonNullElse(message, value2);
            } catch (Exception e) {
                return value2;
            }
        }).orElse(null);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ExceptionMessage {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }
}
