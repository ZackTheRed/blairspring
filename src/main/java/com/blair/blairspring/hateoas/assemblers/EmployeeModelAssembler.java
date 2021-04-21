package com.blair.blairspring.hateoas.assemblers;

import com.blair.blairspring.controllers.EmployeeController;
import com.blair.blairspring.hateoas.entitymodels.EmployeeModel;
import com.blair.blairspring.model.ibatisschema.Employee;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EmployeeModel> {

    @Override
    public EmployeeModel toModel(Employee employee) {
        return new EmployeeModel(employee.getFirstName(), employee.getLastName(), employee.getSalary(), employee.getJob()).add(
                linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }

}
