package com.gingapay.splitcoreapplicationapi.resource;

import com.gingapay.splitcoreapplicationapi.configuration.FeignClientConfiguration;
import com.gingapay.splitcoreapplicationapi.dtos.SaleDetailsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gingapay-order-details-client", url = "${gingapay.order_details.webservice-uri}", configuration = FeignClientConfiguration.class)
public interface GingapayOrderDetailsClient {

    @GetMapping(path = "/api/v1/order-details/find/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    SaleDetailsRequest getOrderDetails(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("orderId") String orderId);

}
