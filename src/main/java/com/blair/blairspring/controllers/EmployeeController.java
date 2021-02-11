package com.blair.blairspring.controllers;

import com.blair.blairspring.hateoas.EmployeeModelAssembler;
import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<Employee> getByFullname() {
        return ResponseEntity.ok(this.employeeService.findByCompleteName("Ioannis", "Lilimpakis"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Employee>> getAllEmployees() {
        log.info("Blair property value: {}", context.getEnvironment().getProperty("blairProperty"));
        List<EntityModel<Employee>> allEmployees = employeeService.findAll().stream()
                .map((assembler::toModel))
                .collect(Collectors.toList());

        return CollectionModel.of(allEmployees, linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }

    @GetMapping("paged")
    public ResponseEntity<Page<Employee>> getAllEmployeesPaged() {
        return ResponseEntity.ok(this.employeeService.findAllPaged());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
        return assembler.toModel(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity(employeeService.create(employee), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

}
