package org.ivanpatiuk;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor
public class SpyUtil<T> {

    @NonNull
    private DefaultSingletonBeanRegistry registry;
    private T spy;
    private String beanName;

    @SuppressWarnings("unchecked")
    public static <T> T spy(Class<T> type){
        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        try {
            constructor.setAccessible(true);
            Object[] parameters = new Object[constructor.getParameterCount()];
            return Mockito.spy((T) constructor.newInstance(parameters));
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            constructor.setAccessible(false);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> SpyUtil<T> spy(DefaultSingletonBeanRegistry registry, Class<T> spyType) {
        final SpyUtil<T> spyUtil = new SpyUtil<>(registry);
        final String className = spyType.getSimpleName();
        spyUtil.beanName = className.substring(0,1).toLowerCase() + className.substring(1);
        try {
            spyUtil.spy = Mockito.spy(spyType);
        } catch (Exception e){
            spyUtil.spy = (T)Mockito.spy(Objects.requireNonNull(registry.getSingleton(spyUtil.beanName)));
        }
        return spyUtil;
    }

    public SpyUtil<T> when(Function<T, ?> whenFunction, Answer<?> answer) {
        Mockito.when(whenFunction.apply(spy)).thenAnswer(answer);
        return this;
    }

    public void build() {
        final String className = spy.getClass().getSimpleName();
        final String beanName = className.substring(0,1).toLowerCase() + className.substring(1);
        registry.destroySingleton(beanName);
        registry.registerSingleton(beanName, spy);
    }
}