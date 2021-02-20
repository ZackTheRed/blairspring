package com.blair.blairspring.controllers;

import com.blair.blairspring.hateoas.UserModelAssembler;
import com.blair.blairspring.model.userschema.User;
import com.blair.blairspring.model.validation.AdminRegistration;
import com.blair.blairspring.services.UserService;
import com.blair.blairspring.util.jwt.AuthenticationRequest;
import com.blair.blairspring.util.jwt.AuthenticationResponse;
import com.blair.blairspring.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserModelAssembler assembler;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

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
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> registerUser(@Validated(AdminRegistration.class) @RequestBody User user) {
        // log.info("BindingResult: {}", bindingResult);
        log.info("User: {}", user);
        EntityModel<User> entityModel = assembler.toModel(userService.registerUser(user));
        // throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "testing");
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{username}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
