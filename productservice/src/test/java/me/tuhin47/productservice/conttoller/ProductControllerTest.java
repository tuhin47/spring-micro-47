package me.tuhin47.productservice.conttoller;

import io.restassured.http.ContentType;
import me.tuhin47.config.CommonBean;
import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.jwt.TokenProvider;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.config.name=application-test")
@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @ClassRule
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest")).withDatabaseName("integration-tests-db").withUsername("root").withPassword("root");

    @LocalServerPort
    private Integer port;

    @MockBean
    private RedisUserService userDetailsService;

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    @Sql("/test-data.sql")
    public void getAllProductBySearch() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new UserRedis(CommonBean.ADMIN_USER_MAIL, UUID.randomUUID().toString(), "Admin", "random", "local", Set.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR)"), ""));

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