package com.blair.blairspring.services.implementations.entitymanager;

import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.services.JobService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Profile("entitymanager")
@Service
public class JobServiceImpl implements JobService {

    @PersistenceContext(unitName = "ibatisSchemaEntityManager")
    private EntityManager entityManager;

    @Override
    public Job findById(Long id) {
        return null;
    }

    @Override
    public List<Job> findAll() {
        return entityManager.createQuery("from Job", Job.class).getResultList();
    }

    @Override
    public Job create(Job job) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

}
