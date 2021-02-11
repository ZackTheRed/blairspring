package com.blair.blairspring.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Running preHandle of TestInterceptor");
        log.info("request: {}", request);
        log.info("response: {}", response);
        log.info("handler: {}", handler);
        log.info("=======================================");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Running postHandle of TestInterceptor");
        log.info("request: {}", request);
        log.info("response: {}", response);
        log.info("handler: {}", handler);
        log.info("modelAndView: {}", modelAndView);
        log.info("=======================================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Running afterCompletion of TestInterceptor");
        log.info("request: {}", request);
        log.info("response: {}", response);
        log.info("handler: {}", handler);
        log.info("ex: {}", ex);
        log.info("=======================================");
    }
}
