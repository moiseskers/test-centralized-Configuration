package com.gingapay.splitcoreapplicationapi;

import com.gingapay.splitcoreapplicationapi.helper.TranslateHelper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableRabbit
public class SplitCoreApplicationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitCoreApplicationApiApplication.class, args);
    }

    @Bean
    TranslateHelper translateHelper() {
        return new TranslateHelper();
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

}
