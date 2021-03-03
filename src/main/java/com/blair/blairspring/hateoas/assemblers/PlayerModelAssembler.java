package com.blair.blairspring.hateoas.assemblers;

import com.blair.blairspring.controllers.PlayerController;
import com.blair.blairspring.model.ibatisschema.Player;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlayerModelAssembler implements RepresentationModelAssembler<Player, EntityModel<Player>> {

    @Override
    public EntityModel<Player> toModel(Player player) {
        return EntityModel.of(player,
                linkTo(methodOn(PlayerController.class).findById(player.getId())).withSelfRel(),
                linkTo(methodOn(PlayerController.class).findAll()).withRel("players"));
    }

    @Override
    public CollectionModel<EntityModel<Player>> toCollectionModel(Iterable<? extends Player> entities) {
        CollectionModel<EntityModel<Player>> playerModels = CollectionModel.of(StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> toModel(entity))
                .collect(Collectors.toList()));

        playerModels.add(linkTo(methodOn(PlayerController.class).findAll()).withSelfRel());
        return playerModels;
    }
}
