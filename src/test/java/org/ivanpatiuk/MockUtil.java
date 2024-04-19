package org.ivanpatiuk;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.util.function.Function;


public class MockUtil {

    public static <T> void spy(Object target, Class<T> spyType, Function<T, ?> whenFunction, Answer<?> answer) {
        for (Field declaredField : target.getClass().getDeclaredFields()) {
            if (declaredField.getType() == spyType) {
                spy(target, spyType, declaredField.getName(), whenFunction, answer);
                return;
            }
        }
        throw new RuntimeException("Target class doesn't have field of type " + spyType);
    }

    public static <T> void spy(Object target, Class<T> spyType, String targetFieldName, Function<T, ?> whenFunction, Answer<?> answer) {
        final T spy = Mockito.spy(spyType);
        Mockito.when(whenFunction.apply(spy)).thenAnswer(answer);
        ReflectionTestUtils.setField(target, targetFieldName, spy);
    }
}
