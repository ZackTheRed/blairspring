package com.blair.blairspring.repositories.ibatisschema;

import com.blair.blairspring.model.ibatisschema.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Optional<Job> findById(Long id);

}
