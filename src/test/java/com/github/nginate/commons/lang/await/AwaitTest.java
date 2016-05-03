package com.github.nginate.commons.lang.await;

import com.google.common.base.Stopwatch;
import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.github.nginate.commons.lang.await.Await.waitUntil;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AwaitTest {
    @Mock
    private Callable<Boolean> callable;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void waitUntilWithSuccess() throws Exception {
        doReturn(true).when(callable).call();
        waitUntil(1000, callable);
        verify(callable).call();
    }

    @Test
    public void waitUntilWithFailure() throws Exception {
        doReturn(false).when(callable).call();
        assertThatThrownBy(() ->  waitUntil(300, callable)).isExactlyInstanceOf(ConditionTimeoutException.class);
    }

    @Test
    public void waitUntilWithZeroTimeout() throws Exception {
        assertThatThrownBy(() ->  waitUntil(0, 0, callable)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void waitUntilWithNegativeTimeout() throws Exception {
        assertThatThrownBy(() ->  waitUntil(-100, callable)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void waitUntilWithZeroStep() throws Exception {
        assertThatThrownBy(() ->  waitUntil(1000, 0, callable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void waitUntilWithNegativeStep() throws Exception {
        assertThatThrownBy(() ->  waitUntil(1000, -100, callable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void waitUntilWithSuccessWithStepEqualToTimeout() throws Exception {
        doReturn(true).when(callable).call();
        waitUntil(1000, 1000, callable);
        verify(callable).call();
    }

    @Test
    public void waitUntilWithStepGreaterThanTimeout() throws Exception {
        assertThatThrownBy(() ->  waitUntil(1000, 2000, callable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void checkFailureMessage() throws Exception {
        String message = "message";
        doReturn(false).when(callable).call();
        assertThatThrownBy(() ->  waitUntil(300, message, callable))
                .isExactlyInstanceOf(ConditionTimeoutException.class)
                .hasMessage(message);
    }

    @Test
    public void checkExceptionPropagation() throws Exception {
        Class<? extends Exception> exceptionClass = IOException.class;
        doThrow(exceptionClass).when(callable).call();

        assertThatThrownBy(() ->  waitUntil(300, callable))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasCauseExactlyInstanceOf(exceptionClass);
    }

    @Test
    public void checkWaitTimeout() throws Exception {
        int timeout = 1000;
        doReturn(false).when(callable).call();

        Stopwatch stopwatch = Stopwatch.createStarted();
        assertThatThrownBy(() ->  waitUntil(timeout, callable)).isExactlyInstanceOf(ConditionTimeoutException.class);
        stopwatch.stop();

        assertThat(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                .isGreaterThanOrEqualTo(timeout);
    }

    @Test
    public void checkWaitCallIterations() throws Exception {
        int timeout = 100;
        int step = 100;
        int count = timeout / step;

        doReturn(false).when(callable).call();
        assertThatThrownBy(() ->  waitUntil(timeout, step, callable))
                .isExactlyInstanceOf(ConditionTimeoutException.class);
        verify(callable, atMost(count+1)).call();
        verify(callable, atLeast(count)).call();
    }
}
