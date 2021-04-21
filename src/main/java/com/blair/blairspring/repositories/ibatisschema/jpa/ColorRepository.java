package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.ibatisschema.Color;
import com.blair.blairspring.model.ibatisschema.projections.NameOnlyColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(path = "my-colors", excerptProjection = NameOnlyColor.class)
public interface ColorRepository extends JpaRepository<Color, Long> {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    Page<Color> findAll(Pageable pageable);

}
