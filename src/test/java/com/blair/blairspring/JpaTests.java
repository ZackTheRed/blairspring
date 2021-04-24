package com.blair.blairspring;

import com.blair.blairspring.exceptions.NotFoundException;
import com.blair.blairspring.model.ibatisschema.Employee;
import com.blair.blairspring.model.ibatisschema.Player;
import com.blair.blairspring.model.ibatisschema.Team;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUtil;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"jpatests", "jpa"})
@SqlGroup({
        @Sql(scripts = {"classpath:sql/nationalities-h2.sql"}),
        @Sql(scripts = {"classpath:sql/teams-h2.sql"}),
        @Sql(scripts = {"classpath:sql/players-h2.sql"}),
        @Sql(scripts = {"classpath:sql/jobs-h2.sql"}),
        @Sql(scripts = {"classpath:sql/employees-h2.sql"})
})
@Slf4j
public class JpaTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    private PersistenceUtil persistenceUtil;

    @BeforeEach
    void checkIfNull() {
        assertAll(
                () -> assertNotNull(playerRepository),
                () -> assertNotNull(entityManager)
        );
        log.info("entityManager: {}", entityManager);

        persistenceUtil = Persistence.getPersistenceUtil();

        /*Nationality greek = new Nationality();
        greek.setName("Greek");
        entityManager.persist(greek);

        Team olympiakos = new Team();
        olympiakos.setGreekName("Όλυμπιακός");
        olympiakos.setName("Olympiakos");

        Player lilimpakis = new Player();
        lilimpakis.setBirthDate(LocalDate.parse("1984-12-13"));
        lilimpakis.setFirstName("Ioannis");
        lilimpakis.setLastName("Lilimpakis");
        lilimpakis.setNationalities(Set.of(greek));
        olympiakos.setPlayers(Set.of(lilimpakis));

        entityManager.persist(olympiakos);*/
    }

    @Test
    void lilimpakisPlayerTest() {
        assertTrue(playerRepository.findAll().size() > 0);

        Player player = playerRepository.findByFirstNameAndLastName("Ioannis", "Lilimpakis").stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Player.class, null));

        log.info("Player found: {}", player);
        assertEquals(player.getLastName(), "Lilimpakis");
        entityManager.detach(player);
        assertFalse(entityManager.contains(player));
        assertThrows(IllegalArgumentException.class, () -> entityManager.remove(player));
        Player reattachedPlayer = entityManager.merge(player);
        assertFalse(entityManager.contains(player));
        assertTrue(entityManager.contains(reattachedPlayer));
        assertThrows(IllegalArgumentException.class, () -> entityManager.remove(player));
        assertDoesNotThrow(() -> playerRepository.delete(player));
        entityManager.clear();
        assertFalse(entityManager.contains(reattachedPlayer));
        entityManager.close();
        reattachedPlayer = entityManager.merge(player);
        entityManager.persist(reattachedPlayer);
    }

    @RepeatedTest(2)
    @Order(1)
    void repeatedTestTest() {
        Player newPlayer = new Player();
        newPlayer.setFirstName("Ioannis");
        newPlayer.setLastName("Lilimpakis");
        newPlayer.setTeam(teamRepository.findById(1L).orElse(null));
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
        players.forEach(player -> log.info(player.getLastName()));
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

    @Test
    void eagerLazyTest() {
        Employee employee = entityManager.find(Employee.class, 3L);
        log.info("Employee last name: {}", employee.getLastName());

        assertTrue(persistenceUtil.isLoaded(employee.getJob()));

        Long id = 1L;
        Team team = teamRepository.findById(id).orElseThrow(() -> new NotFoundException(Team.class, id));
        log.info("team name: {}", team.getName());

        assertFalse(persistenceUtil.isLoaded(team.getPlayers()));

        Player player = team.getPlayers().stream().findFirst().orElseThrow(() -> new NotFoundException(Player.class));
        log.info("player name: {}", player.getLastName());
        assertTrue(persistenceUtil.isLoaded(team.getPlayers()));

    }

}
