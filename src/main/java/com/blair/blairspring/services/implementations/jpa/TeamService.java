package com.blair.blairspring.services.implementations.jpa;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.model.ibatisschema.Team;
import com.blair.blairspring.repositories.ibatisschema.jpa.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team findById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new NotFoundException(Team.class, id));
    }

    public Team findByName(String name) {
        return teamRepository.findByName(name).orElseThrow(() -> new NotFoundException(Team.class, name));
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Transactional
    public Team create(Team team) {
        return teamRepository.save(team);
    }

}
