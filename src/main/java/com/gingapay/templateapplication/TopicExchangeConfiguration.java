package com.gingapay.templateapplication;

/*

    A topic exchange configuration example:

        @Configuration
        @Slf4j
        public class TopicExchangeConfiguration {

            @Value("${gingapay.exchanges.example.name}")
            public String exampleExchange;

            @Bean
            public TopicExchange exampleExchange() {
                return new TopicExchange(exampleExchange);
            }

            @Bean
            public Declarables topicExchangeBindings(TopicExchange exampleExchange) {

                return new Declarables(
                        BindingBuilder.bind(purchaseStatusPaidQueue).to(exampleExchange).with(routingKeyExampleQueue)
                );
            }

        }


* */



