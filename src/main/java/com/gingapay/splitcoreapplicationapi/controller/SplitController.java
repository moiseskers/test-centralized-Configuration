package com.gingapay.splitcoreapplicationapi.controller;

import com.gingapay.splitcoreapplicationapi.dtos.SplitResponse;
import com.gingapay.splitcoreapplicationapi.service.MotorSplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SplitController extends BaseController implements SplitApi {

    @Autowired MotorSplitService motorSplitService;

    @Override
    public ResponseEntity<SplitResponse> findSplitByOrderId(String orderId) {
        return ResponseEntity.ok(this.motorSplitService.findSplitByOrderId(orderId));
    }

}
