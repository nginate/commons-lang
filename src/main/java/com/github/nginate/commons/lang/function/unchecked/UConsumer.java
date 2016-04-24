package com.github.nginate.commons.lang.function.unchecked;

@FunctionalInterface
public interface UConsumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws Exception;
}
