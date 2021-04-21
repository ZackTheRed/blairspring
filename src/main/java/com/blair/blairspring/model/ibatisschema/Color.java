package com.blair.blairspring.model.ibatisschema;

import com.blair.blairspring.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Color extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "html_code")
    private String htmlCode;

}
