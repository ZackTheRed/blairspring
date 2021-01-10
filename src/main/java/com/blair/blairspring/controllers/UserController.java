package com.blair.blairspring.controllers;

import com.blair.blairspring.hateoas.UserModelAssembler;
import com.blair.blairspring.model.userschema.User;
import com.blair.blairspring.repositories.userschema.RoleRepository;
import com.blair.blairspring.repositories.userschema.UserRepository;
import com.blair.blairspring.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserModelAssembler assembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<User>> getAllUsers() {
        List<EntityModel<User>> allUsers = userService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(allUsers, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<User> getUserByUsername(@PathVariable String username) {
        User foundUser = userService.findByUsername(username);
        return assembler.toModel(foundUser);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        EntityModel<User> entityModel = assembler.toModel(userService.registerUser(user));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{username}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
