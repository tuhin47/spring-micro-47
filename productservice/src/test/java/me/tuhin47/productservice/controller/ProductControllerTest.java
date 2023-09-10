package me.tuhin47.productservice.controller;

import io.restassured.http.ContentType;
import me.tuhin47.config.CommonBean;
import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.jwt.TokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.config.name=application-test")
@Testcontainers
public class ProductControllerTest {

    // Already defined on application-test
   /* @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
        .withDatabaseName("testdb")
        .withUsername("root")
        .withPassword("root");*/

    @Container
    public static final GenericContainer redisContainer = new GenericContainer(DockerImageName.parse("redis:latest"))
        .withExposedPorts(6379);


    @Autowired
    private TokenProvider tokenProvider;

    @LocalServerPort
    private Integer port;

    @Autowired
    private RedisUserService userDetailsService;

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Test
    @Sql("/test-data.sql")
    public void getAllProductBySearch() {

        userDetailsService.saveLocalUser(new UserRedis(CommonBean.ADMIN_USER_MAIL, UUID.randomUUID().toString(), "Admin", "random", "local", Set.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR)"), ""));

        given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.createToken(true, CommonBean.ADMIN_USER_MAIL))
            .contentType(ContentType.JSON)
            .when()
            .port(port)
            .get("/product/all?size=3&page=0&sort=productName,desc;id,asc&all=false")
            .then()
            .statusCode(200)
            .body("content.size()", equalTo(3))
            .body("totalElements", equalTo(10))
            .body("size", equalTo(3))
            .body("totalPages", equalTo(4))
            .body("first", equalTo(true))
            .body("last", equalTo(false));
    }
}
