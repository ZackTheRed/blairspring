package com.blair.blairspring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyApplicationRunner implements ApplicationRunner {

    private final CamelContext camelContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        args.getNonOptionArgs().forEach(nonOptionArg -> log.info("NonOptionArg: {}", nonOptionArg));
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        Exchange exchange = new DefaultExchange(camelContext);
        log.info("Camel body after route1: {}", producerTemplate.send("direct:start", exchange).getIn().getBody());
    }
}
