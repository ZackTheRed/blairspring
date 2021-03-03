package com.blair.blairspring.controllers;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.hateoas.assemblers.JobModelAssembler;
import com.blair.blairspring.hateoas.entitymodels.JobModel;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.services.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("jobs")
@Slf4j
public class JobController {

    @Autowired
    ConfigurableApplicationContext context;

    private final JobService jobService;
    private final JobModelAssembler assembler;

    @Autowired
    public JobController(JobService jobService, JobModelAssembler assembler) {
        this.jobService = jobService;
        this.assembler = assembler;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CollectionModel<JobModel>> getAllJobs() {
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.getPropertySources().addFirst(new MapPropertySource("My_MAP", Map.of("blairProperty", "awesome")));
        log.info("Added blairProperty");
        return Optional.of(jobService.findAll())
                .map(assembler::toCollectionModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JobModel> getJobByID(@PathVariable Long id) {
        return Optional.of(jobService.findById(id))
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JobModel> createJob(@RequestBody Job job) {
        JobModel jobModel = assembler.toModel(jobService.create(job));

        return ResponseEntity.created(jobModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(jobModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobModel> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job updatedJob = Optional.ofNullable(jobService.findById(id)).map(currentJob -> {
            currentJob.setDescription(job.getDescription());
            return currentJob;
        }).orElseThrow(() -> new NotFoundException(Job.class, id));

        return ResponseEntity.ok(assembler.toModel(updatedJob));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
