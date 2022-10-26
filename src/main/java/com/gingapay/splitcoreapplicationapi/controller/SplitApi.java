package com.gingapay.splitcoreapplicationapi.controller;

import com.gingapay.splitcoreapplicationapi.dtos.SplitResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/splits")
public interface SplitApi {

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/find-split-by-order-id/{orderId}")
    ResponseEntity<SplitResponse> findSplitByOrderId(@PathVariable String orderId);

}
