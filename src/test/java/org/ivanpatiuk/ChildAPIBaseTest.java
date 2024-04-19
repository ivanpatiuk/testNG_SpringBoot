package org.ivanpatiuk;

import org.testng.annotations.BeforeSuite;

import static org.mockito.ArgumentMatchers.any;

public class ChildAPIBaseTest extends BaseAPITest {

    @BeforeSuite(alwaysRun = true)
    public void prepareMocks() {
        prepareUserRepository();
    }

    private void prepareUserRepository() {
        spy(UserRepository.class)
                .when(repository -> repository.findUserById(any()),
                        invocationOnMock -> new UserDTO("",""))
                .build();
    }
}
