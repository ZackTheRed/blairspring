package com.blair.blairspring;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles({"jpa", "ibatis"})
public class EmployeeServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceTests.class);

    @Autowired
    EmployeeService employeeService;

    @Test
    void findBetween() {
        List<Employee> employeesBetween = employeeService.findBySalaryBetween(0L, 2000L);
        logger.info("Employee list size: {}", employeesBetween.size());
        assertTrue(employeesBetween.size() > 0);
    }

    @Test
    void findBySalaryLessThanEqualAndLastNameStartingWith() {
        List<Employee> employees = employeeService.findBySalaryLessThanEqualAndLastNameStartingWith(10000L, "K");
        employees.forEach(e -> logger.info("Employee: {}", e));
        assertTrue(employees.size() > 0);
    }

    @Test
    void findByLastNameLike() {
        List<Employee> employees = employeeService.findByLastNameLike("K%");
        employees.forEach(e -> logger.info("Employee: {}", e));
        assertTrue(employees.size() > 0);
    }

    @Test
    void findFirst5BySalaryGreaterThan() {
        List<Employee> employees = employeeService.findFirst5BySalaryGreaterThan(1000L);
        employees.forEach(e -> logger.info("Employee: {}", e));
        assertTrue(employees.size() <= 5);
    }

}
