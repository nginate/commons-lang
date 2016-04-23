package com.github.nginate.commons.lang;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;

import java.util.function.BiFunction;

@SuppressWarnings("WeakerAccess")
@UtilityClass
public class NStrings {

    private static final BiFunction<char[], Integer, Integer> SIMPLE_CLOSE_FINDER = (chars, index) -> {
        int testingIndex = index + 1;
        return chars[testingIndex] == '}' ? testingIndex : -1;
    };

    private static final BiFunction<char[], Integer, Integer> NAMED_CLOSE_FINDER = (chars, index) ->
            ArrayUtils.indexOf(chars, '}', index);

    public static String format(@NonNull String message, Object... args) {
        return formatInternal(message, SIMPLE_CLOSE_FINDER, args);
    }

    public static String formatNamed(@NonNull String template, Object... args) {
        return formatInternal(template, NAMED_CLOSE_FINDER, args);
    }

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
