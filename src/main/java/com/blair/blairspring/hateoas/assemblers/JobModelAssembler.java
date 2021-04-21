package com.blair.blairspring.hateoas.assemblers;

import com.blair.blairspring.controllers.JobController;
import com.blair.blairspring.hateoas.entitymodels.JobModel;
import com.blair.blairspring.model.ibatisschema.Job;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JobModelAssembler implements RepresentationModelAssembler<Job, JobModel> {

    @Override
    public JobModel toModel(Job job) {
        return new JobModel(job.getDescription()).add(
                linkTo(methodOn(JobController.class).getJobByID(job.getId())).withSelfRel()
                        .andAffordance(afford(methodOn(JobController.class).updateJob(job.getId(), null)))
                        .andAffordance(afford(methodOn(JobController.class).deleteJob(job.getId()))),
                linkTo(methodOn(JobController.class).getAllJobs()).withRel("jobs"));
    }

}