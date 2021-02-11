package com.blair.blairspring.filters;


import com.blair.blairspring.util.TestComponent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
@Data
public class TestFilter implements Filter {

    private final TestComponent testComponent;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Initializing TestFilter");
        log.info("filterConfig: {}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("filterChain: {}", filterChain);
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}
