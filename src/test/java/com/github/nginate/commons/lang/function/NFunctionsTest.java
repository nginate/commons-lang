package com.github.nginate.commons.lang.function;

import com.github.nginate.commons.lang.function.unchecked.RuntimeIOException;
import com.github.nginate.commons.lang.function.unchecked.UConsumer;
import com.github.nginate.commons.lang.function.unchecked.URunnable;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.nginate.commons.lang.function.NFunctions.memoize;
import static com.github.nginate.commons.lang.function.NFunctions.unchecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class NFunctionsTest {
    @Test
    public void testMemoizeSupplier() throws Exception {
        Supplier<String> simpleSupplier = () -> "";
        Supplier<String> memoizedSupplier = memoize(simpleSupplier);

        assertThat(memoizedSupplier)
                .isNotNull()
                .isNotEqualTo(simpleSupplier);
    }

    @Test
    public void testMemoizeFunction() throws Exception {
        Function<Integer, String> simpleFunction = Integer::toHexString;
        Function<Integer, String> memoizedFunction = memoize(simpleFunction);

        assertThat(memoizedFunction)
                .isNotNull()
                .isNotEqualTo(simpleFunction);
    }

    @Test
    public void testUncheckedRunnable() throws Exception {
        URunnable uRunnable = () -> {};
        Runnable runnable = unchecked(uRunnable);

        assertThat(runnable)
                .isNotNull()
                .isNotEqualTo(uRunnable);
    }

    @Test
    public void testUncheckedRunnableWithMapper() throws Exception {
        Class<? extends Exception> exceptionClass = IOException.class;
        Class<? extends RuntimeException> mappedExceptionClass = RuntimeIOException.class;

        Function<Exception, RuntimeException> function = mock(Function.class);
        URunnable uRunnable = mock(URunnable.class);

        doThrow(exceptionClass).when(uRunnable).run();
        when(function.apply(isA(exceptionClass))).thenReturn(mappedExceptionClass.newInstance());

        Runnable runnable = unchecked(uRunnable, function);
        assertThatThrownBy(runnable::run).isExactlyInstanceOf(mappedExceptionClass);

        assertThat(runnable)
                .isNotNull()
                .isNotEqualTo(uRunnable);

        verify(uRunnable).run();
        verify(function).apply(isA(exceptionClass));
    }

    @Test
    public void testUncheckedConsumer() throws Exception {
        UConsumer<String> uConsumer = System.out::println;
        Consumer<String> consumer = unchecked(uConsumer);

        assertThat(consumer)
                .isNotNull()
                .isNotEqualTo(uConsumer);
    }

    @Test
    public void testUncheckedConsumerWithMapper() throws Exception {
        Class<? extends Exception> exceptionClass = IOException.class;
        Class<? extends RuntimeException> mappedExceptionClass = RuntimeIOException.class;

        Function<Exception, RuntimeException> function = mock(Function.class);
        UConsumer<String> uConsumer = mock(UConsumer.class);
        doThrow(exceptionClass).when(uConsumer).accept(isA(String.class));
        when(function.apply(isA(exceptionClass))).thenReturn(mappedExceptionClass.newInstance());

        Consumer<String> consumer = unchecked(uConsumer, function);

        assertThatThrownBy(() ->  consumer.accept("")).isExactlyInstanceOf(mappedExceptionClass);
        assertThat(consumer)
                .isNotNull()
                .isNotEqualTo(uConsumer);

        verify(uConsumer).accept(isA(String.class));
        verify(function).apply(isA(exceptionClass));
    }
}
