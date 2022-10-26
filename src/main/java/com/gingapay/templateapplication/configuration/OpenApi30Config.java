package com.gingapay.templateapplication.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Slf4j
@Configuration
@OpenAPIDefinition(info = @Info(title = "Template Application", version = "v1"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
public class OpenApi30Config implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        var host = InetAddress.getLoopbackAddress().getHostName();
        var port = event.getWebServer().getPort();
        log.info("Swagger URL for localhost purpose only: {}", "http://" + host + ":" + port + "/" + "swagger-ui.html");
    }

}
