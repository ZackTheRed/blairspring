package com.blair.blairspring.services.implementations.jpa;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.repositories.ibatisschema.jpa.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    private final PlayerRepository playerRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Player createWithoutException() {
        Player player = new Player();
        player.setFirstName("Ioannis");
        player.setLastName("Lilimpakis");
        player.setBirthDate(LocalDate.of(1984, 12, 13));
        return playerRepository.save(player);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Player update(Player player) {
        return playerRepository.save(player);
    }
    
}
