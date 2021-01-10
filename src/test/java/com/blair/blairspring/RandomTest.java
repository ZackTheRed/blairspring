package com.blair.blairspring;

import com.blair.blairspring.aop.subclasses.BasketballPlayer;
import com.blair.blairspring.aop.subclasses.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomTest {

    @Autowired
    private Player player;

    @Test
    void testBreath() {
        player.play();
    }

}
