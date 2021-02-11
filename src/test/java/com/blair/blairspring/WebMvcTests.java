package com.blair.blairspring;

import com.blair.blairspring.configurations.RestAuthenticationSuccessHandler;
import com.blair.blairspring.controllers.MyViewController;
import com.blair.blairspring.controllers.TestController;
import com.blair.blairspring.services.UserService;
import com.blair.blairspring.util.ITestBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TestController.class, MyViewController.class})
@Slf4j
public class WebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @MockBean
    private ITestBean testBean;

    @BeforeEach
    void beforeEach() {
        assertAll(
                () -> assertNotNull(mockMvc)
        );
        mockMvc.getDispatcherServlet().getHandlerMappings().stream()
                .filter(mapping -> mapping instanceof RequestMappingHandlerMapping)
                .findFirst()
                .map(mapping -> (RequestMappingHandlerMapping) mapping)
                .get()
                .getHandlerMethods()
                .entrySet()
                .forEach(entry -> log.info("handlerMethod: {}", entry));
        log.info("=======================================");
    }

    @Test
    void testPathVariable() throws Exception {
        mockMvc.perform(get("/test/path-variable/a/b/c").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{var1=a, var2=b, var3=c}")));
    }

}
