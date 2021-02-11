package com.blair.blairspring.controllers;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.hateoas.JobModelAssembler;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.services.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public CollectionModel<EntityModel<Job>> getAllJobs() {
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.getPropertySources().addFirst(new MapPropertySource("My_MAP", Map.of("blairProperty", "awesome")));
        log.info("Added blairProperty");
        List<EntityModel<Job>> allJobs = jobService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(allJobs, linkTo(methodOn(JobController.class).getAllJobs()).withSelfRel());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Job> getJobByID(@PathVariable Long id) {
        Job foundJob = jobService.findById(id);
        return assembler.toModel(foundJob);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Job>> createJob(@RequestBody Job job) {
        EntityModel<Job> entityModel = assembler.toModel(jobService.create(job));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public EntityModel<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job updatedJob = Optional.ofNullable(jobService.findById(id)).map(currentJob -> {
            currentJob.setDescription(job.getDescription());
            return currentJob;
        }).orElseThrow(() -> new NotFoundException(Job.class, id));

        return assembler.toModel(updatedJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
