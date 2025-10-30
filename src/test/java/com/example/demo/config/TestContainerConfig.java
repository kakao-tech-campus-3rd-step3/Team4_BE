package com.example.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public class TestContainerConfig {

    private static final MySQLContainer<?> MYSQL_CONTAINER =
        new MySQLContainer<>("mysql:8.0.43")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    static {
        // CI 환경일 때만 실행
        if (isCiEnvironment()) {
            MYSQL_CONTAINER.start();
        }
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        if (isCiEnvironment()) {
            registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
            registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
            registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        }
    }

    private static boolean isCiEnvironment() {
        return "true".equalsIgnoreCase(System.getenv("CI"));
    }
}
