package com.blair.blairspring.model.ibatisschema;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("pen")
public class Pen extends Product {

    @Column(name = "color")
    private String color;

}
