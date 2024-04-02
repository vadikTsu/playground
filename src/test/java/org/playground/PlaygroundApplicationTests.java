package org.playground;

import org.junit.jupiter.api.Test;
import org.playground.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class PlaygroundApplicationTests {

    @Autowired
    private LotRepository lotRepository;

    @Test
    void contextLoads() {
        lotRepository.findAll().forEach(System.out::println);
    }

}
