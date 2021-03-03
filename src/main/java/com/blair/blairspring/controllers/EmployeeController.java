package com.blair.blairspring.controllers;

import com.blair.blairspring.hateoas.assemblers.EmployeeModelAssembler;
import com.blair.blairspring.hateoas.entitymodels.EmployeeModel;
import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    @Autowired
    ConfigurableApplicationContext context;

    private final EmployeeService employeeService;
    private final EmployeeModelAssembler assembler;


    @GetMapping("fullName")
    public ResponseEntity<Employee> getByFullName() {
        return ResponseEntity.ok(this.employeeService.findByCompleteName("Ioannis", "Lilimpakis"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CollectionModel<EmployeeModel>> getAllEmployees() {
        log.info("Blair property value: {}", context.getEnvironment().getProperty("blairProperty"));
        return Optional.of(employeeService.findAll())
                .map(assembler::toCollectionModel)
                .map(ResponseEntity::ok)
                .get();
    }

    @GetMapping("paged")
    public ResponseEntity<Page<Employee>> getAllEmployeesPaged() {
        return ResponseEntity.ok(this.employeeService.findAllPaged());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(employeeService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EmployeeModel> create(@RequestBody Employee employee) {
        return Optional.of(employeeService.create(employee))
                .map(assembler::toModel)
                .map(model -> ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

}
