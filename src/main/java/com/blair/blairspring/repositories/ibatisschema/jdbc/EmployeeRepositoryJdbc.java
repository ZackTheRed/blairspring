package com.blair.blairspring.repositories.ibatisschema.jdbc;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.model.rowmappers.EmployeeMapper;
import com.blair.blairspring.repositories.ibatisschema.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Profile("jdbc")
@Repository
public class EmployeeRepositoryJdbc implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public EmployeeRepositoryJdbc(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("employees").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees e INNER JOIN jobs j on j.id = e.job_id", new EmployeeMapper());
    }

    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT e.id, " +
                        "e.first_name, " +
                        "e.last_name, " +
                        "e.salary, " +
                        "e.job_id, " +
                        "j.id, " +
                        "j.dscr FROM employees e " +
                        "INNER JOIN jobs j on j.id = e.job_id " +
                        "WHERE e.id = ?",
                new EmployeeMapper(),
                new Object[]{id}));
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Employee save(Employee employee) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", employee.getFirstName());
        parameters.put("last_name", employee.getLastName());
        parameters.put("salary", employee.getSalary());
        parameters.put("job_id", employee.getJob().getId());
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        return findById(id.longValue()).orElseThrow(() -> new NotFoundException(Employee.class, id));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        Object[] args = new Object[] {id};
        int rowsAffected = jdbcTemplate.update(sql, args);
        if (rowsAffected == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
    }

    @Override
    public Optional<Employee> findByFullName(String firstName, String lastName) {
        return Optional.empty();
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
