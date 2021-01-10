package com.blair.blairspring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class TestBean implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(TestBean.class);

    public void myInit() {
        logger.info("Running myInit method of TestBean");
    }

    public void myDestroy() {
        logger.info("Running myDestroy method of TestBean");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("Running destroy method of TestBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Running afterPropertiesSet method of TestBean");
    }

    public void testMethod() {
        logger.info("Running testMethod method of TestBean");
    }


}
