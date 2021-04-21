package com.blair.blairspring.model.ibatisschema;

import com.blair.blairspring.model.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "animals")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Animal extends AbstractEntity {

    private String type;

}
