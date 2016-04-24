package com.github.nginate.commons.lang.function.memoize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class FunctionMemoizer<T, U> {
    private final Map<T, U> cache = new ConcurrentHashMap<>();

    public Function<T, U> doMemoize(Function<T, U> function) {
        return input -> cache.computeIfAbsent(input, function);
    }
}
