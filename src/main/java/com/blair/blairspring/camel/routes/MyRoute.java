package com.blair.blairspring.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .transacted()
                .transform().simple("HOOOOP")
                .to("direct:second-route");
    }

}