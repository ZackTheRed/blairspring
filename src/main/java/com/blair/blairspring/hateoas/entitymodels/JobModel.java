package com.blair.blairspring.hateoas.entitymodels;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@RequiredArgsConstructor
@Relation(value="job", collectionRelation="jobs")
public class JobModel extends RepresentationModel<JobModel> {

    private final String description;

}
