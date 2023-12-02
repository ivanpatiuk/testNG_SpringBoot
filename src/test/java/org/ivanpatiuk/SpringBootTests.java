package org.ivanpatiuk;

import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTests extends AbstractTestNGSpringContextTests {

    private final static String BASE_URI = "http://localhost:";
    @LocalServerPort
    private int port;

    @Test
    public void verifyController1()  {
        Response response = given().contentType("application/json")
                .header("Content-Type", "application/json")
                .when().get(BASE_URI + port + "/api/v1/users/1")
                .thenReturn();

        String Actual = response.getBody().asString();
        Assert.assertEquals(response.jsonPath().getString("nickName"), "Nickname73");
    }
}