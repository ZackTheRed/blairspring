package com.blair.blairspring;

import com.blair.blairspring.configurations.ConditionalConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionalTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    void testIfBeanExists() {
        contextRunner.withUserConfiguration(ConditionalConfiguration.class).run(context -> {
            assertThat(context).doesNotHaveBean("viewStatusMessagesServlet");
        });
    }

}
