package com.blair.blairspring;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.repositories.ibatisschema.EmployeeRepository;
import com.blair.blairspring.services.implementations.jpa.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    public static final Long EMPLOYEE_ID = 1L;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void findByIdPositive() {
        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
        Employee result = employeeService.findById(EMPLOYEE_ID);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(employee.getId(), result.getId())
        );
        verify(employeeRepository, times(1)).findById(anyLong());
    }
    
}
