package com.blair.blairspring;

import com.blair.blairspring.services.implementations.jpa.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SpringTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void beforeEach() {
        assertNotNull(employeeService);
    }

    @Test
    public void nothing() {
        assertTrue(1 == 1);
    }

}
