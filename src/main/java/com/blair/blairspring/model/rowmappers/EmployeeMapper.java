package com.blair.blairspring.model.rowmappers;

import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.model.ibatisschema.Job;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong(1));
        employee.setFirstName(resultSet.getString(2));
        employee.setLastName(resultSet.getString(3));
        employee.setSalary(resultSet.getLong(4));

        Job job = new Job();
        job.setId(resultSet.getLong(6));
        job.setDescription(resultSet.getString(7));
        employee.setJob(job);
        return employee;
    }
}
