package com.blair.blairspring.repositories.ibatisschema.jpa;

import com.blair.blairspring.model.ibatisschema.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long> {
}
