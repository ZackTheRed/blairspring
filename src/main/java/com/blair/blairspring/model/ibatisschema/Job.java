package com.blair.blairspring.model.ibatisschema;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
public class Job extends AbstractEntity {

    @Column(name = "dscr", unique = true, nullable = false)
    @NotBlank(message = "description is mandatory.")
    private String description;

    public Job(String description) {
        this.description = description;
    }

}
