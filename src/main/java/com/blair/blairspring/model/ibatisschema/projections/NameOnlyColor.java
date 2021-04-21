package com.blair.blairspring.model.ibatisschema.projections;

import com.blair.blairspring.model.ibatisschema.Color;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name-only", types = Color.class)
public interface NameOnlyColor {

    String getName();

}
