package com.github.nginate.commons.lang;

import org.junit.Test;

import static com.github.nginate.commons.lang.NStrings.format;
import static com.github.nginate.commons.lang.NStrings.formatNamed;
import static org.assertj.core.api.Assertions.assertThat;

public class NStringsTest {

    @Test
    public void testFormat() throws Exception {
        assertThat(format("abc {} asd", "test")).isEqualTo("abc test asd");
        assertThat(format("{} abc asd", "test")).isEqualTo("test abc asd");
        assertThat(format("abc asd {}", "test")).isEqualTo("abc asd test");
        assertThat(format("abc asd {} { {}", "test", "test2")).isEqualTo("abc asd test { test2");
        assertThat(format("abc {", "test")).isEqualTo("abc {");
    }

    @Test
    public void testFormatNamed() throws Exception {
        assertThat(formatNamed("abc {asd} asd", "test")).isEqualTo("abc test asd");
        assertThat(formatNamed("{asd} abc asd", "test")).isEqualTo("test abc asd");
        assertThat(formatNamed("abc asd {asd}", "test")).isEqualTo("abc asd test");
        assertThat(formatNamed("abc asd {asd} { {qwe}", "test", "test2")).isEqualTo("abc asd test test2");
        assertThat(formatNamed("abc { asd {{{{ asd", "test")).isEqualTo("abc { asd {{{{ asd");
    }
}