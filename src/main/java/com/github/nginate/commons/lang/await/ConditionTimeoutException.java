package com.github.nginate.commons.lang.await;

public class ConditionTimeoutException extends RuntimeException {
    public ConditionTimeoutException(String message) {
        super(message);
    }
}