package com.blair.blairspring.model.ibatisschema.projections;

import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.model.ibatisschema.Team;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "inlinePlayers", types = Team.class)
public interface InlinePlayers {

    String getName();

    String getGreekName();

    Set<Player> getPlayers();

}
