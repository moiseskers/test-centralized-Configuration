package com.gingapay.templateapplication.queue.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
    A consumer example:

        @Slf4j
        @Component
        public class ExampleConsumer {

            @Autowired
            private ExampleService service;

            @Transactional
            @RabbitListener(queues = {"${gingapay.queues.example.name}"})
            public void receive(@Payload Example example) {
                service.example(example);
            }
        }
* */

@Slf4j
@Component
public class ExampleConsumer {
}
