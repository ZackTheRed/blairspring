package com.blair.blairspring.model.ibatisschema;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("book")
public class Book extends Product {

    @Column(name = "author")
    private String author;

}
