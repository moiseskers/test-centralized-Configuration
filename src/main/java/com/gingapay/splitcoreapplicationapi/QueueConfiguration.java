package com.gingapay.splitcoreapplicationapi;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

	@Value("${gingapay.queues.new-split-queue.name}")
	private String newSplitQueueName;

	@Bean
	public Queue newSplitQueue() {
		return new Queue(newSplitQueueName, true);
	}

	@Bean
	public Queue splitDistributionQueue(
			@Value("${gingapay.queues.split-distribution-queue.name}") String splitDistributionQueueName) {
		return new Queue(splitDistributionQueueName, true);
	}
}
