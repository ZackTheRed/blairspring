package com.blair.blairspring.model.ibatisschema;

import com.blair.blairspring.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "nationalities")
@Data
@EqualsAndHashCode(exclude = "players", callSuper = false)
@ToString(exclude = "players")
public class Nationality extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "nationalities", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("nationalities")
    private Set<Player> players;

}
