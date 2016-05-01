package com.github.nginate.commons.lang.function.unchecked;

/**
 * Default stream API can't handle checked exceptions in functional call, because those interfaces do not throw checked
 * exceptions. This one allows you to say hello to functional programming with old-fashioned checked exceptions
 *
 * @since 1.0
 */
@FunctionalInterface
public interface UConsumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws Exception any checked exception
     */
    void accept(T t) throws Exception;
}
