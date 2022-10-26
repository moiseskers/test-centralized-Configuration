package com.gingapay.templateapplication.queue.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
    A producer example:

        @Autowired
        private RabbitTemplate rabbitTemplate;

        @Autowired
        @Qualifier("exampleQueue")
        private Queue queue;

        public void dispatch(Example example) {
            this.rabbitTemplate.convertAndSend(this.queue.getName(), example);
        }

* */
@Component
@Slf4j
public class ExampleProducer {

}
