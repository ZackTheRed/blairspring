package com.blair.blairspring.services.implementations.jdbc;

import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.repositories.jdbc.JobRepositoryJdbc;
import com.blair.blairspring.services.EmployeeService;
import com.blair.blairspring.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("jdbc")
@Service
@RequiredArgsConstructor
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
        Job newJob = jobRepositoryJdbc.create(job);
        // employeeService.findAll();
        return newJob;
    }

    @Override
    public void deleteById(Long id) {
        jobRepositoryJdbc.deleteById(id);
    }
}
