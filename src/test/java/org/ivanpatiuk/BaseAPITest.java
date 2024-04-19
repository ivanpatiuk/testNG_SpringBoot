package org.ivanpatiuk;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

import java.util.Objects;
import java.util.function.Function;

public abstract class BaseAPITest extends AbstractTransactionalTestNGSpringContextTests {

    @LocalServerPort
    private int port;
    private DefaultSingletonBeanRegistry registry;
    protected String response;

    @BeforeSuite(alwaysRun = true)
    public void prepareTestInstance() throws Exception {
        super.springTestContextPrepareTestInstance();
        registry = (DefaultSingletonBeanRegistry) Objects.requireNonNull(applicationContext).getAutowireCapableBeanFactory();
    }

    public <T> MockUtilV3<T> spy(Class<T> mockType) {
        return MockUtilV3.mock(registry, mockType, true);
    }

    public <T> MockUtilV3<T> mock(Class<T> mockType) {
        return MockUtilV3.mock(registry, mockType, false);
    }

    protected String getResponse(final String query) {
//        return QueryNavigator.getResponse(port, query);
        return null;
    }
}