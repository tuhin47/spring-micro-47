package me.tuhin47.productservice.controller;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import me.tuhin47.config.CommonBean;
import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
public class AbstractControllerTest {

    protected static final int REDIS_PORT = 6379;
    protected static final String USER_MAIL = "user@gmail.com";
    protected static final String MODERATOR_MAIL = "moderator@gmail.com";

    @Container
    public static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(REDIS_PORT);

    @Autowired
    protected TokenProvider tokenProvider;

    @LocalServerPort
    protected Integer port;

    @Autowired
    protected RedisUserService userDetailsService;

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(REDIS_PORT));
    }

    @BeforeEach
    public void setUPData() {
        userDetailsService.saveLocalUser(new UserRedis(CommonBean.ADMIN_USER_MAIL, UUID.randomUUID().toString(), "Admin", "random", "local", Set.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"), ""));
        userDetailsService.saveLocalUser(new UserRedis(MODERATOR_MAIL, UUID.randomUUID().toString(), "Moderator", "random", "local", Set.of("ROLE_USER", "ROLE_MODERATOR"), ""));
        userDetailsService.saveLocalUser(new UserRedis(USER_MAIL, UUID.randomUUID().toString(), "User", "random", "local", Set.of("ROLE_USER"), ""));
    }

    protected RequestSpecification getBaseRequestWithHeader(String mail) {
        return given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.createToken(true, mail))
            .contentType(ContentType.JSON)
            .when()
            .port(port);
    }
}
