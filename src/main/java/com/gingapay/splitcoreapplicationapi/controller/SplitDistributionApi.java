package com.gingapay.splitcoreapplicationapi.controller;

import com.gingapay.splitcoreapplicationapi.dtos.SplitDistribution;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping(value = "/api/v1/split-distributions")
public interface SplitDistributionApi {

    @GetMapping("/find-split-distribution-by-id/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<SplitDistribution> findSplitDistributionsById(@PathVariable UUID id);

}
