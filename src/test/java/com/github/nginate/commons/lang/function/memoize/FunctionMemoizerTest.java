package com.github.nginate.commons.lang.function.memoize;

import org.junit.Test;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionMemoizerTest {
    @Test
    public void checkCaching() throws Exception {
        Function<String, Long> function = s -> new Random().nextLong();
        Function<String, Long> memoized = new FunctionMemoizer<String, Long>().doMemoize(function);

        String input = "";
        Long initRandomValue = memoized.apply(input);
        IntStream.range(0, 30).forEach(value -> {
            Long computed = memoized.apply(input);
            assertThat(computed).isEqualTo(initRandomValue);
        });
    }

    @Test
    public void checkNoCachingOnDifferentInput() throws Exception {
        Function<String, Long> function = s -> new Random().nextLong();
        Function<String, Long> memoized = new FunctionMemoizer<String, Long>().doMemoize(function);

        String input = "";
        Long initRandomValue = memoized.apply(input);
        IntStream.range(0, 30).forEach(value -> {
            Long computed = memoized.apply(input + value);
            assertThat(computed).isNotEqualTo(initRandomValue);
        });
    }
}
