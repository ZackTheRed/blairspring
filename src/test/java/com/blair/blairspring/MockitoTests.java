package com.blair.blairspring;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.repositories.ibatisschema.EmployeeRepository;
import com.blair.blairspring.repositories.ibatisschema.jpa.JobRepository;
import com.blair.blairspring.repositories.ibatisschema.jpa.PlayerRepository;
import com.blair.blairspring.services.implementations.jpa.EmployeeServiceImpl;
import com.blair.blairspring.services.implementations.jpa.JobServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockitoTests {

    public static final Long EMPLOYEE_ID = 1L;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private EmployeeServiceImpl employeeServiceMocked;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private JobServiceImpl jobService;

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

    @Test
    void createJobTest() {
        List<Job> jobs = new ArrayList<>();
        when(jobRepository.save(any(Job.class))).then(i -> i.getArguments()[0]);
        Job newJob = new Job("Actor");
        jobs.add(jobService.create(newJob));
        assertEquals(jobs.size(), 1);
    }

    @Test
    void testPlayerRepository() {
        List<Player> players = List.of(new Player(), new Player());
        PlayerRepository playerRepository = Mockito.mock(PlayerRepository.class);
        when(playerRepository.findAll()).thenReturn(players);
        assertEquals(playerRepository.findAll().size(), 2);
        verify(playerRepository).findAll();
    }
    
}
