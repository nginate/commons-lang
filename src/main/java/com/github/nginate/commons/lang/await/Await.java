package com.github.nginate.commons.lang.await;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Callable;

import static com.google.common.base.Throwables.propagate;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@UtilityClass
public final class Await {

    private static final int DEFAULT_STEP_MILLIS = 200;

    public static void waitUntil(int timeout, Callable<Boolean> condition) {
        waitUntil(timeout, DEFAULT_STEP_MILLIS, condition);
    }

    public static void waitUntil(int timeout, int waitStepMillis, Callable<Boolean> condition) {
        waitUntil(timeout, waitStepMillis, "Waiting for condition timed out", condition);
    }

    public static void waitUntil(int timeout, String failureMessage, Callable<Boolean> condition) {
        waitUntil(timeout, DEFAULT_STEP_MILLIS, failureMessage, condition);
    }

    public static void waitUntil(int timeout, int waitStepMillis, String failureMessage, Callable<Boolean> condition) {
        Preconditions.checkArgument(waitStepMillis <= timeout, "step sleep time must be less or equal to timeout");

        Stopwatch stopwatch = Stopwatch.createStarted();
        while (stopwatch.elapsed(MILLISECONDS) < timeout) {
            try {
                if (condition.call()) {
                    return;
                }
                sleep(waitStepMillis);
            } catch (Exception e) {
                throw propagate(e);
            }
        }
        throw new ConditionTimeoutException(failureMessage);
    }
}
