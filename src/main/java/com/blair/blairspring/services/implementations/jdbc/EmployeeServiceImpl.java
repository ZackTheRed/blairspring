package com.blair.blairspring.services.implementations.jdbc;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.repositories.ibatisschema.EmployeeRepository;
import com.blair.blairspring.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Profile("jdbc")
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        log.info("findById - isActualTransactionActive: {}", TransactionSynchronizationManager.isActualTransactionActive());
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(Employee.class, id));
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findByCompleteName(String firstName, String lastName) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAllPaged() {
        return null;
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Job insertAutomatistis() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findBySalaryBetween(Long min, Long max) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findBySalaryLessThanEqualAndLastNameStartingWith(Long max, String startingLetter) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByLastNameLike(String like) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findFirst5BySalaryGreaterThan(Long min) {
        return null;
    }
}
