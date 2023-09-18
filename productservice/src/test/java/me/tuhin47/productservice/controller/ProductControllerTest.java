package me.tuhin47.productservice.controller;

import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.config.CommonBean;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class ProductControllerTest extends AbstractControllerTest {


    @ParameterizedTest
    @ValueSource(strings = {CommonBean.ADMIN_USER_MAIL, USER_MAIL, MODERATOR_MAIL})
    @Order(1)
    public void getAllProductBySearch(String mail) {
        ValidatableResponse body = getBaseRequestWithHeader(mail)
            .get("/product/all?size=3&page=0&sort=productName,desc;id,asc&all=false")
            .then()
            .log()
            .body();

        if (!mail.equals(CommonBean.ADMIN_USER_MAIL)) {
            body.statusCode(HttpStatus.FORBIDDEN.value());
            return;
        }

        body
            .statusCode(HttpStatus.OK.value())
            .body("content.size()", equalTo(3))
            .body("size", equalTo(3))
            .body("pageable.pageSize", equalTo(3))
            .body("totalElements", equalTo(10))
            .body("numberOfElements", equalTo(3))
            .body("totalPages", equalTo(4))
            .body("first", equalTo(true))
            .body("last", equalTo(false));
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {CommonBean.ADMIN_USER_MAIL, USER_MAIL, MODERATOR_MAIL})
    void getProductById(String mail) {
        getBaseRequestWithHeader(mail)
            .get("/product/" + RandomUtils.nextLong(1, 10))
            .then()
            .log()
            .body()
            .statusCode(HttpStatus.OK.value());

        getBaseRequestWithHeader(CommonBean.ADMIN_USER_MAIL)
            .get("/product/" + UUID.randomUUID())
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());

    }


    @Order(3)
    @ParameterizedTest
    @ValueSource(strings = {CommonBean.ADMIN_USER_MAIL, USER_MAIL, MODERATOR_MAIL})
    void getProductTypeCount(String mail) {

        ValidatableResponse body = getBaseRequestWithHeader(mail)
            .get("/product/report")
            .then()
            .log()
            .body();

        if (!mail.equals(CommonBean.ADMIN_USER_MAIL)) {
            body.statusCode(HttpStatus.FORBIDDEN.value());
            return;
        }

        body
            .statusCode(200)
            .body("[0].productType", equalTo("DRESS"))
            .body("[0].count", equalTo(2));

    }


    @Order(5)
    @ParameterizedTest
    @ValueSource(strings = {CommonBean.ADMIN_USER_MAIL, USER_MAIL, MODERATOR_MAIL})
    void addProduct(String mail) {
        var product = """
            {
              "productName": "Add Product",
              "productType": "ELECTRONIC",
              "price": 5.00,
              "quantity": 10
            }""";

        getBaseRequestWithHeader(mail)
            .and()
            .body(product)
            .post("/product")
            .then()
            .log()
            .body()
            .statusCode(201).extract().response();

    }



    @Order(6)
    @ParameterizedTest
    @ValueSource(strings = {CommonBean.ADMIN_USER_MAIL, USER_MAIL, MODERATOR_MAIL})
    void deleteProductById(String mail) {

        ValidatableResponse body = getBaseRequestWithHeader(mail)
            .delete("/product/" + RandomUtils.nextLong(1, 10))
            .then()
            .log()
            .body();

        if (!mail.equals(CommonBean.ADMIN_USER_MAIL)) {
            body.statusCode(HttpStatus.FORBIDDEN.value());
            return;
        }

        body.statusCode(HttpStatus.ACCEPTED.value());

        getBaseRequestWithHeader(mail)
            .delete("/product/" + UUID.randomUUID())
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }


}
