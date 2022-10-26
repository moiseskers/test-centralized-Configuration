package com.gingapay.splitcoreapplicationapi.resource;

import com.gingapay.splitcoreapplicationapi.configuration.FeignClientConfiguration;
import com.gingapay.splitcoreapplicationapi.dtos.PaymentOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gingapay-payment-client", url = "${gingapay.payment.webservice-uri}", configuration = FeignClientConfiguration.class)
public interface GingapayPaymentFeignClient {

    @GetMapping(path = "/api/v1/orders/{paymentOrderId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    PaymentOrderResponse findPaymentByOrderId(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("paymentOrderId") String paymentOrderId);
}
