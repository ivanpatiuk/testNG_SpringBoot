package org.ivanpatiuk;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.function.Function;

import org.mockito.Answers;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class MockUtilV2<T> {

    @NonNull
    private DefaultSingletonBeanRegistry registry;
    private T mock;
    private String beanName;

    public static <T> MockUtilV2<T> spy(DefaultSingletonBeanRegistry registry, Class<T> spyType) {
        return getInstance(registry, spyType, true);
    }

    public static <T> MockUtilV2<T> mock(DefaultSingletonBeanRegistry registry, Class<T> mockType) {
        return getInstance(registry, mockType, false);
    }

    private static <T> MockUtilV2<T> getInstance(DefaultSingletonBeanRegistry registry, Class<T> mockType, boolean isSpy) {
        final MockUtilV2<T> spyUtil = new MockUtilV2<>(registry);
        final String className = mockType.getSimpleName();
        spyUtil.mock = isSpy ? Mockito.mock(mockType, Answers.CALLS_REAL_METHODS) : Mockito.mock(mockType);
        spyUtil.beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        return spyUtil;
    }

    public MockUtilV2<T> when(Function<T, ?> whenFunction, Answer<?> answer) {
        Mockito.when(whenFunction.apply(mock)).thenAnswer(answer);
        return this;
    }

    public void build() {
        registry.destroySingleton(beanName);
        registry.registerSingleton(beanName, mock);
    }
}