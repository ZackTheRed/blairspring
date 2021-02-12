package com.blair.blairspring;

import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.repositories.ibatisschema.jpa.PlayerRepository;
import com.blair.blairspring.repositories.ibatisschema.jpa.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test", "jpa"})
@SqlGroup({
        @Sql(scripts = {"classpath:sql/nationalities-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sql/teams-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sql/players-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@Slf4j
public class JpaTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void checkIfNull() {
        assertAll(
                () -> assertNotNull(playerRepository),
                () -> assertNotNull(entityManager)
        );
        log.info("entityManager: {}", entityManager);
    }

    @Test
    void someTest() {
        assertTrue(playerRepository.findAll().size() > 0);
    }

    @RepeatedTest(2)
    @Order(1)
    void repeatedTestTest() {
        Player newPlayer = new Player();
        newPlayer.setFirstName("Ioannis");
        newPlayer.setLastName("Lilimpakis");
        newPlayer.setTeam(teamRepository.findById(1L).get());
        newPlayer.setBirthDate(LocalDate.now());
        playerRepository.save(newPlayer);
    }

    @Test
    @Order(2)
    @Transactional(readOnly = true)
    void streamTest() {
        List<Player> players = playerRepository.findAll();
        players.forEach(player -> log.info(player.getLastName()));
        Player newPlayer = new Player();
        playerRepository.save(newPlayer);
    }

    @Test
    @Order(3)
    void sortingTest() {
        Sort.TypedSort<Player> playerTypedSort = Sort.sort(Player.class);
        Sort sort = playerTypedSort.by(Player::getLastName).descending();
        // List<Player> players = playerRepository.findAll(Sort.by("lastName").descending());
        List<Player> players = playerRepository.findAll(sort);
        players.stream().forEach(player -> log.info(player.getLastName()));
    }

    @Test
    @Order(4)
    void pagingTest() {
        Slice<Player> players = playerRepository.findAll(PageRequest.of(0, 2));
        log.info("Slice: {}", players);
        players.stream().forEach(player -> log.info(player.getLastName()));
        /*
        players = playerRepository.findAll(PageRequest.of(1, 2));
        players.stream().forEach(player -> log.info(player.getLastName()));
        */
    }

}
