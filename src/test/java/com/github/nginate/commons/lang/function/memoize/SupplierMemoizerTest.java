package com.github.nginate.commons.lang.function.memoize;

import org.junit.Test;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SupplierMemoizerTest {

    @Test
    public void checkCaching() throws Exception {
        Supplier<String> supplier = mock(Supplier.class);
        Supplier<String> memoized = new SupplierMemoizer<String>().doMemoize(supplier);

        when(supplier.get()).thenReturn(Long.toHexString(new Random().nextLong()));

        String initRandomValue = memoized.get();
        IntStream.range(0, 30).forEach(value -> {
            String computed = memoized.get();
            assertThat(computed).isNotNull().isEqualTo(initRandomValue);
        });

        verify(supplier, times(1)).get();
    }
}
