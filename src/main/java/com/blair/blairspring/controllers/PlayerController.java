package com.blair.blairspring.controllers;

import com.blair.blairspring.hateoas.PlayerModelAssembler;
import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.services.implementations.jpa.PlayerService;
import com.blair.blairspring.services.implementations.jpa.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final TestService testService;
    private final PlayerModelAssembler playerModelAssembler;
    private final PagedResourcesAssembler<Player> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Player>>> findAll() {
        List<Player> players = playerService.findAll();
        return ResponseEntity.ok().body(playerModelAssembler.toCollectionModel(players));
    }

    @GetMapping("paged")
    public ResponseEntity<PagedModel<EntityModel<Player>>> findAll(Pageable pageable) {
        Page<Player> players = playerService.findAllPaged(pageable);
        PagedModel<EntityModel<Player>> collectionModel = pagedResourcesAssembler.toModel(players, playerModelAssembler);
        return ResponseEntity.ok().body(collectionModel);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Player>> findById(@PathVariable Long id) {
        return Optional.of(playerService.findById(id))
                .map(playerModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "age")
    public ResponseEntity<CollectionModel<EntityModel<Player>>> findOlderThan(Pageable pageable, @RequestParam("age") int age) {
        Collection<Player> players = playerService.findOlderThan(age);
        return ResponseEntity.ok().body(playerModelAssembler.toCollectionModel(players));
    }

}
