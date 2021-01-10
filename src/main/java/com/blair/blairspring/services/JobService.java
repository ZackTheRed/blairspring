package com.blair.blairspring.services;

import com.blair.blairspring.model.ibatisschema.Job;

import java.util.List;

public interface JobService {

    Job findById(Long id);

    List<Job> findAll();

    Job create(Job job);

    void deleteById(Long id);

}
