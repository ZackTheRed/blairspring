package com.blair.blairspring.model.ibatisschema;

import com.blair.blairspring.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "teams")
@Data
@EqualsAndHashCode(exclude = "players", callSuper = false)
@ToString(exclude = "players")
public class Team extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "greek_name")
    private String greekName;

    @OneToMany(mappedBy = "team")
    @JsonIgnoreProperties("team")
    private Set<Player> players;

}
