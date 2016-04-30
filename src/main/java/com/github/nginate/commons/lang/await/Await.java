package com.github.nginate.commons.lang.await;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Callable;

import static com.google.common.base.Throwables.propagate;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Better waiting for required periodically polling for result and not just sleeping for some hardcoded period
 *
 * @since 1.0
 */
@UtilityClass
public final class Await {

    /**
     * Default interval for conditions checks
     */
    private static final int DEFAULT_STEP_MILLIS = 200;

    /**
     * Wait till the condition will become true during at most {@code timeout} millis. Will be checking the condition
     * each {@link Await#DEFAULT_STEP_MILLIS} millis.
     *
     * @param timeout   max wait millis
     * @param condition required condition
     * @throws ConditionTimeoutException if condition was not satisfied in configured period
     * @see Await#waitUntil(int, int, Callable)
     */
    public static void waitUntil(int timeout, Callable<Boolean> condition) {
        waitUntil(timeout, DEFAULT_STEP_MILLIS, condition);
    }

    /**
     * Wait till the condition will become true during at most {@code timeout} millis. Will be checking the condition
     * each {@code waitStepMillis} millis.
     *
     * @param timeout        max wait millis
     * @param waitStepMillis step wait millis
     * @param condition      required condition
     * @throws ConditionTimeoutException if condition was not satisfied in configured period
     * @throws IllegalArgumentException  if step millis >= overall wait millis
     * @see Await#waitUntil(int, int, String, Callable)
     */
    public static void waitUntil(int timeout, int waitStepMillis, Callable<Boolean> condition) {
        waitUntil(timeout, waitStepMillis, "Waiting for condition timed out", condition);
    }

    /**
     * Wait till the condition will become true during at most {@code timeout} millis. Will be checking the condition
     * each {@link Await#DEFAULT_STEP_MILLIS} millis.
     *
     * @param timeout        max wait millis
     * @param failureMessage message to see if waiting fails
     * @param condition      required condition
     * @throws ConditionTimeoutException if condition was not satisfied in configured period
     * @throws IllegalArgumentException  if step millis >= overall wait millis
     * @see Await#waitUntil(int, int, String, Callable)
     */
    public static void waitUntil(int timeout, String failureMessage, Callable<Boolean> condition) {
        waitUntil(timeout, DEFAULT_STEP_MILLIS, failureMessage, condition);
    }

    /**
     * Wait till the condition will become true during at most {@code timeout} millis. Will be checking the condition
     * each {@code waitStepMillis} millis.
     *
     * @param timeout        max wait millis
     * @param waitStepMillis step wait millis
     * @param failureMessage message to see if waiting fails
     * @param condition      required condition
     * @throws ConditionTimeoutException if condition was not satisfied in configured period
     * @throws IllegalArgumentException  if step millis >= overall wait millis
     */
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
