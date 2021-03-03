package com.blair.blairspring.hateoas.entitymodels;

import com.blair.blairspring.hateoas.assemblers.JobModelAssembler;
import com.blair.blairspring.model.ibatisschema.Job;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(value = "employee", collectionRelation = "employees")
public class EmployeeModel extends RepresentationModel<EmployeeModel> {

    private final JobModelAssembler jobModelAssembler = new JobModelAssembler();

    private final String firstName;

    private final String lastName;

    private final Long salary;

    private final JobModel job;

    public EmployeeModel(String firstName, String lastName, Long salary, Job job) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.job = jobModelAssembler.toModel(job);
    }
}
