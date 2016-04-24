package com.github.nginate.commons.lang.function;

import com.github.nginate.commons.lang.function.memoize.FunctionMemoizer;
import com.github.nginate.commons.lang.function.memoize.SupplierMemoizer;
import com.github.nginate.commons.lang.function.unchecked.UConsumer;
import com.github.nginate.commons.lang.function.unchecked.URunnable;
import com.google.common.base.Throwables;
import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@UtilityClass
public class NFunctions {

    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return new SupplierMemoizer<T>().doMemoize(supplier);
    }

    public static <T, U> Function<T, U> memoize(Function<T, U> function) {
        return new FunctionMemoizer<T, U>().doMemoize(function);
    }

    public static Runnable unchecked(URunnable runnable) {
        return unchecked(runnable, Throwables::propagate);
    }

    public static Runnable unchecked(URunnable runnable,
            Function<Exception, RuntimeException> exceptionMapper) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw exceptionMapper.apply(e);
            }
        };
    }

    public static <T> Consumer<T> unchecked(UConsumer<T> consumer) {
        return unchecked(consumer, Throwables::propagate);
    }

    public static <T> Consumer<T> unchecked(UConsumer<T> consumer,
            Function<Exception, RuntimeException> exceptionMapper) {
        return input -> {
            try {
                consumer.accept(input);
            } catch (Exception e) {
                throw exceptionMapper.apply(e);
            }
        };
    }
}
