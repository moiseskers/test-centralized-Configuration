package com.gingapay.splitcoreapplicationapi.queue.consumer;

import com.gingapay.splitcoreapplicationapi.dtos.NewOrderQueue;
import com.gingapay.splitcoreapplicationapi.service.MotorSplitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class NewOrderQueueConsumer {

    @Autowired
    private MotorSplitService service;

    @Transactional
    @RabbitListener(queues = {"${gingapay.queues.new-orders.name}"})
    public void receive(@Payload NewOrderQueue response) {
        service.newOrderEvent(response);
    }

}
