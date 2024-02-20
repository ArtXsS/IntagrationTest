package ru.netology.intagrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntagrationTestApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private static final GenericContainer<?> myApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    private static final GenericContainer<?> myAppSecond = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    @BeforeAll
    public static void setUp() {
        myApp.start();
        myAppSecond.start();
    }

    @Test
    void contextLoads() {
        Integer firstPort = myApp.getMappedPort(8081);
        Integer secondPort = myAppSecond.getMappedPort(8080);

        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + firstPort, String.class);
        ResponseEntity<String> forEntitySecond = restTemplate.getForEntity("http://localhost:" + secondPort, String.class);

        System.out.println("First: " + forEntity.getBody() + "Port is: " + firstPort);
        System.out.println("Second: " + forEntitySecond.getBody() + "Port is: " + secondPort);
    }

}
