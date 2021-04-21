package com.blair.blairspring.model.ibatisschema.projections;

import com.blair.blairspring.model.ibatisschema.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullName", types = Employee.class)
public interface FullNameEmployee {

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    int getSalary();
}
