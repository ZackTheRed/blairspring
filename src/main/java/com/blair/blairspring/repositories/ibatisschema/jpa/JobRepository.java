package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.ibatisschema.Job;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Profile("jpa")
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Optional<Job> findById(Long id);

    @Query("SELECT j FROM Job j WHERE j.description = ?1")
    Job findJobByDescriptionIndexed(String description);

    @Query("SELECT j FROM Job j WHERE j.description = :description")
    Job findJobByDescriptionParam(@Param("description") String description);

    @Query("SELECT j FROM Job j")
    Collection<Job> findAllJobs(Sort sort);

    @Query(value = "SELECT * FROM jobs", countName = "SELECT count(*) FROM jobs", nativeQuery = true)
    Page<Job> findAllJobsPaged(Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.description IN :descriptions")
    Collection<Job> findAllJobsIn(Collection<String> descriptions);

    @Modifying
    @Query("UPDATE Job j SET j.description = :newDescription WHERE j.description = :oldDescription")
    int updateJobDescription(String newDescription, String oldDescription);

}
