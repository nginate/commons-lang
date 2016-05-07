package com.github.nginate.commons.lang.function;

import com.github.nginate.commons.lang.function.unchecked.RuntimeIOException;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.nginate.commons.lang.NStrings.format;
import static com.github.nginate.commons.lang.function.NFunctions.memoize;
import static com.github.nginate.commons.lang.function.NFunctions.unchecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NFunctionsTest {

    @Test
    public void testMemoizeSupplier() throws Exception {
        AtomicInteger counter = new AtomicInteger();

        Supplier<Integer> memoizedSupplier = memoize(counter::incrementAndGet);

        assertThat(memoizedSupplier.get()).isEqualTo(1);
        // counter shouldn't be called second time
        assertThat(memoizedSupplier.get()).isEqualTo(1);
    }

    @Test
    public void testMemoizeFunction() throws Exception {
        AtomicInteger counter = new AtomicInteger();

        Function<String, String> memoizedFunction = memoize(key -> format("{}: {}", key, counter.incrementAndGet()));

        assertThat(memoizedFunction.apply("key1")).isEqualTo("key1: 1");
        // counter shouldn't be called second time for same input
        assertThat(memoizedFunction.apply("key1")).isEqualTo("key1: 1");

        // but should for different inputs
        assertThat(memoizedFunction.apply("key2")).isEqualTo("key2: 2");
        assertThat(memoizedFunction.apply("key3")).isEqualTo("key3: 3");
    }

    @Test
    public void testUncheckedRunnable() throws Exception {
        Runnable runnable = unchecked(() -> {
            throw new IOException();
        });

        assertThatThrownBy(runnable::run).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void testUncheckedRunnableWithMapper() throws Exception {
        Runnable runnable = unchecked(() -> {
            throw new IOException();
        }, RuntimeIOException::new);

        assertThatThrownBy(runnable::run).isExactlyInstanceOf(RuntimeIOException.class);
    }

    @Test
    public void testUncheckedConsumer() throws Exception {
        Consumer<String> consumer = unchecked(s -> {
            throw new IOException();
        });

        assertThatThrownBy(() -> consumer.accept("")).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void testUncheckedConsumerWithMapper() throws Exception {
        Consumer<String> consumer = unchecked(s -> {
            throw new IOException();
        }, RuntimeIOException::new);

        assertThatThrownBy(() -> consumer.accept("")).isExactlyInstanceOf(RuntimeIOException.class);
    }
}
