package com.blair.blairspring.services.implementations.jpa;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.repositories.ibatisschema.EmployeeRepository;
import com.blair.blairspring.repositories.ibatisschema.jpa.JobRepository;
import com.blair.blairspring.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Profile("jpa")
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final PlatformTransactionManager manager;
    private final TransactionTemplate transactionTemplate;
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;

    public EmployeeServiceImpl(PlatformTransactionManager transactionManager,
                               PlatformTransactionManager platformTransactionManager,
                               EmployeeRepository employeeRepository,
                               JobRepository jobRepository) {
        manager = transactionManager;
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setReadOnly(true);
        transactionTemplate.setTimeout(1);
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee findByCompleteName(String firstName, String lastName) {
        return employeeRepository.findByFullName(firstName, lastName).orElse(null);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Page<Employee> findAllPaged() {
        return employeeRepository.findAll(PageRequest.of(0, 2));
    }

    /*@Override
    public Employee create(Employee employee) {
       return transactionTemplate.execute(status -> {
           try {
               Thread.sleep(2000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return employeeRepository.save(employee);
       });
    }*/

    @Override
    public Employee create(Employee employee) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("createEmployeeTransaction");
        definition.setReadOnly(true);
        TransactionStatus status = manager.getTransaction(definition);
        Employee newEmployee = employeeRepository.save(employee);
        manager.commit(status);
        // manager.rollback(status);
        return newEmployee;
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = UnexpectedRollbackException.class)
    public Job insertAutomatistis() {
        Job newJob = new Job();
        newJob.setDescription("Automatistis");
        jobRepository.save(newJob);
        throw new UnexpectedRollbackException("Something");
        // return newJob;
    }

    @Override
    public List<Employee> findBySalaryBetween(Long min, Long max) {
        return employeeRepository.findBySalaryBetween(min, max);
    }

    @Override
    public List<Employee> findBySalaryLessThanEqualAndLastNameStartingWith(Long max, String startingLetter) {
        return employeeRepository.findBySalaryLessThanEqualAndLastNameStartingWith(max, startingLetter);
    }

    @Override
    public List<Employee> findByLastNameLike(String like) {
        return employeeRepository.findByLastNameLike(like);
    }

    @Override
    public List<Employee> findFirst5BySalaryGreaterThan(Long min) {
        return employeeRepository.findFirst5BySalaryGreaterThan(min);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void transactionalMethod() {
        log.info("Running EmployeeServiceImpl.transactionalMethod");
    }

}
