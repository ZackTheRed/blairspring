package com.blair.blairspring.model.userschema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("roles")
    private Set<User> users;

}
