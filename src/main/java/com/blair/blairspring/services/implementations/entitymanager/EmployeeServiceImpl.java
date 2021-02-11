package com.blair.blairspring.services.implementations.entitymanager;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.model.ibatisschema.Job;
import com.blair.blairspring.services.EmployeeService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("entitymanager")
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Employee findByCompleteName(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public Page<Employee> findAllPaged() {
        return null;
    }

    @Override
    public Employee create(Employee employee) {
        return null;
    }

    @Override
    public Job insertAutomatistis() {
        return null;
    }

    @Override
    public List<Employee> findBySalaryBetween(Long min, Long max) {
        return null;
    }

    @Override
    public List<Employee> findBySalaryLessThanEqualAndLastNameStartingWith(Long max, String startingLetter) {
        return null;
    }

    @Override
    public List<Employee> findByLastNameLike(String like) {
        return null;
    }

    @Override
    public List<Employee> findFirst5BySalaryGreaterThan(Long min) {
        return null;
    }
}
