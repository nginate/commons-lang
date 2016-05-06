/**
 * Copyright © 2016
 * Maksim Lozbin <maksmtua@gmail.com>
 * Oleksii Ihnachuk <legioner.alexei@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
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

/**
 * Utility methods to wrap unchecked and memoize interfaces implicitly creating instances.
 *
 * @since 1.0
 */
@UtilityClass
public class NFunctions {

    /**
     * Wrap supplier to compute and cache its output.
     *
     * @param supplier supplier to wrap
     * @param <T>      output type
     * @return memoized supplier of same type
     * @see SupplierMemoizer
     */
    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return new SupplierMemoizer<T>().doMemoize(supplier);
    }

    /**
     * Wrap function to compute and cache its output
     *
     * @param function function to wrap
     * @param <T>      input type
     * @param <U>      output type
     * @return memoized function of same type
     * @see FunctionMemoizer
     */
    public static <T, U> Function<T, U> memoize(Function<T, U> function) {
        return new FunctionMemoizer<T, U>().doMemoize(function);
    }

    /**
     * Transform unsafe runnable into simple runnable to use with e.g. some legacy code. All checked exceptions will be
     * wrapped in a RuntimeException
     *
     * @param runnable source unsafe runnable
     * @return simple runnable
     * @see NFunctions#unchecked(URunnable, Function)
     */
    public static Runnable unchecked(URunnable runnable) {
        return unchecked(runnable, Throwables::propagate);
    }

    /**
     * Transform unsafe runnable into simple runnable to use with e.g. some legacy code. Specify how to map checked
     * exceptions from unsafe to runtime exceptions
     *
     * @param runnable        source unsafe runnable
     * @param exceptionMapper exception mapping function
     * @return simple runnable
     */
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

    /**
     * Transform unsafe consumer into simple consumer to use with e.g. some legacy code. All checked exceptions will be
     * wrapped in a RuntimeException
     *
     * @param consumer source unsafe consumer
     * @param <T>      consumer type
     * @return simple consumer
     * @see NFunctions#unchecked(URunnable, Function)
     */
    public static <T> Consumer<T> unchecked(UConsumer<T> consumer) {
        return unchecked(consumer, Throwables::propagate);
    }

    /**
     * Transform unsafe consumer into simple consumer to use with e.g. some legacy code. Specify how to map checked
     * exceptions from unsafe to runtime exceptions
     *
     * @param consumer        source unsafe consumer
     * @param exceptionMapper exception mapping function
     * @param <T>             consumer type
     * @return simple consumer
     */
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
