package com.blair.blairspring.controllers;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("fullName")
    public ResponseEntity<Employee> getByFullname() {
        return ResponseEntity.ok(this.employeeService.findByCompleteName("Ioannis", "Lilimpakis"));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.findAll());
    }

    @GetMapping("paged")
    public ResponseEntity<Page<Employee>> getAllEmployeesPaged() {
        return ResponseEntity.ok(this.employeeService.findAllPaged());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable Long id) {
        return ResponseEntity.ok(this.employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity(employeeService.create(employee), HttpStatus.CREATED);
    }

}
