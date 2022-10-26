package com.gingapay.splitcoreapplicationapi.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class GingapayTokenProperties {

    private String[] allowedUrls;

}
