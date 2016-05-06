/**
 * Copyright © 2016
 * Maksim Lozbin <maksmtua@gmail.com>
 * Oleksii Ihnachuk <legioner.alexei@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.nginate.commons.lang.await;

/**
 * Exception to be thrown if wait condition was not satisfied in provided period
 *
 * @since 1.0
 */
public class ConditionTimeoutException extends RuntimeException {
    public ConditionTimeoutException(String message) {
        super(message);
    }
}
