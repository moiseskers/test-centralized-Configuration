package com.gingapay.templateapplication;

/*

A queue configuration example:

	@Configuration
	public class QueueConfiguration {

		@Value("${gingapay.queues.example.name}")
		private String exampleQueueName;

		@Bean
		public Queue exampleQueue() {
			return new Queue(exampleQueueName, true);
		}
}

* */
