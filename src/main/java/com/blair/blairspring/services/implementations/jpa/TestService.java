package com.blair.blairspring.services.implementations.jpa;

import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.repositories.ibatisschema.jpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final PlayerRepository playerRepository;
    /*private final PlayerService playerService;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, transactionManager = "ibatisSchemaTransactionManager")
    public Player create() {
        Player player = new Player();
        player.setFirstName("Ioannis");
        player.setLastName("Lilimpakis");
        playerRepository.save(player);
        Collection<Player> allPlayers = playerService.findAll();
        log.info("allPlayers: {}", allPlayers);
        throw new RuntimeException();
    }*/

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
