package com.gingapay.templateapplication;

import com.gingapay.templateapplication.helper.TranslateHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
//@EnableRabbit
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }

    @Bean
    TranslateHelper translateHelper() {
        return new TranslateHelper();
    }

//    @Bean
//    public MessageConverter converter(){
//        return new Jackson2JsonMessageConverter();
//    }

}
