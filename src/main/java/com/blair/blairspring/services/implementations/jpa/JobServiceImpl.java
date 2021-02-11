package com.blair.blairspring.services.implementations.jpa;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.repositories.ibatisschema.jpa.JobRepository;
import com.blair.blairspring.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("jpa")
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(transactionManager = "ibatisSchemaTransactionManager")
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final EmployeeServiceImpl employeeService;

    public Job findById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new NotFoundException(Job.class, id));
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job create(Job job) {
        try {
            return jobRepository.save(job);
        } catch (DataAccessException dae) {
            log.info("DataAccessException caught: {}", dae.getMessage());
            throw dae;
        }
    }

    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Transactional
    public void transactionalMethod() {
        employeeService.transactionalMethod();
    }

}
