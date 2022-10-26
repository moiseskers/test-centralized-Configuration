package com.gingapay.splitcoreapplicationapi.service;

import com.gingapay.splitcoreapplicationapi.dtos.NewOrderQueue;
import com.gingapay.splitcoreapplicationapi.dtos.Order;
import com.gingapay.splitcoreapplicationapi.dtos.SaleDetailsRequest;
import com.gingapay.splitcoreapplicationapi.dtos.SplitResponse;
import com.gingapay.splitcoreapplicationapi.entity.OrderEntity;
import com.gingapay.splitcoreapplicationapi.entity.SplitEntity;
import org.springframework.transaction.annotation.Transactional;


public interface MotorSplitService {

    @Transactional
    void newOrderEvent(NewOrderQueue event);

    SplitResponse findSplitByOrderId(String orderId);

    SplitEntity generateSplitEntity(SaleDetailsRequest response, NewOrderQueue newOrderQueue);

    OrderEntity process(Order order, SplitEntity split);
}
