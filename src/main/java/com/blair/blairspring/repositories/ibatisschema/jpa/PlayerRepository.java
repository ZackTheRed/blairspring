package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.ibatisschema.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> //, PlayerRepositoryCustom {
{

    Set<Player> findByFirstNameAndLastName(String firstName, String lastName);

}
