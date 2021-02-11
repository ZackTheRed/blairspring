package com.blair.blairspring.util.proxy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("proxy-test")
@EnableAspectJAutoProxy
@ComponentScan
public class ProxiedAOPConfiguration {
}
