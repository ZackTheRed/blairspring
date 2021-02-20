package com.blair.blairspring.camel.processors;

import com.blair.blairspring.util.RandomClass;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlairProcessor implements Processor {

    private final RandomClass randomClass;

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.setProperty("BlairProcessorProperty", randomClass.getRandomDouble());
    }
}
