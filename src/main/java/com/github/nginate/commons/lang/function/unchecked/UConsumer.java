/**
 * Copyright © 2016
 * Maksim Lozbin <maksmtua@gmail.com>
 * Oleksii Ihnachuk <legioner.alexei@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
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
