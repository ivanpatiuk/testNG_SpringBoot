package org.ivanpatiuk;

import io.restassured.response.Response;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.ivanpatiuk.MockUtil.spy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockTest extends AbstractTestNGSpringContextTests {

    private final static String BASE_URI = "http://localhost:";
    @SpyBean
    private UserRepository userRepository;
    @LocalServerPort
    private int port;

    @Test
    public void controllerMockTest() {
        Mockito.when(userRepository.findUserById(1L))
                .thenReturn(UserDTO.builder().nickName("Mocked73").email("mocked@gmail.com").build());

        Response response = given().contentType("application/json")
                .header("Content-Type", "application/json")
                .when().get(BASE_URI + port + "/api/v1/users/1")
                .thenReturn();

        Assert.assertEquals(response.jsonPath().getString("nickName"), "Mocked73");
    }

    @Test
    public void mockTest(){
        UserRepository repository = Mockito.spy();
        Mockito.when(repository.findUserById(1L)).thenReturn(SpyUtil.spy(UserDTO.class));
        Mockito.when(repository.findUserById(1L).getEmail()).thenReturn("hello");
        String actual = repository.findUserById(1L).getEmail();

        Assert.assertEquals(actual, "hello");
    }
}