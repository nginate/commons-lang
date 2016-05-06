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

import java.util.function.Supplier;

/**
 * Supplier with local cache to save and reuse output of a 'heavy' supplier.
 *
 * @param <T> type of output
 * @since 1.0
 */
public class SupplierMemoizer<T> {
    /**
     * Cached value of original supplier
     */
    private volatile T value;
    /**
     * Monitor object to synchronize cache initialization
     */
    private final Object monitor = new Object();

    /**
     * Wrap your supplier to compute its output once and reuse same cached value among multiple accessors.
     *
     * @param supplier original supplier to use for value computing
     * @return precomputed value from cache
     */
    public Supplier<T> doMemoize(Supplier<T> supplier) {
        return () -> {
            if (value == null) {
                synchronized (monitor) {
                    if (value == null) {
                        value = supplier.get();
                    }
                }
            }
            return value;
        };
    }
}
