package com.blair.blairspring.services.implementations.jpa;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.repositories.ibatisschema.JobRepository;
import com.blair.blairspring.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("jpa")
@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job findById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new NotFoundException(Job.class, id));
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Transactional(noRollbackFor = RuntimeException.class)
    public Job create(Job job) {
        Job newJob = jobRepository.save(job);
        // throw new RuntimeException();
        return newJob;
    }

    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }
}
