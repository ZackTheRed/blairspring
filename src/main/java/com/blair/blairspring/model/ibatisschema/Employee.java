package com.blair.blairspring.model.ibatisschema;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name="Employee.findByFullName", query = "from Employee e where e.firstName=?1 and e.lastName=?2")
})
@Data
public class Employee extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "salary")
    private Long salary;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getSalary() {
        return salary;
    }

    public Job getJob() {
        return job;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}
