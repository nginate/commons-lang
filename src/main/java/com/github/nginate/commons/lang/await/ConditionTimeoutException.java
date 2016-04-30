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
