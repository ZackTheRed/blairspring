package com.blair.blairspring;

import com.blair.blairspring.configurations.TestBeanConfiguration;
import com.blair.blairspring.util.ITestBean;
import com.blair.blairspring.util.TestBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = TestBeanConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class DirtiesContextTests {

    //@Resource(name = "prototypeTestBean")
    @Autowired
    private ITestBean testBean;

    @Test
    void test1() {
        ((TestBean) testBean).getStrings().add("test1");
        log.info("post add testBean in Test1: {}", ((TestBean) testBean).getStrings());
    }

    @Test
    void test2() {
        ((TestBean) testBean).getStrings().add("test2");
        log.info("post add testBean in Test2: {}", ((TestBean) testBean).getStrings());
    }

}
