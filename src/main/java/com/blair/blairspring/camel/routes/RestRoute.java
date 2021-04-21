package com.blair.blairspring.camel.routes;

import com.blair.blairspring.util.jwt.AuthenticationRequest;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().host("localhost:8081").bindingMode(RestBindingMode.json);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("admin", "admin");

        from("direct:rest-route")
                .setBody(constant(authenticationRequest))
                .log(LoggingLevel.INFO, "Body before rest call: ${body}")
                .to("rest:post:users/authenticate")
                .log(LoggingLevel.INFO, "Body after rest call: ${body}")
                // .setHeader("CamelJacksonUnmarshalType", () -> AuthenticationResponse.class)
                // .unmarshal().json(AuthenticationResponse.class)
                // .bean("blairCamelBean", "handleJwt")
                .setHeader("Authorization", constant("Bearer").append(" ").append(jsonpath("$.jwt")))
                .to("log:com.blair.blairspring.camel.routes?marker=restRouteLog&level=INFO&showAll=true&multiline=true&maxChars=99999999")
                .to("rest:get:jobs")
                .unmarshal().json(EntityModel.class)
                .to("log:com.blair.blairspring.camel.routes?marker=restRouteLog&level=INFO&showAll=true&multiline=true&maxChars=99999999");
    }

}
