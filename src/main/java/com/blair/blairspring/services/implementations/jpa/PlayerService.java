package com.blair.blairspring.services.implementations.jpa;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.repositories.ibatisschema.jpa.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class PlayerService {

    private final TransactionTemplate transactionTemplate;

    private final PlayerRepository playerRepository;

    private final TestService testService;

    public PlayerService(@Qualifier("ibatisSchemaTransactionManager") PlatformTransactionManager transactionManager,
                         PlayerRepository playerRepository,
                         TestService testService) {
        this.playerRepository = playerRepository;
        transactionTemplate = new TransactionTemplate(transactionManager);
        // transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        this.testService = testService;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, transactionManager = "ibatisSchemaTransactionManager")
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Page<Player> findAllPaged(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException(Player.class, id));
    }

    public Collection<Player> findOlderThan(int age) {
        /*return playerRepository.findByBirthDateBetween(age, 100);*/
        return null;
    }

    @Transactional(transactionManager = "ibatisSchemaTransactionManager", isolation = Isolation.REPEATABLE_READ)
    public void transactionTest() {
        /*Collection<Player> allPlayers = playerRepository.findByBirthDateBetween(30, 40);
        log.info("all players 1st call: {}", allPlayers);
        log.info("size 1st call: {}", allPlayers.size());
        Player newPlayer = testService.createWithoutException();
        allPlayers = playerRepository.findByBirthDateBetween(30, 40);
        log.info("all players 2nd call: {}", allPlayers);
        log.info("size 2nd call: {}", allPlayers.size());
        playerRepository.deleteById(newPlayer.getId());*/
    }

    @Transactional(transactionManager = "ibatisSchemaTransactionManager", isolation = Isolation.READ_COMMITTED)
    public void repeatableReadTest() {
        List<Player> allPlayersStreamable = playerRepository.findAll();
        List<Player> allPlayers = allPlayersStreamable;
        log.info("1st call, all players size: {}", allPlayers.size());
        Player id4 = allPlayers.stream().filter(player -> player.getId() == 4).findFirst().get();
        String startingName = id4.getFirstName(); // Facundo
        log.info("1st call, id 4 player: {}", id4);
        id4.setFirstName("Ioannis");
        testService.update(id4);
        Player newPlayer = testService.createWithoutException();
        allPlayers = playerRepository.findAll();
        log.info("2nd call, all players size: {}", allPlayers.size());
        id4 = allPlayers.stream().filter(player -> player.getId() == 4).findFirst().get();
        log.info("2nd call, id 4 player: {}", id4);
        id4.setFirstName(startingName);
        playerRepository.save(id4);
        // playerRepository.deleteById(newPlayer.getId());
        newPlayer.setFirstName("Giorgos");
        playerRepository.save(newPlayer);
    }

}
