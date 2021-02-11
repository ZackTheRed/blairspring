package com.blair.blairspring.repositories.ibatisschema.fragment;

import com.blair.blairspring.model.ibatisschema.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {

    @PersistenceContext(unitName = "ibatisSchemaEntityManager")
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Player> findByBirthDateBetween(int minAge, int maxAge) {
        LocalDate minimumDate = LocalDate.now().minus(minAge, ChronoUnit.YEARS);
        LocalDate maximumDate = LocalDate.now().minus(maxAge, ChronoUnit.YEARS);
        return entityManager.createQuery("SELECT p FROM Player p WHERE p.birthDate BETWEEN ?1 AND ?2")
                .setParameter(1, maximumDate)
                .setParameter(2, minimumDate)
                .getResultList();
    }
}
