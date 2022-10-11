package tests;

import models.LoginBodyPojoModel;
import models.LoginResponsePojoModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresInExtendedTests {

    /*
        1. make POST- request to https://reqres.in/api/login
            with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
        2. get response { "token": "QpwL5tke4Pnpja7X4" }
        3. check token is "QpwL5tke4Pnpja7X4"
     */

    @Test
    void loginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // todo bad practice

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginWithPojoTest() {
        LoginBodyPojoModel body = new LoginBodyPojoModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponsePojoModel response =  given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .as(LoginResponsePojoModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

}
