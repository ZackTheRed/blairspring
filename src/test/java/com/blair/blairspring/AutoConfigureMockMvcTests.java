package com.blair.blairspring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AutoConfigureMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void testThatMockMvcIsNotNull() {
        assertNotNull(mockMvc);
    }

    @Test
    void testMockMvc() throws Exception {
        mockMvc.perform(get("/test/testBeanTest")).andDo(print()).andExpect(status().isOk());
    }

}
