package com.blair.blairspring.controllers;

import com.blair.blairspring.model.ibatisschema.Color;
import com.blair.blairspring.services.implementations.jpa.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RepositoryRestController
@RequiredArgsConstructor
public class CustomColorController {

    private final ColorService colorService;

    @GetMapping("/colors/paged")
    public ResponseEntity<Color> getMostRecentColor() {
        return ResponseEntity.ok(colorService
                .getColorsPaged(PageRequest.of(0, 1, Sort.by("createdAt").descending()))
                .getContent()
                .get(0));
    }

}
