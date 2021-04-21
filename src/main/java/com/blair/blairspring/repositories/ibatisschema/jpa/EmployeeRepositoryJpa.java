package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.repositories.ibatisschema.EmployeeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Repository
public interface EmployeeRepositoryJpa extends JpaRepository<Employee, Long>, EmployeeRepository {

    Optional<Employee> findByFullName(String firstName, String lastName);

    List<Employee> findBySalaryBetween(Long min, Long max);

    List<Employee> findBySalaryLessThanEqualAndLastNameStartingWith(Long max, String startingLetter);

    List<Employee> findByLastNameLike(String like);

    List<Employee> findFirst5BySalaryGreaterThan(Long min);

    @RestResource(path = "bySalary", rel = "bySalary")
    Page findBySalary(@Param("salary") Long salary, Pageable pageable);

}
