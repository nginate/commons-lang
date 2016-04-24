package com.github.nginate.commons.lang.function.memoize;

import java.util.function.Supplier;

public class SupplierMemoizer<T> {
    private volatile T value;
    private final Object monitor = new Object();

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
