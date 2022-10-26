package com.gingapay.splitcoreapplicationapi.queue.producer;

import com.gingapay.splitcoreapplicationapi.dtos.NewSplitDistribution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SplitDistributionSqsProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("splitDistributionQueue")
    private Queue queue;

    public void sendSplitDistributionQueue(NewSplitDistribution newSplitDistribution) {
        log.info("New Split Distribution id {}, on queue {}", newSplitDistribution.getSplitDistributionId(), this.queue.getName());
        this.rabbitTemplate.convertAndSend(this.queue.getName(), newSplitDistribution);
    }
}
