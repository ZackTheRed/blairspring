package com.blair.blairspring.services.implementations.jdbc;

import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.repositories.ibatisschema.jdbc.JobRepositoryJdbc;
import com.blair.blairspring.services.EmployeeService;
import com.blair.blairspring.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("jdbc")
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepositoryJdbc jobRepositoryJdbc;
    private final EmployeeService employeeService;

    @Override
    public Job findById(Long id) {
        return jobRepositoryJdbc.findById(id);
    }

    @Override
    public List<Job> findAll() {
        return jobRepositoryJdbc.findAll();
    }

    @Override
    public Job create(Job job) {
        try {
            return jobRepositoryJdbc.create(job);
        } catch (DataAccessException dae) {
            log.info("Exception caught: {}", dae.getLocalizedMessage());
            throw dae;
        }
    }

    @Override
    public void deleteById(Long id) {
        jobRepositoryJdbc.deleteById(id);
    }

}
