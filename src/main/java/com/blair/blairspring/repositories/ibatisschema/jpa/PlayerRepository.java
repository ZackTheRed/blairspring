package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.repositories.ibatisschema.fragment.PlayerRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public interface PlayerRepository extends CrudRepository<Player, Long> //, PlayerRepositoryCustom {
{
    List<Player> findAll();
    Page<Player> findAll(Pageable pageable);
    List<Player> findAll(Sort sort);
}
