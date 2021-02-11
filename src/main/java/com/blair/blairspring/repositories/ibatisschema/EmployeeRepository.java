package com.blair.blairspring.repositories.ibatisschema;

import com.blair.blairspring.model.ibatisschema.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository {

    List<Employee> findAll();

    Page<Employee> findAll(Pageable pageable);

    Optional<Employee> findById(Long id);

    Employee save(Employee employee);

    void deleteById(Long id);

    Optional<Employee> findByFullName(String firstName, String lastName);

    List<Employee> findBySalaryBetween(Long min, Long max);

    List<Employee> findBySalaryLessThanEqualAndLastNameStartingWith(Long max, String startingLetter);

    List<Employee> findByLastNameLike(String like);

    List<Employee> findFirst5BySalaryGreaterThan(Long min);

}
