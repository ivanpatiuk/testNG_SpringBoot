package org.ivanpatiuk;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MockUtilV3<T> {
    @NonNull
    private DefaultSingletonBeanRegistry registry;
    private T mock;
    private String beanName;

    public static <T> MockUtilV3<T> mock(DefaultSingletonBeanRegistry registry, Class<T> mockType, boolean isSpy) {
        final MockUtilV3<T> mockUtil = new MockUtilV3<>(registry);
        final String className = mockType.getSimpleName();
        mockUtil.mock = isSpy ? Mockito.mock(mockType, Answers.CALLS_REAL_METHODS) : Mockito.mock(mockType);
        mockUtil.beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        return mockUtil;
    }

    public MockUtilV3<T> when(Function<T, ?> whenFunction, Answer<?> answer) {
        Mockito.when(whenFunction.apply(mock)).thenAnswer(answer);
        return this;
    }

    public void build() {
        registry.destroySingleton(beanName);
        registry.registerSingleton(beanName, mock);
    }
}
