package org.ivanpatiuk;

import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReflectionMockTest extends AbstractTestNGSpringContextTests {

    private final static String BASE_URI = "http://localhost:";
    @Autowired
    private UserServiceImpl userService;
    @LocalServerPort
    private int port;

    @Test
    public void controllerMockTest() {
        MockUtil.spy(
                userService,
                UserRepository.class,
                when -> when.findUserById(1L),
                answer -> UserDTO.builder().nickName("Mocked73").email("mocked@gmail.com").build());

        Response response = given().contentType("application/json")
                .header("Content-Type", "application/json")
                .when().get(BASE_URI + port + "/api/v1/users/1")
                .thenReturn();

        Assert.assertEquals(response.jsonPath().getString("nickName"), "Mocked73");
    }
}