/**
 * Copyright © 2016
 * Maksim Lozbin <maksmtua@gmail.com>
 * Oleksii Ihnachuk <legioner.alexei@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.nginate.commons.lang.function.memoize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Intermediate operation for any functional flow to cache function results. As example let's assume you have a function
 * that is downloading some file from remote instance, reads first 666 lines and converts them to some dto. Using this
 * wrapper, you can save the results of 'heavy' function and reuse them.
 *
 * @param <T> input type
 * @param <U> result type
 * @since 1.0
 */
public class FunctionMemoizer<T, U> {
    /**
     * Local cache for functions' results
     */
    private final Map<T, U> cache = new ConcurrentHashMap<>();

    /**
     * Try to get already preloaded results of provided function, or compute and save in cache.
     *
     * @param function function to compute something from input
     * @return compute result (from cache if present)
     * @see Map#computeIfAbsent(Object, Function)
     */
    public Function<T, U> doMemoize(Function<T, U> function) {
        return input -> cache.computeIfAbsent(input, function);
    }
}
