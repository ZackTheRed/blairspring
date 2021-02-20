package com.blair.blairspring.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SecondRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:second-route")
                .setProperty("secondRouteProperty", simple("o kipos einai anthiros"))
                .process("blairProcessor")
                // .bean("blairCamelBean", "throwException")
                .to("log:com.blair.blairspring.camel.routes?marker=secondRouteLog&level=INFO&showAll=true&multiline=true&maxChars=99999999")
                .transform().simple("Second route body")
                .to("direct:rest-route");
    }

}
