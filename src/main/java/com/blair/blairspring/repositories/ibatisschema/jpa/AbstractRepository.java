package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.AbstractEntity;

import java.util.Optional;

public interface AbstractRepository <T extends AbstractEntity> {

    void save(T entity);

    void delete(T entity);

    T update(T entity);

    int deleteById(Long entityId);

    Optional<T> findById(Long entityId);

}
