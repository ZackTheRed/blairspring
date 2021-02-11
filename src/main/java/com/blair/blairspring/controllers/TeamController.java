package com.blair.blairspring.controllers;

import com.blair.blairspring.model.ibatisschema.Team;
import com.blair.blairspring.services.implementations.jpa.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("teams")
@Slf4j
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("{id}")
    ResponseEntity<Team> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(teamService.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE, params = "name")
    ResponseEntity<Team> findByName(@RequestParam String name) {
        return ResponseEntity.ok().body(teamService.findByName(name));
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<Team>> findAll() {
        log.info("Executing findAll");
        return ResponseEntity.ok().body(teamService.findAll());
    }
}
