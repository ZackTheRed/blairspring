package com.blair.blairspring.services.implementations.jpa;

import com.blair.blairspring.model.ibatisschema.Color;
import com.blair.blairspring.repositories.ibatisschema.jpa.ColorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public record ColorService(ColorRepository colorRepository) {

    public Page<Color> getColorsPaged(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }

}
