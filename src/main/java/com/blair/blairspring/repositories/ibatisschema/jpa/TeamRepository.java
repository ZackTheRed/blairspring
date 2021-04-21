package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.ibatisschema.Team;
import com.blair.blairspring.model.ibatisschema.projections.InlinePlayers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = InlinePlayers.class)
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

}
