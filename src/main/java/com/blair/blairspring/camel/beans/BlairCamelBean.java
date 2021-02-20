package com.blair.blairspring.camel.beans;

import com.blair.blairspring.util.jwt.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlairCamelBean {

    public void throwException() {
        throw new RuntimeException("blairCamelBeanException");
    }

    public void handleJwt(Exchange exchange) {
        AuthenticationResponse authenticationResponse = exchange.getIn().getBody(AuthenticationResponse.class);
        log.info("authenticationResponse object: {}", authenticationResponse);
        log.info("CamelHttpMethod: {}", exchange.getIn().getHeader("CamelHttpMethod", String.class));
    }

}
