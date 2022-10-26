package com.gingapay.splitcoreapplicationapi.controller;

import com.gingapay.splitcoreapplicationapi.dtos.SplitDistribution;
import com.gingapay.splitcoreapplicationapi.service.SplitDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SplitDistributionController implements SplitDistributionApi{

    @Autowired
    SplitDistributionService splitDistributionService;

    @Override
    public ResponseEntity<SplitDistribution> findSplitDistributionsById(UUID id) {
        return ResponseEntity.ok(splitDistributionService.findById(id));
    }
}
