package com.blair.blairspring.model.ibatisschema;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pets")
@Data
public class Pet extends Animal {

    private String name;

}
