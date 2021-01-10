package com.blair.blairspring.model.ibatisschema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
public class Job extends AbstractEntity {

    @Column(name = "dscr", unique = true)
    private String description;

    public Job(String description) {
        this.description = description;
    }

}
