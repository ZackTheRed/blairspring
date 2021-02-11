package com.blair.blairspring.repositories.ibatisschema.fragment;

import com.blair.blairspring.model.ibatisschema.Player;

import java.util.Collection;

public interface PlayerRepositoryCustom {

    Collection<Player> findByBirthDateBetween(int minAge, int maxAge);

}
