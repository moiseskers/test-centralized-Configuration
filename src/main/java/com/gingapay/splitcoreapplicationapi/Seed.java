package com.gingapay.splitcoreapplicationapi;

import com.gingapay.splitcoreapplicationapi.dtos.NewOrderQueue;
import com.gingapay.splitcoreapplicationapi.dtos.SaleDetailsRequest;
import com.gingapay.splitcoreapplicationapi.helper.JsonHelper;
import com.gingapay.splitcoreapplicationapi.repository.SplitRepository;
import com.gingapay.splitcoreapplicationapi.service.MotorSplitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Seed {

    Logger logger = LoggerFactory.getLogger(Seed.class);

    @Autowired private SplitRepository splitRepository;
    @Autowired private MotorSplitService motorSplitService;


    @EventListener
    @Transactional
    public void seed(ContextRefreshedEvent event) {
        if (this.splitRepository.findAll().isEmpty()) {
            createDefaultEntity();
        }
    }

    void createDefaultEntity() {
        var detailsJson = "{\"organizationsIdentity\": \"vtex\", \"orders\": [{\"storeIdentity\": \"Seller A\", \"products\": [{\"totalAmount\": 500, \"shippingCost\": 50, \"quantity\": 1, \"productId\": \"Monitor\", \"unityPrice\": 500, \"shippingCommission\": 15, \"productCommission\": 15}]},{\"storeIdentity\": \"Seller B\", \"products\": [{\"totalAmount\": 300, \"shippingCost\": 20, \"quantity\": 1, \"productId\": \"Teclado\", \"unityPrice\": 300, \"shippingCommission\": 15, \"productCommission\": 15}]},{\"storeIdentity\": \"Seller C\", \"products\": [{\"totalAmount\": 200, \"shippingCost\": 30, \"quantity\": 2, \"productId\": \"Mouse\", \"unityPrice\": 100, \"shippingCommission\": 0, \"productCommission\": 10}]}]}";
        var response = JsonHelper.convertToObject(SaleDetailsRequest.class, detailsJson);
        var splitEntity =  this.motorSplitService.generateSplitEntity(response, getNewOrderQueue());
        this.splitRepository.save(splitEntity);
    }

    NewOrderQueue getNewOrderQueue () {
        var newOrderQueue = new NewOrderQueue();
        newOrderQueue.setOrderId("order-id");
        newOrderQueue.setEvent("new-order-queue");
        newOrderQueue.setPlatform("vtex");
        newOrderQueue.setOrganizationsId("1");
        newOrderQueue.setOrganizationsIdentity("1234");
        return newOrderQueue;
    }

}


