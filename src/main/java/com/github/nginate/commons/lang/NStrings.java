/**
 * Copyright © 2016
 * Maksim Lozbin <maksmtua@gmail.com>
 * Oleksii Ihnachuk <legioner.alexei@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.nginate.commons.lang;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;

import java.util.function.BiFunction;

/**
 * Basically, this utility should cover everything related to string manipulation
 *
 * @since 1.0
 */
@SuppressWarnings("WeakerAccess")
@UtilityClass
public class NStrings {

    private static final BiFunction<char[], Integer, Integer> SIMPLE_CLOSE_FINDER = (chars, index) -> {
        int testingIndex = index + 1;
        return chars[testingIndex] == '}' ? testingIndex : -1;
    };

    private static final BiFunction<char[], Integer, Integer> NAMED_CLOSE_FINDER = (chars, index) ->
            ArrayUtils.indexOf(chars, '}', index);

    /**
     * Create string from provided template and args. Placeholders should consist of '{' a a start symbol and '}' as
     * ending symbol. Everything in between will be replaced with a provided arg at required position.
     *
     * Be sure to have appropriate string representation in provided args (aka. overridden toString())
     *
     * @param message string template with placeholders
     * @param args args to inject in template
     * @return plain string with injected args
     */
    public static String format(@NonNull String message, Object... args) {
        return formatInternal(message, SIMPLE_CLOSE_FINDER, args);
    }

    /**
     * Same purpose as in {@link NStrings#format(String, Object...)}, except all placeholders are named - you can use
     * {@code '{arg1} some {arg2} words {alsoArg}'} instead of simple '{}'
     *
     * Args will be injected in same order, they occur in array. It really does not matter what is written in
     * placeholders. This was originally made to use in JAX-RS {@code @Path} annotations.
     *
     * @param template string template with placeholders
     * @param args args to inject in template
     * @return plain string with injected args
     */
    public static String formatNamed(@NonNull String template, Object... args) {
        return formatInternal(template, NAMED_CLOSE_FINDER, args);
    }

    /**
     * Internal template generator. Consumes one of function for simple ('{}') or named placeholder ('{arg1}') template
     * @param template string template with placeholders
     * @param closeFinder function to determine end symbol for a placeholder
     * @param args args to inject in template
     * @return plain string with injected args
     */
    private static String formatInternal(String template,
                                         BiFunction<char[], Integer, Integer> closeFinder,
                                         Object... args) {
        StringBuilder sb = new StringBuilder(template.length() * 2);
        int index = 0;
        int argIndex = 0;
        char[] chars = template.toCharArray();
        while (index < template.length()) {
            char curChar = chars[index];
            if (index < template.length() - 1 && curChar == '{') {
                int closeIndex = closeFinder.apply(chars, index);
                if (closeIndex > 0) {
                    sb.append(args[argIndex]);
                    argIndex++;
                    index = closeIndex + 1;
                } else {
                    sb.append(curChar);
                    index++;
                }
            } else {
                sb.append(curChar);
                index++;
            }
        }
        return sb.toString();
    }
}
